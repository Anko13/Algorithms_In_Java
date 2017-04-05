import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

  private int numbOfSegments;
  private LineSegment[] segments;
  private Point[] hi;
  
  // finds all line segments containing 4 or more points
  public FastCollinearPoints(Point[] points1) {
    Point[] points = Arrays.copyOf(points1, points1.length);

    // input corner cases check
    if (points == null)
      throw new NullPointerException();
    Arrays.sort(points);
    if (points[0] == null)
      throw new NullPointerException();
    for (int i = 1; i < points.length; i++) {
      if (points[i] == null)
        throw new NullPointerException();
      if (points[i].compareTo(points[i - 1]) == 0)
        throw new IllegalArgumentException();
    }

    segments = new LineSegment[points.length * points.length + 1];
    double[] slope = new double[points.length * points.length + 1];
    hi = new Point[points.length * points.length + 1];
    int n = 1;
    for (int p = 0; p < points.length - 3; p++) { // for each point p
      Point[] npoints = new Point[points.length - 1 - p]; // array of not checked points
      Comparator<Point> compar = points[p].slopeOrder();
      for (int i = 0; i < npoints.length; i++)
        npoints[i] = points[i + p + 1];
      Arrays.sort(npoints, compar); // non checked points sorted in order of slope to points[p]
        for (int i = 0; i < npoints.length - 2; i += n) {
        double thisSlope = points[p].slopeTo(npoints[i]);
        if (thisSlope == points[p].slopeTo(npoints[i+2])) {
          n = 3;
          while (n < npoints.length - i && thisSlope == points[p].slopeTo(npoints[i+n]))
            n++;
          addSegment(slope, points[p], npoints[i + n - 1], thisSlope);
        } else
         if (n != 1) n = 1;
      }
    }


  }

  private void addSegment(double[] slope, Point loP, Point hiP,
      double thisSlope) {
    for (int s = 0; s < numbOfSegments; s++) {
      if (hi[s] == hiP && thisSlope == slope[s])
        return;
    }
    
    hi[numbOfSegments] = hiP;
    slope[numbOfSegments] = thisSlope;
    segments[numbOfSegments] = new LineSegment(loP, hiP);
    numbOfSegments++;
  }

  // the number of line segments
  public int numberOfSegments() {
    return numbOfSegments;
  }

  // the line segments
  public LineSegment[] segments() {
       return Arrays.copyOf(segments, numbOfSegments);
  }

  public static void main(String[] args) {
    // read the n points from a file
    // String s = "D:\\Java\\Algoritms in
    // java\\Workspace\\Collinear\\input\\collinear\\input56.txt";
    // In in = new In(s);

    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
