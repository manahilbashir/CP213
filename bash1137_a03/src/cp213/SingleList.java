package cp213;

/**
 * A single linked list structure of <code>Node T</code> objects. These data
 * objects must be Comparable - i.e. they must provide the compareTo method.
 * Only the <code>T</code> object contained in the priority queue is visible
 * through the standard priority queue methods. Extends the
 * <code>SingleLink</code> class.
 *
 * @author David Brown
 * @version 2025-05-04
 * @param <T> this SingleList data type.
 */
public class SingleList<T extends Comparable<T>> extends SingleLink<T> {

    /**
     * Searches for the first occurrence of key in this SingleList. Private helper
     * methods - used only by other ADT methods.
     *
     * @param key The object to look for.
     * @return A pointer to the node previous to the node containing key.
     */
    private SingleNode<T> linearSearch(final T key) {

	SingleNode<T> previous = null;
	SingleNode<T> current = this.front;

	while (current != null && current.getArtifact().compareTo(key) != 0) {
	    previous = current;
	    current = current.getNext();
	}

	return previous;
    }

    /**
     * Appends data to the end of this SingleList.
     *
     * @param artifact The object to append.
     */
    public void append(final T artifact) {

	SingleNode<T> node = new SingleNode<>(artifact, null);

	if (this.front == null) {
	    this.front = node;
	} else {
	    this.rear.setNext(node);
	}

	this.rear = node;
	this.length++;
    }

    /**
     * Removes duplicates from this SingleList. The list contains one and only one
     * of each object formerly present in this SingleList. The first occurrence of
     * each object is preserved.
     */
    public void clean() {

	SingleNode<T> current = this.front;

	while (current != null) {
	    T key = current.getArtifact();
	    SingleNode<T> prev = current;
	    SingleNode<T> scan = current.getNext();

	    while (scan != null) {
		if (scan.getArtifact().compareTo(key) == 0) {
		    prev.setNext(scan.getNext());
		    this.length--;
		    if (scan == this.rear) {
			this.rear = prev;
		    }
		} else {
		    prev = scan;
		}
		scan = scan.getNext();
	    }

	    current = current.getNext();
	}
    }

    /**
     * Combines contents of two lists into a third. Values are alternated from the
     * origin lists into this SingleList. The origin lists are empty when finished.
     * NOTE: data must not be moved, only nodes.
     *
     * @param left  The first list to combine with this SingleList.
     * @param right The second list to combine with this SingleList.
     */
    public void combine(final SingleList<T> left, final SingleList<T> right) {

	while (!left.isEmpty() || !right.isEmpty()) {
	    if (!left.isEmpty()) {
		this.moveFrontToRear(left);
	    }
	    if (!right.isEmpty()) {
		this.moveFrontToRear(right);
	    }
	}
    }

    /**
     * Determines if this SingleList contains key.
     *
     * @param key The key object to look for.
     * @return true if key is in this SingleList, false otherwise.
     */
    public boolean contains(final T key) {

	return this.find(key) != null;
    }

    /**
     * Finds the number of times key appears in list.
     *
     * @param key The object to look for.
     * @return The number of times key appears in this SingleList.
     */
    public int count(final T key) {

	int cnt = 0;
	SingleNode<T> current = this.front;

	while (current != null) {
	    if (current.getArtifact().compareTo(key) == 0) {
		cnt++;
	    }
	    current = current.getNext();
	}

	return cnt;
    }

    /**
     * Finds and returns the object in list that matches key.
     *
     * @param key The object to search for.
     * @return The object that matches key, null otherwise.
     */
    public T find(final T key) {

	SingleNode<T> current = this.front;

	while (current != null) {
	    if (current.getArtifact().compareTo(key) == 0) {
		return current.getArtifact();
	    }
	    current = current.getNext();
	}

	return null;
    }

    /**
     * Get the nth object in this SingleList.
     *
     * @param n The index of the object to return.
     * @return The nth object in this SingleList.
     * @throws ArrayIndexOutOfBoundsException if n is not a valid index.
     */
    public T get(final int n) throws ArrayIndexOutOfBoundsException {

	if (n < 0 || n >= this.length) {
	    throw new ArrayIndexOutOfBoundsException();
	}

	SingleNode<T> current = this.front;

	for (int i = 0; i < n; i++) {
	    current = current.getNext();
	}

	return current.getArtifact();
    }

    /**
     * Determines whether two lists are identical.
     *
     * @param source The list to compare against this SingleList.
     * @return true if this SingleList contains the same objects in the same order
     *         as source, false otherwise.
     */
    public boolean equals(final SingleList<T> source) {

	if (this.length != source.length) {
	    return false;
	}

	SingleNode<T> curr1 = this.front;
	SingleNode<T> curr2 = source.front;

	while (curr1 != null && curr2 != null) {
	    if (curr1.getArtifact().compareTo(curr2.getArtifact()) != 0) {
		return false;
	    }
	    curr1 = curr1.getNext();
	    curr2 = curr2.getNext();
	}

	return true;
    }

    /**
     * Finds the first location of a object by key in this SingleList.
     *
     * @param key The object to search for.
     * @return The index of key in this SingleList, -1 otherwise.
     */
    public int index(final T key) {

	int i = 0;
	SingleNode<T> current = this.front;

	while (current != null) {
	    if (current.getArtifact().compareTo(key) == 0) {
		return i;
	    }
	    current = current.getNext();
	    i++;
	}

	return -1;
    }

    /**
     * Inserts object into this SingleList at index i. If i greater than the length
     * of this SingleList, append data to the end of this SingleList.
     *
     * @param i        The index to insert the new data at.
     * @param artifact The new object to insert into this SingleList.
     */
    public void insert(int i, final T artifact) {

	if (i <= 0 || this.front == null) {
	    this.prepend(artifact);
	} else if (i >= this.length) {
	    this.append(artifact);
	} else {
	    SingleNode<T> prev = this.front;
	    for (int j = 0; j < i - 1; j++) {
		prev = prev.getNext();
	    }

	    SingleNode<T> node = new SingleNode<>(artifact, prev.getNext());
	    prev.setNext(node);
	    this.length++;
	}
    }

    /**
     * Creates an intersection of two other SingleLists into this SingleList. Copies
     * data to this SingleList. left and right SingleLists are unchanged. Values
     * from left are copied in order first, then objects from right are copied in
     * order.
     *
     * @param left  The first SingleList to create an intersection from.
     * @param right The second SingleList to create an intersection from.
     */
    public void intersection(final SingleList<T> left, final SingleList<T> right) {

	for (T l : left) {
	    for (T r : right) {
		if (l.compareTo(r) == 0 && !this.contains(l)) {
		    this.append(l);
		}
	    }
	}
    }

    /**
     * Finds the maximum object in this SingleList.
     *
     * @return The maximum object.
     */
    public T max() {

	if (this.isEmpty()) {
	    return null;
	}

	T max = this.front.getArtifact();
	SingleNode<T> current = this.front.getNext();

	while (current != null) {
	    if (current.getArtifact().compareTo(max) > 0) {
		max = current.getArtifact();
	    }
	    current = current.getNext();
	}

	return max;
    }

    /**
     * Finds the minimum object in this SingleList.
     *
     * @return The minimum object.
     */
    public T min() {

	if (this.isEmpty()) {
	    return null;
	}

	T min = this.front.getArtifact();
	SingleNode<T> current = this.front.getNext();

	while (current != null) {
	    if (current.getArtifact().compareTo(min) < 0) {
		min = current.getArtifact();
	    }
	    current = current.getNext();
	}

	return min;
    }

    /**
     * Inserts object into the front of this SingleList.
     *
     * @param artifact The object to insert into the front of this SingleList.
     */
    public void prepend(final T artifact) {

	SingleNode<T> node = new SingleNode<>(artifact, this.front);
	this.front = node;
	if (this.rear == null) {
	    this.rear = node;
	}
	this.length++;
    }

    /**
     * Finds, removes, and returns the object in this SingleList that matches key.
     *
     * @param key The object to search for.
     * @return The object matching key, null otherwise.
     */
    public T remove(final T key) {

	T result = null;

	if (this.front != null) {
	    if (this.front.getArtifact().compareTo(key) == 0) {
		result = this.front.getArtifact();
		this.front = this.front.getNext();
		if (this.front == null) {
		    this.rear = null;
		}
		this.length--;
	    } else {
		SingleNode<T> prev = this.linearSearch(key);
		if (prev != null && prev.getNext() != null) {
		    SingleNode<T> target = prev.getNext();
		    result = target.getArtifact();
		    prev.setNext(target.getNext());
		    if (target == this.rear) {
			this.rear = prev;
		    }
		    this.length--;
		}
	    }
	}

	return result;
    }

    /**
     * Removes the object at the front of this SingleList.
     *
     * @return The object at the front of this SingleList.
     */
    public T removeFront() {

	if (this.front == null) {
	    return null;
	}

	T result = this.front.getArtifact();
	this.front = this.front.getNext();
	if (this.front == null) {
	    this.rear = null;
	}
	this.length--;

	return result;
    }

    /**
     * Finds and removes all objects in this SingleList that match key.
     *
     * @param key The object to search for.
     */
    public void removeMany(final T key) {

	while (this.contains(key)) {
	    this.remove(key);
	}
    }

    /**
     * Reverses the order of the objects in this SingleList.
     */
    public void reverse() {

	SingleNode<T> prev = null;
	SingleNode<T> current = this.front;
	this.rear = this.front;

	while (current != null) {
	    SingleNode<T> next = current.getNext();
	    current.setNext(prev);
	    prev = current;
	    current = next;
	}

	this.front = prev;
    }

    /**
     * Splits the contents of this SingleList into the left and right SingleLists.
     * Moves nodes only - does not move object or call the high-level methods insert
     * or remove. this SingleList is empty when done. The first half of this
     * SingleList is moved to left, and the last half of this SingleList is moved to
     * right. If the resulting lengths are not the same, left should have one more
     * object than right. Order is preserved.
     *
     * @param left  The first SingleList to move nodes to.
     * @param right The second SingleList to move nodes to.
     */
    public void split(final SingleList<T> left, final SingleList<T> right) {

	int mid = (this.length + 1) / 2;
	for (int i = 0; i < this.length; i++) {
	    if (i < mid) {
		left.moveFrontToRear(this);
	    } else {
		right.moveFrontToRear(this);
	    }
	}
    }

    /**
     * Splits the contents of this SingleList into the left and right SingleLists.
     * Moves nodes only - does not move object or call the high-level methods insert
     * or remove. this SingleList is empty when done. Nodes are moved alternately
     * from this SingleList to left and right. Order is preserved.
     *
     * @param left  The first SingleList to move nodes to.
     * @param right The second SingleList to move nodes to.
     */
    public void splitAlternate(final SingleList<T> left, final SingleList<T> right) {

	boolean toLeft = true;
	while (!this.isEmpty()) {
	    if (toLeft) {
		left.moveFrontToRear(this);
	    } else {
		right.moveFrontToRear(this);
	    }
	    toLeft = !toLeft;
	}
    }

    /**
     * Creates a union of two other SingleLists into this SingleList. Copies object
     * to this list. left and right SingleLists are unchanged. Values from left are
     * copied in order first, then objects from right are copied in order.
     *
     * @param left  The first SingleList to create a union from.
     * @param right The second SingleList to create a union from.
     */
    public void union(final SingleList<T> left, final SingleList<T> right) {

	for (T value : left) {
	    if (!this.contains(value)) {
		this.append(value);
	    }
	}

	for (T value : right) {
	    if (!this.contains(value)) {
		this.append(value);
	    }
	}
    }
}
