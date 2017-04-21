
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private int moves;
    private Stack<Board> res;
    private MinPQ<ComparableBoard> openQ;
    private Stack<ComparableBoard> closeQ = new Stack<>();
    private MinPQ<ComparableBoard> twinOpenQ;
    private Stack<ComparableBoard> twinCloseQ = new Stack<>();
    private boolean solvable = true;

    private class ComparableBoard implements Comparable<ComparableBoard> {

        private int step;
        private int priority;
        private ComparableBoard parent;
        private Board board;

        public ComparableBoard(Board board, int step, ComparableBoard parent) {
            this.board = board;
            this.step = step;
            this.parent = parent;
            setPriority();
        }

        private void setPriority() {
            priority = board.manhattan() + step;
        }

        @Override
        public int compareTo(ComparableBoard that) {
            if (this.priority > that.priority)
                return 1;
            if (this.priority < that.priority)
                return -1;
            if (this.board.hamming() + this.step > that.board.hamming() + that.step)
                return 1;
            if (this.board.hamming() + this.step < that.board.hamming() + that.step)
                return -1;
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        res = new Stack<>();
        openQ = new MinPQ<ComparableBoard>();
        twinOpenQ = new MinPQ<ComparableBoard>();
        ComparableBoard thisboard = new ComparableBoard(initial, 0, null);
        ComparableBoard twinboard = new ComparableBoard(initial.twin(), 0, null);
        openQ.insert(thisboard);
        twinOpenQ.insert(twinboard);
        // closeQ.enqueue(initial);

        int step = 0;
        int twinboardstep = 0;
        Iterable<Board> childs;
        while (!thisboard.board.isGoal()) {

            step = thisboard.step + 1;
            childs = thisboard.board.neighbors();
            for (Board b : childs) {
                if (b.equals(thisboard.parent.board))
                    continue;
                addChild(b, step, thisboard);
            }
            thisboard = openQ.delMin();
            closeQ.push(thisboard);
            twinboardstep = twinboard.step + 1;
            childs = twinboard.board.neighbors();
            for (Board b : childs) {
                if (b.equals(twinboard.parent.board))continue;
                addtwinChild(b, twinboardstep, twinboard);
            }
            twinboard = twinOpenQ.delMin();
            if (twinboard.board.isGoal()) {
                solvable = false;
                moves = -1;
                return;
            }
            twinCloseQ.push(twinboard);

        }
        moves = thisboard.step;
        while (thisboard != null) {
            res.push(thisboard.board);
            thisboard = thisboard.parent;
        }

    }

    // private void defindIsSolvable(int in [][]) {

    // }

    private void addChild(Board board, int step, ComparableBoard parent) {
        for (ComparableBoard cb : closeQ)
            if (board.equals(cb.board))
                return;
        for (ComparableBoard cb : openQ)
            if (board.equals(cb.board)) {
                if (step < cb.step) {
                    cb.step = step;
                    cb.setPriority();
                    cb.parent = parent;
                }
                return;
            }
        openQ.insert(new ComparableBoard(board, step, parent));
    }

    private void addtwinChild(Board board, int step, ComparableBoard parent) {
        for (ComparableBoard cb : twinCloseQ)
            if (board.equals(cb.board))
                return;
        for (ComparableBoard cb : twinOpenQ)
            if (board.equals(cb.board)) {
                if (step < cb.step) {
                    cb.step = step;
                    cb.setPriority();
                    cb.parent = parent;
                }
                return;
            }
        twinOpenQ.insert(new ComparableBoard(board, step, parent));
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
        if (!solvable)
            return null;
        return res;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // String s = "D:\\Java\\Algoritms in java\\Workspace\\8puzzle\\testing\\8puzzle\\puzzle33.txt";
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

        // print solution to standard output if (!solver.isSolvable()) StdOut.println("No solution possible"); else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }

}
