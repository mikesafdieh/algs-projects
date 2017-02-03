/**
 * Created by michaelsafdieh on 2/2/17.
 */

import java.util.Iterator;
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

    //TODO: Add necessary instance variables


    /**
     * is the queue empty?
     */
    public boolean isEmpty() {
        //TODO: Fill in...
        return false;
    }

    /**
     * return the number of items on the queue
     */
    public int size() {
        //TODO: Fill in...
        return -1;
    }

    /**
     * add the item
     */
    public void enqueue(Item item) {
        //TODO: Fill in...
    }

    /**
     * remove and return a random item
     */
    public void deque() {
        //TODO: Fill in...
    }

    /**
     * return (but do not remove) a random item
     */
    public Item sample() {
        //TODO: Fill in...
        return null;
    }

    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        //TODO: Add necessary instance variables

        public boolean hasNext() {
            //TODO: Fill in...
            return false;
        }

        public Item next() {
            //TODO: Fill in...
            return null;
        }

        public void remove() {}
    }



    /************************************************************************************************************
     *** TEST CLIENT ********************************************************************************************
     ************************************************************************************************************/

    /**
     * unit testing (optional)
     */
    public static void main(String[] args)  {

    }
}
