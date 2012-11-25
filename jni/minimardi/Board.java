package minimardi;

public class Board extends MiniMardiNativeObject{

	public Board(){
		
	}
	
    protected Board(long cptr){
        super(cptr);
    }		
    
	public void emptyBoard(){
		if(!m_disposed){
			emptyBoardNative(m_CPtr);
		}
		else{
			throw new RuntimeException("already disposed");
		}
	}
	
	public void freeBoard(){		
		cleanup();		
	}
	
	public void printBoard(){
		if(!m_disposed){
			printBoardNative(m_CPtr);
		}
		else{
			throw new RuntimeException("already disposed");
		}
		
	}
	
	public void chessBoard(){
		if(!m_disposed){
			chessBoardNative(m_CPtr);
		}
		else{
			throw new RuntimeException("already disposed");
		}
	}
	
	public char getPiece(int pos){
		if(!m_disposed){
			return getPieceNative(m_CPtr,pos);
		}
		else{
			throw new RuntimeException("already disposed");
		}
	}
	
	public void setPiece(char piece,int pos){
		if(!m_disposed){
			setPieceNative(piece,pos,m_CPtr);
		}
		else{
			throw new RuntimeException("already disposed");
		}
	}
	
	protected native long nativeCreate();
	
	protected native void nativeFree(long cptr);
	
	protected static native void emptyBoardNative(long cptr);
			
	protected static native void printBoardNative(long cptr);
	
	protected static native void chessBoardNative(long cptr);
	
	public static native boolean onBoard(int move);
	
	public static native char getPieceNative(long cptr,int pos);
	
	public static native void setPieceNative(char piece,int pos,long cptr);
	
	public static native int rank(int pos);
	
	public static native int file(int pos);
 
}
