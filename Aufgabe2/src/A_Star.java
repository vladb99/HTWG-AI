import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Ihr Name
 */
public class A_Star {
    // cost ordnet jedem Board die Aktuellen Pfadkosten (g-Wert) zu.
    // pred ordnet jedem Board den Elternknoten zu. (siehe Skript S. 2-25).
    // In cost und pred sind genau alle Knoten der closedList und openList enthalten!
    // Nachdem der Zielknoten erreicht wurde, lässt sich aus cost und pred der Ergebnispfad ermitteln.
    private static HashMap<Board, Integer> cost = new HashMap<>();
    private static HashMap<Board, Board> pred = new HashMap<>();

    // openList als Prioritätsliste.
    // Die Prioritätswerte sind die geschätzen Kosten f = g + h (s. Skript S. 2-66) (g = aktuelle kosten, h = kosten von heuristik)
    private static IndexMinPQ<Board, Integer> openList = new IndexMinPQ<>();
/*
    c) Bestimmen Sie die Anzahl der vom Suchverfahren generierten Zustände und die Länge der
    Lösungsfolge für verschiedene Startzustände.
    ?????

    d) Sind Ihre Zugfolgen optimal? Wenn ja, warum?
    IDFS:
    Da jede Aktion der gleiche positive Kosten hat und der Algorithmus immer Ebenenweise sucht, wird die schnellsmögliche Lösung gefunden.

    A*:
    Da immer verglichen wird ob es zu einem Knoten einen günsitgeren Weg gibt, wird schlussendlich der beste Weg zurückgegeben mit den kleinsten Kosten.

    e) Welches Problem könnte entstehen (nicht ausprobieren!), falls A* für die Lösung des 15-
    Puzzle eingesetzt werden würde?
    Speicherprobleme

*/

    public static Deque<Board> aStar(Board startBoard) {
        cost.clear();
        pred.clear();
        openList.clear();

        if (startBoard.isSolved()) {
            Deque<Board> path = new LinkedList<>();
            path.add(startBoard);
            return path;
        }

        openList.add(startBoard, startBoard.h2());
        cost.put(startBoard, startBoard.h2());

        final LinkedList<Board> closedList = new LinkedList();

        while (!openList.isEmpty()) {
            Board board = openList.removeMin();
            if (board.isSolved()) {
                Deque<Board> path = new LinkedList<>();
                //Add the solved board
                path.add(board);
                while ((board = pred.get(board)) != null) {
                    //Add the parents of the solved board
                    path.addFirst(board);
                }
                return path;
            }

            closedList.add(board);

            for (final Board possibleBoard : board.possibleActions()) {
                final int costs = cost.get(board) + 1 + possibleBoard.h2();
                if ((openList.get(possibleBoard) == null) && !closedList.contains(possibleBoard)) {//Knoten ist nicht in der Openlist und nicht in der closedList => komplett neuer knoten
                    openList.add(possibleBoard, costs);
                    pred.put(possibleBoard, board);
                    cost.put(possibleBoard, cost.get(board) + 1);
                } else if (openList.get(possibleBoard) != null) {//Knoten ist in der OpenList
                    if (cost.get(board) + 1 < cost.get(pred.get(possibleBoard)) + 1) {
                        openList.change(possibleBoard, costs);
                        pred.put(possibleBoard, board);
                        cost.put(possibleBoard, cost.get(board) + 1);
                    }
                }
            }
        }
        return null; // Keine Lösung
    }
}
