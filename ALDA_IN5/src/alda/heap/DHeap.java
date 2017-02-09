 package alda.heap;

 /**
 * Vår implementation av en D-ary Heap (från en Binary Heap)
 * @author Filip Fellman
 * @author Oskar Steinhauf
 * @author Erik Svärdson
 */

 public class DHeap<AnyType extends Comparable<? super AnyType>> {

	private static final int DEFAULT_CAPACITY = 10;
	private int numChild; // Number of children
	private int currentSize; // Number of elements in heap
	private AnyType[] array; // The heap array

/*
* Vi har tre konstruktorer.
* Första tar ej emot några argument och skapar en binär Heap.
* Andra tar emot ett argument int d som avgör hur många barn-noder en förälder-nod kan ha (minst 2 krävs).
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
 * En koll görs först om längden på listan är mindre än nuvarande storlek på Heap:en, förstorar Heapen om plats saknas.
 * Om plats finns läggs x till på den tomma platsen (hålet) i Heap:en.
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
 * Hittar föräldern till barnet som skickas som argument.
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
 * Filtrerar igenom trädet och skapar ett hål vid paramtern. Loopen går igenom trädet för att se vart det nya hålet ska hamna.  
 */
	private void percolateDown(int hole) {
		AnyType tmp = array[hole];

		for (; findMinimumChild(hole) <= currentSize && tmp.compareTo(minimumChild(hole)) >= 0; hole = findMinimumChild(hole)){
			array[hole] = minimumChild(hole);
		}
		array[hole] = tmp;
	}
	
	/*
	 * Hitta förälderns index till barnet man skickar in och kastar ett undantag ifall det ger mindre än 1. Eftersom föräldern kan inte vara 0.
	 * */

	public int parentIndex(int i) {
		if (i <= 1) {
			throw new IllegalArgumentException();
		}

		int index = (i + numChild - 2) / numChild;

		return index;
	}
	
	/*
	 * Hittar första barnets index via paramtern i som är föräldern. 
	 * */

	public int firstChildIndex(int i) {

		if (i > 0) {
			return (i - 1) * numChild + 2;
		} else {
			throw new IllegalArgumentException();
		}

	}

	/*
	 * Funktion som returnerar index för minsta barnet i trädet. Funktionen jämför barnen i trädet för att hitta det minsta.
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