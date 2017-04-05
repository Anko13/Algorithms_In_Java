

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private WeightedQuickUnionUF grid;
  private boolean[] isOpen;
  private int size;
  private int opened;
  private boolean isPerc;
  

  public Percolation(int n) // create n-by-n grid, with all sites blocked
  {
    if (n <= 0)
      throw new IllegalArgumentException();
    size = n;
    grid = new WeightedQuickUnionUF((n * n) + 1);
    isOpen = new boolean[(n * n) + 1];
    isOpen[0] = true;
      
    
    }
 

  private int getNumber(int row, int col) // converts 2d scales coordinates to 1d array number;
  {
    return (row - 1) * size + col;
  }

  public void open(int row, int col) // open site (row, col) if it is not open already
  {
    if (row < 1 || row > size || col < 1 || col > size)
      throw new IndexOutOfBoundsException();
    int scale = getNumber(row, col);
    if (isOpen[scale])    return;
    isOpen[scale] = true;
    opened++;
    if (row == 1) grid.union(scale, 0);
    if (row > 1 && isOpen[getNumber(row - 1, col)])
      { grid.union(scale, getNumber(row - 1, col));
     if (row == size) isPerc = isFull(row, col); }
    if (row < size && isOpen[getNumber(row + 1, col)])
      grid.union(scale, getNumber(row + 1, col));
    if (col > 1 && isOpen[getNumber(row, col - 1)])
      grid.union(scale, getNumber(row, col - 1));
    if (col < size && isOpen[getNumber(row, col + 1)])
      grid.union(scale, getNumber(row, col + 1));

  }

  public boolean isOpen(int row, int col) // is site (row, col) open?
  {
    if (row < 1 || row > size || col < 1 || col > size)
      throw new IndexOutOfBoundsException();
    return isOpen[getNumber(row, col)];
  }

  public boolean isFull(int row, int col) // is site (row, col) full?
  {
    if (row < 1 || row > size || col < 1 || col > size)
      throw new IndexOutOfBoundsException();
    if (!isOpen[getNumber(row, col)]) return false;
    return grid.connected(0, getNumber(row, col));
  }

  public int numberOfOpenSites() // number of open sites
  {
    return opened;
  }

  public boolean percolates() // does the system percolate?
  {
    return isPerc;
  }

  public static void main(String[] args) // test client (optional)
  {
    
  
    
   }

}
