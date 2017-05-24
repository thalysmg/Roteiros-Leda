package sorting.simpleSorting;

import sorting.AbstractSorting;

/**
 * As the insertion sort algorithm iterates over the array, it makes the
 * assumption that the visited positions are already sorted in ascending order,
 * which means it only needs to find the right position for the current element
 * and insert it there.
 */
public class InsertionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (array == null) {
			throw new RuntimeException();
		}
		for (int i = leftIndex + 1; i <= rightIndex; i++) {
			T actualElement = array[i];

			int j = i;
			while (j > 0 && (array[j - 1]).compareTo(actualElement) > 0) {
				array[j] = array[j - 1]; // shifting the elements which are
											// greater than the actualElement to
											// right
				j--;
			}
			array[j] = actualElement;   //inserting the actualElement in it's correct place
		}

	}
}
