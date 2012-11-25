package minimardi;

public class Utils {

	private Utils(){
		
	}
	
	public static native int setTimeControl(int base, int increment);
	
	public static native int setCompTime(int time);
	
	public static native int setOppTime(int time);
	
	public static native int outOfTime();
	
	public static native int allocateTime();
	
}
