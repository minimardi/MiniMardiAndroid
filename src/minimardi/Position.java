package minimardi;

public class Position extends MiniMardiNativeObject {

	
	public Position(){
		
	}
	
	public Position(String fen){
		m_CPtr = nativeNewChessPositionFen(fen);
		m_disposed = false;
	}

	public int moveGenerator(){
		if(!m_disposed){
			return moveGeneratorNative(m_CPtr);
		}
		else{
			throw new RuntimeException("already disposed");	
		}
	}
	
	public long getZobristKey(){
		return getZobristKeyNative(m_CPtr);
	}
	
	public char getToMove(){
		return getToMoveNative(m_CPtr);
	}
	
	public int getMaterial(){
		return getMaterialNative(m_CPtr);
	}
	
	public int getPosMoves(){
		return getPosMovesNative(m_CPtr);
	}
	
	public int getOutOfBook(){
		return getOutOfBookNative(m_CPtr);
	}
	
	public void setToMove(char tomove){
		setToMoveNative(tomove, m_CPtr);
	}
	
	public void doMove(int move){
		doMoveNative(move,m_CPtr);
	}
	
	public void undoMove(int move){
		undoMoveNative(move,m_CPtr);
	}
	
	public void freePosition(){
		cleanup();
	}
	public void printPosition(){
		printPositionNative(m_CPtr);
	}
	
	public void debugPrintCapList(int listptr){
		debugPrintCapListNative(listptr,m_CPtr);
	}
		
	public int legalOpponentMove(int move){
		return legalOpponentMoveNative(move,m_CPtr);
	}
	
	public int drawOrCheckmate(){
		return drawOrCheckmateNative(m_CPtr);
	}
	
	public boolean inCheckIllegal(){
		return inCheckIllegalNative(m_CPtr);
	}
	
	public char getOpponentSide(){
		return getOpponentSideNative(m_CPtr);
	}
	
	public void setSideWhite(){
		setSideWhiteNative(m_CPtr);
	}
	
	public void setSideBlack(){
		setSideBlackNative(m_CPtr);
	}
	
	public char getCompSide(){
		return getCompSideNative(m_CPtr);
	}
	
	public boolean isCompBlack(){
		return isCompBlackNative(m_CPtr);
	}
	
	public boolean isGameOver(){
		return isGameOverNative(m_CPtr);
	}
	
	public int getValue(){
		return getValueNative(m_CPtr);
	}
	
	public int getNodes(){
		return getNodesNative(m_CPtr);
	}
	
	public void setValue(int value,int nodes){
		setValueNative(value,nodes,m_CPtr);
	}
	
	public void setOutOfBook(){
		setOutOfBookNative(m_CPtr);
	}
	
	public boolean inCheck(){
		return inCheckNative(m_CPtr);
	}
		
	
	public int repSearch(){
		return repSearchNative(m_CPtr);
	}
	
	public int getPly(){
		return getPlyNative(m_CPtr);
	}
	
	public boolean whiteIsCastled(){
		return whiteIsCastledNative(m_CPtr);
	}
	
	public boolean blackIsCastled(){
		return blackIsCastledNative(m_CPtr);
	}
	
	public boolean lastMoveNull(){
		return lastMoveNullNative(m_CPtr);
	}
	
	public int pieces(){
		return piecesNative(m_CPtr);
	}
	
	public HistoryHeuristic getHistHeuristic(){
		return getHistHeuristicNative(m_CPtr);
	}
	
	public MoveListStack getMoveStack(){
		return getMoveStackNative(m_CPtr);
	}
	
	public Board getBoard() {
	    return getBoardNative(m_CPtr);
	}
	
	protected native void nativeFree(long cptr);
	
	protected native long nativeCreate();
	
	protected native long nativeNewChessPositionFen(String fen);
	
	protected static native int moveGeneratorNative(long cptr);
	
	protected static native long getZobristKeyNative(long cptr);
	
	protected static native char getToMoveNative(long cptr);
	
	protected static native int getMaterialNative(long cptr);
	
	protected static native int getPosMovesNative(long cptr);
	
	protected static native int getOutOfBookNative(long cptr);
	
	protected static native void setToMoveNative(char tomove,long cptr);
	
	protected static native void doMoveNative(int move,long cptr);
	
	protected static native void undoMoveNative(int move,long cptr);
			
	protected static native void debugPrintCapListNative(int listptr,long cptr);
	
	protected static native void printPositionNative(long cptr);	
	
	protected static native int legalOpponentMoveNative(int move,long cptr);
	
	protected static native int drawOrCheckmateNative(long cptr);
	
	protected static native boolean inCheckIllegalNative(long cptr);
	
	protected static native char getOpponentSideNative(long cptr);
	
	protected static native void setSideWhiteNative(long cptr);
	
	protected static native void setSideBlackNative(long cptr);
	
	protected static native char getCompSideNative(long cptr);
	
	protected static native boolean isCompBlackNative(long cptr);
	
	protected static native boolean isGameOverNative(long cptr);
	
	protected static native int getValueNative(long cptr);
	
	protected static native int getNodesNative(long cptr);
	
	protected static native void setValueNative(int value,int nodes,long cptr);
	
	protected static native void setOutOfBookNative(long cptr);
	
	protected static native boolean inCheckNative(long cptr);
			
	protected static native int repSearchNative(long cptr);
	
	protected static native int getPlyNative(long cptr);
	
	protected static native boolean whiteIsCastledNative(long cptr);
	
	protected static native boolean blackIsCastledNative(long cptr);
	
	protected static native boolean lastMoveNullNative(long cptr);
	
	protected static native int piecesNative(long cptr);
	
	protected static native HistoryHeuristic getHistHeuristicNative(long cptr);
	
	protected static native MoveListStack getMoveStackNative(long cptr);
	
	protected static native Board getBoardNative(long cptr);
	

	
}
