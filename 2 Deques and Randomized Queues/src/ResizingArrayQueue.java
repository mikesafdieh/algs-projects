/**
 * Created by michaelsafdieh on 2/3/17.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ResizingArrayQueue<Item> implements Iterable<Item> {

    private Item[] q; // array of items
    private int n; // number of elements on stack
    private int head; // index of first element of queue
    private int tail; // index of next available slot (equal to head when queue is empty)

    public ResizingArrayQueue() {
        q = (Item[]) new Object[2];
        n = 0;
        head = 0;
        tail = 0;
    }


    public boolean isEmpty() { return n == 0; }

    public int size() { return n; }

    public void enqueue(Item item) {
        if (n == q.length) resize(2 * q.length);
        if (tail == q.length) tail = 0; // circular queue
        q[tail++] = item;
        n++;

        StdOut.println("enqueue(" + item + "):");
        printQueue();
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue Underflow");
        if (head == q.length) head = 0; // circular queue
        Item item = q[head++];
        q[head - 1] = null; // avoid loitering
        if (n == 0) tail = head;
        n--;
        if (n > 0 && n == q.length/4) resize(q.length/2);

        StdOut.println("dequeue():");
        printQueue();

        return item;
    }

    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        //TODO: Add necessary instance variables
        private int i = 0;

        public boolean hasNext() { return i < n; }

        public Item next() { 
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[(head + i) % q.length];
            i++;
            return item;
        }

        public void remove() { throw new UnsupportedOperationException(); }
    }



    /************************************************************************************************************
     *** PRIVATE METHODS ****************************************************************************************
     ************************************************************************************************************/

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int curr = head, i = 0; i < n; curr++, i++) {
            if (curr == q.length)
                curr = 0;
            temp[i] = q[curr];
        }
        head = 0;
        tail = n;
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
        StdOut.println("head: " + head);
        StdOut.println("tail: " + tail);
        StdOut.println("size of array: " + q.length + "\n");
    }



    /************************************************************************************************************
     *** TEST CLIENT ********************************************************************************************
     ************************************************************************************************************/

    public static void main(String[] args) {
        ResizingArrayQueue<String> queue = new ResizingArrayQueue<String>();
        while (!StdIn.isEmpty()) {
            // StdOut.println("Count: " + count);
            String item = StdIn.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println("(" + queue.size() + " left on queue)");
    }
}
