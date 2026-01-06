package cp213;

/**
 * A single linked queue structure of <code>Node T</code> objects. Only the
 * <code>T</code> object contained in the queue is visible through the standard
 * queue methods. Extends the <code>SingleLink</code> class.
 *
 * @author David Brown
 * @version 2025-05-04
 * @param <T> the SingleQueue data type.
 */
public class SingleQueue<T> extends SingleLink<T> {

    /**
     * Combines the contents of the left and right SingleQueues into the current
     * SingleQueue. Moves nodes only - does not refer to objects in any way, or call
     * the high-level methods insert or remove. left and right SingleQueues are
     * empty when done. Nodes are moved alternately from left and right to this
     * SingleQueue.
     *
     * You have two source queues named left and right. Move all nodes from these
     * two queues to the current queue. It does not make a difference if the current
     * queue is empty or not, just get nodes from the right and left queues and add
     * them to the current queue. You may use any appropriate SingleLink helper
     * methods available.
     *
     * Do not assume that both right and left queues are of the same length.
     *
     * @param left  The first SingleQueue to extract nodes from.
     * @param right The second SingleQueue to extract nodes from.
     */
    public void combine(final SingleQueue<T> left, final SingleQueue<T> right) {

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
     * Adds object to the rear of the queue. Increments the queue length.
     *
     * @param artifact The object to added to the rear of the queue.
     */
    public void insert(final T artifact) {

	SingleNode<T> node = new SingleNode<>(artifact, null);

	if (this.rear == null) {
	    this.front = node;
	} else {
	    this.rear.setNext(node);
	}

	this.rear = node;
	this.length++;
    }

    /**
     * Returns the front object of the queue and removes that object from the queue.
     * The next node in the queue becomes the new first node. Decrements the queue
     * length.
     *
     * @return The object at the front of the queue.
     */
    public T remove() {

	if (this.front == null) {
	    return null;
	}

	T data = this.front.getArtifact();
	this.front = this.front.getNext();

	if (this.front == null) {
	    this.rear = null;
	}

	this.length--;
	return data;
    }

    /**
     * Splits the contents of the current SingleQueue into the left and right
     * SingleQueues. Moves nodes only - does not move object or call the high-level
     * methods insert or remove. this SingleQueue is empty when done. Nodes are
     * moved alternately from this SingleQueue to left and right. left and right may
     * already contain objects.
     *
     * This is the opposite of the combine method.
     *
     * @param left  The first SingleQueue to move nodes to.
     * @param right The second SingleQueue to move nodes to.
     */
    public void splitAlternate(final SingleQueue<T> left, final SingleQueue<T> right) {

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
}
