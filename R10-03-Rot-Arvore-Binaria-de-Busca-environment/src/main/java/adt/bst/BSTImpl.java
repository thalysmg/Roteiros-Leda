
package adt.bst;

import java.util.ArrayList;
import java.util.List;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return recursiveHeight(this.root, -1);

	}

	private int recursiveHeight(BSTNode<T> node, int height) {
		if (!node.isEmpty()) {
			int rightHeight = recursiveHeight((BSTNode<T>) node.getRight(), height + 1);
			int leftHeight = recursiveHeight((BSTNode<T>) node.getLeft(), height + 1);

			if (rightHeight > leftHeight) {
				height = rightHeight;

			} else {
				height = leftHeight;
			}
		}
		return height;
	}

	@Override
	public BSTNode<T> search(T element) {
		return recursiveSearch(this.root, element);
	}

	private BSTNode<T> recursiveSearch(BSTNode<T> node, T element) {
		BSTNode<T> result = node;
		if (node.isEmpty() || node.getData().equals(element)) {
			result = node;

		} else {
			if (element.compareTo(node.getData()) > 0) {
				result = recursiveSearch((BSTNode<T>) node.getRight(), element);

			} else {
				result = recursiveSearch((BSTNode<T>) node.getLeft(), element);
			}
		}
		return result;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			insertRecursive(element, null, this.root);
		}
	}

	private void insertRecursive(T element, BSTNode<T> parentNode, BSTNode<T> node) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parentNode);

		} else {
			if (element.compareTo(node.getData()) > 0) {
				insertRecursive(element, node, (BSTNode<T>) node.getRight());

			} else {
				insertRecursive(element, node, (BSTNode<T>) node.getLeft());
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		BSTNode<T> maximum = null;

		if (this.root.isEmpty()) {
			// doesn't do anything, the "value" null will be returned

		} else {
			maximum = recursiveMaximum(this.root);
		}
		return maximum;
	}

	private BSTNode<T> recursiveMaximum(BSTNode<T> node) {
		BSTNode<T> maximum = node;
		if (node.getRight().isEmpty()) {
			// doesn't do anything

		} else {
			maximum = recursiveMaximum((BSTNode<T>) node.getRight());
		}
		return maximum;
	}

	@Override
	public BSTNode<T> minimum() {
		BSTNode<T> minimum = null;

		if (this.root.isEmpty()) {
			// doesn't do anything, the "value" null will be returned

		} else {
			minimum = recursiveMinimum(this.root);
		}
		return minimum;
	}

	private BSTNode<T> recursiveMinimum(BSTNode<T> node) {
		BSTNode<T> minimum = node;
		if (node.getLeft().isEmpty()) {
			// doesn't do anything

		} else {
			minimum = recursiveMinimum((BSTNode<T>) node.getLeft());
		}
		return minimum;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> sucessor = this.search(element);

		if (sucessor.isEmpty()) {
			sucessor = null;

		} else if (!sucessor.getRight().isEmpty()) {
			sucessor = recursiveMinimum((BSTNode<T>) sucessor.getRight());

		} else {
			sucessor = recursiveSucessor((BSTNode<T>) sucessor.getParent(), sucessor);
		}
		return sucessor;
	}

	private BSTNode<T> recursiveSucessor(BSTNode<T> parentNode, BSTNode<T> node) {
		BSTNode<T> sucessor = parentNode;
		if (parentNode != null && node.equals(parentNode.getRight())) {
			sucessor = recursiveSucessor((BSTNode<T>) parentNode.getParent(), parentNode);
		}
		return sucessor;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> predecessor = this.search(element);

		if (predecessor.isEmpty()) {
			predecessor = null;

		} else if (!predecessor.getLeft().isEmpty()) {
			predecessor = recursiveMaximum((BSTNode<T>) predecessor.getLeft());

		} else {
			predecessor = recursivePredecessor((BSTNode<T>) predecessor.getParent(), predecessor);
		}
		return predecessor;
	}

	private BSTNode<T> recursivePredecessor(BSTNode<T> parentNode, BSTNode<T> node) {
		BSTNode<T> predecessor = parentNode;
		if (parentNode != null && node.equals(parentNode.getLeft())) {
			predecessor = recursivePredecessor((BSTNode<T>) parentNode.getParent(), parentNode);
		}
		return predecessor;
	}

	public void remove(T element) {
		BSTNode<T> node = this.search(element);

		if (!this.isEmpty()) {
			this.remove(node);
		}
	}

	private void remove(BSTNode<T> node) {
		//node has 2 children
		if (!node.getLeft().isEmpty() && !node.getRight().isEmpty()) {
			BSTNode<T> newNode = sucessor(node.getData());
			node.setData(newNode.getData());
			remove(newNode);
			
			//node has only one kid
		} else {
			BSTNode<T> newNode = (BSTNode<T>) node.getLeft();

			if (newNode.isEmpty()) { // left child is null
				newNode = (BSTNode<T>) node.getRight();
			}

			node.setData(newNode.getData());
			node.setRight(newNode.getRight());
			node.setLeft(newNode.getLeft());

			if (!node.isEmpty() && node.getRight() != null)
				node.getRight().setParent(node);
			if (!node.isEmpty() && node.getLeft() != null)
				node.getLeft().setParent(node);
		}
	}

	/**
	 * Method which puts a node in a list
	 * 
	 * @param list
	 * @param node
	 */
	private void visit(List<T> list, BSTNode<T> node) {
		list.add(node.getData());
	}

	@Override
	public T[] preOrder() {
		T[] array = (T[]) new Comparable[size()];
		List<T> list = new ArrayList<T>();

		preOrder(this.root, list);

		return makeArray(array, list);
	}

	private void preOrder(BSTNode<T> node, List<T> list) {
		if (node.getData() != null) {
			visit(list, node);
			preOrder((BSTNode<T>) node.getLeft(), list);
			preOrder((BSTNode<T>) node.getRight(), list);
		}
	}

	@Override
	public T[] order() {
		T[] array = (T[]) new Comparable[size()];
		List<T> list = new ArrayList<T>();

		order(this.root, list);
		return makeArray(array, list);
	}

	private void order(BSTNode<T> node, List<T> list) {
		if (node.getData() != null) {
			order((BSTNode<T>) node.getLeft(), list);
			visit(list, node);
			order((BSTNode<T>) node.getRight(), list);
		}
	}

	@Override
	public T[] postOrder() {
		T[] array = (T[]) new Comparable[size()];
		List<T> list = new ArrayList<T>();

		postOrder(this.root, list);

		return makeArray(array, list);
	}

	private void postOrder(BSTNode<T> node, List<T> list) {
		if (node.getData() != null) {
			postOrder((BSTNode<T>) node.getLeft(), list);
			postOrder((BSTNode<T>) node.getRight(), list);
			visit(list, node);
		}
	}

	private T[] makeArray(T[] array, List<T> list) {
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}