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
		int pivot = partition(array, leftIndex, rightIndex);
		sort(array, leftIndex, pivot - 1);
		sort(array, pivot + 1, rightIndex);
	}

	
	/*
	 * This function chooses a pivot and put all the greatest elements on it's 
	 * right side and all the minors on it's left side, and return the index of
	 *  the pivot. 
	 */
	private int partition(T[] array, int leftIndex, int rightIndex) {
		int left = leftIndex + 1;
		int right = rightIndex;
		T pivot = array[leftIndex];

		while (left <= right) {
			if (array[left].compareTo(pivot) <= 0) {
				left++;
			} else if (array[right].compareTo(pivot) > 0) {
				right--;
			} else {
				Util.swap(array, left, right);
			}
		}
		Util.swap(array, leftIndex, right);
		return right;
	}
}
