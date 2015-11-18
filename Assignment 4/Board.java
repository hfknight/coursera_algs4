import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

/**
 *
 * @author Feo
 */
public class Board {

    private int N;
    private int[][] blocks;

    // construct a board from an N-by-N array of blocks, (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks[0].length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() { // board dimension N
        return N;
    }

    public int hamming() { // number of blocks out of place
        // The number of blocks in the wrong position
        // plus the number of moves made so far to get to the search node. 
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * N + j + 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public int manhattan() { // sum of Manhattan distances between blocks and goal
        // The sum of the Manhattan distances (sum of the vertical and horizontal 
        // distance) from the blocks to their goal positions, plus the number of 
        // moves made so far to get to the search node.        
        int count = 0;
        int row, col, block;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                block = blocks[i][j];
                if (block != 0) {
                    row = (block - 1) / N;
                    col = (block - 1) % N;
                    count += Math.abs(row - i) + Math.abs(col - j);
                }
            }
        }
        return count;
    }

    public boolean isGoal() {               // is this board the goal board?
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != i * N + j + 1 && !(i == N - 1 && j == N - 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board twin() { // a board that is obtained by exchanging any pair of blocks
        Board twinBoard = new Board(blocks);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (twinBoard.blocks[i][j] != 0 && twinBoard.blocks[i][j + 1] != 0) {
                    twinBoard.swap(i, j, i, j + 1);
                    return twinBoard;
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object y) { // does this board equal y?
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board yBoard = (Board) y;
        if (yBoard.blocks.length != N || yBoard.blocks[0].length != N) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (yBoard.blocks[i][j] != blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() { // all neighboring boards
        if (N < 2) {
            return null;
        }
        List<Board> nbBoards = new ArrayList<Board>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    if (i > 0) {
                        nbBoards.add(generateNeighborsBoard(i, j, i - 1, j));
                    }
                    if (i < N - 1) {
                        nbBoards.add(generateNeighborsBoard(i, j, i + 1, j));
                    }
                    if (j > 0) {
                        nbBoards.add(generateNeighborsBoard(i, j, i, j - 1));
                    }
                    if (j < N - 1) {
                        nbBoards.add(generateNeighborsBoard(i, j, i, j + 1));
                    }
                    return nbBoards;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() { // string representation of this board (in the output format specified below)
        StringBuilder sb = new StringBuilder(N + "\n");
        for (int i = 0; i < N; i++) {
            sb.append(" ");
            for (int j = 0; j < N; j++) {
                sb.append(blocks[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void swap(int row, int col, int row2, int col2) {
        int temp = blocks[row][col];
        blocks[row][col] = blocks[row2][col2];
        blocks[row2][col2] = temp;
    }

    private Board generateNeighborsBoard(int row, int col, int row2, int col2) {
        Board newBoard = new Board(blocks);
        newBoard.swap(row, col, row2, col2);
        return newBoard;
    }

    public static void main(String[] args) { // unit tests (not graded)    
        int N = StdIn.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = StdIn.readInt();
            }
        }
        Board board = new Board(blocks);
        StdOut.println(board);
        StdOut.println("Board Dimension: " + board.dimension());
        StdOut.println("Board Hamming: " + board.hamming());
        StdOut.println("Board Manhattan: " + board.manhattan());
        StdOut.println("Board isGoal: " + board.isGoal());
        StdOut.println("Board Twin:");
        StdOut.println(board.twin());
        StdOut.println("Neighbors:");
        Iterable<Board> iter = board.neighbors();
        for (Board b : iter) {
            StdOut.println(b);
        }
    }
}
