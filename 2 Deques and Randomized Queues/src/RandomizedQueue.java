/**
 * Created by michaelsafdieh on 2/2/17.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/***********************************************************************************************************************
 * public class RandomizedQueue<Item> implements Iterable<Item> {
 *     public RandomizedQueue()                 // construct an empty randomized queue
 *     public boolean isEmpty()                 // is the queue empty?
 *     public int size()                        // return the number of items on the queue
 *     public void enqueue(Item item)           // add the item
 *     public Item dequeue()                    // remove and return a random item
 *     public Item sample()                     // return (but do not remove) a random item
 *     public Iterator<Item> iterator()         // return an independent iterator over items in random order
 *     public static void main(String[] args)   // unit testing (optional)
 * }
 **********************************************************************************************************************/

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q; // array of items
    private int n; // number of elements on stack
    // NOTE: Don't think you even need to keep track of the head and tail. In a standard queue, you need them because
    // we always delete from the front and add to the back, thus, as a whole, the queue "moves" in the array q[]. Here
    // we just add items as normal, delete them RANDOMLY and swap that random item with the current last item (index
    // n-1). Thus everything stays in place and we don't need to keep track of the head and tail. The head is always 0,
    // and the tail is always n. We resize when necessary. This is basically a Bag with the option to delete a random
    // item and to view a random item. Thus using the queue terms is rather confusing.
    // I could be completely wrong though...

    /**
     * construct an empty deque
     */
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        n = 0;
    }

    /**
     * is the queue empty?
     */
    public boolean isEmpty() { return n == 0; }

    /**
     * return the number of items on the queue
     */
    public int size() { return n; }

    /**
     * add the item
     */
    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException("Can't add a null item");
        if (n == q.length) resize(2 * q.length);
        q[n++] = item;
    }

    /**
     * remove and return a random item
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue Underflow");
        int r = StdRandom.uniform(n); // r is random index from [0, n)
        Item item = q[r]; // save the deleted element to be returned
        q[r] = q[n-1]; // copy the last item element to the randomly selected element
        q[n-1] = null; // avoid loitering
        n--;
        if (n > 0 && n == q.length/4) resize(q.length/2);
        return item;
    }

    /**
     * return (but do not remove) a random item
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue Underflow");
        int r = StdRandom.uniform(n); // r is random index from [0, n)
        return q[r];
    }

    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int i = n;

        public boolean hasNext() { return i > 0; }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int r = StdRandom.uniform(n);
            i--;
            return q[r];
        }

        public void remove() { throw new UnsupportedOperationException(); }
    }



    /************************************************************************************************************
     *** PRIVATE METHODS ****************************************************************************************
     ************************************************************************************************************/

    private void resize(int capacity) {
        // textbook implementation
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[i];
        }
        q = temp;
    }

    // for debugging
    private void printQueue(){
        StdOut.println("Printing queue with " + n + " items:");
        for (int i = 0; i < n; i++)
            StdOut.print(q[i] + " ");
        StdOut.print("\nq[]: [");
        int i;
        for (i = 0; i < q.length-1; i++)
            StdOut.print(q[i] + ", ");
        StdOut.println(q[i] + "]");
        StdOut.println("size of array: " + q.length + "\n");
    }



    /************************************************************************************************************
     *** TEST CLIENT ********************************************************************************************
     ************************************************************************************************************/

    /**
     * unit testing (optional)
     */
    public static void main(String[] args)  {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        final int N = 10;
        for (int i = 0; i < N; i++)
            rq.enqueue(i);

        StdOut.println("foreach #1:");
        for(Integer i : rq)
            StdOut.print(i + " ");
        StdOut.println();

        StdOut.println("foreach #2:");
        for(Integer i : rq)
            StdOut.print(i + " ");
        StdOut.println();

        StdOut.println("Samples:");
        for (int i = 0; i < 20; i++) //NOTE: we can sample as much as we want since we are not affecting the queue size
            StdOut.print(rq.sample() + " ");
        StdOut.println();

        StdOut.println("Deletions:");
        for (int i = 0; i < N; i++)
            StdOut.print(rq.dequeue() + " ");
//            StdOut.println("\n" + deleted + "\nsize: " + rq.size());
        StdOut.println();

    }
}
