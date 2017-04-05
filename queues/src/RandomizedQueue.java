import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Node<Item> firstNode;

    private class Node<Item> {
        private Item item;
        private Node<Item> next;

        Node(Item item) {
            this.item = item;
            this.next = null;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        firstNode = null;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node<Item> newfirstNode = new Node<>(item);
        newfirstNode.next = firstNode;
        firstNode = newfirstNode;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException();
        Item item;
        int n = StdRandom.uniform(size);
        if (n == 0) {
            item = firstNode.item;
            firstNode = firstNode.next;
        } else {
            Node<Item> current = firstNode;
            while (--n > 0) {
                current = current.next;
            }
            item = current.next.item;
            current.next = current.next.next;
        }
        size--;
        return item;

    }

    // return (but do not remove) a random item
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();
        int n = StdRandom.uniform(size);
        Node<Item> current = firstNode;
        while (n-- > 0)
            current = current.next;
        return current.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator<Item>(firstNode);
    }

    private class QueueIterator<Item> implements Iterator<Item> {

        private Node<Item> node;

        QueueIterator(Node<Item> first) {
            node = first;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item current = node.item;
            node = node.next;
            return current;
        }

    }

    // unit testing (optional)
    public static void main(String[] args) {

    }

}
