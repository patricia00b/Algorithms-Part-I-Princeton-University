/******************************************************************************
*  Name:    Patrícia Bota
*  NetID:   patricia00b
*  Precept: P01
*
*  Partner Name:    N/A
*  Partner NetID:   N/A
*  Partner Precept: N/A
* 
*  Description:  Modeling Percolation using an N-by-N grid and Union-Find data 
*                structures to determine the threshold.
* Usage:
* javac-algs4 PercolationVisualizer.java
* java-algs4 PercolationVisualizer input6.txt
* checkstyle-algs4 Percolation.java
* findbugs-algs4 Percolation.class
******************************************************************************/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// percolation data type
public class Percolation {
    private final WeightedQuickUnionUF id; // id[r][c] = component identifier of i
    private boolean[][] status;
    private int opensites = 0;
    private final int n;
    private int topNode;
    private int bottomNode;

    public Percolation(int n) {
    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own 
     * component. (not open)
     *
     * @param  n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    
        if (n <= 0) throw new java.lang.IllegalArgumentException();
        id = new WeightedQuickUnionUF(n*n+2);
        status = new boolean[n][n];
        this.n = n;
        topNode = n*n;
        bottomNode = n*n+1;
    }

    public void open(int row, int col) {
        /**
        * open site (row, col) if it is not open already. links the site in question to its open neighbors
        * @param indices [row][col] of the site to open
        */

        isIndexValid(row, col);
        row--;
        col--;

        final int CURRENTCORD = xyTo1D(row, col);

        if (status[row][col] == false) { // if it's closed
            status[row][col] = true; // marks it as open
            // connect it to neighbor sites
            // if not on top row
            opensites++;

            if (row == 0) id.union(CURRENTCORD, n*n);
            if (row == n - 1 ) id.union(CURRENTCORD, n*n + 1);
            if (row + 1 < n && status[row+1][col]) id.union(CURRENTCORD, CURRENTCORD + n);
            if (row - 1 >= 0 && status[row-1][col]) id.union(CURRENTCORD, CURRENTCORD - n);
            if (col + 1 < n && status[row][col+1]) id.union(CURRENTCORD, CURRENTCORD + 1);
            if (col - 1 >= 0 && status[row][col-1]) id.union(CURRENTCORD, CURRENTCORD - 1);
        }
    }

    public boolean isOpen(int row, int col) {
        /** 
        * @param indices [row][col] of the site
        * @returns if the site is open: true or false
        */ 
        isIndexValid(row, col);
        row--;
        col--;

        return status[row][col]; // if it's open
    }

    public boolean isFull(int row, int col) {
        /**
        * @param indices [row][col]
        * @returns if the site (row, col) is open and connected to the top site
        */
        isIndexValid(row, col);
        row--;
        col--;

        if (isOpen(row+1, col+1) && id.connected(topNode, xyTo1D(row, col)))
                return true;
        return false;
    }  

    public int numberOfOpenSites() {
        return opensites;
    }

    public boolean percolates() { 
        /**
        * @returns if the system percolates
        */
        // the system percolates if top and bottom are coonected
        return id.connected(topNode, bottomNode);

    }

    private int xyTo1D(int row, int col) {
        return (row*n + col);
    }

    private void isIndexValid(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) throw new java.lang.IllegalArgumentException();
    }

}
