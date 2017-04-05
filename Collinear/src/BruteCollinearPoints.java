import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
  private LineSegment[] allSegments;
  private int segments = 0;

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points1) {
    if (points1 == null)
      throw new NullPointerException();
    for (int i = 0; i < points1.length; i++) {
      if (points1[i] == null)  throw new NullPointerException();
      for (int j = i+1; j < points1.length; j++)             
        if (points1[i].compareTo(points1[j]) == 0) throw new IllegalArgumentException();

    }
    Point[] points = Arrays.copyOf(points1, points1.length);
    Arrays.sort(points);
    Point[] lo = new Point[points.length];
    Point[] hi = new Point[points.length];
    double firstSlope;
    for (int i = 0; i < points.length - 3; i++) {
      for (int j = i + 1; j < points.length - 2; j++) {
        firstSlope = points[i].slopeTo(points[j]);
        for (int k = j + 1; k < points.length - 1; k++) {
          if (points[i].slopeTo(points[k]) == firstSlope) {
            for (int r = points.length - 1; r > k; r--) {
              if (points[i].slopeTo(points[r]) == firstSlope) {
                addSegment(lo, hi, points[i], points[r]);

                break;
              }
            }
            break;
          }
        }
      }
    }
    allSegments = new LineSegment[segments];
    for (int i = 0; i < segments; i++) {
      allSegments[i] = new LineSegment(lo[i], hi[i]);
    }
  }

  private void addSegment(Point[] lo, Point[] hi, Point loP, Point hiP) {
    for (int s = 0; s < segments; s++) {
      double thisSlope = loP.slopeTo(hiP);
      double thatSlope = lo[s].slopeTo(hi[s]);
      if (hi[s] == hiP && thisSlope == thatSlope)
        return;
    }
    lo[segments] = loP;
    hi[segments] = hiP;
    segments++;
  }

  // the number of line segments
  public int numberOfSegments() {
    return segments;
  }

  // the line segments
  public LineSegment[] segments() {
    return Arrays.copyOf(allSegments, segments);
  }

  public static void main(String[] args) {
    // read the n points from a file
    // String s = "D:\\Java\\Algoritms in java\\Workspace\\Collinear\\input\\collinear\\input2.txt";
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
