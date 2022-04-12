import java.util.*;

/**
 * Klasse IDFS für iterative deepening depth-first search
 *
 * @author Ihr Name
 */
public class IDFS {
    // s. Skript 2-51
    private static Deque<Board> dfs(Board curBoard, Deque<Board> path, int limit) {
        // Wir bekommen alle möglichen Actions für das aktuelle Board
        List<Board> actions = curBoard.possibleActions();
        // Wenn das Board gelöst ist können wir den Path zurückgeben
        if (curBoard.isSolved()) {
            return path;
        } else if (limit == 0) { // Maximal zulässige Rekursionstiefe erreicht
            return null;
        } else {
            // Wenn nicht müssen wir weiterlaufen
            boolean cutOffOccured = false;
            for (Board child : actions) {
                // Wenn der Path ein Child besitzt, wird dieses zum Path geadded
                // Wenn der rekursive Aufruf für dieses Kind null ist wird der Teil des Weges abgekappt
                if (path.contains(child)) {
                    continue;
                }
                path.add(child);
                Deque<Board> result = dfs(child, path, limit - 1);

                if (result != null) {
                    return result;
                } else {
                    cutOffOccured = true;
                }
                // Für den Backtrack-Schritt wird der letzte Knoten immer entfernt
                path.removeLast();
            }
            if (cutOffOccured) {
                // Keine Lösung, Limit war zu klein.
                return null;
            }
            return null;
        }
    }

    private static Deque<Board> idfs(Board curBoard, Deque<Board> path) {
        for (int limit = 5; limit < Integer.MAX_VALUE; limit++) {
            Deque<Board> result = dfs(curBoard, path, limit);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public static Deque<Board> idfs(Board curBoard) {
        Deque<Board> path = new LinkedList<>();
        path.addLast(curBoard);
        Deque<Board> res = idfs(curBoard, path);
        return res;
    }
}
