
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private int trials; 
  private double[] tresholdPercent; // treshold persent for each trial;

  public PercolationStats(int n, int trials) // perform trials independent
  // experiments on an n-by-n grid
  {
    if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
    this.trials = trials;
    Percolation perc;

    tresholdPercent = new double[trials];
    int col;
    int row;
    for (int i = 0; i < trials; i++) {

      perc = new Percolation(n);
      while (true) {
        col = StdRandom.uniform(1, n + 1);
        row = StdRandom.uniform(1, n + 1);
        if (perc.isOpen(row, col))
          continue;
        perc.open(row, col);
        if (perc.percolates())
          break;
      }

      tresholdPercent[i] = (((double) perc.numberOfOpenSites()) / (n * n));
    }

  }

  public double mean() // sample mean of percolation threshold
  {
    double sum = 0;
    for (double d : tresholdPercent)
      sum += d;
    return sum / tresholdPercent.length;
  }

  public double stddev() // sample standard deviation of percolation threshold
  {
    if (trials == 0)
      return Double.NaN;
    return StdStats.stddev(tresholdPercent);
  }

  public double confidenceLo() // low endpoint of 95% confidence interval
  {
    return (mean() - ((1.96 * stddev()) / Math.sqrt(trials)));
  }

  public double confidenceHi() // high endpoint of 95% confidence interval

  {
    return (mean() + ((1.96 * stddev()) / Math.sqrt(trials)));
  }

  public static void main(String[] args) // test client (described below)
  {

    PercolationStats test = new PercolationStats(Integer.parseInt(args[0]),
        Integer.parseInt(args[0]));
    StdOut.printf("%-23s = %.17f \n", "mean", test.mean());
    StdOut.printf("%-23s = %.17f \n", "stddev", test.stddev());
    StdOut.printf("%-23s = [%.16f , %.16f]", "95% confidence interval", test.confidenceLo(),
        test.confidenceHi());

  }

}
