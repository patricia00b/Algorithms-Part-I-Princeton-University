/******************************************************************************
* Name:    Patrícia Bota
* NetID:   patricia00b
* Precept: P01
*
* Partner Name:    N/A
* Partner NetID:   N/A
* Partner Precept: N/A
* 
* Description:  performs a series of computational experiments, trials, to get an average p.
* Usage:
* javac-algs4 PercolationStats.java
* java-algs4 PercolationStats input6.txt
* checkstyle-algs4 Percolation.java
* findbugs-algs4 Percolation.class
******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] thresholds;
    private final double confidenceFraction;

    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        double NUMBER_TOTAL_SITES = (double) n*n;
        thresholds = new double[trials];
        for (int i = 0; i < trials; i++) { // performs each trial
            Percolation percolation = new Percolation(n);

            int rowToOpen;
            int colToOpen;

            while (!percolation.percolates()) { // while it doesn't percolate, open a new site randomly
                do {
                    rowToOpen = 1 + StdRandom.uniform(n);
                    colToOpen = 1 + StdRandom.uniform(n);
                }
                while (percolation.isOpen(rowToOpen, colToOpen));

                percolation.open(rowToOpen, colToOpen);
            }
            
            thresholds[i] = (percolation.numberOfOpenSites() + 0.0)/ NUMBER_TOTAL_SITES;
        }
        confidenceFraction = (1.96 * StdStats.stddev(thresholds)) / Math.sqrt(thresholds.length);
    }

    public double mean() {                          // sample mean of percolation threshold
        return StdStats.mean(thresholds);
    }

    public double stddev() {                        // sample standard deviation of percolation threshold
        return StdStats.stddev(thresholds);
    }
   
    public double confidenceLo() {                 // low  endpoint of 95% confidence interval
        double mean = this.mean();
        return mean - confidenceFraction;
    }
    public double confidenceHi() {                  // high endpoint of 95% confidence interval
        double mean = this.mean();
        return mean + confidenceFraction;
    }

    public static void main(String[] args) {        // test client (described below)
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
