import edu.princeton.cs.algs4.WeightedQuickUnionUF;

@SuppressWarnings("WeakerAccess")
public class Percolation {

    private int openSitesCount = 0;
    private int fulfillCondition[][];
    private int[][] grid;
    private final WeightedQuickUnionUF weightedQuickUnionUF;

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
        weightedQuickUnionUF = new WeightedQuickUnionUF(((int) Math.pow(n, 2)));
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = 1;
            openSitesCount++;
            connectNeighbors(row, col);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1] == 1;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return isOpen(row, col);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        int[] topSites = countOpenSitesOnTop();
        int[] bottomSites = countOpenSitesOnBottom();
        if (topSites.length == 0 || bottomSites.length == 0)
            return false;
        else {
            for (int topSite : topSites) {
                for (int bottomSite : bottomSites) {
                    if (weightedQuickUnionUF.connected(topSite, bottomSite))
                        return true;
                }
            }
            return false;
        }
    }

    private int getFlatten(int row, int col) {
        validate(row, col);
        return --row * grid.length + --col;
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

    private void validate(int row, int col) {
        if (row > grid.length || col > grid.length)
            throw new IndexOutOfBoundsException();
        if (row < 1 || col < 1)
            throw new IndexOutOfBoundsException();
    }

    private void printFlatten() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid.length; j++)
                stringBuilder.append(getFlatten(i + 1, j + 1))
                        .append((i + 1 + j + 1) != 2 * grid.length ? "," : "");
        System.out.println(stringBuilder.toString());
    }


    private int[] countOpenSitesOnTop() {
        int[] openSites;
        int count = 0;
        for (int j = 0; j < grid.length; j++)
            if (isOpen(1, j + 1))
                count++;
        openSites = new int[count];
        if (count == 0) {
            return openSites;
        } else {
            int k = 0;
            for (int j = 0; j < grid.length; j++)
                if (isOpen(1, j + 1)) {
                    openSites[k] = getFlatten(1, j + 1);
                    k++;
                }
            return openSites;
        }
    }

    private int[] countOpenSitesOnBottom() {
        int[] openSites;
        int count = 0;
        for (int j = 0; j < grid.length; j++)
            if (isOpen(grid.length, j + 1))
                count++;
        openSites = new int[count];
        if (count == 0) {
            return openSites;
        } else {
            int k = 0;
            for (int j = 0; j < grid.length; j++)
                if (isOpen(grid.length, j + 1)) {
                    openSites[k] = getFlatten(grid.length, j + 1);
                    k++;
                }
            return openSites;
        }
    }

}
