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
        final int N = points.length;

        // corner cases
        if (points == null) throw new NullPointerException("argument is null");
        for (int i = 0; i < N; i++)
            if (points[i] == null) throw new NullPointerException("argument is null");


        //TODO: **DEBUG**

        Point p, q1, q2;
        LineSegment[] tempSegs = new LineSegment[N];
        Point[] tempPoints = new Point[N];
//        Double[] slopes = new Double[N];

        // copy points to temp points
        for (int i = 0; i < N; i++)
            tempPoints[i] = points[i];

//        System.out.println("\nDEBUGGING\n\n"); // **DEBUG**
//        System.out.println("Points:"); // **DEBUG**
//        printPoints(points); // **DEBUG**

        // loop through each point p in points as the reference point for slopes
        for (int i = 0; i < N; i++) {

            p = points[i];
            int numPoints = 0; // number of points in a given line segment

//            System.out.println("\n\n***********************************************************************************\n\n"); // **DEBUG**
//            System.out.println("Reference point p: " + p); // **DEBUG**

            Arrays.sort(tempPoints, p.slopeOrder()); // sort points by slopes with respect to point p

//            System.out.println("Points sorted by slope:"); // **DEBUG**
//            printPoints(tempPoints); // **DEBUG**

            for (int j = 2; j < N; j++) {
                q1 = tempPoints[j-1];
                q2 = tempPoints[j];

//                System.out.println("numPoints: " + numPoints); // **DEBUG**
//                System.out.println("j: " + j); // **DEBUG**
//                System.out.println("slopes of " + q1 + " and " + q2 + ": "); // **DEBUG**
//                System.out.println(p.slopeTo(q1) + " and " + p.slopeTo(q2)); // **DEBUG**
//                System.out.println(); // **DEBUG**

                if (p.slopeTo(q1) == p.slopeTo(q2)) {
                    numPoints++;
                }
                else if (numPoints >= 3) {
                    System.out.println("!!!! numPoints == " + numPoints + " !!!!"); // **DEBUG**

                    Arrays.sort(tempPoints, j - numPoints, j); // sort by points to get the endpoints
                    tempSegs[numberOfSegments++] = new LineSegment(tempPoints[j-numPoints], tempPoints[j-1]);
                    Arrays.sort(tempPoints, j - numPoints, j, p.slopeOrder()); //resort subarray by slope for the outer loop
                }
                numPoints = 0;
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
