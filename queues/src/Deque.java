import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> last; // last element, points null;
    private Node<Item> first; // begining of the linked list
    private int size = 0;

    private class Node<Item> {
        private Item item;
        private Node<Item> next;

        Node(Item item, Node<Item> next) {
            this.item = item;
            this.next = next;
        }

        Node(Item item) {
            this.item = item;
            this.next = null;
        }
    }

    // construct an empty deque
    public Deque() {
        last = null;
        first = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node<Item> node = new Node<>(item, first);
        first = node;
        if (isEmpty())
            last = node;
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (isEmpty()) {
            first = new Node<>(item);
            last = first;
        } else {
            Node<Item> oldlast = last;
            last = new Node<>(item);
            oldlast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null)
            throw new NoSuchElementException();
        Item res = first.item;
        first = first.next;
        size--;
        if (size == 0) last = null;
        return res;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (last == null)
            throw new NoSuchElementException();
        Item res = last.item;
        if (size == 1) { last = null;
        first = null;
        size--;
        return res; }
        
        Node<Item> node = first;
        for (int i = size; i > 2; i--)
            node = node.next;
        node.next = null;
        last = node;
        size--;
        return res;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {

        private Node<Item> node;

        ListIterator(Node<Item> first) {
            node = first;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = node.item;
            node = node.next;
            return item;
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
     Deque<String> d = new Deque<>();
     d.addLast("0");
     d.addLast("1");
     d.addLast("2");
     d.addLast("4");
     
     System.out.println(d.removeLast());
     System.out.println(d.removeLast());
     System.out.println(d.removeLast());
     System.out.println(d.removeLast());
     System.out.println(d.removeLast());
   
   
  
    
     System.out.println(d.size);
    }

}
