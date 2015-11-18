
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

/*
 * A randomized queue is similar to a stack or queue, except that the item 
 * removed is chosen uniformly at random from items in the data structure. 
 */
/**
 *
 * @author Fei
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;
    private int N = 0;

    public RandomizedQueue() {
        q = (Item[]) new Object[1];
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (N == q.length) {
            resize(2 * N);
        }
        q[N++] = item;
    }

    private void resize(int size) {
        if (size >= N) {
            Item[] nq = (Item[]) new Object[size];
            // System.arraycopy(q, 0, nq, 0, N);
            for (int i = 0; i < N; i++) {
                nq[i] = q[i];
            }
            q = nq;
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int idx = StdRandom.uniform(N);
        Item item = q[idx];

        //shirk array, swap q[idx] with the last element and set null
        q[idx] = q[N - 1];
        q[N - 1] = null;
        N--;
        if (N > 0 && N == q.length / 4) {
            resize(q.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int idx = StdRandom.uniform(N);
        return q[idx];
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {

        private Item[] q2 = (Item[]) new Object[q.length];
        private int M = N;

        public ArrayIterator() {
            for (int i = 0; i < q.length; i++) {
                q2[i] = q[i];
            }
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            int idx = StdRandom.uniform(M);
            Item item = q2[idx];
            q2[idx] = q2[M - 1];
            q2[M - 1] = null;
            M--;
            return item;
        }

        public boolean hasNext() {
            return M > 0;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        for (String s : args) {
            if (!s.equals("-")) {
                q.enqueue(s);
            } else if (!q.isEmpty()) {
                StdOut.print(q.dequeue() + " ");
            }
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }

}
