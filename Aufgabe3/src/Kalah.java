/**
 * Hauptprogramm für KalahMuster.
 * @since 29.3.2021
 * @author oliverbittel
 */
public class Kalah {
	
	private static final String ANSI_BLUE = "\u001B[34m";

	/**
	 *
	 * @param args wird nicht verwendet.
	 */
	public static void main(String[] args) {
		//testExample();
		testHHGame();
	}
	
	/**
	 * Beispiel von https://de.wikipedia.org/wiki/Kalaha
	 */
	public static void testExample() { 
		KalahBoard kalahBd = new KalahBoard(new int[]{5,3,2,1,2,0,0,4,3,0,1,2,2,0}, 'B');
		kalahBd.print();
		
		System.out.println("B spielt Mulde 11");
		kalahBd.move(11);
		kalahBd.print();
		
		System.out.println("B darf nochmals ziehen und spielt Mulde 7");
		kalahBd.move(7);
		kalahBd.print();
	}
	
	/**
	 * Mensch gegen Mensch
	 */
	// B: 7170 6268 6445
	// C: 2730 277 1589 292 4861 258
	// D: 2431 305 556 312 4731 296

	// B:    0   5   12   5   4   12   11   3   10   2   11   5   12   0   9   1   8   5   12   4   12   9   5   3   11   5   4   12   10   2   12   11   4   1   9   3   5   7   4   9   5         === Player A ===
	// C:    5   7   0   7   4   7   5   7   1   7   4   8   4   7   0   8   1   9   0   7   1   8   5   7   2   7   0   8   4   9   3   12   0   7   1   8   2   9   4   7   5   7   3   4   11   0   8   5   7   1   11   2   12   0   10   4   7   1   8   3   10   2   9   3   10   0   12   0   11   0   12   7         === Player A ===
	// D:    5   7   0   7   4   7   5   7   1   7   4   8   4   7   0   8   1   9   0   7   1   12   0   7   1   8   4   9   2   7   0   8   1   9   2   11   0   7   1   8   2   9   4   7   3   7   0   9   1   12   0   11   0   8   9   1   11   2   12   0   10   0   10   2   9   4   7   3   7   4   9   1   2   8   3   9   5   7   0   8   1   12   0   11   0   7   1   8   2   7   3   7   5   7   4   8   0   7   1   8   5   2         === Player A ===

	public static void testHHGame() {
		KalahBoard kalahBd = new KalahBoard();
		kalahBd.print();

		while (!kalahBd.isFinished()) {
			KalahBoard botAction = kalahBd.maxAction_d(kalahBd.getCurPlayer() == KalahBoard.APlayer, 5);
			//System.out.println("Count: " + kalahBd.countD);
			//botAction.print();
			System.out.print("   " + botAction.getLastPlay());
			//int action = kalahBd.readAction();
			kalahBd.move(botAction.getLastPlay());
			//kalahBd.print();
		}
		kalahBd.print();
		System.out.println("\n" + ANSI_BLUE + "GAME OVER");
	}
}
