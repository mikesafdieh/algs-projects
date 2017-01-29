/**
 * Created by michaelsafdieh on 1/25/17.
 */

// *** API ***
//
//    public class PercolationStats {
//        public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
//        public double mean()                          // sample mean of percolation threshold
//        public double stddev()                        // sample standard deviation of percolation threshold
//        public double confidenceLo()                  // low  endpoint of 95% confidence interval
//        public double confidenceHi()                  // high endpoint of 95% confidence interval
//
//        public static void main(String[] args)        // test client (described below)
//    }

// NOTE: You can only use the following libraries:
// StdIn, StdOut, StdRandom, StdStats, WeightedQuickUnionUF, and java.lang.

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {

    private double[] thresholds;
    private final int TRIALS;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        validateInputs(n, trials);
        TRIALS = trials;

        Percolation perc = new Percolation(n);

        //TODO: Fill in...
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        //TODO: Fill in...
        return -1;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        //TODO: Fill in...
        return -1;
    }



    /************************************************************************************************************
     *** PRIVATE METHODS ****************************************************************************************
     ************************************************************************************************************/

    private void validateInputs(int n, int trials) {
        if (n <= 0) throw new IllegalArgumentException("n must be greater than 0"); // validation
        if (trials <= 0) throw new IllegalArgumentException("trials must be greater than 0"); // validation
    }



    /************************************************************************************************************
     *** TEST CLIENT ********************************************************************************************
     ************************************************************************************************************/

    // test client (described below)
    public static void main(String[] args) {
        //TODO: Fill in...
        // perform the Monte Carlo Simulation T times
        //TODO: Quickly look up Monte Carlo Simulation (Khan Academy).
    }


}
