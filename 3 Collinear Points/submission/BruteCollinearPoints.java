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
 * public class BruteCollinearPoints {
 *     public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
 *     public           int numberOfSegments()        // the number of line segments
 *     public LineSegment[] segments()                // the line segments
 * }
 **********************************************************************************************************************/

public class BruteCollinearPoints {

    private int numberOfSegments;
    private LineSegment[] segments;

    /**
     * finds all line segments containing 4 points
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        final int N = points.length;

        // corner cases
        if (points == null) throw new NullPointerException("argument is null");
        for (int i = 0; i < N; i++)
            if (points[i] == null) throw new NullPointerException("argument is null");
        for (int i = 0; i < N; i++) // check for duplicate points (corner case) here as opposed to at beginning
            for (int j = i+1; j < N; j++)
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException("duplicate points found");

        if (N < 4) {
            numberOfSegments = 0;
            segments = new LineSegment[numberOfSegments];
            return;
        }

        Point[] tempPoints = new Point[N]; // because points[] should be immutable
        arrayCopy(points, tempPoints);



        Point p,q,r,s; // declare 4 points (not necessary but just looks cleaner)
        double pq, qr, rs; // declare the 3 slopes that will be compared
        numberOfSegments = 0;
        LineSegment[] tempSegments = new LineSegment[N-3]; // N-3 is the max # of quadruples of collinear points in this implementation
        // NOTE: maybe use of a resizing array would be better?

        Arrays.sort(tempPoints); // sort the points for proper processing
        for (int i = 0; i < N; i++){
            for (int j = i+1; j < N; j++){
                for (int k = j+1; k < N; k++){
                    for (int l = k+1; l < N; l++){
                        p = tempPoints[i]; q = tempPoints[j]; r = tempPoints[k]; s = tempPoints[l];
                        pq = p.slopeTo(q); qr = q.slopeTo(r); rs = r.slopeTo(s);
                        if (pq == qr && qr == rs){
                            tempSegments[numberOfSegments++] = new LineSegment(p,s);
                        }
                    }
                }
            }
        }

        // copy all tempSegments to segments array
        segments = new LineSegment[numberOfSegments]; // array size is the exact number of segments
        for (int i = 0; i < numberOfSegments; i++)
            segments[i] = tempSegments[i];
    }

    /**
     * the number of line segments
     * @return
     */
    public int numberOfSegments() { return numberOfSegments; }

    /**
     * the line segments
     * @return
     */
    public LineSegment[] segments() { return segments; }

    private void arrayCopy(Point[] p1, Point[] p2) {
        assert p1.length == p2.length;
        final int N = p1.length;
        for (int i = 0; i < N; i++)
            p2[i] = p1[i];
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

//                        System.out.println("i:" + i + "  j:" + j + "  k:" + k + "  l:" + l);

//System.out.println("Segment:");
//        System.out.println(tempSegments[numberOfSegments-1]); // **DEBUG**
//        System.out.println("Points:");
//        System.out.println(points[i] + ", " + points[j] + ", " + points[k] + ", " + points[l]);
//        System.out.println("Slopes:");
//        System.out.println(pq + ", " + qr + ", " + rs);
//        System.out.println();