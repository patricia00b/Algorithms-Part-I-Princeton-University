/**
*
* Goals: Estimate p* (site vacancy probability) in a connectivity problem so that * when p > p* the system percolates.
*
*/
import edu.princeton.cs.algs4.QuickFindUF;

// percolation data type
public class Percolation {
	private QuickFindUF id; // id[r][c] = component identifier of i
	private int[][] status;
	private int sitenumber = 1; // number of components
	private int opensites = 0;
	private int n;

	public Percolation(int n) {
    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own 
     * component. (not open)
     *
     * @param  n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    
	    if (n < 0) throw new IllegalArgumentException();
	    id = new QuickFindUF(n*n);
	    status = new int[n][n];
	    this.n = n;
	    
	    for (int r = 0; r < n; r++) {
	        for (int c = 0; c < n; c++) {
	            status[r][c] = 0; // 0 represents the site closed
	            sitenumber++;
	        }
	    }
	}

	public void open(int row, int col) {
		/**
		* open site (row, col) if it is not open already. links the site in question to its open neighbors
		* @param indices [row][col] of the site to open
		*/

		row--;
		col--;

		final int CURRENTCORD = xyTo1D(row, col);

		if (status[row][col] == 0) { // if it's closed
			status[row][col] = 1; // marks it as open
			opensites++;
			// connect it to neighbor sites
			// if not on top row
			if (row != 0) {
				if (isOpen(row, col+1))// top
					id.union(CURRENTCORD, xyTo1D(row-1, col));
			}
			// if not last row
			if (row != n-1) {
				if (isOpen(row+2, col+1))// down
					id.union(CURRENTCORD, xyTo1D(row+1, col));
			}
			if (col != 0) {
				if (isOpen(row+1, col))// left
					id.union(CURRENTCORD, xyTo1D(row, col-1));
			}
			if (col != n-1) {
				if (isOpen(row+1, col+2))// right
					id.union(CURRENTCORD, xyTo1D(row, col+1));
			}
		}
	}

	public boolean isOpen(int row, int col) {
		/** 
		* @param indices [row][col] of the site
		* @returns if the site is open: true or false
		*/ 
		row--;
		col--;

		if (status[row][col] == 1) // if it's open
			return true;
		// else it's closed
		return false; 
	}

	public boolean isFull(int row, int col) {
		/**
		* @param indices [row][col]
		* @returns if the site (row, col) is open and connected to the top site
		*/
		row--;
		col--;

		for (int c = 0; c < n; c++) {
			if (isOpen(row+1, col+1) && id.connected(c, xyTo1D(row, col)))
				return true;
		}
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
		for (int c = 1; c <= n; c++) { // iterates over lower row
			if (isFull(n-1, c))
				return true;
		}
		return false;
	}

/**
*	private boolean isidexvalid(int row, int col) {
*		if ((row < 1) || (row > n) || (col <1) || (col>n)){
*			throw new IllegalArgumentException();
*		}
*		return true;
*	}
**/
	private int xyTo1D(int row, int col) {
		return (row*n + col);
	}

	public static void main(String[] args) {
	}
}
