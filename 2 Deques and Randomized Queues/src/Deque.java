/**
 * Created by michaelsafdieh on 2/2/17.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/***********************************************************************************************************************
 * public class Deque<Item> implements Iterable<Item> {
 *     public Deque()                           // construct an empty deque
 *     public boolean isEmpty()                 // is the deque empty?
 *     public int size()                        // return the number of items on the deque
 *     public void addFirst(Item item)          // add the item to the front
 *     public void addLast(Item item)           // add the item to the end
 *     public Item removeFirst()                // remove and return the item from the front
 *     public Item removeLast()                 // remove and return the item from the end
 *     public Iterator<Item> iterator()         // return an iterator over items in order from front to end
 *     public static void main(String[] args)   // unit testing (optional)
 * }
 **********************************************************************************************************************/

public class Deque<Item> implements Iterable<Item> {

    private int n;            // number of elements in deque
    private DoubleNode first; // pointer to first element of deque
    private DoubleNode last;  // pointer to last element of deque

    // helper doubly-linked list class
    private class DoubleNode {
        Item item;       // data in the node
        DoubleNode next; // link to next node
        DoubleNode prev; // link to previous node
    }

    /**
     * construct an empty deque
     */
    public Deque() {
        n = 0;
        first = null;
        last = null;
    }

    /**
     * is the deque empty?
     */
    public boolean isEmpty() { return first == null; }

    /**
     * return the number of items on the deque
     */
    public int size() { return n; }

    /**
     * add the item to the front
     */
    public void addFirst(Item item) {
        if (item == null) throw new java.lang.NullPointerException("Can't add a null item");
        DoubleNode oldFirst = first;
        first = new DoubleNode();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (oldFirst == null) // if we just added the first node (could have done "if (n == 0)")
            last = first; // since this is the 1st node being added to the deque, we have to make sure last is updated
        else
            oldFirst.prev = first; // don't forget to handle the 2nd link!
        n++;
    }

    /**
     * add the item to the end
     */
    public void addLast(Item item) {
        if (item == null) throw new java.lang.NullPointerException("Can't add a null item");
        DoubleNode oldLast = last;
        last = new DoubleNode();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty())
            first = last;
        else
            oldLast.next = last;
        n++;
    }

    /**
     * remove and return the item from the front
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item; // store the item before deleting it
        first = first.next;
        if (isEmpty()) // NOTE: IntelliJ's warning/suggestion here is incorrect! This can indeed evaluate to true.
            last = null; // avoid loitering
        else
            first.prev = null; // the new first node (originally the 2nd) has to make its prev pointer null
            // NOTE: if the deque was empty, this operation would cause an error (that's why we need the else)!
        n--;
        return item;
    }

    /**
     * remove and return the item from the end
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = last.item; // store the item before deleting it
        last = last.prev;
        if (last == null)
            first = null; // avoid loitering
        else
            last.next = null;
        n--;
        return item;
    }

    /**
     * return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class DequeIterator implements Iterator<Item> {
        private DoubleNode current = first;

        public boolean hasNext() { return current != null; }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() { throw new UnsupportedOperationException(); }
    }



    /************************************************************************************************************
     *** TEST CLIENT ********************************************************************************************
     ************************************************************************************************************/

    /**
     * unit testing (optional)
     */
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        System.out.println("Initial size: " + deque.size());

        final int N = 12;
        for (int i = 1; i <= N; i++)
            deque.addFirst(i);
        System.out.println("Size after inserts: " + deque.size());

        for (Integer i : deque)
            StdOut.print(i + " ");
        System.out.println();
        System.out.println("isEmpty(): " + deque.isEmpty());

        for (int i = 1; i <= N; i++)
            StdOut.print(deque.removeLast() + " ");
        System.out.println("\nSize after deletions: " + deque.size());
        System.out.println("isEmpty(): " + deque.isEmpty());



        for (int i = 1; i <= N; i++)
            deque.addLast(i);
        System.out.println("\n\nSize after inserts: " + deque.size());

        for (Integer i : deque)
            StdOut.print(i + " ");
        System.out.println();
        System.out.println("isEmpty(): " + deque.isEmpty());

        for (int i = 1; i <= N; i++)
            StdOut.print(deque.removeFirst() + " ");
        System.out.println("\nSize after deletions: " + deque.size());
        System.out.println("isEmpty(): " + deque.isEmpty());

//        deque.removeFirst();
//        deque.removeLast();
        // Both lines above throw exceptions, as expected :)
    }
}
