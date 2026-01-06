package cp213;

/**
 * DO NOT CHANGE THE CONTENTS OF THIS CLASS.
 *
 * The individual node of a linked structure that stores <code>T</code> objects.
 * This is a singly linked node. The node link can be updated, but not the node
 * artifact, in order to avoid copying or moving values between nodes. Data
 * structures must be updated by moving nodes, not by copying or moving
 * artifact.
 *
 * @author David Brown
 * @version 2025-05-04
 * @param <T> data type for structure.
 */
public final class SingleNode<T> {

    /**
     * The generic artifact stored in the node.
     */
    private T artifact = null;
    /**
     * Link to the next Node.
     */
    private SingleNode<T> next = null;

    /**
     * Creates a new node with artifact and link to next node. Not copy safe as it
     * accepts a reference to the artifact rather than a copy of the artifact.
     *
     * @param artifact the artifact to store in the node.
     * @param next     the next node to link to.
     */
    public SingleNode(final T artifact, final SingleNode<T> next) {
	this.artifact = artifact;
	this.next = next;
    }

    /**
     * Returns the node artifact. Not copy safe as it returns a reference to the
     * artifact, not a copy of the artifact.
     *
     * @return The artifact portion of the node.
     */
    public T getArtifact() {
	return this.artifact;
    }

    /**
     * Returns the next node in the linked structure.
     *
     * @return The node that follows this node.
     */
    public SingleNode<T> getNext() {
	return this.next;
    }

    /**
     * Links this node to the next node.
     *
     * @param next The new node to link to.
     */
    public void setNext(final SingleNode<T> next) {
	this.next = next;
    }
}
