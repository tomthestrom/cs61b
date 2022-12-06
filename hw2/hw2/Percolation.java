package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation data type
 * Models a percolation system
 */
public class Percolation {

    /**
     * Used to define the size of the grid -> N x N
     */
    private int N;
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

        this.N = N;
        int size = N * N;

        //grid + index for virtual top, + index for virtual bottom
        grid = new WeightedQuickUnionUF(size + 2);
        valGrid = new boolean[size];
        virtualTopIndex = size;
        virtualBottomIndex = size + 1;
        openSitesNr = 0;
    }

    /**
     * Opens site at row, col
     * If possible - connects with neighboring sites
     */
    public void open(int row, int col) {
        if (!isValidIndex(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        int index = getIndexFromRowCol(row, col);

        if (isOpen(row, col)) {
            return;
        }

        valGrid[index] = true;
        openSitesNr++;
        union(row, col);
    }

    /**
     * Returns whether a site at row, col is open
     */
    public boolean isOpen(int row, int col) {
        int index = getIndexFromRowCol(row, col);

        //default (closed) value is null
        return valGrid[index];
    }

    /**
     * Indicates whether a site has been filled
     */
    public boolean isFull(int row, int col) {
        if (!isValidIndex(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        int index = getIndexFromRowCol(row, col);
        return grid.connected(index, virtualTopIndex);
    }

    /**
     * Returns the number of open sites
     */
    public int numberOfOpenSites() {
        return openSitesNr;
    }

    /**
     * Returns whether the system percolates - is full/filled connecting the top to the bottom
     */
    public boolean percolates() {
        return grid.connected(virtualTopIndex, virtualBottomIndex);
    }

    /**
     * Converts x, y / row, col to one-dimensional value for grid
     */
    private int getIndexFromRowCol(int row, int col) {
       return N * row + col;
    }

    /**
     * Check if requested index in bounds
     */
    private boolean isValidIndex(int row, int col) {
        int index = getIndexFromRowCol(row, col);

        return index >= 0 && index < valGrid.length;
    }

    /**
     * Creates unions in all possible directions
     */
    private void union(int row, int col) {
        int index = getIndexFromRowCol(row, col);

        unionAbove(row, col);
        unionRight(row, col);
        unionLeft(row, col);
        unionBottom(row, col);

    }

    /**
     * If possible - connects element at  row, col with the element above itself
     */
    private void unionAbove(int row, int col) {
        int index = getIndexFromRowCol(row, col);

        // if top row, connect with virtualTop - fill the site
        if (row == 0) {
            grid.union(virtualTopIndex, index);
            return;
        }

        int indexAbove = getIndexFromRowCol(row - 1, col);

        //if site above the current site is open - union them
        if (valGrid[indexAbove]) {
            grid.union(indexAbove, index);
        }
    }

    /**
     * If possible - connects element at  row, col with the element to the right of itself
     */
    private void unionRight(int row, int col) {
        //col at the right edge;
        if (col == N - 1) {
            return;
        }

        int index = getIndexFromRowCol(row, col);
        int indexRight = getIndexFromRowCol(row, col + 1);

        if (valGrid[indexRight]) {
            grid.union(indexRight, index);
        }
    }

    /**
     * If possible - connects element at  row, col with the element to the left
     */
    private void unionLeft(int row, int col) {
        //col at the left edge;
        if (col == 0) {
            return;
        }

        int index = getIndexFromRowCol(row, col);
        int indexLeft = getIndexFromRowCol(row, col - 1);

        if (valGrid[indexLeft]) {
            grid.union(indexLeft, index);
        }
    }

    /**
     * If possible - connects element at  row, col with the element to the bottom
     */
    private void unionBottom(int row, int col) {
        int index = getIndexFromRowCol(row, col);
        int indexBottom = getIndexFromRowCol(row + 1, col);
        boolean isLastRow = row == N - 1;

        /*
        * if is in the last row and site is connected to virtualTop index (isFull),
        * connect with virtual bottom - we have a percolating system
        *  */

        if (isLastRow && isFull(row, col)) {
            grid.union(index, virtualBottomIndex);
        }

        if (!isLastRow && valGrid[indexBottom]) {
            grid.union(index, indexBottom);
        }
    }
}
