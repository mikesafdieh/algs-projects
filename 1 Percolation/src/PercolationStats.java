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

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private double[] thresholds;
    private final int TRIALS;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        validateInputs(n, trials);
        TRIALS = trials;
        thresholds = new double[TRIALS];

        for (int i = 0; i < TRIALS; i++) {
            Percolation perc = new Percolation(n);

            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1); // random int in [1, n+1) i.e. [1, n]
                int col = StdRandom.uniform(1, n + 1); // random int in [1, n+1) i.e. [1, n]
                if (!perc.isOpen(row, col))
                    perc.open(row,col);
            }
            double openSites = perc.numberOfOpenSites();
            double totalSites = n*n;
            thresholds[i] = openSites / totalSites;
        }

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
        return mean() - ( (1.96*stddev()) / Math.sqrt(TRIALS));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ( (1.96*stddev()) / Math.sqrt(TRIALS));
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

    // test client
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(n, trials);
        String meanString = "mean";
        String stddevString = "stddev";
        String confidenceString = "95% confidence interval";

        System.out.printf("%-23s = %.18f\n", meanString, percStats.mean());
        System.out.printf("%-23s = %.18f\n", stddevString, percStats.stddev());
        System.out.printf("%-23s = [%.18f, %.18f]\n", confidenceString, percStats.confidenceLo(), percStats.confidenceHi());

    }


}
