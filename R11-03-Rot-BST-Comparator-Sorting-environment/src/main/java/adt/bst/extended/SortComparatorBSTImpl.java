package adt.bst.extended;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;

/**
 * Implementacao de SortComparatorBST, uma BST que usa um comparator interno em
 * suas funcionalidades e possui um metodo de ordenar um array dado como
 * parametro, retornando o resultado do percurso desejado que produz o array
 * ordenado.
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class SortComparatorBSTImpl<T extends Comparable<T>> extends BSTImpl<T> implements SortComparatorBST<T> {

	private Comparator<T> comparator;

	public SortComparatorBSTImpl(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}

	@Override
	public T[] sort(T[] array) {
		if (array.length == 0) {
			return array;
		}
		super.root = new BSTNode<>();
		
		fillTree(array);
		
		return sortByStrategy("order");
	}
	/*
	 * Method which returns an ordered array according to the ordering strategy chosen
	 */
	public T[] sortByStrategy(String strategy) {
		T[] result = (T[]) new Comparable[super.size()];
		
		if (strategy.equals("preOrder")) {
			result = super.preOrder();
		
		} else if (strategy.equals("order")) {
			result = super.order();
		
		} else if (strategy.equals("postOrder")) {
			result = super.postOrder();
		
		} else if (strategy.equals("reverseOrder")) {
			result = this.reverseOrder();
		}
		return result;
	}
	
	/*
	 * Method which insert elements in a tree
	 */
	private void fillTree(T[] array) {
		for (T element : array) {
			this.insert(element);
		}
	}
	
	@Override
	public T[] reverseOrder() {
		T[] array = (T[]) new Comparable[size()];
		List<T> list = new ArrayList<T>();

		reverseOrder(this.root, list);

		return makeArray(array, list);
	}

	private void reverseOrder(BSTNode<T> node, List<T> list) {
		if (node.getData() == null) {
			return;

		} else {
			reverseOrder((BSTNode<T>) node.getRight(), list);
			visit(list, node);
			reverseOrder((BSTNode<T>) node.getLeft(), list);
		}

	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@Override
	public BSTNode<T> search(T element) {
		return recursiveSearch(this.root, element);
	}

	private BSTNode<T> recursiveSearch(BSTNode<T> node, T element) {
		if (node.isEmpty() || node.getData().equals(element)) {
			return node;

		} else {
			if (comparator.compare(element, node.getData()) > 0) {
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
			if (comparator.compare(element, node.getData()) > 0) {
				insertRecursive(element, node, (BSTNode<T>) node.getRight());

			} else {
				insertRecursive(element, node, (BSTNode<T>) node.getLeft());
			}
		}
	}
}
