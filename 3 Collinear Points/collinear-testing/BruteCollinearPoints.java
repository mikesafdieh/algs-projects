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

    //TODO: decide on proper instance variables
    private int numberOfSegments;
    private LineSegment[] segments;

    /**
     * finds all line segments containing 4 points
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        //TODO: throw proper exceptions
        Point p,q,r,s; // declare 4 points (not necessary but just looks cleaner)
        final int N = points.length;

        numberOfSegments = 0;
        tempSegments = new LineSegment[N-3]; // N-3 is the max # of quadruples of collinear points in this implementation
        // NOTE: maybe use of a resizing array would be better?
        System.out.println("\nTESTING**************\n"); // **DEBUG**

        Arrays.sort(points);

        for (int i = 0; i < N; i++){
            for (int j = i+1; j < N; j++){
                for (int k = j+1; k < N; k++){
                    for (int l = k+1; l < N; l++){
                        p = points[i]; 
                        q = points[j]; 
                        r = points[k];
                        s = points[l];
                        System.out.println("i:" + i + "  j:" + j + "  k:" + k + "  l:" + l);
                        if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)){
                            segments[numberOfSegments++] = new LineSegment(p,s);
                            System.out.println(segments[numberOfSegments-1]); // **DEBUG**
                        }
                    }
                }
            }
            i+=4;
        }
        System.out.println("!!!DONE!!!");
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
