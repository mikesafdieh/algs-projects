/**
 * Created by michaelsafdieh on 2/17/17.
 */

import edu.princeton.cs.algs4.In;

/**
 * public class Board
 *     public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
 *                                            // (where blocks[i][j] = block in row i, column j)
 *     public int dimension()                 // board dimension n
 *     public int hamming()                   // number of blocks out of place
 *     public int manhattan()                 // sum of Manhattan distances between blocks and goal
 *     public boolean isGoal()                // is this board the goal board?
 *     public Board twin()                    // a board that is obtained by exchanging any pair of blocks
 *     public boolean equals(Object y)        // does this board equal y?
 *     public Iterable<Board> neighbors()     // all neighboring boards
 *     public String toString()               // string representation of this board (in the output format specified below
 *     public static void main(String[] args) // unit tests (not graded)
 */

public final class Board {

    private int[][] board;
    private int hamming;
    private int manhattan;
    private final int N;

    public Board(int[][] blocks) {
        N = blocks.length;
        board = blocks;
        int tile;

        // calculate hamming & manhattan distances
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tile = board[i][j];
                if (tile != 0  &&  tile != correctTile(i, j) ) {
                    // NOTE: we have to skip 0 because it's not actually a tile, just an empty space
                    hamming++;
                    manhattan += manhattanX(tile, i) + manhattanY(tile, j);
                }
            }
        }
    }

    public int dimension() { return N; }

    public int hamming() { return hamming; }

    public int manhattan() { return manhattan; }

    public boolean isGoal() {
        int counter = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (board[i][j] == correctTile(i, j))
                    counter++;
        return counter == N*N;
    }

    public Board twin() {
        //TODO: fill in...
        return null;
    }

    public boolean equals(Object y) {
        //NOTE: look at textbook pg 104 for details on how to implement equals()
        //TODO: fill in...
        return false;
    }

    public Iterable<Board> neighbors() {
        //TODO: fill in...
        return null;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }



    /************************************************************************************************************
     *** PRIVATE METHODS ****************************************************************************************
     ************************************************************************************************************/

    /**
     * returns the correct tile for the given indices
     * @param i the row
     * @param j the column
     * @return the tile that should be at row i and column j
     */
    private int correctTile(int i, int j) {
        if (i == N-1 && j == N-1) return 0; // the last tile should always be 0
        else return N*i + j + 1; // NOTE: The +1 is needed to accommodate for 0 array indexing
    }

    /**
     * calculates the correct row index of tile
     * @param tile given tile
     * @return correct row index of tile
     */
    private int correctRowIndex(int tile) {
        //TODO: maybe throw an exception if tile == 0
        if (tile == 0) return N-1;
        else return (tile - 1)/N;
    }

    /**
     * calculates the correct column index of tile
     * @param tile given tile
     * @return correct column index of tile
     */
    private int correctColIndex(int tile) {
        //TODO: maybe throw an exception if tile == 0
        int col = tile % N;
        if (col == 0) return N-1;
        else return col-1;
    }

    /**
     * calculates the distance to the correct row of tile
     * @param tile the given tile
     * @param i current row index of tile (0..N-1)
     * @return
     */
    private int manhattanX(int tile, int i) { return Math.abs(i - correctRowIndex(tile)); }

    /**
     * calculates the distance to the correct column of tile
     * @param tile the given tile
     * @param j current col index of tile (0..N-1)
     * @return
     */
    private int manhattanY(int tile, int j) { return Math.abs(j - correctColIndex(tile)); }



    /************************************************************************************************************
     *** TEST CLIENT ********************************************************************************************
     ************************************************************************************************************/

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // some custom tests:
        System.out.println("\nBoard:\n" + initial);
        System.out.println("hamming: " + initial.hamming());
        System.out.println("manhattan: " + initial.manhattan());
        System.out.println();
    }
}


// Need the -1 to account for the 0 at the end which will never be equal to N*N