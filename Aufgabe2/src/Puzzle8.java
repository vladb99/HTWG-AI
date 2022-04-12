import java.util.Deque;

/**
 * Hauptprogramm für 8-Puzzle-Problem.
 *
 * @author Ihr Name
 */
public class Puzzle8 {

    public static void main(String[] args) {
        // Aufgabe b
        for (int i = 0; i < 10; i++) {
            Board b = new Board();
            System.out.println(b);
            System.out.println(b.h1());
            System.out.println(b.h2());

            Deque<Board> res = A_Star.aStar(b);
            int n = res == null ? -1 : res.size();
            System.out.println("Anz.Zuege: " + n + ": " + res);

            res = IDFS.idfs(b);
            n = res == null ? -1 : res.size();
            System.out.println("Anz.Zuege: " + n + ": " + res);
            System.out.println();
        }
    }
}
