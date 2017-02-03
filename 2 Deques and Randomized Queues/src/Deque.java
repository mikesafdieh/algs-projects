/**
 * Created by michaelsafdieh on 2/2/17.
 */

import java.util.Iterator;
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

    //TODO: Add necessary instance variables

    /**
     * is the deque empty?
     */
    public boolean isEmpty() {
        //TODO: Fill in...
        return false;
    }

    /**
     * return the number of items on the deque
     */
    public int size() {
        //TODO: Fill in...
        return -1;
    }

    /**
     * add the item to the front
     */
    public void addFirst(Item item) {
        //TODO: Fill in...
    }

    /**
     * add the item to the end
     */
    public void addLast(Item item) {
        //TODO: Fill in...
    }

    /**
     * remove and return the item from the front
     */
    public Item removeFirst() {
        //TODO: Fill in...
        return null;
    }

    /**
     * remove and return the item from the end
     */
    public Item removeLast() {
        //TODO: Fill in...
        return null;
    }

    /**
     * return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
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
    public static void main(String[] args) {

    }
}
