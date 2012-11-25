package minimardi;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Position p = new Position();
		while (!p.isGameOver()) {
			int bestMove = Search.bestMove(p, 3, 100);
			String moveAlg = Move.moveToAlg(bestMove);
			System.out.println("Best move is " + moveAlg);
		
			p.doMove(bestMove);
			p.printPosition();
		}	
	}
}
