 package alda.heap;

 /**
 * V�r implementation av en D-ary Heap (fr�n en Binary Heap)
 * @author Filip Fellman
 * @author Oskar Steinhauf
 * @author Erik Sv�rdson
 */

 public class DHeap<AnyType extends Comparable<? super AnyType>> {

	private static final int DEFAULT_CAPACITY = 10;
	private int numChild; // Number of children
	private int currentSize; // Number of elements in heap
	private AnyType[] array; // The heap array

/*
* Vi har tre konstruktorer.
* F�rsta tar ej emot n�gra argument och skapar en bin�r Heap.
* Andra tar emot ett argument int d som avg�r hur m�nga barn-noder en f�r�lder-nod kan ha (minst 2 kr�vs).
* Tredje tar in en Array av godtycklig typ och bygger en Heap.
*/
	public DHeap() {
		this(2);
	}

	public DHeap(int d) {
		if (d > 1) {
			numChild = d;
			currentSize = 0;
			array = (AnyType[]) new Comparable[DEFAULT_CAPACITY+1];
		} else {
			throw new IllegalArgumentException();
		}
	}

	public DHeap(AnyType[] items) {
		currentSize = items.length;
		array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];

		int i = 1;
		for (AnyType item : items) {
			array[i++] = item;
		}
		buildHeap();
	}
/*
 * En koll g�rs f�rst om l�ngden p� listan �r mindre �n nuvarande storlek p� Heap:en, f�rstorar Heapen om plats saknas.
 * Om plats finns l�ggs x till p� den tomma platsen (h�let) i Heap:en.
 */
	public void insert(AnyType x) {
		if (currentSize == array.length - 1) {
			enlargeArray(array.length * 2 + 1);
		}

		int hole = ++currentSize; // hole = tom plats i Heapen
		array[0] = x;
		for (array[0] = x; hole > 1 && x.compareTo(findParent(hole)) < 0; hole = parentIndex(hole)) {
			array[hole] = findParent(hole);

		}
		array[hole] = x;

	}
/*
 * Hittar f�r�ldern till barnet som skickas som argument.
 */
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

	public AnyType findMin() {
		if (isEmpty()) {
			throw new UnderflowException();
		}
		return array[1];
	}
/*
 * Hittar minsta elementet i Heap:en via findMin() och tar bort det.
 */
	public AnyType deleteMin() {
		if (isEmpty()) {
			throw new UnderflowException();
		} else {

			AnyType minItem = findMin();
			array[1] = array[currentSize--];
			percolateDown(1);

			return minItem;
		}
	}

	private void buildHeap() {
		for (int i = currentSize / 2; i > 0; i--)
			percolateDown(i);
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}

	public void makeEmpty() {
		currentSize = 0;
	}
	
/*
 * Filtrerar igenom tr�det och skapar ett h�l vid paramtern. Loopen g�r igenom tr�det f�r att se vart det nya h�let ska hamna.  
 */
	private void percolateDown(int hole) {
		AnyType tmp = array[hole];

		for (; findMinimumChild(hole) <= currentSize && tmp.compareTo(minimumChild(hole)) >= 0; hole = findMinimumChild(hole)){
			array[hole] = minimumChild(hole);
		}
		array[hole] = tmp;
	}
	
	/*
	 * Hitta f�r�lderns index till barnet man skickar in och kastar ett undantag ifall det ger mindre �n 1. Eftersom f�r�ldern kan inte vara 0.
	 * */

	public int parentIndex(int i) {
		if (i <= 1) {
			throw new IllegalArgumentException();
		}

		int index = (i + numChild - 2) / numChild;

		return index;
	}
	
	/*
	 * Hittar f�rsta barnets index via paramtern i som �r f�r�ldern. 
	 * */

	public int firstChildIndex(int i) {

		if (i > 0) {
			return (i - 1) * numChild + 2;
		} else {
			throw new IllegalArgumentException();
		}

	}

	/*
	 * Funktion som returnerar index f�r minsta barnet i tr�det. Funktionen j�mf�r barnen i tr�det f�r att hitta det minsta.
	 * 
	 *  Om barnet inte finns genom paramtern parent tvingas ett indexOutOfBounds exception.
	 * */
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
				if (compareVar != null && minimumChild.compareTo(compareVar) > 0) {
					compareChild = firstChild + i;
					minimumChild = compareVar;
				}
			}
		}

		return compareChild;
	}

	private AnyType minimumChild(int parent) {
		return get(findMinimumChild(parent));
	}

	public AnyType get(int i) {
		return array[i];
	}

	public int size() {
		return currentSize;
	}
}