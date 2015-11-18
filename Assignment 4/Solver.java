import edu.princeton.cs.algs4.MinPQ;
import java.util.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

/**
 *
 * @author Fei
 */
public class Solver {

    private MinPQ<SNode> pq;
    //private MinPQ<SNode> pqTwin;
    private int minMoves;
    private SNode best;
    private boolean isSolved;
    //private boolean isSolvedTwin;

    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        if (initial == null) {
            throw new java.lang.NullPointerException();
        }
        pq = new MinPQ<SNode>();
        //pqTwin = new MinPQ<SNode>();
        minMoves = -1;
        isSolved = false;
        //isSolvedTwin = false;

        pq.insert(new SNode(initial, 0, null));
        pq.insert(new SNode(initial.twin(), 0, null));

        while (!pq.isEmpty()) {
            SNode current = pq.delMin();
            if (current.board.isGoal()) {
                SNode root = findRoot(current);
                if (!root.board.equals(initial)) {
                    break;
                }
                isSolved = true;
                if (minMoves == -1 || current.moves < minMoves) {
                    minMoves = current.moves;
                    best = current;
                }
            }
            if (minMoves == -1 || current.moves + current.dist < minMoves) {
                for (Board b : current.board.neighbors()) {
                    if (current.prev == null || !b.equals(current.prev.board)) {
                        pq.insert(new SNode(b, current.moves + 1, current));
                    }
                }
            } else {
                break;
            }
        }

    }

    // find root SNode ( initial or twin)
    private SNode findRoot(SNode node) {
        SNode current = node;
        while (current.prev != null) {
            current = current.prev;
        }
        return current;
    }

    public boolean isSolvable() {           // is the initial board solvable?
        return isSolved;
    }

    public int moves() {                     // min number of moves to solve initial board; -1 if unsolvable
        return minMoves;
    }

    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        if (isSolvable()) {
            edu.princeton.cs.algs4.Stack <Board> solution = new edu.princeton.cs.algs4.Stack<Board>(); // using edu.princeton.cs.algs4.Stack. The class java.util.Stack is poorly designed, including an iterator that iterates in the wrong direction.
            SNode snode = best;
            while (snode != null) {
                solution.push(snode.board);
                snode = snode.prev;
            }
            return solution;
        }
        return null;
    }

    private class SNode implements Comparable<SNode> {

        private Board board;
        private int moves, dist;
        private SNode prev;

        public SNode(Board board, int moves, SNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.dist = board.manhattan();
        }

        @Override
        public int compareTo(SNode that) {
            return this.moves + this.dist - that.moves - that.dist;
        }
    }

    public static void main(String[] args) { // solve a slider puzzle (given below)    
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
