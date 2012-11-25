package minimardi;

public class MoveListStack extends MiniMardiNativeObject {

	public MoveListStack(){
		
	}
	
	protected MoveListStack(long cptr){
		super(cptr);
	}
	

	public int newHistMove(){
		return newHistMovesNative(m_CPtr);
	}
	
	public void  push(int move,int score){
		pushNative(m_CPtr,move,score);
	}
	
	public int pop(int old_sp){
		return popNative(m_CPtr,old_sp);
	}
	
	public int look(int old_sp,int look_at){
		return lookNative(m_CPtr,old_sp,look_at);
	}
	
	public int lookAtHist(int old_sp,int look_at){
		return lookAtHistNative(m_CPtr,old_sp,look_at);
	}
	
	public void initMoveList(){
		initMoveListNative(m_CPtr);
	}
	
	public void pushHist(int move){
		pushHistNative(m_CPtr,move);
	}
	
	public int popHist(int old_sp){
		return popHistNative(m_CPtr,old_sp);
	}
	
	public int lookHist(int old_sp){
		return lookHistNative(m_CPtr,old_sp);
	}
	
	
	public void freeMoveList(int old_sp){
		freeMoveListNative(m_CPtr,old_sp);
	}
	
	public void sortMoveScores(int moves){
		sortMoveScoresNative(m_CPtr,moves);
	}
	
	public int lookStack(int old_sp,int look_at){
		return lookStackNative(m_CPtr,old_sp,look_at);
	}
	
	@Override
	protected native long nativeCreate();
	

	@Override
	protected native void nativeFree(long cptr);
	
	protected static native int newHistMovesNative(long cptr);
	
	protected static native void pushNative(long cptr,int move, int score);
	
	protected static native int popNative(long cptr,int old_sp);
	
	protected static native int lookNative(long cptr,int old_sp,int look_at);
	
	protected static native int lookAtHistNative(long cptr,int old_sp,int look_at);
	
	protected static native void initMoveListNative(long cptr);
	
	protected static native void pushHistNative(long cptr,int move);
	
	protected static native int popHistNative(long cptr,int old_sp);
	
	protected static native int lookHistNative(long cptr,int old_sp);
	
	protected static native void freeMoveListNative(long cptr,int old_sp);
	
	protected static native void sortMoveScoresNative(long cptr,int moves);
	
	protected static native int lookStackNative(long cptr,int old_sp,int look_at);
	
	
}
