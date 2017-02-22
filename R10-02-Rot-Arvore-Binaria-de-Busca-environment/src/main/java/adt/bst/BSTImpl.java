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

	private int height(BSTNode<T> node, int height) {
		if (!node.isEmpty()) {
			int left = height((BSTNode<T>) node.getLeft(), height + 1);
			int right = height((BSTNode<T>) node.getRight(), height + 1);

			if (right > left) {
				height = right;

			} else {
				height = left;
			}
		}
		return height;
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {
		if (node.isEmpty() || element.equals(node.getData())) {
			return node;

		} else {
			if (element.compareTo(node.getData()) < 0) {
				return search((BSTNode<T>) node.getLeft(), element);
			} else {
				return search((BSTNode<T>) node.getRight(), element);
			}
		}
	}

	@Override
	public BSTNode<T> search(T element) {
		return search(this.root, element);
	}

	@Override
	public void insert(T element) {
		insert(null, this.root, element);
	}

	private void insert(BSTNode<T> nodeParent, BSTNode<T> node, T element) {
		if (node.isEmpty()) {

			node.setData(element);

			node.setLeft(new BSTNode<T>());

			node.setRight(new BSTNode<T>());
			node.setParent(nodeParent);

		} else {
			if (element.compareTo(node.getData()) < 0) {
				insert(node, (BSTNode<T>) node.getLeft(), element);
			} else {
				insert(node, (BSTNode<T>) node.getRight(), element);
			}
		}
	}

	private BSTNode<T> maximum(BSTNode<T> node) {
		while (!node.getRight().isEmpty()) {
			node = (BSTNode<T>) node.getRight();
		}
		return node;
	}

	@Override
	public BSTNode<T> maximum() {
		if (this.root.isEmpty()) {
			return null;
		}
		return maximum(this.root);

	}

	private BSTNode<T> minimum(BSTNode<T> node) {
		while (!node.getLeft().isEmpty()) {
			node = (BSTNode<T>) node.getLeft();
		}
		return node;
	}

	@Override
	public BSTNode<T> minimum() {
		if (this.root.isEmpty()) {
			return null;
		}
		return minimum(this.root);
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> auxNode = search(this.root, element);

		if (auxNode.isEmpty()) {
			return null;
		}

		if (!auxNode.getRight().isEmpty()) {
			return minimum((BSTNode<T>) auxNode.getRight());
		} else {
			BSTNode<T> auxParent = (BSTNode<T>) auxNode.getParent();
			while (auxParent != null && auxNode.equals(auxParent.getRight())) {
				auxNode = auxParent;
				auxParent = (BSTNode<T>) auxNode.getParent();
			}
			return auxParent;
		}
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> auxNode = search(this.root, element);

		if (auxNode.isEmpty())
			return null;

		if (!auxNode.getLeft().isEmpty()) {
			return maximum((BSTNode<T>) auxNode.getLeft());
		} else {
			BSTNode<T> auxParent = (BSTNode<T>) auxNode.getParent();
			while (auxParent != null && auxNode.equals(auxParent.getLeft())) {
				auxNode = auxParent;
				auxParent = (BSTNode<T>) auxNode.getParent();
			}
			return auxParent;
		}
	}

	@Override
	public void remove(T element) {
		BSTNode<T> auxNode = search(element);

		if (!auxNode.isEmpty() || auxNode != null) {
			if (auxNode.isLeaf()) {
				auxNode.setData(null);

			} else if (auxNode.getLeft().isEmpty() || auxNode.getRight().isEmpty()) {
				if (auxNode.getParent() != null) {
					if (auxNode.equals(auxNode.getParent().getLeft())) {
						if (!auxNode.getLeft().isEmpty()) {
							auxNode.getParent().setLeft(auxNode.getLeft());
							auxNode.getLeft().setParent(auxNode.getParent());
						} else {
							auxNode.getParent().setLeft(auxNode.getRight());
							auxNode.getRight().setParent(auxNode.getParent());
						}
					} else {
						if (!auxNode.getLeft().isEmpty()) {
							auxNode.getParent().setRight(auxNode.getLeft());
							auxNode.getLeft().setParent(auxNode.getParent());

						} else {
							auxNode.getParent().setRight(auxNode.getRight());
							auxNode.getRight().setParent(auxNode.getParent());
						}
					}

				} else {
					if (auxNode.getLeft().isEmpty()) {
						this.root = ((BSTNode<T>) auxNode.getRight());

					} else {
						this.root = ((BSTNode<T>) auxNode.getLeft());
					}
					this.root.setParent(null);
				}
			} else {
				T sucessor = sucessor(auxNode.getData()).getData();
				remove(sucessor);
				auxNode.setData(sucessor);
			}
		}
	}

	private void visit(List<T> lista, BSTNode<T> node) {
		lista.add(node.getData());
	}

	private void preOrder(BSTNode<T> node, List<T> lista) {
		if (node.getData() != null) {
			visit(lista, node);
			preOrder((BSTNode<T>) node.getLeft(), lista);
			preOrder((BSTNode<T>) node.getRight(), lista);
		}
	}

	@Override
	public T[] preOrder() {
		T[] array = (T[]) new Comparable[size()];
		List<T> list = new ArrayList<T>();

		preOrder(this.root, list);

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
	public T[] order() {
		T[] array = (T[]) new Comparable[size()];
		List<T> list = new ArrayList<T>();

		order(this.root, list);
		return makeArray(array, list);
	}

	private void postOrder(BSTNode<T> node, List<T> list) {
		if (node.getData() != null) {
			postOrder((BSTNode<T>) node.getLeft(), list);
			postOrder((BSTNode<T>) node.getRight(), list);
			visit(list, node);
		}
	}

	@Override
	public T[] postOrder() {
		T[] array = (T[]) new Comparable[size()];
		List<T> list = new ArrayList<T>();

		postOrder(this.root, list);

		return makeArray(array, list);
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