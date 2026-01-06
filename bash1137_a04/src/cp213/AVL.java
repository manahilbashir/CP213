package cp213;

/**
 * Implements an AVL (Adelson-Velsky Landis) tree. Extends BST.
 *
 * @author Manahil Bashir, 169061137, bash1137@mylaurier.ca
 * @author David Brown
 * @version 2025-07-14
 */
public class AVL<T extends Comparable<T>> extends BST<T> {

    /**
     * Returns the balance data of node. If greater than 1, then left heavy, if less
     * than -1, then right heavy. If in the range -1 to 1 inclusive, the node is
     * balanced. Used to determine whether to rotate a node upon insertion.
     *
     * @param node The TreeNode to analyze for balance.
     * @return A balance number.
     */
    private int balance(final TreeNode<T> node) {

	if (node == null) {
	    return 0;
	}
	int left = (node.getLeft() == null) ? 0 : node.getLeft().getHeight();
	int right = (node.getRight() == null) ? 0 : node.getRight().getHeight();
	return left - right;
    }

    /**
     * Rebalances the current node if its children are not balanced.
     *
     * @param node the node to rebalance
     * @return replacement for the rebalanced node
     */

    private TreeNode<T> rebalance(TreeNode<T> node) {

	int balance = balance(node);

	if (balance > 1) {
	    if (balance(node.getLeft()) < 0) {
		node.setLeft(rotateLeft(node.getLeft()));
	    }
	    node = rotateRight(node);
	} else if (balance < -1) {
	    if (balance(node.getRight()) > 0) {
		node.setRight(rotateRight(node.getRight()));
	    }
	    node = rotateLeft(node);
	}
	return node;
    }

    /**
     * Performs a left rotation around node.
     *
     * @param node The subtree to rotate.
     * @return The new root of the subtree.
     */

    private TreeNode<T> rotateLeft(final TreeNode<T> node) {

	TreeNode<T> child = node.getRight();
	if (child == null) {
	    return node;
	}

	node.setRight(child.getLeft());
	child.setLeft(node);

	node.updateHeight();
	child.updateHeight();

	return child;
    }

    /**
     * Performs a right rotation around node.
     *
     * @param node The subtree to rotate.
     * @return The new root of the subtree.
     */

    private TreeNode<T> rotateRight(final TreeNode<T> node) {

	TreeNode<T> child = node.getLeft();
	if (child == null) {
	    return node;
	}

	node.setLeft(child.getRight());
	child.setRight(node);

	node.updateHeight();
	child.updateHeight();

	return child;

    }

    /**
     * Auxiliary method for insert. Inserts data into this AVL. Same as BST
     * insertion with addition of rebalance of nodes.
     *
     * @param node The current node (TreeNode).
     * @param data Data to be inserted into the node.
     * @return The inserted node.
     */

    @Override
    protected TreeNode<T> insertAux(TreeNode<T> node, final CountedData<T> data) {

	if (node == null) {
	    node = new TreeNode<>(data);
	    data.incrementCount();
	    size++;
	} else {
	    int cmp = node.getData().compareTo(data);
	    if (cmp > 0) {
		node.setLeft(insertAux(node.getLeft(), data));
	    } else if (cmp < 0) {
		node.setRight(insertAux(node.getRight(), data));
	    } else {
		node.getData().incrementCount();
	    }
	}
	node.updateHeight();
	return rebalance(node);
    }

    /**
     * Auxiliary method for valid. Determines if a subtree based on node is a valid
     * subtree. An AVL must meet the BST validation conditions, and additionally be
     * balanced in all its subtrees - i.e. the difference in height between any two
     * children must be no greater than 1.
     *
     * @param node The root of the subtree to test for validity.
     * @return true if the subtree base on node is valid, false otherwise.
     */

    @Override
    protected boolean isValidAux(final TreeNode<T> node, TreeNode<T> minNode, TreeNode<T> maxNode) {

	if (node == null) {
	    return true;
	}

	int leftH = (node.getLeft() == null) ? 0 : node.getLeft().getHeight();
	int rightH = (node.getRight() == null) ? 0 : node.getRight().getHeight();

	boolean heightCorrect = node.getHeight() == Math.max(leftH, rightH) + 1;
	boolean nodeInRange = (minNode == null || node.getData().compareTo(minNode.getData()) > 0)
		&& (maxNode == null || node.getData().compareTo(maxNode.getData()) < 0);

	return heightCorrect && nodeInRange && isValidAux(node.getLeft(), minNode, node)
		&& isValidAux(node.getRight(), node, maxNode);
    }

    public boolean equals(final AVL<T> target) {
	return super.equals(target);
    }

    @Override
    protected TreeNode<T> removeAux(TreeNode<T> node, final CountedData<T> data) {

	if (node == null) {
	    return null;
	}

	int cmp = data.compareTo(node.getData());

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
		TreeNode<T> predParent = node;
		TreeNode<T> pred = node.getRight();

		while (pred.getLeft() != null) {
		    predParent = pred;
		    pred = pred.getLeft();
		}

		if (predParent != node) {
		    predParent.setLeft(pred.getRight());
		    pred.setRight(node.getRight());
		}
		pred.setLeft(node.getLeft());
		node = pred;
	    }
	}

	node.updateHeight();
	return rebalance(node);
    }
}