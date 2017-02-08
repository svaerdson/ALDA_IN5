// Klassen i denna fil m�ste d�pas om till DHeap f�r att testerna ska fungera. 
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
//Throws IllegalArgumentException as appropriate

/**
* Implements a binary heap.
* Note that all "matching" is based on the compareTo method.
* @author Filip Fellman
* @author Oskar Steinhauf
*/
public class DHeap<AnyType extends Comparable<? super AnyType>>
{
 /**
  * Construct the binary heap.
  */
	
 private static final int DEFAULT_CAPACITY = 10;	 
 private int numChild;			//Number of children 
 private int currentSize;      // Number of elements in heap
 private AnyType [ ] array; // The heap array
 
 public DHeap( )
 {
     this( 2 );
 }

 /**
  * Construct the binary heap.
  * @param capacity the capacity of the binary heap.
  */
 public DHeap( int d )
 {	 
	 if (d > 1) {
		 numChild = d;
	     currentSize = 0;
	     array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY * d ];
	 }else {
		 throw new IllegalArgumentException();
	 }
 }
 
 /**
  * Construct the binary heap given an array of items.
  */
 public DHeap( AnyType [ ] items )
 {
         currentSize = items.length;
         array = (AnyType[]) new Comparable[ ( currentSize + 2 ) * 11 / 10 ];

         int i = 1;
         for( AnyType item : items )
             array[ i++ ] = item;
         buildHeap( );
 }

 /**
  * Insert into the priority queue, maintaining heap order.
  * Duplicates are allowed.
  * @param x the item to insert.
  */
 public void insert( AnyType x )
 {
     if( currentSize == array.length - 1 )
         enlargeArray( array.length * 2 + 1 );

         // Percolate up
     int hole = ++currentSize;
     for( array[ 0 ] = x; x.compareTo( array[ hole / 2 ] ) < 0; hole /= 2 )
         array[ hole ] = array[ hole / 2 ];
     array[ hole ] = x;
 }


 private void enlargeArray( int newSize )
 {
         AnyType [] old = array;
         array = (AnyType []) new Comparable[ newSize ];
         for( int i = 0; i < old.length; i++ )
             array[ i ] = old[ i ];        
 }
 
 /**
  * Find the smallest item in the priority queue.
  * @return the smallest item, or throw an IllegalArgumentException if empty.
  */
 public AnyType findMin( )
 {
     if( isEmpty( ) )
         throw new IllegalArgumentException( ); //UnderFlowException?
     return array[ 1 ];
 }

 /**
  * Remove the smallest item from the priority queue.
  * @return the smallest item, or throw an IllegalArgumentException if empty.
  */
 public AnyType deleteMin( )
 {
     if( isEmpty( ) )
         throw new IllegalArgumentException( ); //UnderFlowException?

     AnyType minItem = findMin( );
     array[ 1 ] = array[ currentSize-- ];
     percolateDown( 1 );

     return minItem;
 }

 /**
  * Establish heap order property from an arbitrary
  * arrangement of items. Runs in linear time.
  */
 private void buildHeap( )
 {
     for( int i = currentSize / 2; i > 0; i-- )
         percolateDown( i );
 }

 /**
  * Test if the priority queue is logically empty.
  * @return true if empty, false otherwise.
  */
 public boolean isEmpty( )
 {
     return currentSize == 0;
 }

 /**
  * Make the priority queue logically empty.
  */
 public void makeEmpty( )
 {
     currentSize = 0;
 }

 /**
  * Internal method to percolate down in the heap.
  * @param hole the index at which the percolate begins.
  */
 private void percolateDown( int hole )
 {
     int child;
     AnyType tmp = array[ hole ];

     for( ; hole * 2 <= currentSize; hole = child )
     {
         child = hole * 2;
         if( child != currentSize &&
                 array[ child + 1 ].compareTo( array[ child ] ) < 0 )
             child++;
         if( array[ child ].compareTo( tmp ) < 0 )
             array[ hole ] = array[ child ];
         else
             break;
     }
     array[ hole ] = tmp;
 }

     // Test program
 public static void main( String [ ] args )
 {
     int numItems = 10000;
     DHeap<Integer> h = new DHeap<>( );
     int i = 37;

     for( i = 37; i != 0; i = ( i + 37 ) % numItems )
    	 System.out.println(i);
         h.insert( i );
     for( i = 1; i < numItems; i++ )
         if( h.deleteMin( ) != i )
             System.out.println( "Oops! " + i );
 }

public int parentIndex(int i) {
	System.out.println(numChild);
	return (i+numChild-2) / numChild;
}

public int firstChildIndex(int i) {
	// TODO Auto-generated method stub
	return 0;
}

public AnyType get(int i) {
	return array[i];
}

public int size() {
	return currentSize;
}
}