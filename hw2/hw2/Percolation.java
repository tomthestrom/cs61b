package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /**
     * 1-dimensional, holds N by N grid
     * Row nr as index / N
     * Col nr as index % N
     */
    private WeightedQuickUnionUF grid;

    /**
     * Mirrors grid
     * Sets value at x, y to true, if site is opened
     */
    private boolean[] valGrid;
    /**
     * Nr of opened sites
     */
    private int openSitesNr;

    /**
     * The value of the virtual top Index
     */
    private int virtualTopIndex;

    /**
     * The value of the virtual bottom Index
     */
    private int virtualBottomIndex;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be > 0");
        }
        int size = N * N;

        //grid + virtual top - index: (size + 1) + virtual bottom - index: (size + 2)
        grid = new WeightedQuickUnionUF(size + 2);
        valGrid = new boolean[size];
        virtualTopIndex = size - 2;
        virtualBottomIndex = size - 1;
        openSitesNr = 0;
    }

    public void open(int row, int col) {

    }
}
