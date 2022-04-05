import java.util.*;

/**
 * Klasse IDFS für iterative deepening depth-first search
 * @author Ihr Name
 */
public class IDFS {

	private static Deque<Board> dfs(Board curBoard, Deque<Board> path, int limit) {
		List<Board> actions = curBoard.possibleActions();
		if (curBoard.isSolved()) {
			return path;
		} else if (limit == 0) { // Maximal zulässige Rekursionstiefe erreicht
			return null;
		} else {
			boolean cutOffOccured = false;
			for (Board child: actions) {
				if (path.contains(child)) {
					continue;
				}
				path.add(child);
				Deque<Board> result = dfs(child, path, limit - 1);

				if (result != null) {
					return result;
				}else {
					cutOffOccured = true;
				}

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
			Deque<Board> result = dfs(curBoard,path,limit);
			if (result != null) {
				System.out.println("LIMIT: " + limit);
				return result;
			}
		}
		return null;
	}
	
	public static Deque<Board> idfs(Board curBoard) {
		Deque<Board> path = new LinkedList<>();
		path.addLast(curBoard);
		Deque<Board> res =  idfs(curBoard, path); 
		return res;
	}
}
