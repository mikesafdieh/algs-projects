/**
 * Created by michaelsafdieh on 2/17/17.
 */

import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

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

    //TODO: maybe make all instance vars final for full immutability??
    private int[][] board;
    private int hamming;
    private int manhattan;
    private final int N;
    private Stack<Board> neighbors; // stack of neighboring boards for the game tree
    private int spaceI; // row index of the space (0 tile)
    private int spaceJ; // col index of the space (0 tile)
    private boolean foundSpace; // flag to check that a space was found in the input

    public Board(int[][] blocks) {
        // TODO: Seriously consider doing hamming & manhattan calculations in their own functions from API (look at addNeighbors())
        // !!! DO NOT CALL neighbors() IN CONSTRUCTOR! THIS WILL CAUSE AN INFINITE LOOP!!!
        // -> for each neighbor created, it will create its own neighbors, and same for them, and so on...
        // ==> STACK OVERFLOW ERROR!!!

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
                else if (tile == 0) {
                    spaceI = i;
                    spaceJ = j;
                    foundSpace = true;
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
        int[][] twinTiles = safeBoardCopy();
        if (board[0][0] != 0 && board[0][1] != 0)
            exchangeTiles(0, 0, 0, 1, twinTiles); // exchange 1st 2 tiles on 1st row
        else
            exchangeTiles(1, 0, 1, 1, twinTiles); // exchange 1st 2 tiles on 2nd row
        Board twinBoard = new Board(twinTiles);
        return twinBoard;
        //TODO: This is an elementary implementation, maybe change this. Not sure if they want me to choose a random tile to swap.
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.N != that.N) return false;
        if (this.hamming != that.hamming) return false;
        if (this.manhattan != that.manhattan) return false;
        if (!Arrays.deepEquals(this.board, that.board)) return false;
        return true; // NOTE: we don't need to check that the neighbors are equal.
    }

    public Iterable<Board> neighbors() {
        assert foundSpace; // check that the space was found
        addNeighbors(spaceI, spaceJ);
        return neighbors;
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
     * @return the horizontal distance to desired position
     */
    private int manhattanX(int tile, int i) { return Math.abs(i - correctRowIndex(tile)); }

    /**
     * calculates the distance to the correct column of tile
     * @param tile the given tile
     * @param j current col index of tile (0..N-1)
     * @return the vertical distance to desired position
     */
    private int manhattanY(int tile, int j) { return Math.abs(j - correctColIndex(tile)); }

    /**
     * checks whether 2D array indices are in bounds
     * @param i index of row
     * @param j index of column
     * @return true if i & j are valid 2D array indices
     */
    private boolean isValidPosition(int i, int j) {
        return ( (i >= 0) && (i < N) && (j >= 0) && (j < N) );
    }

    /**
     * pushes each of the board's neighbors in the game tree to the neighbors stack
     * @param i row index of space on board (0 tile)
     * @param j column index of space on board (0 tile)
     */
    private void addNeighbors(int i, int j) {
        neighbors = new Stack<Board>();
        if (isValidPosition(i-1, j)) { // up
            int[][] upNeb = safeBoardCopy();
            // !!!NOTE: We MUST make safe copy for EACH neighbor so stack items stay independent of each other.
            // Otherwise, if we just use one copy, all boards on stack will be references to the SAME obj and thus the
            // last board pushed to the stack will be what is referred to by EVERY board [pointer] on the stack!
            exchangeTiles(i, j, i-1, j, upNeb); // exchange
            neighbors.push(new Board(upNeb)); // push to neighbors stack
        }
        if (isValidPosition(i+1, j)) { // down
            int[][] downNeb = safeBoardCopy();
            exchangeTiles(i, j, i+1, j, downNeb);
            neighbors.push(new Board(downNeb));
        }
        if (isValidPosition(i, j-1)) { // left
            int[][] leftNeb = safeBoardCopy();
            exchangeTiles(i, j, i, j-1, leftNeb);
            neighbors.push(new Board(leftNeb));
        }
        if (isValidPosition(i, j+1)) { // right
            int[][] rightNeb = safeBoardCopy();
            exchangeTiles(i, j, i, j+1, rightNeb);
            neighbors.push(new Board(rightNeb));
        }
    }

    /**
     * exchanges 2 tiles on the board
     * @param i row index of 1st tile
     * @param j col index of 1st tile
     * @param r row index of 2nd tile
     * @param s col index of 2nd tile
     * @param tiles board where exchange of specified tiles is to take place
     */
    private void exchangeTiles(int i, int j, int r, int s, int[][] tiles) {
        int temp = tiles[i][j];
        tiles[i][j] = tiles[r][s];
        tiles[r][s] = temp;
    }

    /**
     * creates a safe copy of the board[][] instance variable
     * @return safe copy of board[][]
     */
    private int[][] safeBoardCopy() {
        int[][] boardCopy = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                boardCopy[i][j] = board[i][j];
            }
        }
        return boardCopy;
    }


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
        System.out.println("Goal board? --> " + initial.isGoal());

        // twin testing
        Board twin = initial.twin();
        System.out.println("\nTwin Board:\n" + twin);

        // neighbor testing:
        Iterable<Board> neighbors = initial.neighbors();
        System.out.println("\nNeighbors:\n");
        for (Board neb : neighbors)
            System.out.println(neb);

//        // exchange testing:
//        int[][] tiles = {{1, 0, 3},
//                         {2, 4, 5},
//                         {7, 8, 6}};
//        int i = 0, j = 1;
//        System.out.println("Before exchange:");
//        System.out.println("tiles[0][1] = " + tiles[0][1]);
//        System.out.println("tiles[0][1] = " + tiles[0][0]);
//        exchangeTiles(i, j, i, j-1, tiles);
//        System.out.println("After exchange:");
//        System.out.println("tiles[0][1] = " + tiles[0][1]);
//        System.out.println("tiles[0][1] = " + tiles[0][0]);
//        exchangeTiles(i, j, i, j-1, tiles);
//        System.out.println("After exchange once again:");
//        System.out.println("tiles[0][1] = " + tiles[0][1]);
//        System.out.println("tiles[0][1] = " + tiles[0][0]);

        System.out.println();
    }
}


// Need the -1 to account for the 0 at the end which will never be equal to N*N

//    private String tilesToString(int[][] tiles) {
//        StringBuilder s = new StringBuilder();
//        int n = tiles.length;
//        int m = tiles[0].length;
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < m; j++) {
//                s.append(String.format("%2d ", tiles[i][j]));
//            }
//            s.append("\n");
//        }
//        return s.toString();
//    }