package main;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

@SuppressWarnings("WeakerAccess")
public class Percolation {

    int openSitesCount = 0;
    int fulfillCondition[][];
    int[][] grid;
    final WeightedQuickUnionUF weightedQuickUnionUF;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        grid = new int[n][n];
        fulfillCondition = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
                fulfillCondition[i][j] = 0;
            }
        }
        weightedQuickUnionUF = new WeightedQuickUnionUF(n);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = 1;
            openSitesCount++;
            connectNeighbors(row, col);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > grid.length || col > grid.length)
            throw new IllegalArgumentException();
        if (row < 1 || col <  1)
            throw new IllegalArgumentException();
        return grid[row - 1][col - 1] == 1;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return fulfillCondition[row - 1][col - 1] == 1;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    private void printGrid() {
        for (int[] i : this.grid)
            System.out.println(Arrays.toString(i));
    }

    private int getFlatten(int row, int col) {
        return --row * grid.length + col;
    }

    private void connectNeighbors(int row, int col) {
        //higher neighbor
        if (row != 1)
            if (isOpen(row - 1, col) && !weightedQuickUnionUF.connected(getFlatten(row, col),
                    getFlatten(row - 1, col)))
                weightedQuickUnionUF.union(getFlatten(row, col), getFlatten(row - 1, col));
        //lower neighbor
        if (row != grid.length)
            if (isOpen(row + 1, col) && !weightedQuickUnionUF.connected(getFlatten(row, col),
                    getFlatten(row + 1, col)))
                weightedQuickUnionUF.union(getFlatten(row, col), getFlatten(row + 1, col));
        //left neighbor
        if (col != 1)
            if (isOpen(row, col - 1) && !weightedQuickUnionUF.connected(getFlatten(row, col),
                    getFlatten(row, col - 1)))
                weightedQuickUnionUF.union(getFlatten(row, col), getFlatten(row, col - 1));
        //right neighbor
        if (col != grid.length)
            if (isOpen(row, col + 1) && !weightedQuickUnionUF.connected(getFlatten(row, col),
                     getFlatten(row, col + 1)))
                weightedQuickUnionUF.union(getFlatten(row, col), getFlatten(row, col + 1));
    }

    private void printFlatten() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid.length; j++)
                stringBuilder.append(getFlatten(i + 1, j + 1))
                        .append((i + 1 + j + 1) != 2 * grid.length ? "," : "");
        System.out.println(stringBuilder.toString());
    }

    public static void main(String[] argv) {
        final Percolation percolation = new Percolation(6);
        percolation.open(1,1);
        percolation.printGrid();
    }

}
