package minimardi;

public class Piece {

	public static final char WK = 9;
	
	public static final char WQ = 10;
	
	public static final char WR = 11;
	
	public static final char WB = 12;
	
	public static final char WN = 13;
	
	public static final char WP = 14;
	
	public static final char BK = 1;
	
	public static final char BQ = 2;
	
	public static final char BR = 3;
	
	public static final char BB = 4;
	
	public static final char BN = 5;

	public static final char BP = 6;
	
	public static final char EMPTY = 16;
	
	public static final char BLACK = 0;
	
	public static final char WHITE = 8;
	
	private Piece(){
		
	}
	
	public static native boolean valid_piece(char p);
	
	public static native boolean valid_piece_not_empty(char p);
	
	public static native char makeSameColor(char p1, char p2);
			
}
