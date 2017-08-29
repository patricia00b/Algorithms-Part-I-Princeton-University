/******************************************************************************
 *  Compilation:  javac-algs4 BruteCollinearPoints.java
 *  Execution: java BruteCollinearPoints
 *  Dependencies: StdDraw.java Point.java
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *  
 *  Description: Examines 4 points at a time and checks whether they all lie
 *              on the same line segment returning all such line segments
 *
 ******************************************************************************/
//package Point;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
	private ArrayList<LineSegment> lineSegArrayList = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if(points == null) throw new java.lang.NullPointerException();
        for (Point p : points) {
            if (p == null) throw new java.lang.NullPointerException();
        }

        Arrays.sort(points);
        
        int len = points.length;
        for (int i = 0; i < len; i++) {
        	for (int j = i + 1; j < len; j++) {
        		if (points[i].compareTo(points[j]) == 0) throw new java.lang.IllegalArgumentException(); // duplicate
                for (int k = j + 1; k < len; k++) {
                    for (int m = k + 1; m < len; m++) {
                        Point p1 = points[i], p2 = points[j], p3 = points[k], p4 = points[m];
                        if (p1.slopeTo(p2) == p1.slopeTo(p3) && p1.slopeTo(p2) == p1.slopeTo(p4))
                            lineSegArrayList.add(new LineSegment(p1, p4));
        			}
        		}
        	}
        }
	}
	
	// the number of line segments
    public int numberOfSegments() { return lineSegArrayList.size(); }  

    // the line segments
    public LineSegment[] segments() { 
    	LineSegment[] lineSegments = new LineSegment[numberOfSegments()];
    	return lineSegArrayList.toArray(lineSegments);
    }

    public static void main(String[] args) {

        // read the n points from a file
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
