package cp213;

/**
 * Implements a Popularity Tree. Extends BST.
 *
 * @author Manahil Bashir, 169061137, bash1137@mylaurier.ca
 * @author David Brown
 * @version 2024-07-14
 */
public class PopularityTree<T extends Comparable<T>> extends BST<T> {

    /**
     * Auxiliary method for valid. May force node rotation if the retrieval count of
     * the located node data is incremented.
     */
    private TreeNode<T> retrieveAux(TreeNode<T> node, final CountedData<T> key) {

	if (node == null) {
	    return null;
	}

	int cmp = node.getData().compareTo(key);
	comparisons++;

	if (cmp == 0) {
	    node.getData().incrementCount();
	    return node;
	}

	TreeNode<T> result = (cmp > 0) ? retrieveAux(node.getLeft(), key) : retrieveAux(node.getRight(), key);

	if (result != null) {
	    TreeNode<T> resultChild = (cmp > 0) ? node.getLeft() : node.getRight();

	    if (resultChild != null && resultChild.getData().getCount() > node.getData().getCount()) {
		TreeNode<T> newRoot = (cmp > 0) ? rotateRight(node) : rotateLeft(node);
		updateRootIfNeeded(node, newRoot);
		return result;
	    }
	}

	return result;
    }

    private void updateRootIfNeeded(TreeNode<T> node, TreeNode<T> newRoot) {

	if (node.getData().compareTo(this.root.getData()) == 0) {
	    this.root = newRoot;
	}

	node.updateHeight();
	newRoot.updateHeight();
    }

    /**
     * Performs a left rotation around node.
     *
     * @param parent The subtree to rotate.
     * @return The new root of the subtree.
     */
    private TreeNode<T> rotateLeft(final TreeNode<T> parent) {

	TreeNode<T> rightChild = parent.getRight();
	parent.setRight(rightChild.getLeft());
	rightChild.setLeft(parent);
	parent.updateHeight();
	rightChild.updateHeight();
	return rightChild;
    }

    /**
     * /** Performs a right rotation around {@code node}.
     *
     * @param parent The subtree to rotate.
     * @return The new root of the subtree.
     */
    private TreeNode<T> rotateRight(final TreeNode<T> parent) {

	TreeNode<T> leftChild = parent.getLeft();
	if (leftChild == null) {
	    return parent;
	}

	parent.setLeft(leftChild.getRight());
	leftChild.setRight(parent);
	parent.updateHeight();
	leftChild.updateHeight();
	return leftChild;
    }

    /**
     * Replaces BST insertAux - does not increment count on repeated insertion.
     * Counts are incremented only on retrieve.
     */
    @Override
    protected TreeNode<T> insertAux(TreeNode<T> node, final CountedData<T> data) {

	if (node == null) {
	    size++;
	    return new TreeNode<>(data);
	}

	int cmp = node.getData().compareTo(data);

	if (cmp > 0) {
	    node.setLeft(insertAux(node.getLeft(), data));
	} else if (cmp < 0) {
	    node.setRight(insertAux(node.getRight(), data));
	}

	node.updateHeight();
	return node;
    }

    /**
     * Auxiliary method for valid. Determines if a subtree based on node is a valid
     * subtree. An Popularity Tree must meet the BST validation conditions, and
     * additionally the counts of any node data must be greater than or equal to the
     * counts of its children.
     *
     * @param node The root of the subtree to test for validity.
     * @return true if the subtree base on node is valid, false otherwise.
     */
    @Override
    protected boolean isValidAux(final TreeNode<T> node, TreeNode<T> minNode, TreeNode<T> maxNode) {

	if (node == null) {
	    return true;
	}

	int leftHeight = (node.getLeft() == null) ? 0 : node.getLeft().getHeight();
	int rightHeight = (node.getRight() == null) ? 0 : node.getRight().getHeight();

	int leftCount = (node.getLeft() == null) ? 0 : node.getLeft().getData().getCount();
	int rightCount = (node.getRight() == null) ? 0 : node.getRight().getData().getCount();

	boolean validOrdering = (minNode == null || node.getData().compareTo(minNode.getData()) > 0)
		&& (maxNode == null || node.getData().compareTo(maxNode.getData()) < 0);
	boolean heightOK = node.getHeight() == Math.max(leftHeight, rightHeight) + 1;
	boolean countOK = node.getData().getCount() >= leftCount && node.getData().getCount() >= rightCount;

	return validOrdering && heightOK && countOK && isValidAux(node.getLeft(), minNode, node)
		&& isValidAux(node.getRight(), node, maxNode);
    }

    /**
     * Determines whether two PopularityTrees are identical.
     *
     * @param target The PopularityTree to compare this PopularityTree against.
     * @return true if this PopularityTree and target contain nodes that match in
     *         position, item, count, and height, false otherwise.
     */
    public boolean equals(final PopularityTree<T> target) {
	return super.equals(target);
    }

    /**
     * Very similar to the BST retrieve, but increments the data count here instead
     * of in the insertion.
     *
     * @param key The key to search for.
     */
    @Override
    public CountedData<T> retrieve(CountedData<T> key) {

	TreeNode<T> found = retrieveAux(root, key);
	return (found != null) ? found.getData() : null;
    }
}