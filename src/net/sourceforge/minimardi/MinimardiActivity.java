/*
 * Copyright (C) 2011 Juan Pablo Fernandez
 *
 */
package net.sourceforge.minimardi;

import java.util.ArrayList;
import java.util.Hashtable;

import com.admob.android.ads.AdManager;
import com.admob.android.ads.AdView;

import minimardi.Move;
import minimardi.Piece;
import minimardi.Position;
import minimardi.Search;
import minimardi.Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MinimardiActivity extends Activity implements OnTouchListener{

    /** JNI class exposing minimardi native code **/
    private Position mPosition;

    /** Always start as white. Switch sides is used to change color. **/
    private char mPlayerSide;
    
    /** Settings **/
    private boolean mPlayBothSides;
    private int mLevel;
    
    /** GUI board is a table with each square being a frame. 
     * Each frame contains 2 ImageViews. One for the piece and one for the bg.
     * The name of the frame is "f" + algebraic notation (fa1..fh8)
     * **/    
    private FrameLayout mSelectedSrcFrameLayout;
    private FrameLayout mSelectedDstFrameLayout;
    private ImageView mSelectedSrcImageView;
    private ImageView mSelectedDstImageView;
    private String mScoreSheet;
    
    /** State **/
    private volatile int mState;
    private static final int STATE_COMPUTER_THINKING=0;
    private static final int STATE_WAITING_SELECT_PROMOTION=1;
    private static final int STATE_WAITING_PLAYER_MOVE=2;
    private static final int STATE_GAME_OVER=3;
    private static final int STATE_RESTORE=4;
       
    /** Actions that change the state **/
    private static final int ACTION_MOVE_MADE=0;
    private static final int ACTION_UNDO_MOVE=1;
    private static final int ACTION_NEW_GAME=2;
    private static final int ACTION_SETTINGS_CHANGED=3;
    private static final int ACTION_SWITCH_SIDES=4;
    
    private int mPromotionMove;
    private ArrayList<TakebackMove> mMoves;
    private ComputerMoveTask mCompTask=null;
    
    /** Debug - play engine vs engine **/
    private static final int DEBUG_MAX_MOVES=150;
    private boolean mDebug=false;

    /** GUI references for promotion **/
    private ImageView mImageViewWhiteQueen;
    private ImageView mImageViewWhiteRook;
    private ImageView mImageViewWhiteBishop;
    private ImageView mImageViewWhiteKnight;

    private ImageView mImageViewBlackQueen;
    private ImageView mImageViewBlackRook;
    private ImageView mImageViewBlackBishop;
    private ImageView mImageViewBlackKnight;
    
    /** Map view ids to square as a string (a1...h8) **/
    private Hashtable<Integer, String> mSquares = new Hashtable<Integer, String>();    
    private static final String TAG = "minimardi";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);        
        setupStartPosition();         
//        mCompTask = (ComputerMoveTask) getLastNonConfigurationInstance();               
        restorePosition();
        
//        AdManager.setTestDevices( new String[] { AdManager.TEST_EMULATOR } );
        AdView adView = (AdView)findViewById(R.id.ad);        
        if (adView != null)
            adView.requestFreshAd();        
    }

    /**
     * Init everything. Draw the initial starting position with white on the move.
     * Player always starts as white.
     */
    private void setupStartPosition() {                
        mPosition = new Position();
        mMoves = new ArrayList<TakebackMove>();
        mPlayerSide = Piece.WHITE;
        mState = STATE_WAITING_PLAYER_MOVE;
//        mPlayBothSides=false;
        mPromotionMove = 0;
        mScoreSheet = "";
        mCompTask = null;
        setContentView(R.layout.main);

        // Loop over the board and save ids of all squares (frames)
        // for fast lookup. Also register onTouch callback for all 64 squares.
        for (int i=0; i<8; i++){
            for (int j=1; j<9; j++){               
                String square = new Character((char)('a'+i)).toString() + Integer.toString(j);
                int id = this.getResources().getIdentifier("f" + square, "id", this.getPackageName());
                FrameLayout frame = (FrameLayout) findViewById(id);
                mSquares.put(new Integer(id), square);
                frame.setOnTouchListener(this);
            }
        }

        // Highlight source square Image
        mSelectedSrcImageView = (ImageView) findViewById(R.id.selectedSquare);
        FrameLayout selectedImageParent = (FrameLayout) mSelectedSrcImageView.getParent();
        selectedImageParent.removeView(mSelectedSrcImageView);

        // Highlight destination square Image        
        mSelectedDstImageView = new ImageView(this);                                        
        mSelectedDstImageView.setLayoutParams(mSelectedSrcImageView.getLayoutParams());                                       
        mSelectedDstImageView.setImageDrawable(mSelectedSrcImageView.getDrawable());                                      
        
        // Reference to pieces to copy on promotion (white)
        mImageViewWhiteQueen = (ImageView) findViewById(R.id.whiteQueen);
        mImageViewWhiteRook = (ImageView) findViewById(R.id.whiteRook);
        mImageViewWhiteBishop = (ImageView) findViewById(R.id.whiteBishop);
        mImageViewWhiteKnight = (ImageView) findViewById(R.id.whiteKnight);

        // Reference to pieces to copy on promotion (black)
        mImageViewBlackQueen = (ImageView) findViewById(R.id.blackQueen);
        mImageViewBlackRook = (ImageView) findViewById(R.id.blackRook);
        mImageViewBlackBishop = (ImageView) findViewById(R.id.blackBishop);
        mImageViewBlackKnight = (ImageView) findViewById(R.id.blackKnight);     
        
        mSelectedSrcFrameLayout=null;
        mSelectedDstFrameLayout=null;
    }
    
    /**
     * Restore by replaying the game.
     */
    private void restorePosition() {
        SharedPreferences prefs = getPreferences(0);
//        mDebug = prefs.getBoolean("DEBUG", false);
        String pgn = prefs.getString("PGN", null);        
        if (pgn != null && pgn.length() > 0) {
            mState = STATE_RESTORE;
            String[] moves = pgn.split("\\s+");
            for (int i=0; i < moves.length; i++) {
                int move = Move.algToMove(moves[i], mPosition.getToMove());
                synchronized (mPosition) {
                    move = mPosition.legalOpponentMove(move);                    
                }
                if (move != 0)
                    doComputerMove(move);
                else {
                    Log.w(TAG, "Stored move is illegal.");
                }
            }
            mPlayerSide = (char) prefs.getInt("PLAYERSIDE", mPlayerSide);
            if (mPlayerSide == Piece.BLACK && !mDebug) {                
                mPlayerSide = Piece.WHITE;
                onSwitchSides();
            }
            mState = prefs.getInt("STATE", mState);
        }               
    }
    
    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mPlayBothSides = !sharedPrefs.getBoolean("engine_on", !mPlayBothSides);
        mLevel = Integer.parseInt( sharedPrefs.getString("engine_strength", "2") ); 
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        changeState(ACTION_SETTINGS_CHANGED);
    }
    
//    @Override
//    public Object onRetainNonConfigurationInstance() {
//        return mCompTask;
//    }
    
    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
        cancelComputerMove();
        SharedPreferences.Editor editor = getPreferences(0).edit();
        editor.putInt("STATE", mState);
        editor.putInt("PLAYERSIDE", mPlayerSide);
        editor.putString("PGN", mScoreSheet);         
//        editor.putBoolean("DEBUG", mDebug);
        editor.commit();
    }
    
    private void cancelComputerMove() {
        if (mCompTask != null) {
            if (!mCompTask.cancel(false)) {
                Log.w(TAG, "Computer task not cancelled");               
            }
            mCompTask=null;
        }        
    }
    
    /**
     * Get minimardi internal move from two squares (algebraic notation). 
     * @param srcSquare
     * @param dstSquare
     * @return 0 if this move is illegal else a legal move that can be given to Position.doMove.
     */
    private int getLegalMove(FrameLayout srcSquare, FrameLayout dstSquare) {
        String algMove = mSquares.get(srcSquare.getId()) + mSquares.get(dstSquare.getId());
        int move = Move.algToMove(algMove, mPosition.getToMove());
        move = mPosition.legalOpponentMove(move);
        return move;
    }

    /**
     * Computer to move.
     * @return
     */
    private boolean isComputerToMove() {                
        if (mPosition.getToMove()==mPlayerSide)
            return false;
        return true;
    }
    
    /**
     * Don't change state when waiting for computer to make a move.
     * Or when waiting for the user to select a piece to promote.
     * @return
     */
    private boolean isChangeStateAllowed() {
        return 
            mState != STATE_COMPUTER_THINKING && 
            mState != STATE_WAITING_SELECT_PROMOTION;
    }

    /**
     * Change internal state from a action such as a move made, settings changed etc.
     * @param action
     */
    private void changeState(int action) {
        if (mState == STATE_RESTORE)
            return;        
        boolean gameOver=mPosition.isGameOver();
                
        switch(action)
        {
        case ACTION_MOVE_MADE:
            if (mDebug && (mMoves.size() >= DEBUG_MAX_MOVES)){
                mState = STATE_GAME_OVER;
            }
            if (!mPlayBothSides && isComputerToMove() && !gameOver) {
                //Let computer think ...
                mState=STATE_COMPUTER_THINKING;
                if (mCompTask == null) { 
                    mCompTask = new ComputerMoveTask();
                    mCompTask.execute(mPosition, mLevel);
                }
            } else if (gameOver) {
                mState=STATE_GAME_OVER;
            } else if (mPlayBothSides) {
                mState=STATE_WAITING_PLAYER_MOVE;
            } else if (!isComputerToMove()) {
                mState=STATE_WAITING_PLAYER_MOVE;
            }
                
            break;
        case ACTION_NEW_GAME:
            break;
        case ACTION_SETTINGS_CHANGED:            
        case ACTION_SWITCH_SIDES:
            if (!mPlayBothSides && !gameOver && isComputerToMove()) {
                //Let computer think ...
                if (mDebug && (mMoves.size() >= DEBUG_MAX_MOVES)){
                    mState = STATE_GAME_OVER;
                    return;
                }

                mState=STATE_COMPUTER_THINKING;
                if (mCompTask == null) {                
                    mCompTask = new ComputerMoveTask();
                    mCompTask.execute(mPosition, mLevel);
                }
            }                                
            break;
        case ACTION_UNDO_MOVE:
            mState=STATE_WAITING_PLAYER_MOVE;
            break;
        }        
    }
    
    /**
     * Update score sheet on undo.
     * @param move
     */
    private void doUndoScoreSheet(int move) {
        String str = mScoreSheet;        
        int index = str.lastIndexOf(" ");
        if (index != -1)
            str=str.substring(0, index);
        index = str.lastIndexOf(" ");
        
        mScoreSheet = ((index == -1) ? "": str.substring(0, index + 1));
    }
    
    /**
     * Update score sheet on a made move.
     * @param move
     */
    private void doUpdateScoreSheet(int move) {
        String str = Move.moveToAlg(move);
        str = str.replaceAll("\\n", "");
        mScoreSheet += (str + " ");
    }
    
    /**
     * Make a computer move.
     * @param move
     */
    private void doComputerMove(int move) {
        String algMove = Move.moveToAlg(move);
        String src = algMove.substring(0, 2);
        String dst = algMove.substring(2, 4);
      
        int idSrc = this.getResources().getIdentifier("f" + src, "id", this.getPackageName());
        int idDst = this.getResources().getIdentifier("f" + dst, "id", this.getPackageName());
        
        FrameLayout srcSquare = (FrameLayout) findViewById(idSrc);
        FrameLayout dstSquare = (FrameLayout) findViewById(idDst);

        doPlayerMove(srcSquare, dstSquare, move); 
        
        mCompTask = null;
        
        if (mDebug) {
            mPlayerSide = (mPlayerSide==Piece.WHITE) ? Piece.BLACK: Piece.WHITE;
            changeState(ACTION_SETTINGS_CHANGED);        
        }
    }

    /**
     * Move a piece graphically on the screen.
     * Invalidates src and dst frames.
     */
    private void doMovePiece(String algMove) {
        String src = algMove.substring(0, 2);
        String dst = algMove.substring(2, 4);

        // Get the piece
        int pieceId = getResources().getIdentifier("f"+src, "id", this.getPackageName());
        FrameLayout pieceSrcFrame = (FrameLayout) findViewById(pieceId);
        ImageView pieceView = (ImageView) pieceSrcFrame.getChildAt(1);

        // Remove the piece from src
        // TODO: Animation
        pieceSrcFrame.removeViewAt(1);

        // Get the dst frame
        int pieceDstId = getResources().getIdentifier("f" + dst, "id", this.getPackageName());
        FrameLayout pieceDstFrame = (FrameLayout) findViewById(pieceDstId);

        // Check if dst also has a piece and remove it.
        if (pieceDstFrame.getChildCount() > 1) {
            pieceDstFrame.removeViewAt(1);
        }

        // Add piece to dst
        pieceDstFrame.addView(pieceView);

        // Invalidate both src and dst
        pieceSrcFrame.invalidate();
        pieceDstFrame.invalidate();
    }
    
    /** This would be really nice. Looping over the board and just looking where the pieces are.
     * If we would do this after every single move, could probably be too slow..
     * Leaving it here showing how to loop over 0x88 chess board.
     * 
    private void setupPositionFromFEN() { 
        Board board = mPosition.getBoard();
        int rank=1;
        int file=0;
        
        for (int i=0; i < 128; i++) {
            if ((i & 0x88) == 0){               
                String square = new Character((char)('a'+file)).toString() + Integer.toString(rank);
                int id = mSquareFromBoardId.get(square);
                FrameLayout frame = (FrameLayout) findViewById(id);                
                ImageView image = null;
                char piece = board.getPiece(i);
                switch(piece) {
                case Piece.WK: {image = mImageViewWhiteQueen; break;}
                case Piece.WQ: {image = mImageViewWhiteQueen; break;}
                case Piece.WR: {image = mImageViewWhiteQueen; break;}
                case Piece.WB: {image = mImageViewWhiteQueen; break;}
                case Piece.WN: {image = mImageViewWhiteQueen; break;}
                case Piece.WP: {image = mImageViewWhiteQueen; break;}
                case Piece.BK: {image = mImageViewWhiteQueen; break;}
                case Piece.BQ: {image = mImageViewWhiteQueen; break;}
                case Piece.BR: {image = mImageViewWhiteQueen; break;}
                case Piece.BB: {image = mImageViewWhiteQueen; break;}
                case Piece.BN: {image = mImageViewWhiteQueen; break;}
                case Piece.BP: {image = mImageViewWhiteQueen; break;}
                case Piece.EMPTY: {System.out.println(".."); break;}
                default: {System.out.println("??"); break;}                
                }
                if (image != null ){
                    ImageView copy = new ImageView(this);                                        
                    copy.setLayoutParams(image.getLayoutParams());                                       
                    copy.setImageDrawable(image.getDrawable());                                      
                    frame.addView(copy);
                }
                file = (file++)%8;
                rank++;
            }
         }        
    }    
    */
    
    /**
     * Make a move graphically on the screen and update the position.
     * Need to check for castle, promotion and en passant moves. 
     * @param srcSquare
     * @param dstSquare
     * @param move
     */
    private void doPlayerMove(FrameLayout srcSquare, FrameLayout dstSquare, int move) {        
        final ImageView piece = (ImageView) srcSquare.getChildAt(1);
        
        TakebackMove tkMove = new TakebackMove(move, piece, srcSquare, dstSquare);        
        
        // Move piece here instead of using movePiece. (Alot faster)
        srcSquare.removeView(piece);

        // A capture
        if (Move.isCaptureMove(move) && dstSquare.getChildCount() > 1) {
            tkMove.setCapturedPiece((ImageView) dstSquare.getChildAt(1));
            dstSquare.removeViewAt(1);            
        }

        dstSquare.addView(piece);
                    	
        if (Move.isCastleMove(move)) {
            // Castle move, move the rook
            final String [] castleAlgebraic = {"e1g1", "e1c1", "e8g8", "e8c8"};
            final String [] rookMoveAlgebraic = {"h1f1", "a1d1", "h8f8", "a8d8"};
            final String algMove = mSquares.get(srcSquare.getId()) + mSquares.get(dstSquare.getId());;

            for (int i=0; i < castleAlgebraic.length; i++) {
                if (castleAlgebraic[i].equals(algMove)) {
                    doMovePiece(rookMoveAlgebraic[i]);
                    tkMove.setRookAlgMove(rookMoveAlgebraic[i]);
                }
            }			
        } else if (Move.isPassantMove(move)) {
            // En passant, clear square behind dst
            String dst = mSquares.get(dstSquare.getId());
            int rank = Integer.parseInt(dst.substring(1, 2));
            rank += mPosition.getToMove()==Piece.WHITE ? -1:1;
            
            String passant = dst.substring(0,1) + String.valueOf(rank);
            int squareId = getResources().getIdentifier("f"+passant, "id", this.getPackageName());
            FrameLayout passantFrame = (FrameLayout) findViewById(squareId);
            tkMove.setPassantFrame(passantFrame);
            tkMove.setPassantCapturedPawn((ImageView) passantFrame.getChildAt(1));
            passantFrame.removeViewAt(1);
        } else if (Move.isPromMove(move)) {
            // Promotion, set new piece
            String algMove = Move.moveToAlg(move);
            dstSquare.removeView(piece);            
            
            String promotionToPiece = algMove.substring(4, 5);
            final String[] legalPromotionPieces = {"q", "r", "n", "b"};
            final ImageView[] piecesWhite = {mImageViewWhiteQueen, mImageViewWhiteRook, mImageViewWhiteKnight, mImageViewWhiteBishop};
            final ImageView[] piecesBlack = {mImageViewBlackQueen, mImageViewBlackRook, mImageViewBlackKnight, mImageViewBlackBishop};
            ImageView promotionPieceImage = null;
            
            for (int i=0; i < legalPromotionPieces.length; i++) {
                if (promotionToPiece.equalsIgnoreCase(legalPromotionPieces[i])){
                    if (mPosition.getToMove() == Piece.WHITE)
                        promotionPieceImage = piecesWhite[i];
                    else promotionPieceImage = piecesBlack[i];
                    
                    ImageView copy = new ImageView(this);                                        
                    copy.setLayoutParams(promotionPieceImage.getLayoutParams());                                       
                    copy.setImageDrawable(promotionPieceImage.getDrawable());                                      
                    dstSquare.addView(copy);
                    tkMove.setPromotedPiece(copy);
                }
            }
        }
        
        // Highligth dst square        
        if (mSelectedDstImageView.getParent() != null) {
            ((FrameLayout) mSelectedDstImageView.getParent()).removeView(mSelectedDstImageView);
        }
        dstSquare.addView(mSelectedDstImageView);
        
        srcSquare.invalidate();
        dstSquare.invalidate();

        // do Move 
        synchronized (mPosition) {
            move = mPosition.legalOpponentMove(move);
            if (move == 0 || mPosition.isGameOver()) {
                Log.w(TAG, "Move is illegal: " + Move.moveToAlg(move));
                throw new RuntimeException("Illegal move");
            }
            mPosition.doMove(move);    
            Log.v(TAG, "move " + Move.moveToAlg(move));
        }
        
        doUpdateScoreSheet(move);
        
        // save takeback move
        mMoves.add(tkMove);
        boolean gameOver=false;
        
        synchronized (mPosition) {
            gameOver = mPosition.isGameOver();
        }
        
        if (gameOver){        
            Log.v(TAG, "game over");
            String text="Game over";
            int result = mPosition.drawOrCheckmate();
            if (result == 0)
                text = "Game drawn";
            else if (result == 1)
            {
                if (mPosition.getToMove() == Piece.BLACK) {
                    text = "White wins";
                } else {
                    text = "Black wins";
                }
            }
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
        }
        
        changeState(ACTION_MOVE_MADE);
    }

    /*
     * Undo a move. Everything needed is stored in a TakebackMove.
     */
    public void undoPlayerMove()
    {
        if ((mMoves.size() == 0))
            return; 
        
        TakebackMove tkMove=mMoves.remove(mMoves.size() - 1);
        
        //move piece back to src
        tkMove.getDstSquare().removeView(tkMove.getPiece());
        tkMove.getSrcSquare().addView(tkMove.getPiece());
        
        
        if (Move.isCaptureMove(tkMove.getMove()) && tkMove.getCapturedPiece()!=null) {
            tkMove.getDstSquare().addView(tkMove.getCapturedPiece());
        }
        
        if (Move.isCastleMove(tkMove.getMove())) {
            //Move rook back 
            String src = tkMove.getRookAlgMove().substring(0, 2);
            String dst = tkMove.getRookAlgMove().substring(2, 4);
            doMovePiece(dst+src);
        }
        else if (Move.isPassantMove(tkMove.getMove())) {
            tkMove.getPassantFrame().addView(tkMove.getPassantCapturedPawn());
            tkMove.getPassantFrame().invalidate();
        } else if (Move.isPromMove(tkMove.getMove())) {
            // Already moved back the pawn as "piece" is a pawn.
            // So only need to remove the Queen (probably)
            tkMove.getDstSquare().removeView(tkMove.getPromotedPiece());
        }
        
        tkMove.getDstSquare().invalidate();
        tkMove.getSrcSquare().invalidate();           

        //--- clear highlighted square TODO: Refactor
        if (mSelectedDstImageView.getParent() != null) {
            FrameLayout parent = (FrameLayout) mSelectedDstImageView.getParent();
            parent.removeView(mSelectedDstImageView);
            parent.invalidate();
        }
        if (mSelectedSrcImageView.getParent() != null) {
            FrameLayout parent = (FrameLayout) mSelectedSrcImageView.getParent();
            parent.removeView(mSelectedSrcImageView);
            parent.invalidate();
        }     
        mSelectedSrcFrameLayout = null;
        mSelectedDstFrameLayout = null;
        //----
        synchronized (mPosition) {
            mPosition.undoMove(tkMove.getMove());
        }
        doUndoScoreSheet(tkMove.getMove());
        changeState(ACTION_UNDO_MOVE);
    }
    
    /**
     * Player wants a new game, ask for confirmation.
     */
    public void onNewGame(){
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      
      builder.setMessage("Start new game?")
             .setCancelable(false)
             .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                     setupStartPosition();
                     dialog.cancel();                     
                 }
             })
             .setNegativeButton("No", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                                                 
                 }
             });
      AlertDialog alert = builder.create();
      alert.show();
    }

    public void onAbout(){
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      
      builder.setMessage("(c) 2011 Juan Pablo Fernandez")
             .setCancelable(true)
             .setTitle("Minimardi for Android")
             .setIcon(R.drawable.icon)
             .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {                     
                     dialog.cancel();                     
                 }
             });
      AlertDialog alert = builder.create();
      alert.show();
    }

    /**
     * Player wants to switch sides. Do it and change mPlayerSide.
     */
    public void onSwitchSides()
    {
        TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);
        ArrayList<TableRow> flipped = new ArrayList<TableRow>();
        for (int i=0; i < table.getChildCount(); i++) {
            flipped.add((TableRow)table.getChildAt(i));            
        }
        
        table.removeAllViews();
        
        for (int i=flipped.size()-1; i>=0; i--){
            TableRow row = flipped.get(i);
            ArrayList<FrameLayout> squares = new ArrayList<FrameLayout>();
            
            for (int j=0; j < row.getChildCount(); j++){
                squares.add((FrameLayout)row.getChildAt(j));                
            }
            
            row.removeAllViews();

            for (int j=squares.size()-1; j >= 0; j--){
                row.addView(squares.get(j));                
            }            
        }        
        
        for (int i=flipped.size()-1; i>=0; i--){
            table.addView(flipped.get(i));
        }
        table.invalidate();
        
        mPlayerSide = (mPlayerSide==Piece.WHITE) ? Piece.BLACK: Piece.WHITE;
        changeState(ACTION_SETTINGS_CHANGED);        
    }

    /**
     * Player wants draw, only accept when worse.
     */
    public void onDrawOffer()
    {
        int val = mPosition.getValue();
        int threshold  = 200; //2 Pawns
        int ply = mPosition.getPly();
        if (mPlayerSide == Piece.BLACK)
            val=val*(-1);
        
        if (((val-threshold)>0) && ply > 10) {
            mState=STATE_GAME_OVER;
            Toast.makeText(getApplicationContext(), "Minimardi accepts your draw offer", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Draw offer declined (" +  (float) mPosition.getValue()/100 + ")", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Player wants a takeback. When engine is on make it takeback 2.
     */
    public void onTakeback() {
        undoPlayerMove();
        
        if (!mPlayBothSides && 
                mPosition.getToMove() != mPlayerSide)
            undoPlayerMove(); // Takeback 2
    }
    
    private void onDebug() {
        mDebug=true;
        if (mPosition.getToMove() != mPlayerSide)
            ;
        else mPlayerSide = (mPlayerSide==Piece.WHITE) ? Piece.BLACK: Piece.WHITE;
        changeState(ACTION_SETTINGS_CHANGED);                
    }
    
    /**
     * Player is trying to make a move. 
     * Do some highlighting and check if move is legal.
     */
    public boolean onTouch(View view, MotionEvent me) {    	
        Integer key = new Integer(view.getId());

        if ( (me.getAction() != MotionEvent.ACTION_DOWN) || 
             (mPosition.getToMove() != mPlayerSide && !mPlayBothSides) ||
             (mState == STATE_GAME_OVER) || 
             (!isChangeStateAllowed()))
        {
            return false;
        }

        if (mSquares.containsKey(key)) {
            FrameLayout square = (FrameLayout) view;
            int move = 0;
            // Source square
            if (mSelectedSrcFrameLayout == null) {
                if (square.getChildCount() == 1) {
                   return true; //empty square                    
                }

                // Highlight source square
                square.addView(mSelectedSrcImageView);
                square.invalidate();

                mSelectedSrcFrameLayout = square;
                return true;                
            }

            // Remove highlight
            FrameLayout selectedImageParent = (FrameLayout) mSelectedSrcImageView.getParent();
            selectedImageParent.removeView(mSelectedSrcImageView);
            selectedImageParent.invalidate();

            mSelectedDstFrameLayout = square;
            
            move=getLegalMove(mSelectedSrcFrameLayout, mSelectedDstFrameLayout);
            if (move==0) {
                mSelectedSrcFrameLayout = null;  
                mSelectedDstFrameLayout = null;
                Toast.makeText(MinimardiActivity.this, "Illegal move", Toast.LENGTH_SHORT).show();
                return true;                
            }
            if (Move.isPromMove(move)) {
                final CharSequence[] items = {"Queen", "Rook", "Bishop", "Knight"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Promote to");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        char promPiece = Piece.WQ;
                        boolean whiteToMove = (mPosition.getToMove() == Piece.WHITE);
                        if (items[item].equals(items[0])) {
                            promPiece = whiteToMove ? Piece.WQ:Piece.BQ; 
                        } else if (items[item].equals(items[1])) {
                            promPiece = whiteToMove ? Piece.WR:Piece.BR;
                        } else if (items[item].equals(items[2])) {
                            promPiece = whiteToMove ? Piece.WB:Piece.BB;
                        } else if (items[item].equals(items[3])) {
                            promPiece = whiteToMove ? Piece.WN:Piece.BN;
                        }                        
                        int move=Move.newMoveWithProm(Move.getSrc(mPromotionMove), Move.getDest(mPromotionMove), promPiece);
                        move=mPosition.legalOpponentMove(move);
                        doPlayerMove(mSelectedSrcFrameLayout, mSelectedDstFrameLayout, move);
                        mSelectedSrcFrameLayout = null;
                        mSelectedDstFrameLayout = null;
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();                
                mState=STATE_WAITING_SELECT_PROMOTION;
                mPromotionMove=move;
                return true;
            }
            doPlayerMove(mSelectedSrcFrameLayout, mSelectedDstFrameLayout, move);
            
            mSelectedSrcFrameLayout = null;
            mSelectedDstFrameLayout = null;
                        
        }
        return false;        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!isChangeStateAllowed())
            return true; 

        switch (item.getItemId()) {

        case R.id.new_game:                       
            onNewGame();
            return true;
        case R.id.switch_sides:
            onSwitchSides();
            return true;
        case R.id.settings:
            Intent intent = new Intent(MinimardiActivity.this, MinimardiPreferenceActivity.class);
            MinimardiActivity.this.startActivity(intent);
            return true;
        case R.id.offer_draw:
            onDrawOffer();
            return true;
        case R.id.undo:
            onTakeback();
            return true;
//        case R.id.debug:
//            onDebug();
//            return true;
                        
        default:
            return super.onOptionsItemSelected(item);
        }
    }  

    private class ComputerMoveTask extends AsyncTask<Object, Void, Integer>  {        
        @Override
        protected Integer doInBackground(Object... vargs) {
            synchronized (mPosition) {                            
                int depth = (Integer) vargs[1];
                int maxDepth = 20;
                Utils.setTimeControl(120, 0);
                switch (depth)
                {
                case 1: //Easy
                    depth = 1;   
                    maxDepth = 1;
                    Utils.setCompTime(0);
                    Utils.setOppTime(100);
                    break;
                case 2: //Medium
                    depth = 3;
                    maxDepth = 4;
                    Utils.setCompTime(60);
                    Utils.setOppTime(60);                                
                    break;
                case 3: //Hard
                    depth = 4;
                    Utils.setCompTime(60*3);
                    Utils.setOppTime(60*3);                
                    break;
                case 4: //Expert 
                    Utils.setCompTime(60*5);
                    Utils.setOppTime(60*5);
                    depth = 5;
                }
                int allocTime = Utils.allocateTime();
                Log.v(TAG, "Computer started thinking ("+allocTime+")");
                
                return new Integer(Search.bestMove((Position)vargs[0], maxDepth, depth));
            }
        }

        @Override
        protected void onPostExecute(Integer move) {
            if (isCancelled()) {
                Log.w(TAG, "Computer cancelled");
                return;
            }
            if (mCompTask==null) {
                Log.w(TAG, "New activity");
                return;
            }
            
            doComputerMove(move);    		
        }
    }    
}