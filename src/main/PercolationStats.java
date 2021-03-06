import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

@SuppressWarnings("WeakerAccess")
public class PercolationStats {
    private Percolation percolation;
    private double[] openSites;
    private double mean;
    private double standardDeviation;
    private double confidenceLo;
    private double confidenceHi;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.openSites = new double[trials];

        for (int i = 0; i < trials; i++) {
            this.percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);

            }
            this.openSites[i] = percolation.numberOfOpenSites();
        }

        this.mean = mean();
        this.standardDeviation = stddev();

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSites);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (1.96 * standardDeviation/ Math.sqrt(openSites.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (1.96 * standardDeviation/ Math.sqrt(openSites.length));
    }

    // test client (described below)
    public static void main(String[] args) {
        final PercolationStats percolationStats = new PercolationStats(5, 5);
    }
}
