package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The combsort algoritm.
 */
public class CombSort<T extends Comparable<T>> extends AbstractSorting<T> {
	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		int gap = array.length;
		boolean swapped = true;

		while (gap > 1 || swapped) {
			if (gap > 1) {
				gap = (int) (gap / 1.247330950103979);
			}
			swapped = false;
			int i = leftIndex;
			while (i <= rightIndex-gap) {
				if (array[i].compareTo(array[i + gap]) > 0) {
					Util.swap(array, i, i + gap);
					swapped = true;
				}
				i++;
			}
		}
	}
}
