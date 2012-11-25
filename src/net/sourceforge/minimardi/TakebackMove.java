package net.sourceforge.minimardi;

import android.widget.FrameLayout;
import android.widget.ImageView;

public class TakebackMove {
    private int move;
    private ImageView piece;        
    private FrameLayout srcSquare;
    private FrameLayout dstSquare;
    
    private ImageView capturedPiece;        
    
    /** Passant **/
    private FrameLayout passantFrame;
    private ImageView passantCapturedPawn;
    
    /** Castled **/
    private String rookAlgMove;
    
    /** Promotion **/
    private ImageView promotedPiece;
    
    public TakebackMove(int move, ImageView piece, FrameLayout srcSquare, FrameLayout dstSquare) {
        this.move=move;
        this.piece=piece;
        this.srcSquare=srcSquare;
        this.dstSquare=dstSquare;
    }

    
    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public ImageView getPiece() {
        return piece;
    }

    public void setPiece(ImageView piece) {
        this.piece = piece;
    }

    public FrameLayout getSrcSquare() {
        return srcSquare;
    }

    public void setSrcSquare(FrameLayout srcSquare) {
        this.srcSquare = srcSquare;
    }

    public FrameLayout getDstSquare() {
        return dstSquare;
    }

    public void setDstSquare(FrameLayout dstSquare) {
        this.dstSquare = dstSquare;
    }

    public ImageView getCapturedPiece() {
        return capturedPiece;
    }

    public void setCapturedPiece(ImageView capturedPiece) {
        this.capturedPiece = capturedPiece;
    }

    public FrameLayout getPassantFrame() {
        return passantFrame;
    }

   public void setPassantFrame(FrameLayout passantFrame) {
        this.passantFrame = passantFrame;
    }

    public ImageView getPassantCapturedPawn() {
        return passantCapturedPawn;
    }

    public void setPassantCapturedPawn(ImageView passantCapturedPawn) {
        this.passantCapturedPawn = passantCapturedPawn;
    }

    public ImageView getPromotedPiece() {
        return promotedPiece;
    }

    public void setPromotedPiece(ImageView promotedPawn) {
        this.promotedPiece = promotedPawn;
    }
    
    public String getRookAlgMove() {
        return rookAlgMove;
    }


    public void setRookAlgMove(String rookAlgMove) {
        this.rookAlgMove = rookAlgMove;
    }
}
