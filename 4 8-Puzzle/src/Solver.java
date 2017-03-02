/**
 * Created by michaelsafdieh on 2/17/17.
 */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;


/**
 * public class Solver {
 *     public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
 *     public boolean isSolvable()            // is the initial board solvable?
 *     public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
 *     public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
 *     public static void main(String[] args) // solve a slider puzzle (given below)
 *
 */

public final class Solver {

    private MinPQ<SearchNode> solver;    // min PQ for deleting the minimum priority nodes
    private MinPQ<SearchNode> solverTwin; // another min PQ for deleting the minimum priority nodes (for a twin board)
    private int totalMoves; // total moves to solve the puzzle
    private SearchNode init; // pointer to initial Search Node
    private SearchNode initTwin;
//    private SearchNode currMin; // pointer to the current min on game tree
    private boolean isSolvable;
    private Queue<Board> solution;
    private final Comparator<SearchNode> MANHATTAN_PRIORITY = new ManhattanPriority();
    private final Comparator<SearchNode> HAMMING_PRIORITY = new HammingPriority();


    private static class SearchNode{
        private Board board;
        private int moves;
        private SearchNode prev;
    }

    public Solver(Board initial) {
        if (initial == null) throw new java.lang.NullPointerException();

        init = new SearchNode();
        init.board = initial;
        init.moves = 0;
        init.prev = null;
        /***LOCKSTEP***/
        initTwin = new SearchNode();
        initTwin.board = initial.twin();
        initTwin.moves = 0;
        initTwin.prev = null;

        //TODO: Maybe also add a MinPQ with HAMMING_PRIORITY
        //TODO: integrate both priorities to one MinPQ???

        solver = new MinPQ<SearchNode>(MANHATTAN_PRIORITY); // enqueue onto minPQ
        solver.insert(init);
        /***LOCKSTEP***/
        solverTwin = new MinPQ<SearchNode>(MANHATTAN_PRIORITY);
        solverTwin.insert(initTwin);

        solution = new Queue<Board>(); // initialize solution queue

        SearchNode currMin = safeSearchNodeCopy(init);
        /***LOCKSTEP***/
        SearchNode currMinTwin = safeSearchNodeCopy(initTwin);

        solution.enqueue(currMin.board); // save the current min priority board to the solution queue

        while (!currMin.board.isGoal() && !currMinTwin.board.isGoal()) {
            totalMoves++; // increment the number of moves for each iteration

            for (Board neb : currMin.board.neighbors()) { // add each of the neighbors of the deleted (previous) board
                //TODO: clean up this ugliness
                if (currMin.prev == null) { // needed for the optimization
                    SearchNode sn = new SearchNode();
                    sn.board = neb;
                    sn.moves = totalMoves;
                    sn.prev = currMin;
                    solver.insert(sn);
                }
                else if (!(neb.equals(currMin.prev.board))) { // CRITICAL OPTIMIZATION (I think)
                    SearchNode sn = new SearchNode(); // add the corresponding search node of neb
                    sn.board = neb;
                    sn.moves = totalMoves;
                    sn.prev = currMin;
                    solver.insert(sn);
                }
            }
            /***LOCKSTEP***/
            for (Board neb : currMinTwin.board.neighbors()) {
                //TODO: clean up this ugliness
                if (currMinTwin.prev == null) {
                    SearchNode sn = new SearchNode();
                    sn.board = neb;
                    sn.moves = totalMoves;
                    sn.prev = currMinTwin;
                    solverTwin.insert(sn);
                }
                else if (!(neb.equals(currMinTwin.prev.board))) {
                    SearchNode sn = new SearchNode();
                    sn.board = neb;
                    sn.moves = totalMoves;
                    sn.prev = currMinTwin;
                    solverTwin.insert(sn);
                }
            }

            currMin = safeSearchNodeCopy(solver.delMin()); // delete min priority and save to var
            /***LOCKSTEP***/
            currMinTwin = safeSearchNodeCopy(solverTwin.delMin()); // delete min priority and save to var

            solution.enqueue(currMin.board); // save the current min priority board to the solution queue
        }

        if (currMin.board.isGoal()){
            isSolvable = true;
        }
        // We don't need to add anymore logic when currMinTwin.board.isGoal()
        // is true, because isSolvable is initially false.
    }

    public boolean isSolvable() { return isSolvable; }

    public int moves() {
        if (isSolvable) return totalMoves;
        else return -1;
    }

    public Iterable<Board> solution() {
        if (isSolvable) return solution;
        else return null;
    }



    /************************************************************************************************************
     *** PRIVATE METHODS ****************************************************************************************
     ************************************************************************************************************/

    private void printSearchNode(SearchNode snPtr){
        System.out.println("Board:\n" + snPtr.board + "\nMoves: " + snPtr.moves + "\nPrev: " + snPtr.prev);
    }

    private int hammingPriority(SearchNode sn) { return sn.board.hamming() + sn.moves; }

    private int manhattanPriority(SearchNode sn) { return sn.board.manhattan() + sn.moves; }

    private class HammingPriority implements Comparator<SearchNode> {
        public int compare(SearchNode sn1, SearchNode sn2) {
            if (hammingPriority(sn1) > hammingPriority(sn2)) return +1;
            if (hammingPriority(sn1) < hammingPriority(sn2)) return -1;
            return 0;
        }
    }

    private class ManhattanPriority implements Comparator<SearchNode> {
        public int compare(SearchNode sn1, SearchNode sn2) {
            if (manhattanPriority(sn1) > manhattanPriority(sn2)) return +1;
            if (manhattanPriority(sn1) < manhattanPriority(sn2)) return -1;
            return 0;
        }
    }

    private SearchNode safeSearchNodeCopy(SearchNode sn) {
        SearchNode copy = new SearchNode();
        copy.board = sn.board;
        copy.moves = sn.moves;
        copy.prev = sn.prev;
        return copy;
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

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}


// 1. delete min priority and save to var (currMin?)
// 2. add deleted node's neighbors (node.board.neighbors()) to minPQ
// 3. repeat until goal board is deleted
// LATER:
// i.  add critical optimization
// ii. add lockstep process of twin game tree and minPQ