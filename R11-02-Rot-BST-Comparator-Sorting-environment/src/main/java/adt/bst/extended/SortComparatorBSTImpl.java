package adt.bst.extended;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;

/**
 * Implementacao de SortComparatorBST, uma BST que usa um comparator interno em suas funcionalidades
 * e possui um metodo de ordenar um array dado como parametro, retornando o resultado do percurso
 * desejado que produz o array ordenado. 
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
		while(!this.isEmpty()) {
			this.remove(this.root.getData());
		}
		for (int i = 0; i < array.length; i++) {
			this.insert(array[i]);
		}
		return this.order();
	}

	private void reverseOrder(BSTNode<T> node, List<T> list) {
		if (node.getData() != null) {
			reverseOrder((BSTNode<T>) node.getRight(), list);
			super.visit(list, node);
			reverseOrder((BSTNode<T>) node.getRight(), list);
		}
	}
	
	@Override
	public T[] reverseOrder() {
		T[] array = (T[]) new Comparable[size()];
		List<T> list = new ArrayList<T>();

		reverseOrder(this.root, list);

		return makeArray(array, list);

	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}
	
}
