/*
 * Write a program to estimate the value of the percolation threshold via Monte Carlo simulation.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 *
 * @author Fei
 */
public class Percolation {

    private int mSize;
    private int vTop = 0;
    private int vBottom = 0;
    private boolean[] openState;
    private WeightedQuickUnionUF qfVTB;
    private WeightedQuickUnionUF qfVT;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.mSize = N;
        this.vBottom = N * N + 1;
        openState = new boolean[N * N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                openState[ i * N + j] = false;
            }
        }
        qfVTB = new WeightedQuickUnionUF(N * N + 2);    // M_TOP, M_BOTTOM is open
        qfVT = new WeightedQuickUnionUF(N * N + 1);     // ONLY includes M_TOP
    }

    public void open(int i, int j) {
        // Corner cases
        if (i < 1 || j < 1 || i > mSize || j > mSize) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int row = i - 1;
        int col = j - 1;
        if (!openState[row * mSize + col]) {
            openState[row * mSize + col] = true;

            /* union operation
             *      - x -
             *      x o x
             *      - x -
             * qf idx 0 is virtual top site
             */
            if (row - 1 >= 0 && isOpen(i - 1, j)) {
                qfVTB.union(row * mSize + col + 1, (row - 1) * mSize + col + 1);
                qfVT.union(row * mSize + col + 1, (row - 1) * mSize + col + 1);
            }

            if (row + 1 < mSize && isOpen(i + 1, j)) {
                qfVTB.union(row * mSize + col + 1, (row + 1) * mSize + col + 1);
                qfVT.union(row * mSize + col + 1, (row + 1) * mSize + col + 1);
            }

            if (col - 1 >= 0 && isOpen(i, j - 1)) {
                qfVTB.union(row * mSize + col + 1, row * mSize + col);
                qfVT.union(row * mSize + col + 1, row * mSize + col);
            }

            if (col + 1 < mSize && isOpen(i, j + 1)) {
                qfVTB.union(row * mSize + col + 1, row * mSize + col + 2);
                qfVT.union(row * mSize + col + 1, row * mSize + col + 2);
            }
            if (row == 0) {
                qfVTB.union(row * mSize + col + 1, vTop);
                qfVT.union(row * mSize + col + 1, vTop);
            }
            if (row == mSize - 1) {
                qfVTB.union(row * mSize + col + 1, vBottom);
            }
        }


    }

    public boolean isOpen(int i, int j) {
        if (i < 1 || j < 1 || i > mSize || j > mSize) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return openState[(i - 1) * mSize + j - 1];
    }

    /* A full site is an open site that can be connected to
     an open site in the top row via a chain of neighboring (left, right, up, down) open sites.
     */
    public boolean isFull(int i, int j) {
        if (i < 1 || j < 1 || i > mSize || j > mSize) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int row = i - 1;
        int col = j - 1;
        return openState[row * mSize + col] && qfVT.connected(row * mSize + col + 1, vTop);
    }

    public boolean percolates() {
        return qfVTB.connected(vTop, vBottom);
    }
}
