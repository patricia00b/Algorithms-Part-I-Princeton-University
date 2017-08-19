/**
* A randomized queue is similar to a stack or queue, 
* except that the item removed is chosen uniformly at random from items in the data structure.
* 
* Usage:
* javac-algs4 RandomizedQueue.java
* java-algs4 RandomizedQueue
*/

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // Instance varibales
    private Item[] q; // array of items
    private int n; // number of elements on stack

    /**
     * Initializes an empty randomized queue.
     */
    public RandomizedQueue() {
    	q = (Item[]) new Object[2]; // special java cast for creation of arrays of generic types
    	n = 0;
    }
    
    /** 
     * Is the queue empty?
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() { return n == 0; }
    
    /**
     * Returns the number of items on the queue.
     * @return the number of items on the queue
     */
    public int size() { return n; } 

    /**
     * Adds the item to this queue.
     * @param item the item to add
     * @throws java.util.IllegalArgumentException if item is null
     */    
    public void enqueue(Item item) {
    	if (item == null) throw new java.lang.IllegalArgumentException();
    	if (n == q.length) resize(2*q.length); // double the size of the array of necessary
    	q[n++] = item; // add item
	}
    /**
     * Removes and returns a random item from the queue.
     * @return a random item.
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item dequeue() {
    	if (isEmpty()) throw new NoSuchElementException();
    	int idx = StdRandom.uniform(n);
    	Item item = q[idx];
    	q[n-1] = null; // to avoid loitering
    	n--;
    	// shrink size of array if necessary
    	if (n > 0 && n == q.length/4) resize(q.length/2);
    	return item;
    }

    /**
     * Returns (but do not remove) a random item
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item sample() {
    	if (isEmpty()) throw new NoSuchElementException();
    	int idx = StdRandom.uniform(n);
    	Item item = q[idx];
    	return item;
    }
    
    /**
     * Returns an iterator to this stack that iterates through the items in random order.
     * @return an iterator to this stack that iterates through the items in random order.
     */
    public Iterator<Item> iterator()
	{
		return new RandomQueueIterator();
	}
	
	private class RandomQueueIterator implements Iterator<Item>
	{
		private int i = 0;
		private final int[] indices;
		
		public RandomQueueIterator()
		{
			indices = new int[n];
			for (int j = 0; j < indices.length; j++)
			{
				indices[j] = j;
			}
			StdRandom.shuffle(indices);
		}
		
		public boolean hasNext()
		{
			return i < n;
		}
		
		public Item next() // throws NoSuchElementException
		{
			if (!hasNext()) throw new NoSuchElementException();
			return q[indices[i++]];
		}
		
		public void remove() // throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();
		}
	}
    // resize the underlying array holding the elements
    private void resize(int capacity) {
    	assert capacity >= n;

        Item[] temp = (Item[]) new Object[capacity];
    	for (int i = 0; i < n; i++) {
    		temp[i] = q[i];
    	}
    	q = temp;
    	// alternative implementation
        // a = java.util.Arrays.copyOf(a, capacity);
    }
}
