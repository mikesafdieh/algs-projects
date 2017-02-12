/**
 * Created by michaelsafdieh on 2/9/17.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import java.util.Arrays;

/***********************************************************************************************************************
 * public class FastCollinearPoints {
 *     public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
 *     public           int numberOfSegments()        // the number of line segments
 *     public LineSegment[] segments()                // the line segments
 * }
 **********************************************************************************************************************/

public class FastCollinearPoints {

    private int numberOfSegments;
    private LineSegment[] segments;

    /**
     * finds all line segments containing 4 or more points
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        //TODO: **DEBUG**

        final int N = points.length;
        Point p, q1, q2;
        LineSegment[] tempSegs = new LineSegment[N];

        System.out.println("\nDEBUGGING\n\n");
        System.out.println("Points:");
        printPoints(points); // **DEBUG**

        // loop through each point p in points as the reference point for slopes
        for (int i = 0; i < N; i++) {
//            System.out.println("Outer Loop: i = " + i);
            p = points[i];
            int numPoints = 0; // number of points in a given line segment

            System.out.println("Reference point p: " + p);
            Arrays.sort(points, p.slopeOrder()); // sort points by slopes with respect to point p
            System.out.println("Points sorted by slope:");
            printPoints(points);

            for (int j = 2; j < N; j++) {
                q1 = points[j];
                q2 = points[j-1];
                if (p.slopeTo(q1) == p.slopeTo(q2)) {
                    numPoints++;
                    continue;
                }
                if (numPoints >= 3) {
                    System.out.println("!!!!numPoints >= 3!!!!");
                    Arrays.sort(points, j - numPoints, j);
                    tempSegs[numberOfSegments++] = new LineSegment(points[j-numPoints], points[j-1]);
                }
            }
        }

        // copy all tempSegs to segments array
        segments = new LineSegment[numberOfSegments]; // array size is the exact number of segments
        for (int i = 0; i < numberOfSegments; i++)
            segments[i] = tempSegs[i];
    }

    /**
     * the number of line segments
     * @return
     */
    public int numberOfSegments() {
        //TODO: fill in...
        return numberOfSegments;
    }

    /**
     * the line segments
     * @return
     */
    public LineSegment[] segments() {
        //TODO: fill in...
        return segments;
    }

    private void printPoints(Point[] points) {
        for (Point p : points)
            System.out.print(p + " ");
        System.out.println("\n");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
