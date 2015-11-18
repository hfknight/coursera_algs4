/*
 * Write a program to estimate the value of the percolation threshold via Monte Carlo simulation.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/**
 *
 * @author Fei
 */
public class PercolationStats {

    private Percolation pc;
    private double[] stat;
    private int count;
    private int openedCount;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.count = T;
        this.stat = new double[T];
        for (int i = 0; i < count; i++) {
            pc = new Percolation(N);
            openedCount = 0;
            while (!pc.percolates()) {
                int row = StdRandom.uniform(N) + 1;
                int col = StdRandom.uniform(N) + 1;
                if (!pc.isOpen(row, col)) {
                    pc.open(row, col);
                    openedCount++;
                }
            }
            pc = null;
            stat[i] = (double) openedCount / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(stat);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(stat);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(count);
    }
    // high endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(count);
    }

    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            throw new java.lang.IllegalArgumentException();
        }
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        Stopwatch timer = new Stopwatch();

        PercolationStats ps = new PercolationStats(N, T);
        double time = timer.elapsedTime();

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("Time elapse             = " + time);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
