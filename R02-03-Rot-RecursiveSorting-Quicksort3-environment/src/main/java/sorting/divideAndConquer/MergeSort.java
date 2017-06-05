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

		int left = leftIndex;
		int right = middle + 1;

		for (int l = leftIndex; l <= rightIndex; l++) {
			if (left > middle) { /*
									 * checking if all the elements from the
									 * first part of array "aux" have already
									 * been put in the actual array
									 */
				array[l] = aux[right];
				right++;
			} else if (right > rightIndex) { /*
												 * checking if all the elements
												 * from the second part of array
												 * aux have already been put in
												 * the actual array
												 */
				array[l] = aux[left];
				left++;
			} else if (aux[left]
					.compareTo(aux[right]) > 0) { /*
													 * merging the elements from
													 * both parts of array (ordering)
													 */
				array[l] = aux[right];
				right++;
			} else {
				array[l] = aux[left];
				left++;
			}
		}

	}
}
