import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class Board {

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
        this.blocks = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    zeroi = i;
                    zeroj = j;
                }

            }
        setHamming();
        setManhattan();
    }

    private Board() {
    }

    // board dimension n
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        return hammingV;
    }

    private void setHamming() {
        int res = 0;
        int block;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                block = this.blocks[i][j];
                if (block != 0 && block != (i * size + j + 1))
                    res++;
            }
        hammingV = res;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattanV;
    }

    private void setManhattan() {
        int res = 0;
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
                if (blocks[i][j] != 0 && blocks[i][j] != (i * size + j + 1))
                    return false;

        return true;
    }

    // a board that is obtained by exchanging any pair of blocks

    public Board twin() {
        int[][] blocksN = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                blocksN[i][j] = this.blocks[i][j];
        int exch;
        int s = size - 1;
        if (blocksN[0][0] == 0) {
            exch = blocksN[0][s];
            blocksN[0][s] = blocksN[s][0];
            blocksN[s][0] = exch;
        } else if (blocksN[0][1] == 0) {
            exch = blocksN[0][0];
            blocksN[0][0] = blocksN[s][s];
            blocksN[s][s] = exch;
        } else {
            exch = blocksN[0][0];
            blocksN[0][0] = blocksN[0][1];
            blocksN[0][1] = exch;
        }
        return new Board(blocksN);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null || y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension())
            return false;
        if (this.size != that.size || this.manhattanV != that.manhattanV)
            return false;

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

    private Board swap(int in, int jn) {
        Board res = new Board();
        res.blocks = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                res.blocks[i][j] = blocks[i][j];
            }

        res.blocks[zeroi][zeroj] = res.blocks[in][jn];
        res.blocks[in][jn] = 0;
        res.size = size;
        res.zeroi = in;
        res.zeroj = jn;
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
        // s.append("h=" + this.hamming() + " m=" + this.manhattan());
        // s.append("\n");
        return s.toString();
    }

    // unit tests
    public static void main(String[] args) {
        // read blocks from file
        // String s = "D:\\Java\\Algoritms in java\\Workspace\\8puzzle\\testing\\8puzzle\\puzzle04.txt";
        // In in = new In(s);
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board b1 = new Board(blocks);

        Iterable<Board> it = b1.neighbors();
        System.out.print(b1);
        System.out.print(b1.twin());
        for (Board b : it) {
            // System.out.print(b);
            // System.out.println("h=" + b.hamming() + " m=" + b.manhattan());
        }

    }

}
