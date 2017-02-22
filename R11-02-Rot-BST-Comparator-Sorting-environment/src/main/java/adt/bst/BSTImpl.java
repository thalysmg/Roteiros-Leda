
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
		return height(this.root, -1);
	}

	private int height(BSTNode<T> node, int h) {
		if (!node.isEmpty()) {
			int right = height((BSTNode<T>) node.getRight(), h + 1);
			int left = height((BSTNode<T>) node.getLeft(), h + 1);

			if (right > left) {
				h = right;
			}
			else {
				h = left;
			}
		}

		return h;

	}

	@Override
	public BSTNode<T> search(T element) {
		return this.search(this.root, element);
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {
		if (node.isEmpty() || element.compareTo(node.getData()) == 0) {
			return node;
		} else {
			if (element.compareTo(node.getData()) > 0)
				return search((BSTNode<T>) node.getRight(), element);
			else
				return search((BSTNode<T>) node.getLeft(), element);
		}
	}

	@Override
	public void insert(T element) {
		insert(null, this.root, element);
	}

	public void insert(BSTNode<T> parent, BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parent);
		} else {
			if (element.compareTo(node.getData()) > 0) {
				insert(node, (BSTNode<T>) node.getRight(), element);
			} else {
				insert(node, (BSTNode<T>) node.getLeft(), element);
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		if (root.isEmpty())
			return null;

		return maximum(this.root);
	}

	private BSTNode<T> maximum(BSTNode<T> node) {
		while (!node.getRight().isEmpty()) {
			node = (BSTNode<T>) node.getRight();
		}
		return node;
	}

	@Override
	public BSTNode<T> minimum() {
		if (root.isEmpty())
			return null;

		return minimum(this.root);
	}

	private BSTNode<T> minimum(BSTNode<T> node) {
		while (!node.getLeft().isEmpty()) {
			node = (BSTNode<T>) node.getLeft();
		}
		return node;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = this.search(this.root, element);

		if (node.isEmpty())
			return null;

		if (!node.getRight().isEmpty())
			return minimum((BSTNode<T>) node.getRight());

		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while (parent != null && (parent.getRight().equals(node))) {
			node = parent;
			parent = (BSTNode<T>) node.getParent();
		}

		return parent;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = this.search(this.root, element);

		if (node.isEmpty())
			return null;

		if (!node.getLeft().isEmpty())
			return maximum((BSTNode<T>) node.getLeft());

		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while (parent != null && (parent.getLeft().equals(node))) {
			node = parent;
			parent = (BSTNode<T>) node.getParent();
		}

		return parent;
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		if (!node.isEmpty() || node != null) {
			if (node.isLeaf()) {
				node.setData(null);
			} else if (node.getLeft().isEmpty() || node.getRight().isEmpty()) {
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
				} else {
					if (node.getLeft().isEmpty()) {
						root = (BSTNode<T>) node.getRight();
					} else {
						root = (BSTNode<T>) node.getLeft();
					}
					root.setParent(null);
				}
			} else {
				T sucessor = sucessor(node.getData()).getData();
				remove(sucessor);
				node.setData(sucessor);
			}
		}
	}

	@Override
	public T[] preOrder() {
		T[] result = (T[]) new Comparable[size()];
		List<T> list = new ArrayList<T>();

		preOrder(root, list);

		return makeArray(result, list);

	}

	public void preOrder(BSTNode<T> node, List<T> list) {
		if (node.getData() != null) {
			visit(list, node);
			preOrder((BSTNode<T>) node.getLeft(), list);
			preOrder((BSTNode<T>) node.getRight(), list);
		}
	}

	@Override
	public T[] order() {
		T[] result = (T[]) new Comparable[size()];
		List<T> list = new ArrayList<T>();

		order(root, list);

		return makeArray(result, list);
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
		T[] result = (T[]) new Comparable[size()];
		List<T> list = new ArrayList<T>();

		postOrder(root, list);

		return makeArray(result, list);
	}

	public void postOrder(BSTNode<T> node, List<T> list) {
		if (node.getData() != null) {
			postOrder((BSTNode<T>) node.getLeft(), list);
			postOrder((BSTNode<T>) node.getRight(), list);
			visit(list, node);
		}
	}

	protected T[] makeArray(T[] result, List<T> list) {
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}

	protected void visit(List<T> list, BSTNode<T> node) {
		list.add(node.getData());
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
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}

