/******************************************************************************
 *  Compilation:  javac-algs4 -d . Point.java
 * // javac-algs4 -d . Point.java to create package
 *  Execution:    java-algs4 Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *  
 *  Goal: Given a set of n distinct points in the plane, find every (maximal) 
 *       line segment that connects a subset of 4 or more of the points.
 *
 ******************************************************************************/
//package Point;

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

// immutable data type Point that represents a point in the plane
public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point


	/**
     * Initializes a new point.
     *
     * @param  x the x-coordinate of the point
     * @param  y the y-coordinate of the point
     */
    public Point(int x, int y) {
	    this.x = x;
        this.y = y;
	}

    /**
     * Draws this point to standard draw.
     */
    public   void draw() { 
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point that
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }   
    
    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value 0 if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        int one = 1;
        if (this.x == that.x && this.y == that.y) return 0;
        else if (this.y < that.y || (this.y == that.y && this.x < that.x)) return -one;
        else return +one;
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        else if (this.x == that.x) return Double.POSITIVE_INFINITY;
        else if (this.y == that.y) return +0.0;
        return (that.y - this.y) / (double) (that.x - this.x);
    }
    
    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            public int compare(Point q1, Point q2) {
                double Point1Slope = Point.this.slopeTo(q1);
                double Point2Slope = Point.this.slopeTo(q2);

                if (Point1Slope > Point2Slope) return +1;
                else if (Point1Slope < Point2Slope) return -1;
                else return 0;
            }
        };
    }

    /**
     * Unit tests the Point data type.
     */
    //public static void main(String[] args)
}