package main;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

@SuppressWarnings("WeakerAccess")
public class PercolationStats {
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        final Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            percolation.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
        }
        percolation.printGrid();
    }

    // sample mean of percolation threshold
    public double mean() {
        return 0;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0;
    }

    // test client (described below)
    public static void main(String[] args) {
        final PercolationStats percolationStats = new PercolationStats(5, 2);
    }
}
