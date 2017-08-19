/**
* Goals: Create a generic data type Deque/double-ended queue or deque. 
* A generalization of a stack and a queue that supports adding and removing
* items from either the front or the back of the data structure.
* Usage:
* javac Deque.java
* java Deque
*/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	// instance variables
	private int n;          // size of the deque
    private Node first;     // top of deque
    private Node last;     // top of deque

    // helper linked list class
  	private class Node {
  	    private Item item;
        private Node next;
        private Node prev;

    }

    public Deque() { // construct an empty deque       
        /**
         * Initializes an empty deque.
         */
    	first = null;
    	last = null;
        n = 0;
        assert check();
    }
    
    /**
     * Is this stack empty?
     * @return true if this deque is empty; false otherwise
     */
    public boolean isEmpty() {
	    return first == null; }

    /**
     * Returns the number of items in the deque.
     * @return the number of items in the deque
     */
    public int size() {
    	return n; } 

	/** add the item to the front 
	 * @param new item list to add
	 */                     
    public void addFirst(Item item) {
        checkAdd(item);
        Node oldfirst = first; // save a link to the list
        first = new Node(); // create a new node for the beggining
        first.item = item; // set the instance variables in the new node
        first.next = oldfirst;
        if (size() != 0) {
            oldfirst.prev = first;
        }
        else {
            last = first;
        }

        n++;
        assert check();
    }

    public void addLast(Item item) { // add the item to the end
        checkAdd(item); 
        Node oldlast = last; // save a link to last node
        last = new Node(); // create a new node for the end
        last.item = item;
        last.prev = oldlast;
        last.next = null;
        if (size() != 0) {
            oldlast.next = last;
        }
        else {
            first = last;
        }
        n++;
        assert check();
    }
    
    /**
     * remove and return the item from the front
     */
    public Item removeFirst() {
        checkRemove();
		Item item = first.item; // save item to return
        first = first.next; // delete first node
        if (size() == 1) last = null;   // to avoid loitering
        if (first != null) first.prev = null;
        n--;
        assert check();

        return item;    
    }

    public Item removeLast() { // remove and return the item from the end
        checkRemove();
		Item item = last.item; // save item to return
        last = last.prev; // delete first node
        if (size() == 1) first = null;   // to avoid loitering
        if (last != null) last.next = null;
        n--;
        assert check();
        
        return item;
    }

    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     * @return an iterator to this stack that iterates through the items in LIFO order.
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    private boolean check() {

        // check a few properties of instance variable 'first'
        if (n < 0) {
            return false;
        }
        if (n == 0) {
            if (first != null) return false;
        }
        else if (n == 1) {
            if (first == null)      return false;
            if (first.next != null) return false;
        }
        else {
            if (first == null)      return false;
            if (first.next == null) return false;
        }

        // check internal consistency of instance variable n
        int numberOfNodes = 0;
        for (Node x = first; x != null && numberOfNodes <= n; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != n) return false;

        return true;
    }

    private void checkAdd(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
    }
    private void checkRemove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }
    /** public static void main(final String[] str) {
        Deque<Integer> deque = new Deque<>();
        if (deque.isEmpty())
            System.out.println("IsEmpty");
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        System.out.println(deque);
        System.out.println(deque.size()); // 3
        if (!deque.isEmpty())
            System.out.println("Not Empty");
        int number = deque.removeFirst();
        System.out.println(number); // 3
        number = deque.removeLast();
        System.out.println(number); // 1
        number = deque.removeFirst();
        System.out.println(number); // 2
        System.out.println(deque.isEmpty()); // true
        deque.addLast(1);
        deque.addLast(2);
        number = deque.removeFirst(); 
        System.out.println(number); // 1
        number = deque.removeFirst(); 
        System.out.println(number); // 2
    }  
    **/
}
