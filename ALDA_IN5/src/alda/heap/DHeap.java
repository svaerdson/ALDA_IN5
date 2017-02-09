// Klassen i denna fil mï¿½ste dï¿½pas om till DHeap fï¿½r att testerna ska fungera. 
package alda.heap;

//DHeap class
//
//CONSTRUCTION: with optional capacity (that defaults to 100)
//            or an array containing initial items
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements a binary heap. Note that all "matching" is based on the compareTo
 * method.
 * 
 * @author Filip Fellman
 * @author Oskar Steinhauf
 * @author Erik Svärdson
 */
public class DHeap<AnyType extends Comparable<? super AnyType>> {
	/**
	 * Construct the binary heap.
	 */

	private static final int DEFAULT_CAPACITY = 10;
	private int numChild; // Number of children
	private int currentSize; // Number of elements in heap
	private AnyType[] array; // The heap array

	public DHeap() {
		this(2);
	}

	/**
	 * Construct the binary heap.
	 * 
	 * @param capacity
	 *            the capacity of the binary heap.
	 */
	public DHeap(int d) {
		if (d > 1) {
			numChild = d;
			currentSize = 0;
			array = (AnyType[]) new Comparable[DEFAULT_CAPACITY * d];
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Construct the binary heap given an array of items.
	 */
	public DHeap(AnyType[] items) {
		currentSize = items.length;
		array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];

		int i = 1;
		for (AnyType item : items) {
			array[i++] = item;
		}
		buildHeap();
	}

	/**
	 * Insert into the priority queue, maintaining heap order. Duplicates are
	 * allowed.
	 * 
	 * @param x
	 *            the item to insert.
	 */
	public void insert(AnyType x) {
		if (currentSize == array.length - 1) {
			enlargeArray(array.length * 2 + 1);
		}

		// Percolate up
		int hole = ++currentSize; // hole = tom plats i Heapen
		array[0] = x;
		for (array[0] = x; hole > 1 && x.compareTo(findParent(hole)) < 0; hole = parentIndex(hole)) {
			array[hole] = findParent(hole);
		}
		array[hole] = x;

	}

	public AnyType findParent(int child) {
		return array[parentIndex(child)];
	}

	private void enlargeArray(int newSize) {
		AnyType[] old = array;
		array = (AnyType[]) new Comparable[newSize];
		for (int i = 0; i < old.length; i++) {
			array[i] = old[i];
		}
	}

	/**
	 * Find the smallest item in the priority queue.
	 * 
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType findMin() {
		if (isEmpty()) {
			throw new UnderflowException(); // UnderFlowException?
		}
		return array[1];
	}

	/**
	 * Remove the smallest item from the priority queue.
	 * 
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType deleteMin() {
		if (isEmpty()) {
			throw new UnderflowException(); // UnderFlowException?
		}else{

		AnyType minItem = findMin();
		array[1] = array[currentSize--];
		percolateDown(1);

		return minItem;
		}
	}
	/**
	 * Establish heap order property from an arbitrary arrangement of items.
	 * Runs in linear time.
	 */
	private void buildHeap() {
		for (int i = currentSize / 2; i > 0; i--)
			percolateDown(i);
	}

	/**
	 * Test if the priority queue is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return currentSize == 0;
	}

	/**
	 * Make the priority queue logically empty.
	 */
	public void makeEmpty() {
		currentSize = 0;
	}

	/**
	 * Internal method to percolate down in the heap.
	 * 
	 * @param hole
	 *            the index at which the percolate begins.
	 */
	private void percolateDown(int hole) {
		AnyType tmp = array[hole];

		for (; findMinimumChild(hole) <= currentSize && tmp.compareTo(minimumChild(hole)) >= 0; hole = findMinimumChild(hole)) {
				array[hole] = minimumChild(hole);
			}
		array[hole] = tmp;
	}

	public int parentIndex(int i){
		if (i <= 1) {
			throw new IllegalArgumentException();
		}
		
		int index = (i + numChild - 2)/ numChild;

		return index;
	}

	public int firstChildIndex(int i) {

		if (i > 0) {
			return (i - 1) * numChild + 2;
		} else {
			throw new IllegalArgumentException();
		}

	}
	
	private int findMinimumChild(int parent) {
		int firstChild = firstChildIndex(parent);
		
		if (firstChild > currentSize) {
			return Integer.MAX_VALUE;
		}
		
		AnyType minimumChild = array[firstChild];
		int compareChild = firstChild;
		
		for (int i = 1; i < numChild; i++) {
			if (firstChild + i <= currentSize) {
				AnyType compareVar = array[firstChild + i];
				if (compareVar != null  && minimumChild.compareTo(compareVar) > 0) {
					compareChild = firstChild + i;
					minimumChild = compareVar;
				}
			} 
		}
		
		return compareChild;
	}
	
    private AnyType minimumChild(int parent){
        return get(findMinimumChild(parent));
    }

	public AnyType get(int i) {
		return array[i];
	}

	public int size() {
		return currentSize;
	}
}