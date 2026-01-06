package cp213;

import java.util.ArrayList;

/**
 * Implements a Binary Search Tree.
 *
 * @author Manahil Bashir, 169061137, bash1137@mylaurier.ca
 * @author David Brown
 * @version 2025-07-14
 */
public class BST<T extends Comparable<T>> {
    // Attributes.
    /**
     * Count of comparisons performed by tree.
     */
    protected int comparisons = 0;

    /**
     * Root node of the tree.
     */
    protected TreeNode<T> root = null;

    /**
     * Number of nodes in the tree.
     */
    protected int size = 0;

    /**
     * Auxiliary method for {@code equals}. Determines whether two subtrees are
     * identical in datas and height.
     *
     * @param source Node of this BST.
     * @param target Node of that BST.
     * @return true if source and target are identical in datas and height.
     */
    protected boolean equalsAux(final TreeNode<T> source, final TreeNode<T> target) {

	if (source == null || target == null || source.getHeight() != target.getHeight()) {
	    return false;
	}
	ArrayList<CountedData<T>> list1 = source.inOrder();
	ArrayList<CountedData<T>> list2 = target.inOrder();

	if (list1.size() != list2.size()) {
	    return false;
	}

	for (int i = 0; i < list1.size(); i++) {
	    if (!list1.get(i).equals(list2.get(i))) {
		return false;
	    }
	}
	return true;
    }

    /**
     * Auxiliary method for insert. Inserts data into this BST.
     *
     * @param node The current node (TreeNode).
     * @param data Data to be inserted into the tree.
     * @return The inserted node.
     */
    protected TreeNode<T> insertAux(TreeNode<T> node, final CountedData<T> data) {

	if (node == null) {
	    data.incrementCount();
	    size++;
	    return new TreeNode<>(data);
	}

	int result = node.getData().compareTo(data);

	if (result > 0) {
	    node.setLeft(insertAux(node.getLeft(), data));
	} else if (result < 0) {
	    node.setRight(insertAux(node.getRight(), data));
	} else {
	    node.getData().incrementCount();
	}

	node.updateHeight();
	return node;
    }

    /**
     * Auxiliary method for valid. Determines if a subtree based on node is a valid
     * subtree.
     *
     * @param node    The root of the subtree to test for validity.
     * @param minNode The node of the minimum data in the current subtree.
     * @param maxNode The node of the maximum data in the current subtree.
     * @return true if the subtree base on node is valid, false otherwise.
     */
    protected boolean isValidAux(final TreeNode<T> node, TreeNode<T> minNode, TreeNode<T> maxNode) {

	if (node == null) {
	    return true;
	}

	int left = (node.getLeft() == null) ? 0 : node.getLeft().getHeight();
	int right = (node.getRight() == null) ? 0 : node.getRight().getHeight();

	boolean heightCheck = node.getHeight() == Math.max(left, right) + 1;
	boolean orderingCheck = (minNode == null || node.getData().compareTo(minNode.getData()) > 0)
		&& (maxNode == null || node.getData().compareTo(maxNode.getData()) < 0);

	return heightCheck && orderingCheck && isValidAux(node.getLeft(), minNode, node)
		&& isValidAux(node.getRight(), node, maxNode);
    }

    /**
     * Returns the height of a given TreeNode. Required for when TreeNode is null.
     *
     * @param node The TreeNode to determine the height of.
     * @return The height attribute of node, 0 if node is null.
     */
    protected int nodeHeight(final TreeNode<T> node) {

	return (node != null) ? node.getHeight() : 0;
    }

    private TreeNode<T> findMin(TreeNode<T> node) {

	while (node.getLeft() != null) {
	    node = node.getLeft();
	}
	return node;

    }

    /**
     * Auxiliary method for remove. Removes data from this BST.
     *
     * @param node The current node (TreeNode).
     * @param data Data to be removed from the tree.
     * @return The replacement node.
     */
    protected TreeNode<T> removeAux(TreeNode<T> node, final CountedData<T> data) {

	if (node == null) {
	    return null;
	}

	int cmp = data.getData().compareTo(node.getData().getData());

	if (cmp < 0) {
	    node.setLeft(removeAux(node.getLeft(), data));
	} else if (cmp > 0) {
	    node.setRight(removeAux(node.getRight(), data));
	} else {
	    if (node.getLeft() == null) {
		return node.getRight();
	    } else if (node.getRight() == null) {
		return node.getLeft();
	    } else {
		TreeNode<T> successor = findMin(node.getRight());
		node.setRight(removeAux(node.getRight(), successor.getData()));
		node.getData().setCount(successor.getData().getCount());
		node.getData().setCount(successor.getData().getCount());
	    }
	}

	node.updateHeight();
	return node;

    }

    /**
     * Helper method to find the minimum node in a subtree.
     *
     * @param node The root of the subtree.
     * @return The node with the minimum value in the subtree.
     */
    private TreeNode<T> findMinNode(TreeNode<T> node) {

	while (node.getLeft() != null) {

	    node = node.getLeft();
	}
	return node;
    }

    /**
     * Determines if this BST contains key.
     *
     * @param key The key to search for.
     * @return true if this contains key, false otherwise.
     */
    public boolean contains(final CountedData<T> key) {
	return retrieve(key) != null;
    }

    /**
     * Determines whether two trees are identical.
     *
     * @param target The tree to compare this BST against.
     * @return true if this and target contain nodes that match in position, data,
     *         count, and height, false otherwise.
     */
    public boolean equals(final BST<T> target) {
	return this.size == target.size && equalsAux(this.root, target.root);
    }

    /**
     * Get number of comparisons executed by the retrieve method.
     *
     * @return comparisons
     */
    public int getComparisons() {

	return this.comparisons;
    }

    /**
     * Returns the height of the root node of this tree.
     *
     * @return height of root node, 0 if the root node is null.
     */
    public int getHeight() {

	return this.root != null ? this.root.getHeight() : 0;

    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in this tree.
     */
    public int getSize() {
	return this.size;
    }

    /**
     * Returns a list of the data in the current tree. The list contents are in
     * order from smallest to largest.
     *
     * Not thread safe as it assumes contents of the tree are not changed by an
     * external thread during the loop.
     *
     * @return The contents of this tree as a list of data.
     */
    public ArrayList<CountedData<T>> inOrder() {
	return this.root != null ? this.root.inOrder() : new ArrayList<>();
    }

    /**
     * Inserts data into this tree.
     *
     * @param data Data to store.
     */
    public void insert(final CountedData<T> data) {

	this.root = insertAux(this.root, data);
    }

    /**
     * Determines if this tree is empty.
     *
     * @return true if this tree is empty, false otherwise.
     */
    public boolean isEmpty() {

	return this.root == null;
    }

    /**
     * Determines if this tree is a valid BST; i.e. a node's left child data is
     * smaller than its data, and its right child data is greater than its data, and
     * a node's height is equal to the maximum of the heights of its two children
     * (empty child nodes have a height of 0), plus 1.
     *
     * @return true if this tree is a valid BST, false otherwise.
     */
    public boolean isValid() {

	return isValidAux(this.root, null, null);

    }

    /**
     * Returns a list of the data in the current tree. The list contents are in node
     * level order starting from the root node. Helps determine the structure of the
     * tree.
     *
     * Not thread safe as it assumes contents of the tree are not changed by an
     * external thread during the loop.
     *
     * @return this tree data as a list of data.
     */
    public ArrayList<CountedData<T>> levelOrder() {

	return this.root != null ? this.root.levelOrder() : new ArrayList<>();

    }

    /**
     * Returns a list of the data in the current tree. The list contents are in node
     * preorder.
     *
     * Not thread safe as it assumes contents of the tree are not changed by an
     * external thread during the loop.
     *
     * @return The contents of this tree as a list of data.
     */
    public ArrayList<CountedData<T>> preOrder() {

	return this.root != null ? this.root.preOrder() : new ArrayList<>();

    }

    /**
     * Removes data from the tree. Decrements the node count, and if the count is 0,
     * removes the node entirely.
     *
     * @param data Data to decrement or remove.
     */
    public void remove(final CountedData<T> data) {

	this.root = removeAux(this.root, data);

    }

    /**
     * Resets the comparison count to 0.
     */

    public void resetComparisons() {

	this.comparisons = 0;

    }

    /**
     * Retrieves a copy of data matching key (key should have data count of 0).
     * Returning a complete CountedData gives access to the data and its count.
     *
     * @param key The key to look for.
     * @return data The complete CountedData that matches key, null otherwise.
     */
    public CountedData<T> retrieve(final CountedData<T> key) {

	TreeNode<T> current = root;

	while (current != null) {
	    comparisons++;
	    int result = current.getData().compareTo(key);

	    if (result == 0) {
		return current.getData();
	    } else if (result > 0) {
		current = current.getLeft();
	    } else {
		current = current.getRight();
	    }
	}
	return null;
    }
}