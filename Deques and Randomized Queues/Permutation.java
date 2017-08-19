/**
* GOALS: client program Permutation.java that takes a command-line integer k; 
* Reads in a sequence of strings from standard input using StdIn.readString();
* Prints exactly k of them, uniformly at random. Print each item from the sequence at most once.
* 
* Usage: 
* javac-algs4 Permutation.java
* java Permutation
*/

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
   	    int k = Integer.parseInt(args[0]);
   	    RandomizedQueue<String> queue = new RandomizedQueue<>();
   	    while (!StdIn.isEmpty()) {
   	    	queue.enqueue(StdIn.readString());
   	    }
   	    while (k > 0) {
            System.out.println(queue.dequeue());
            k--;
        }
   	}
}
