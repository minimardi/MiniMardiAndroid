package minimardi;

public class HistoryHeuristic extends MiniMardiNativeObject {

	
	public HistoryHeuristic(){
		
	}
	
	protected HistoryHeuristic(long cptr){
		super(cptr);
	}
	
	public void histInc(int move,int score){
		histIncNative(move,score,m_CPtr);
	}
	
	public int histScore(int move){
		return histScoreNative(move,m_CPtr);
	}
	
	public int histScore2(int src,int dst){
		return histScore2Native(src,dst,m_CPtr);
	}
	
	public void clearHistory(){
		clearHistoryNative(m_CPtr);
	}
	
	public void initHistory(){
		initHistoryNative(m_CPtr);
	}
	
	protected native long nativeCreate();
	
	protected native void nativeFree(long cptr);
	
	protected static native void histIncNative(int move,int score,long cptr);
	
	protected static native int histScoreNative(int move,long cptr);
	
	protected static native int histScore2Native(int src,int dst,long cptr);
	
	protected static native void clearHistoryNative(long cptr);
	
	protected static native void initHistoryNative(long cptr);

}
