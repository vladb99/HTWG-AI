import java.util.*;

/**
 * Klasse Board für 8-Puzzle-Problem
 *
 * @author Ihr Name
 */
public class Board {

    /**
     * Problmegröße
     */
    public static final int N = 8;

    /**
     * Board als Feld.
     * Gefüllt mit einer Permutation von 0,1,2, ..., 8.
     * 0 bedeutet leeres Feld.
     */
    protected Integer[] board = new Integer[N + 1];

    /**
     * Generiert ein zufälliges Board.
     */
    public Board() {
        Integer[] intArray = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        List<Integer> intList = Arrays.asList(intArray);
        Collections.shuffle(intList);
        board = intList.toArray(intArray);

        while (calculate_parity() % 2 != 0) {
            Collections.shuffle(intList);
            board = intList.toArray(intArray);
        }
    }

    /**
     * Generiert ein Board und initialisiert es mit board.
     *
     * @param board Feld gefüllt mit einer Permutation von 0,1,2, ..., 8.
     */
    public Board(Integer[] board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "Puzzle{" + "board=" + Arrays.toString(board) + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Board other = (Board) obj;
        return Arrays.equals(this.board, other.board);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Arrays.hashCode(this.board);
        return hash;
    }

    public int calculate_parity() {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            if (i == board.length - 1) {
                break;
            }
            int left = board[i];
            if (left == 0) {
                continue;
            }
            for (int j = i + 1; j < board.length; j++) {
                int right = board[j];
                if (right == 0) {
                    continue;
                }
                if (left > right) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Paritätsprüfung.
     *
     * @return Parität.
     */
    public boolean parity(Board board) {
        return board.calculate_parity() % 2 == calculate_parity() % 2;
    }

    /**
     * Heurstik h1. (siehe Aufgabenstellung)
     *
     * @return Heuristikwert.
     */
    public int h1() {
        int count = 0;
        // Index i ist die korrekte Position
        for (int i = 0; i < board.length; i++) {
            if (!Objects.equals(board[i], i) && board[i] != 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * Heurstik h2. (siehe Aufgabenstellung)
     *
     * @return Heuristikwert.
     */
    public int h2() {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            if (!Objects.equals(board[i], i) && board[i] != 0) {
                // Idee wäre Position in der Matrix von falsche und richtige Zahl finden.
                // Man kann dann die Positionen subtrahieren und auf die Distanz kommen.
                // Bsp.: Index 5 hat die Position 1,2 (Reihe, Spalte)  (5 % 3 = 1 Rest 2)
                // 4, 1, 5
                // 3, 0, 2
                // 6, 7, 8
                int wrong_index = i;
                int correct_index = board[i];

                int row1 = wrong_index / 3;
                int column1 = wrong_index % 3;

                int row2 = correct_index / 3;
                int column2 = correct_index % 3;

                count += Math.abs(row1 - row2) + Math.abs(column1 - column2);
            }
        }
        return count;
    }

    /**
     * Liefert eine Liste der möglichen Aktion als Liste von Folge-Boards zurück.
     *
     * @return Folge-Boards.
     */
    public List<Board> possibleActions() {
        List<Board> boardList = new LinkedList<>();

        for (int i = 0; i < board.length; i++) {
            if (Objects.equals(board[i], 0)) {
                int row = i / 3;
                int column = i % 3;

                int up = row - 1;
                int down = row + 1;
                int left = column - 1;
                int right = column + 1;

                if (up >= 0) {
                    boardList.add(switchPosition(board, i, i - 3));
                }
                if (down <= 2) {
                    boardList.add(switchPosition(board, i, i + 3));
                }
                if (left >= 0) {
                    boardList.add(switchPosition(board, i, i - 1));
                }
                if (right <= 2) {
                    boardList.add(switchPosition(board, i, i + 1));
                }
            }
        }
        return boardList;
    }

    private Board switchPosition(Integer[] board, int from, int to) {
        Integer[] clone = Arrays.copyOf(board, board.length);
        int tmp = clone[from];
        clone[from] = clone[to];
        clone[to] = tmp;
        return new Board(clone);
    }


    /**
     * Prüft, ob das Board ein Zielzustand ist.
     *
     * @return true, falls Board Ziestzustand (d.h. 0,1,2,3,4,5,6,7,8)
     */
    public boolean isSolved() {
        return Arrays.equals(board, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
    }


    public static void main(String[] args) {
        Board b = new Board(new Integer[]{7, 2, 4, 5, 0, 6, 8, 3, 1});        // abc aus Aufgabenblatt
        Board goal = new Board(new Integer[]{1, 0, 2, 3, 4, 5, 6, 7, 8});

        Deque<Board> path = IDFS.idfs(b);
        for (Board board : path) {
            System.out.println(board);
        }

//        System.out.println(b);
//        System.out.println(b.parity(goal));
//        System.out.println(b.calculate_parity());
//        System.out.println(b.h1());
//        System.out.println(b.h2());
//        System.out.println(b.isSolved());
//
//        System.out.println(goal.possibleActions());
//
//        for (Board child : b.possibleActions())
//            System.out.println(child);
//
//        System.out.println(goal.isSolved());
    }
}

