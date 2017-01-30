/**
 * Created by michaelsafdieh on 1/25/17.
 */

/****** API **************************************************************************************
 *
 *   public class Percolation {
 *       public Percolation(int n)                // create n-by-n grid, with all sites blocked
 *       public    void open(int row, int col)    // open site (row, col) if it is not open already
 *       public boolean isOpen(int row, int col)  // is site (row, col) open?
 *       public boolean isFull(int row, int col)  // is site (row, col) full?
 *       public     int numberOfOpenSites()       // number of open sites
 *       public boolean percolates()              // does the system percolate?
 *       public static void main(String[] args)   // test client (optional)
 *   }
 *************************************************************************************************/


// NOTE: You can only use the following libraries:
// StdIn, StdOut, StdRandom, StdStats, WeightedQuickUnionUF, and java.lang.

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Scanner;


//TODO: Add @return and @throws to the documentation of each method that requires them (or it).
//TODO: Add descriptions for @param in method documentation.
//TODO: Add documentation to private methods.
//TODO: Clean up the code (get rid of unnecessary comments etc.).

// IMPORTANT NOTE: Since the API specifies the use of a 1 to n indexing system (as opposed to a 0 to n-1 based
// system), when dealing with array accesses in methods that take row/col arguments BE SURE to subtract 1 from
// those values when accessing the grid!!!
public class Percolation {

    private boolean[][] grid; // declares our n-by-n grid
    private final int SIZE; // size of (edge of) the grid
    private final int GRIDSIZE;
    private int numOpenSites; // number of open sites in grid
    private WeightedQuickUnionUF uf; // used to identify connections between sites
    private int vTop; // index of virtual top site in uf
    private int vBottom; // index of virtual bottom site in uf
    private int[] neighbors;
    private boolean[] openSites; // this is for checking open sites by site number

    //TODO: Investigate the possibility of connecting to neighboring sites in a CLEANER way (that means rewriting part of open(),getting rid of the UGLY setNeighbors() method, and possibly deleting a few instance variables).

    /**
     * Create n-by-n grid, with all sites blocked
     * @param n the size of the square grid
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be greater than 0"); // validation

        SIZE = n;
        grid = new boolean[SIZE][SIZE]; // initializes all sites to blocked (false)
        GRIDSIZE = SIZE*SIZE;
        numOpenSites = 0;
        uf = new WeightedQuickUnionUF(GRIDSIZE+2); // every site in grid is a node in union find object, plus the 2 virtual sites
        vTop = GRIDSIZE;
        vBottom = GRIDSIZE + 1;
        connectVirtualTop();
        connectVirtualBottom();
        neighbors = new int[4];
        openSites = new boolean[GRIDSIZE];
    }

    /**
     * Open site (row, col) if it is not open already
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        // validateIndicies(row, col);
        // NOTE: We don't need to validate because isOpen() does this for us!!

        if (!isOpen(row,col)) { // checks if site is not open
            int site = mapTo1D(row-1,col-1); // get the site number
            grid[row-1][col-1] = true; // open site on grid
            openSites[site] = true; // mark site as open in our list of site numbers
            numOpenSites++; // increment our total number of open sites by 1

            //TODO: find a better, cleaner fix than this
            if (SIZE > 1) { // this is done because then the access to openSites[i>0] would throw an ArrayIndexOutOfBounds exception (because openSites is a 1 element array when SIZE == 1)
                setNeighbors(row-1,col-1,site); // get the neighbors of our newly opened site
                int neighbor;
                for (int i = 0; i < 4; i++) {
                    neighbor = neighbors[i];
                    if (neighbor != -1 && openSites[neighbor]) // check if neighbor is open and inbounds
                        uf.union(site, neighbor); // create connection from newly opened site to its open neighbor
                }
            }
        }
    }

    /**
     * Is site (row, col) open?
     * @param row
     * @param col
     */
    public boolean isOpen(int row, int col) {
        validateIndicies(row, col);
        return grid[row-1][col-1];
    }

    /**
     * Is site (row, col) full?
     * @param row
     * @param col
     */
    public boolean isFull(int row, int col) {
//        validateIndicies(row, col);
        // NOTE: We don't need to validate because isOpen() does this for us!!

        if (isOpen(row, col)) {
            int site = mapTo1D(row - 1, col - 1);
            return uf.connected(vTop, site);
        }
        return false;
    }

    /**
     * Number of open sites
     */
    public int numberOfOpenSites() { return numOpenSites; }

    /**
     * Does the system percolate?
     */
    public boolean percolates() { return uf.connected(vTop, vBottom); }



    /************************************************************************************************************
     *** PRIVATE METHODS ****************************************************************************************
     ************************************************************************************************************/

    private int mapTo1D(int i, int j) { return SIZE*i + j; }

    private void validateIndicies(int row, int col) {
        if (row <= 0 || row > SIZE) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (col <= 0 || col > SIZE) throw new IndexOutOfBoundsException("col index i out of bounds");
    }

    private void connectVirtualTop() {
        for (int i = 0; i < SIZE; i++){
            uf.union(vTop, i);
        }
    }

    private void connectVirtualBottom() {
        int bottomRowStartIndex = mapTo1D(SIZE-1, 0); // get site number in of bottom row, first column
        for (int i = bottomRowStartIndex; i < GRIDSIZE; i++){
            uf.union(vBottom, i);
        }
    }

    private void setNeighbors(int i, int j, int site) { // 0: top, 1: right, 2: bottom, 3: left
        if (i == 0 && j == 0) { // top left corner
            neighbors[0] = -1;
            neighbors[1] = site + 1;
            neighbors[2] = site + SIZE;
            neighbors[3] = -1;
        }
        else if (i == 0 && j == SIZE-1) { // top right corner
            neighbors[0] = -1;
            neighbors[1] = -1;
            neighbors[2] = site + SIZE;
            neighbors[3] = site - 1;
        }
        else if (i == SIZE-1 && j == SIZE-1) { // bottom right corner
            neighbors[0] = site - SIZE;
            neighbors[1] = -1;
            neighbors[2] = -1;
            neighbors[3] = site - 1;
        }
        else if (i == SIZE-1 && j == 0) { // bottom left corner
            neighbors[0] = site - SIZE;
            neighbors[1] = site + 1;
            neighbors[2] = -1;
            neighbors[3] = -1;
        }
        else if (i == 0) { // top edge
            neighbors[0] = -1;
            neighbors[1] = site + 1;
            neighbors[2] = site + SIZE;
            neighbors[3] = site - 1;
        }
        else if (j == SIZE-1) { // right edge
            neighbors[0] = site - SIZE;
            neighbors[1] = -1;
            neighbors[2] = site + SIZE;
            neighbors[3] = site - 1;
        }
        else if (i == SIZE-1) { // bottom edge
            neighbors[0] = site - SIZE;
            neighbors[1] = site + 1;
            neighbors[2] = -1;
            neighbors[3] = site - 1;
        }
        else if (j == 0) { // left edge
            neighbors[0] = site - SIZE;
            neighbors[1] = site + 1;
            neighbors[2] = site + SIZE;
            neighbors[3] = -1;
        }
        else {
            neighbors[0] = site - SIZE;
            neighbors[1] = site + 1;
            neighbors[2] = site + SIZE;
            neighbors[3] = site - 1;
        }
        // an entry of -1 means that this "neighbor" does not exist because it is out of bounds
    }



    /************************************************************************************************************
     *** TEST CLIENT ********************************************************************************************
     ************************************************************************************************************/

    // Temporary printing method
    private static void printGrid(Percolation perc){
        int N = perc.SIZE;
        for(int i = 0; i < N; i++) {
            StdOut.print("       ");
            for (int j = 0; j < N; j++) {
                if (perc.grid[i][j])
                    StdOut.print("1 ");
                else
                    StdOut.print("0 ");
            }
            StdOut.println();
        }
    }

    private static void fillCol(Percolation perc, int col) {
        int N = perc.SIZE;
        for(int row = 1; row <= N; row++) {
            perc.open(row, col);
        }
    }

    private static void testVTop(Percolation perc) {
        int N = perc.SIZE;
        for(int j = 0; j < N; j++) {
            System.out.println(j + ": " + perc.uf.find(j));
        }
    }

    private static void testVBottom(Percolation perc) {
        int N = perc.SIZE;
        for(int j = N*N-N; j < N*N; j++) {
            System.out.println(j + ": " + perc.uf.find(j));
        }
    }


    /**
     * Test client (optional)
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // Testing grid initialization
        StdOut.println("\nTesting grid initialization:\n");
        final int N = 5;
        Percolation perc = new Percolation(N);
        printGrid(perc);

        // Test top and bottom virtual sites
        System.out.println("\nTesting Virtual Top:\n");
        testVTop(perc);
        System.out.println("\nTesting Virtual Bottom:\n");
        testVBottom(perc);

        // Testing open()
        perc.open(2, 2);
        System.out.println("\nopen(2,2):\n");
        printGrid(perc);

        // open a col
        fillCol(perc, 1);
        System.out.println("\nfilling column:\n");
        printGrid(perc);
        System.out.println("\nisFull(1,4)?");
        System.out.println(perc.isFull(1, 4));
        System.out.println("\npercolates?");
        System.out.println(perc.percolates());

        perc.open(2,4);
        System.out.println("\nopen(2,4):\n");
        printGrid(perc);
        System.out.println("\nisFull(2,4)?");
        System.out.println(perc.isFull(1, 4));

        System.out.println("\nOpen Sites: " + perc.numberOfOpenSites());

        System.out.println(perc.uf.find(8)); // 8 corresponds to (2,4)
        System.out.println("20: " + perc.uf.find(20));
        System.out.println("15: " + perc.uf.find(15));
        System.out.println("10: " + perc.uf.find(10));
        System.out.println("5: " + perc.uf.find(5));
        System.out.println("0: " + perc.uf.find(0));

        System.out.println("\nNum of Components: " + perc.uf.count());

    }



}
