package minimardi;

public class Move {

	private Move(){
		
	}
			
	public static native int setMoveCastle(int move);
	
	public static native int newMoveWithProm(int src, int dst, char prom_piece);
	
	public static native int newMoveWithPassant(int src, int dst, char cap);
	
	public static native int newMoveAll(int src, int dst, char cap, char prom, int ca, int pa);
	
	public static native void printMoveAlg(int move);
	
	public static native void printMove(int move);
	
	public static native String moveToAlg(int move);
	
	public static native int algToMove(String movealg,char side);
	
	public static native int debugLegalMove(int move);
	
	public static native char getPromPiece(int move);
	
	public static native char getCapPiece(int move);

	public static native char getSrc(int move);
	
	public static native char getDest(int move);
	
	public static native boolean isCastleMove(int move);
	
	public static native boolean isPassantMove(int move);
	
	public static native boolean isPromMove(int move);
	
	public static native boolean isCaptureMove(int move);
	
	public static native boolean legaMove(int move);
	
	public static native int newMove(int src,int dest);
	
	public static native int newMoveWithCap(int src,int dest,int cap);
}
