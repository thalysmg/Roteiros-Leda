package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * The bubble sort algorithm iterates over the array multiple times, pushing big
 * elements to the right by swapping adjacent elements, until the array is
 * sorted.
 */
public class BubbleSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (array == null) {
			throw new RuntimeException();
		}
		boolean swapped = true;

		while (swapped) { 	// if the array is already sorted it will only pass a
							// single time through the array
			swapped = false;
			for (int i = leftIndex; i <= rightIndex - 1; i++) {
				if (array[i].compareTo(array[i + 1]) == 1) {
					Util.swap(array, i, i + 1);
					swapped = true; // when an element is greater than it's
									// successor they are swapped
				}
			}
		}
	}
}
