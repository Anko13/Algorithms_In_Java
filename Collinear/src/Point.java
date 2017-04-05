import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
  
  private final int x;     // x-coordinate of this point
  private final int y;     // y-coordinate of this point

  // constructs the point (x, y)
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // draws this point
  public void draw() {
    StdDraw.point(x, y);
  }                               

  // draws the line segment from this point to that point
  public void drawTo(Point that) {
    StdDraw.line(this.x, this.y, that.x, that.y);
  }                  

  // string representation
  public String toString() { return "(" + x + ", " + y + ")"; }                          

  // compare two points by y-coordinates, breaking ties by x-coordinates
  public   int compareTo(Point that) {
    if (this.y < that.y) return -1;
    else if (this.y > that.y) return 1;
    else {
    if (this.x < that.x) return -1;
    else if (this.x > that.x) return 1; }
    return 0; }     

  // the slope between this point and that point
  public  double slopeTo(Point that) {
    if (this.x == that.x)
      if (this.y == that.y) return Double.NEGATIVE_INFINITY;
      else return Double.POSITIVE_INFINITY;
    if (this.y == that.y) return 0.0;
    return  (double) (that.y-this.y)/(that.x-this.x);
         }

  // compare two points by slopes they make with this point
  public Comparator<Point> slopeOrder() {
    return new MyCompare(this);
  }
  
  private class MyCompare implements Comparator<Point> {

   private Point a;
    
    MyCompare(Point a) { this.a = a; }
    
    public int compare(Point a1, Point a2) {
      
      if (a.slopeTo(a1) > (a).slopeTo(a2)) return 1;
      else if (a.slopeTo(a1) < (a.slopeTo(a2))) return -1;
      return 0;
    } 
    }

  public static void main(String[] args) {
  }
}
