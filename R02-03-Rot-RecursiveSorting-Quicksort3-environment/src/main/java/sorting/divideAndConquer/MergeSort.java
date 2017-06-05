package sorting.divideAndConquer;

import java.util.LinkedList;

import sorting.AbstractSorting;

/**
 * Merge sort is based on the divide-and-conquer paradigm. The algorithm
 * consists of recursively dividing the unsorted list in the middle, sorting
 * each sublist, and then merging them into one single sorted list. Notice that
 * if the list has length == 1, it is already sorted.
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex < rightIndex) {
			LinkedList<T> list = new LinkedList<>();
			int middle = (rightIndex + leftIndex) / 2;

			sort(array, leftIndex, middle);
			sort(array, middle + 1, rightIndex);
			merge(array, leftIndex, middle, rightIndex);

		}
	}

	private void merge(T[] array, int leftIndex, int middle, int rightIndex) {
		T[] aux = (T[]) new Comparable[array.length];

		for (int i = 0; i < aux.length; i++) {
			aux[i] = array[i];
		}

		int i = leftIndex;
		int j = middle + 1;

		for (int l = leftIndex; l <= rightIndex; l++) {
			if (i > middle) {
				array[l] = aux[j];
				j++;
			} else if (j > rightIndex) {
				array[l] = aux[i];
				i++;
			} else if (aux[i].compareTo(aux[j]) > 0) {
				array[l] = aux[j];
				j++;
			} else {
				array[l] = aux[i];
				i++;
			}
		}

	}
}
