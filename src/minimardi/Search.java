package minimardi;

public class Search {

	private Search(){
		
	}
	public static native int bestMove(Position position,int depth,int time_ply);
	
	public static native int perft(Position position,int depth);
}
