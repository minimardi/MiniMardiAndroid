package minimardi;



public abstract class MiniMardiNativeObject implements Disposable {

	protected long m_CPtr;
	
	protected boolean m_disposed;
	
	static{
		System.loadLibrary("minimardi-jni");
	}
	public MiniMardiNativeObject(){
		m_CPtr = nativeCreate();
		m_disposed = false;
	}
		
	protected MiniMardiNativeObject(long cptr){
		m_CPtr = cptr;
		m_disposed = false;
	}
	protected void cleanup(){
		if(!m_disposed){						
			nativeFree(m_CPtr);
			m_CPtr = -1;
			m_disposed = true;			
		}else{
			System.out.println("already disposed");
		}
	}
	
	protected void finalize(){
		cleanup();		
	}

	public void dispose(){
		cleanup();
	}
	
	
	protected abstract long nativeCreate();
	
	protected abstract void nativeFree(long cptr);
	
}
