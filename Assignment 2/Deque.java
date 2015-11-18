/*
 * A double-ended queue or deque (pronounced "deck") is a generalization of a stack 
 * and a queue that supports adding and removing items from either the front or the 
 * back of the data structure. 
 */

/**
 *
 * @author Fei
 */
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private int N;
    private Node first;
    private Node last;

    private class Node {

        private Item item;
        private Node prev;
        private Node next;
    }

    public Deque() {
        N = 0;
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;

        if (isEmpty()) {
            last = first;
            first.next = null;
        } else {
            oldFirst.prev = first;
            first.next = oldFirst;
        }
        N++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
            last.prev = null;
        } else {
            oldLast.next = last;
            last.prev = oldLast;
        }
        N++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }

        return item;

    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = last.item;
        last = last.prev;
        N--;

        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node curr = first;

        public boolean hasNext() {
            return curr != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = curr.item;
            curr = curr.next;
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addLast("come");
        deque.addFirst("has");
        deque.addLast("to");
        deque.addFirst("V");

        StdOut.println(" ---- add first all ----");
        for (String s : deque) {
            StdOut.print(s + " ");
        }

        StdOut.println("\n ---- remove first : " + deque.removeFirst());
        for (String s : deque) {
            StdOut.print(s + " ");
        }

        StdOut.println("\n ---- remove last : " + deque.removeLast());
        for (String s : deque) {
            StdOut.print(s + " ");
        }
        deque.removeLast();
        deque.removeLast();
    }
}
