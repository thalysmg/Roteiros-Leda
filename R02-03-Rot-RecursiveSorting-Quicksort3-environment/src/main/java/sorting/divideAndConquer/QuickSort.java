package sorting.divideAndConquer;

import util.Util;
import sorting.AbstractSorting;

/**
 * Quicksort is based on the divide-and-conquer paradigm. The algorithm chooses
 * a pivot element and rearranges the elements of the interval in such a way
 * that all elements lesser than the pivot go to the left part of the array and
 * all elements greater than the pivot, go to the right part of the array. Then
 * it recursively sorts the left and the right parts. Notice that if the list
 * has length == 1, it is already sorted.
 */
public class QuickSort<T extends Comparable<T>> extends AbstractSorting<T> {
	
	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex >= rightIndex) {
			return;
		}
		int pivo = partition(array, leftIndex, rightIndex);
		sort(array, leftIndex, pivo - 1);
		sort(array, pivo + 1, rightIndex);
	}

	
	/*
	 * This function chooses a pivot and put all the greatest elements on it's 
	 * right side and all the minors on it's left side, and return the index of
	 *  the pivot. 
	 */
	private int partition(T[] array, int left, int right) {
		int i = left + 1;
		int j = right;
		T pivot = array[left];

		while (i <= j) {
			if (array[i].compareTo(pivot) <= 0) {
				i++;
			} else if (array[j].compareTo(pivot) > 0) {
				j--;
			} else {
				Util.swap(array, i, j);
			}
		}
		Util.swap(array, left, j);
		return j;

	}

}
