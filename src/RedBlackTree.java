import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * This class will organize the 3 largest daily covid cases
 * in a single day which was found using the MinPQ() class
 * Data Structures: Array, Binary Search Tree
 * Note: Unless stated otherwise, all the code was written from scratch
 * 
 * @author Bryce Lehnen
 */

public class RedBlackTree<Key extends Comparable<Key>, Value> {
	
	/**
	 * Creates red and black varaibles and the node class
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 */
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	private Node root;
	
	private class Node {
		private Key key;			// Key
		private Value val;			// Data
		private Node left, right;	// Links to children
		private boolean color;		// Color of parent link
		private int size;			// Subtree count
		
		// Creates a new node based on the inputs
		public Node(Key key, Value val, boolean color, int size) {
			this.key = key;
			this.val = val;
			this.color = color;
			this.size = size;
		}
	}
	
	/**
	 * Creates a blank BST. Only 1 will be created for Program 1
	 */
	public RedBlackTree() {
	}
	
	/**
	 * Helper methods
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 */
	// True if x is red, false if x is black
	private boolean IsRed(Node x) {
		if (x == null) return false;
		return x.color == RED;
	}
	// Number of nodes in a subtree rooted at x; 0 if x is null
	private int Size(Node x) {
		if (x == null) return 0;
		return x.size;
	}
	// Returns the size of the entire tree
	public int size() {
		return Size(root);
	}
	// Checks if the tree is empty
	public boolean IsEmpty() {
		return root == null;
	}
	
	
	/**
	 * Public method that calls the private method
	 * Will overwrite the value if the key already exists
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 * 
	 * @param data
	 */
	public void insert(Key key, Value val) {
		// Excepetions if null is given for the key or value
		if (key == null) throw new IllegalArgumentException("first argument to put() is null");
		if (val == null) {
			delete(key);
			return;
		}
		
		root = Insert(root, key, val);
		root.color = BLACK;
	}
	
	/**
	 * Inserts given data into the BST. If it already exists
	 * ie. the number for new cases is already in the tree
	 * then it does not overwrite it
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 * 
	 * @param n The current node in the BST
	 * @param data The continent, country, date, total cases, new cases, and population 
	 */
	private Node Insert(Node h, Key key, Value val) {
		// Placed new node
		if (h == null) {
			return new Node(key, val, RED, 1);
		}
		
		int cmp = key.compareTo(h.key);
		// Uses recursion based on whether the key
		// is less than or greater than the current key
		if (cmp < 0) {
			h.left = Insert(h.left, key, val);
		}
		else if (cmp > 0) {
			h.right = Insert(h.right, key, val);
		}
		else {
			h.val = val;
		}
		
		// Rotates the BST to keep it in balance
		if (IsRed(h.right) && !IsRed(h.left)) {
			h = RotateLeft(h);
		}
		if (IsRed(h.left) && IsRed(h.left.left)) {
			h = RotateRight(h);
		}
		if (IsRed(h.left) && IsRed(h.right)) {
			FlipColors(h);
		}
		
		// Corrects the size and returns h
		h.size = Size(h.left) + Size(h.right) + 1;  
		return h;
	}
	
	/**
	 * Helper functions to rotate the BST keeping
	 * it in balance based on the number of black node
	 * connections
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 */
	// Makes a right leaning link lean to the left
	// Makes the right child the parent, the parent the left child
	// and if the the right child has a left child, that child now becomes the right child
	// of the former parent
	private Node RotateLeft(Node h) {
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = x.left.color;
		x.left.color = RED;
		x.size = h.size;
		h.size = Size(h.left) + Size(h.right) + 1;  
		
		return x;
	}
	
	// Makes a left leaning link lean to the right
	// Makes the left child the parent, the parent the right child
	// and if the left child has a right child, that child now becomes the left child
	// of the former parent
	private Node RotateRight(Node h) {
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = x.right.color;
		x.right.color = RED;
		x.size = h.size;
		h.size = Size(h.left)+ Size(h.right) + 1;
		
		return x;
	}
	
	// Simply flips the colors of node h and
	// two children
	private void FlipColors(Node h) {
		h.color = !h.color;
		h.left.color = !h.left.color;
		h.right.color = !h.right.color;
	}
	
	/**
	 * Deletes the given node
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 */
	public void delete(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to delete() is null");
		if (!Contains(key)) return;
		
		// If both children are black, set root to red
		if (!IsRed(root.left) && !IsRed(root.right)) {
			root.color = RED;
		}
		
		root = Delete(root, key);
		if (!IsEmpty()) {
			root.color = BLACK;
		}
	}
	// Different cases based on the configuration of children
	// and the color of those children to rotate and balance
	// the tree
	private Node Delete(Node h, Key key) {
		if (key.compareTo(h.key) < 0) {
			if (IsRed(h.left) && !IsRed(h.left.left)) {
				h = MoveRedLeft(h);
			}
			h.left = Delete(h.left, key);
		}
		else {
			if (IsRed(h.left)) {
				h = RotateRight(h);
			}
			if (key.compareTo(h.key) == 0 && (h.right == null)) {
				return null;
			}
			if (!IsRed(h.right) && !IsRed(h.right.left)) {
				h = MoveRedRight(h);
			}
			if (key.compareTo(h.key) == 0) {
				Node x = Min(h.right);
				h.key = x.key;
				h.val = x.val;
				h.right = DeleteMin(h.right);
			}
			else {
				h.right = Delete(h.right, key);
			}
		}
		return Balance(h);
	}
	
	/**
	 * Helper functions for the delete
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 */
	// Assuming that h is red and both its left child and grandchild
	// are black, make h.left or one of its children red
	private Node MoveRedLeft(Node h) {
		FlipColors(h);
		if (IsRed(h.right.left)) {
			h.right = RotateRight(h.right);
			h = RotateLeft(h);
			FlipColors(h);
		}
		return h;
	}
	// Assuming that h is red and both its right child and h.right.left
	// are black, make h.right or one of its children red
	private Node MoveRedRight(Node h) {
		FlipColors(h);
		if (IsRed(h.left.left)) {
			h = RotateRight(h);
			FlipColors(h);
		}
		return h;
	}
	
	/**
	 * Deletes the minimum of the BST
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 */
	public void deleteMin() {
		if (IsEmpty()) throw new NoSuchElementException("BST underflow");
		
		// If both children are black, sets root to red
		if (!IsRed(root.left) && !IsRed(root.right)) {
			root.color = RED;
		}
		
		root = DeleteMin(root);
		if (!IsEmpty()) {
			root.color = BLACK;
		}
	}
	private Node DeleteMin(Node h) {
		if (h.left == null) {
			return null;
		}
		if (!IsRed(h.left) && !IsRed(h.left.left)) {
			h = MoveRedLeft(h);
		}
		
		h.left = DeleteMin(h.left);
		return Balance(h);
	}
	
	/**
	 * Balances the tree with RotateLeft, RotateRight, and FlipColors
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 */
	private Node Balance(Node h) {
		// Rotates the BST to keep it in balance
		if (IsRed(h.right) && !IsRed(h.left)) {
			h = RotateLeft(h);
		}
		if (IsRed(h.left) && IsRed(h.left.left)) {
			h = RotateRight(h);
		}
		if (IsRed(h.left) && IsRed(h.right)) {
			FlipColors(h);
		}
		
		h.size = Size(h.left) + Size(h.right) + 1;
		return h;
	}
	
	/**
	 * Returns all the data for a given node if
	 * it can match with the key given. If not,
	 * then it returns null
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 * 
	 * @param k The number of new cases
	 * @return The continent, country, date, total cases, new cases, and population in an array
	 */
	public Value get(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to get() is null");
		return Get(root, key);
	}
	private Value Get(Node x, Key key) {
		while (x != null) {
			int cmp = key.compareTo(x.key);
			
			// If the key given is less than the current key, go to the left child
			// If the key given is greater than the current key, go to the right child
			// If it matches, return the value at the given key
			// If unfound, return null
			if (cmp < 0) {
				x = x.left;
			}
			else if (cmp > 0) {
				x = x.right;
			}
			else {
				return x.val;
			}
		}
		return null;
	}
	
	/**
	 * Returns the minimum key
	 * Travels down the left branch until it reaches the bottom
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 */
	public Key min() {
		if (IsEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
		return Min(root).key;
	}
	private Node Min(Node x) {
		if (x.left == null) {
			return x;
		}
		else {
			return Min(x.left);
		}
	}
	
	/**
	 * Returns the maximum key
	 * Travels down the right branch until it reaches the bottom
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 */
	public Key max() {
		if (IsEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
		return Max(root).key;
	}
	private Node Max(Node x) {
		if (x.right == null) {
			return x;
		}
		else {
			return Max(x.right);
		}
	}
	
	/**
	 * Rerturns true or false based on whether or not the
	 * given key is already in the BST. Used as a way
	 * to not overwrite the first key/value pair that was
	 * written first
	 */
	public boolean Contains(Key key) {
		return get(key) != null;
	}
	
	/**
	 * Returns the color of the given node
	 */
	public boolean getColor(Key key) {
		return GetColor(root, key);
	}
	private boolean GetColor(Node x, Key key) {
		while (x != null) {
			int cmp = key.compareTo(x.key);
			
			// If the key given is less than the current key, go to the left child
			// If the key given is greater than the current key, go to the right child
			// If it matches, return the value at the given key
			// If unfound, return null
			if (cmp < 0) {
				x = x.left;
			}
			else if (cmp > 0) {
				x = x.right;
			}
			else {
				return x.color;
			}
		}
		return false;
	}
	
	/**
	 * Returns all the keys in the RBT
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 */
	public Iterable<Key> keys() {
		return keys(min(), max());
	}
	
	/**
	 * Returns all the keys in a given range
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 */
	public Iterable<Key> keys(Key lo, Key hi) {
		if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");
        
        Queue<Key> queue = new LinkedList<>();
        Keys(root, queue, lo, hi);
        return queue;
	}
	
	/**
	 * Adds the keys between hi and lo in the subtree of rooted at x
	 * Ie. every node from lo to x to hi (lo<x<hi)
	 * 
	 * Taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/RedBlackBST.java.html
	 */
	private void Keys(Node x, Queue<Key> queue, Key lo, Key hi) {
		if (x == null) return;
		int cmplo = lo.compareTo(x.key);
		int cmphi = hi.compareTo(x.key);
		
		if (cmplo < 0) {
			Keys(x.left, queue, lo, hi);
		}
		if (cmplo <= 0 && cmphi >= 0) {
			queue.add(x.key);
		}
		if (cmphi > 0) {
			Keys(x.right, queue, lo, hi);
		}
	}
}
