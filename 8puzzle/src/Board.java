import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class Board {

    private int moves;
    private int[][] blocks;
    private int size;
    private int zeroi;
    private int zeroj;
    private int manhattanV;
    private int hammingV;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)

    public Board(int[][] blocks) {
        // this.blocks = blocks;
        size = blocks.length;
        moves = 0;
        this.blocks = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0)
                    zeroi = i;
                zeroj = j;
            }
        setHamming();
        setManhattan();
    }

    private Board() {
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of blocks out of place
    public int hamming() {
        return hammingV;
    }

    private void setHamming() {
        int res = moves;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (this.blocks[i][j] != 0 && blocks[i][j] != (i * size + j + 1))
                    res++;
        hammingV = res;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattanV;
    }

    private void setManhattan() {
        int res = moves;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                if (blocks[i][j] == 0)
                    continue;
                res += Math.abs((blocks[i][j] - 1) / size - i);
                res += Math.abs((blocks[i][j] - 1) % size - j);
            }
        manhattanV = res;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (blocks[i][j] != (i * size + j + 1) && blocks[i][j] != 0)
                    return false;

        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board b = new Board();
        b.blocks = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                b.blocks[i][j] = this.blocks[i][j];

        b.moves = moves;
        b.size = size;
        b.zeroi = zeroi;
        b.zeroj = zeroj;
        b.manhattanV = manhattanV;
        b.hammingV = hammingV;

        return b;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (this.blocks[i][j] != that.blocks[i][j])
                    return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        Queue<Board> childs = new Queue<>();
        if (zeroi > 0)
            childs.enqueue(swap(zeroi - 1, zeroj));
        if (zeroi < size - 1)
            childs.enqueue(swap(zeroi + 1, zeroj));
        if (zeroj > 0)
            childs.enqueue(swap(zeroi, zeroj - 1));
        if (zeroj < size - 1)
            childs.enqueue(swap(zeroi, zeroj + 1));
        return childs;

    }

    private Board swap(int i, int j) {
        Board res = this.twin();
        res.blocks[zeroi][zeroj] = res.blocks[i][j];
        res.blocks[i][j] = 0;
        res.moves = this.moves + 1;
        res.setManhattan();
        res.setHamming();
        return res;
    }

    // string representation of this board (in the output format specified
    // below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests
    public static void main(String[] args) {
        // read blocks from file
        String s = "D:\\Java\\Algoritms in java\\Workspace\\8puzzle\\testing\\8puzzle\\puzzle01.txt";
        In in = new In(s);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board b1 = new Board(blocks);

        Iterable<Board> it = b1.neighbors();
        for (Board b : it) {
            System.out.print(b);
            System.out.println("h=" + b.hamming() + " m=" + b.manhattan());
        }

    }

}
