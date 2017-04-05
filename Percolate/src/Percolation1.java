
public class Percolation1 {

  private int[][][] scales;
  private boolean[][] isOpen;
  private int size;
  private int openSites;

  public Percolation1(int n) { // create n-by-n grid, with all sites blocked
    if (n < 1) {
      throw new IllegalArgumentException();
    }
    size = n;
    openSites = 0;
    scales = new int[n + 1][n + 1][2];
    isOpen = new boolean[n + 1][n + 1];
    for (int i = 1; i < n; i++) {
      for (int j = 1; j <= n; j++) {
        scales[i][j][0] = i;
        scales[i][j][1] = j;
        isOpen[0][0] = true;
      }
    }

  }

  private int[] getRoot(int row, int col) {
    int[] root = scales[row][col];
    while (root[0] != row || root[1] != col) {
      row = root[0];
      col = root[1];
      root = scales[row][col];
    }
    return scales[row][col];

  }

  private void connect(int row1, int col1, int row2, int col2) {
    int[] root1 = getRoot(row1, col1);
    int[] root2 = getRoot(row2, col2);
    if (root1 == root2) {
      return;
    }
    if (row1 > row2) {
      root1[0] = row2;
      root1[1] = col2;
    } else {
      root2[0] = row1;
      root2[1] = col1;
    }

  }

  public void open(int row, int col) { // open site (row, col) if it is not open already
    if (row < 1 || row > size || col < 1 || col > size) {
      throw new IndexOutOfBoundsException();
    }
    isOpen[row][col] = true;
    openSites++;
    if (col < size && isOpen[row][col + 1]) {
      connect(row, col, row, col + 1);
    }
    if (col > 1 && isOpen[row][col - 1]) {
      connect(row, col, row, col - 1);
    }
    if (row > 1 && isOpen[row - 1][col]) {
      connect(row, col, row - 1, col);
    }
    if (row < size && isOpen[row + 1][col]) {
      connect(row, col, row + 1, col);
    }

  }

  public boolean isOpen(int row, int col) { // is site (row, col) open?
    if (row < 1 || row > size || col < 1 || col > size) {
      throw new IndexOutOfBoundsException();
    }
    return isOpen[row][col];
  }

  public boolean isFull(int row, int col) { // is site (row, col) full?connects top
    if (row < 0 || row > size || col < 0 || col > size) {
      throw new IndexOutOfBoundsException();
    }
    if (!isOpen[row][col]) {
      return false;
    }
    int[] root = scales[row][col];
    while (root[0] != 1) {
      if (root[0] == row && root[1] == col)
        return false;

      row = root[0];
      col = root[1];
      root = scales[row][col];
    }
    return true;
  }

  public int numberOfOpenSites() { // number of open sites
    return openSites;
  }

  public boolean percolates() { // does the system percolate?
    return isFull(0, 0);
  }

  public static void main(String[] args) { // test client (optional)

  }

}
