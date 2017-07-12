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
			int rightHeight = recursiveHeight((BSTNode<T>) node.getRight(), height+1);
			int leftHeight = recursiveHeight((BSTNode<T>) node.getLeft(), height+1);

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
		if (node.isEmpty() || node.getData().equals(element)) {
			return node;

		} else {
			if (element.compareTo(node.getData()) > 0) {
				return recursiveSearch((BSTNode<T>) node.getRight(), element);

			} else {
				return recursiveSearch((BSTNode<T>) node.getLeft(), element);
			}
		}
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
		if (this.root.isEmpty()) {
			return null;

		} else {
			return recursiveMaximum(this.root);
		}
	}

	private BSTNode<T> recursiveMaximum(BSTNode<T> node) {
		if (node.getRight().isEmpty()) {
			return (BSTNode<T>) node;

		} else {
			return recursiveMaximum((BSTNode<T>) node.getRight());
		}
	}

	@Override
	public BSTNode<T> minimum() {
		if (this.root.isEmpty()) {
			return null;

		} else {
			return recursiveMinimum(this.root);
		}
	}

	private BSTNode<T> recursiveMinimum(BSTNode<T> node) {
		if (node.getLeft().isEmpty()) {
			return (BSTNode<T>) node;

		} else {
			return recursiveMinimum((BSTNode<T>) node.getLeft());
		}
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = this.search(element);

		if (node.isEmpty()) {
			return null;

		} else if (!node.getRight().isEmpty()) {
			return recursiveMinimum((BSTNode<T>) node.getRight());

		} else {
			return recursiveSucessor((BSTNode<T>) node.getParent(), node);
		}
	}

	private BSTNode<T> recursiveSucessor(BSTNode<T> parentNode, BSTNode<T> node) {
		if (parentNode != null && node.equals(parentNode.getRight())) {
			return recursiveSucessor((BSTNode<T>) parentNode.getParent(), parentNode);

		} else {
			return parentNode;
		}
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = this.search(element);

		if (node.isEmpty()) {
			return null;

		} else if (!node.getLeft().isEmpty()) {
			return recursiveMaximum((BSTNode<T>) node.getLeft());

		} else {
			return recursivePredecessor((BSTNode<T>) node.getParent(), node);
		}
	}

	private BSTNode<T> recursivePredecessor(BSTNode<T> parentNode, BSTNode<T> node) {
		if (parentNode != null && node.equals(parentNode.getLeft())) {
			return recursivePredecessor((BSTNode<T>) parentNode.getParent(), parentNode);

		} else {
			return parentNode;
		}
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);

		if (!node.isEmpty() || node != null) {
			if (node.isLeaf()) {
				node.setData(null);

			} else if (node.getLeft().isEmpty() || node.getRight().isEmpty()) { //checking if the node has 1 child
				if (node.getParent() != null) {
					if (node.equals(node.getParent().getLeft())) {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setLeft(node.getLeft());
							node.getLeft().setParent(node.getParent());
						} else {
							node.getParent().setLeft(node.getRight());
							node.getRight().setParent(node.getParent());
						}
					} else {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setRight(node.getLeft());
							node.getLeft().setParent(node.getParent());

						} else {
							node.getParent().setRight(node.getRight());
							node.getRight().setParent(node.getParent());
						}
					}

				} else {  //node is root
					if (node.getLeft().isEmpty()) {
						this.root = ((BSTNode<T>) node.getRight());

					} else {
						this.root = ((BSTNode<T>) node.getLeft());
					}
					this.root.setParent(null);
				}
			} else { //node has 2 childs
				T sucessor = sucessor(node.getData()).getData();
				remove(sucessor);
				node.setData(sucessor);
			}
		}
	}
	
	/**
	 * Method which puts a node in a list
	 * @param list
	 * @param node
	 */
	protected void visit(List<T> list, BSTNode<T> node) {
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
	
	protected T[] makeArray(T[] array, List<T> list) {
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
