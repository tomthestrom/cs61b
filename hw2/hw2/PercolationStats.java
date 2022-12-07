package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Perform T independent experiments on an N-by-N grid
 * Calculates:
 *  mean
 *  standard deviation
 *  low endpoint of 95% confidence interval
 *  high endpoint of 95% confidence interval
 */
public class PercolationStats {




    /**
     * Defines grid size as NxN
     */
    private int N;

    /**
     * Defined by parameter T
     */
    int experimentsAmount;

    /**
     * Sample mean of percolation threshold
     * Calculated in the constructor
     */
    private double mean;

    /**
     * Sample standard deviation of percolation threshold
     * Calculated in the constructor
     */
    private double stdDev;

    private PercolationFactory percolationFactory;

    /**
     * Calculates the data as / specification on init.
     * @param N - int grid size
     * @param T - int sample size
     * @param pf - an instance of PercolationFactory
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be higher than 0, N: " + N + " T: " +T);
        }

        this.N = N;
        experimentsAmount = T;
        percolationFactory = pf;

        //ratio of open sites to total sites - till percolation reached for each T;
        double[] openSitesRatioLog = new double[experimentsAmount];

        for (int i = 0; i < T; i++) {
            openSitesRatioLog[i] = (double) sitesTillNPercolates() / Math.pow(N, 2);
        }

        mean = StdStats.mean(openSitesRatioLog, 0, openSitesRatioLog.length);
        stdDev = StdStats.stddev(openSitesRatioLog, 0, openSitesRatioLog.length);

    }

    /**
     * sample mean of percolation threshold
     * */
    public double mean() {
        return mean;
    }

    /**
     * sample standard deviation of percolation threshold
     * */
    public double stddev() {
        return stdDev;
    }

    /**
     * low endpoint of 95% confidence interval
     */
    public double confidenceLow() {
        return mean - ((1.96 * stdDev)/Math.sqrt(experimentsAmount));
    }

    /**
     * high endpoint of 95% confidence interval
     */
    public double confidenceHigh() {
        return mean + ((1.96 * stdDev)/Math.sqrt(experimentsAmount));
    }

    /**
     * Returns the nr of the sites opened till
     */
    private int sitesTillNPercolates() {
        Percolation p = percolationFactory.make(this.N);

        while (!p.percolates()) {
           openRandomSite(p);
        }

        return p.numberOfOpenSites();
    }

    /**
     * Get a random nr. in the range of our grid - used for generating random coords
     */
    private int getRandomSiteCoord() {
       return StdRandom.uniform(0, N);
    }

    /**
     * Opens a random site - with checks - if the randomly generated site is already opened, tries to open a new one
     */
    private void openRandomSite(Percolation p) {
       int row = getRandomSiteCoord();
       int col = getRandomSiteCoord();

       while (p.isOpen(row, col)) {
           row = getRandomSiteCoord();
           col = getRandomSiteCoord();
       }

       p.open(row, col);
    }

    public static void main(String args[]) {
        PercolationStats ps = new PercolationStats(50, 50, (new PercolationFactory()));

        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLow());
        System.out.println(ps.confidenceHigh());
    }
}
