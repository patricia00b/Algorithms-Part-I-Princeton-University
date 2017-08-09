import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    /**
    * performs a series of computational experiments, trials, to get an average p.
    * compile: javac-algs4 PercolationStats.java
    * run: java-algs4 PercolationStats n trials
    * 
    * output:
    * mean                    = 0.4160675
    * stddev                  = 0.1860310633182044
    * 95% confidence interval = 0.4149144675237681, 0.41722053247623186
    */

    private double NUMBER_TOTAL_SITES;
    private double[] thresholds;
    private double confidenceFraction;
    Stopwatch timer = new Stopwatch();
    double time1;

    public PercolationStats(int n, int trials){    // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        NUMBER_TOTAL_SITES = (double) n*n;
        thresholds = new double[trials];
        Stopwatch time = new Stopwatch();
        for (int i = 0; i < trials; i++) { // performs each trial
            Percolation percolation =new Percolation(n);

            int rowToOpen;
            int colToOpen;

            while(!percolation.percolates()){ // while it doesn't percolate, open a new site randomly
                do {
                    rowToOpen = 1 + StdRandom.uniform(n);
                    colToOpen = 1 + StdRandom.uniform(n);
                }
                while(percolation.isOpen(rowToOpen, colToOpen));

                percolation.open(rowToOpen,colToOpen);
            }
            
            thresholds[i] = (percolation.numberOfOpenSites() + 0.0)/NUMBER_TOTAL_SITES;
            time1 = timer.elapsedTime();
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