import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private int moves;
    private MinPQ<Board> openQ;
    private Queue<Board> closeQ = new Queue<>();
    private MinPQ<Board> twinOpenQ;
    private Queue<Board> twinCloseQ = new Queue<>();
    private boolean solvable = true;

    private class ComparBoard implements Comparator<Board> {

        @Override
        public int compare(Board a, Board b) {
            if (a.hamming() > b.hamming())
                return 1;
            if (a.hamming() < b.hamming())
                return -1;
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        openQ = new MinPQ<Board>(new ComparBoard());
        twinOpenQ = new MinPQ<Board>(new ComparBoard());
        Board thisboard = initial;
        Board twinboard = initial.twin();
        openQ.insert(initial);
        twinOpenQ.insert(twinboard);
        // closeQ.enqueue(initial);
        moves = -1;
        while (!thisboard.isGoal()) {
            Iterable<Board> childs = thisboard.neighbors();
            for (Board b : childs)
                addChild(b);
            thisboard = openQ.delMin();
            closeQ.enqueue(thisboard);
            childs = twinboard.neighbors();
            for (Board b : childs)
                addtwinChild(b);
            twinboard = twinOpenQ.delMin();
            if (twinboard.isGoal()) {
                solvable = false;
                break;
            }
            twinCloseQ.enqueue(twinboard);
            moves++;

        }

    }

    private void addChild(Board board) {
        for (Board b : closeQ)
            if (board.equals(b))
                return;
        for (Board b : openQ)
            if (board.equals(b))
                return;
        openQ.insert(board);
    }

    private void addtwinChild(Board board) {
        for (Board b : twinCloseQ)
            if (board.equals(b))
                return;
        for (Board b : twinOpenQ)
            if (board.equals(b))
                return;
        twinOpenQ.insert(board);
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return closeQ;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // String s = "D:\\Java\\Algoritms in java\\Workspace\\8puzzle\\testing\\8puzzle\\puzzle04.txt";
        // In in = new In(s);

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
