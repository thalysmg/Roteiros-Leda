package sorting.divideAndConquer.quicksort3;

import sorting.AbstractSorting;
import util.Util;

/**
 * A classe QuickSortMedianOfThree representa uma variação do QuickSort que
 * funciona de forma ligeiramente diferente. Relembre que quando o pivô
 * escolhido divide o array aproximadamente na metade, o QuickSort tem um
 * desempenho perto do ótimo. Para aproximar a entrada do caso ótimo, diversas
 * abordagens podem ser utilizadas. Uma delas é usar a mediana de 3 para achar o
 * pivô. Essa técnica consiste no seguinte: 1. Comparar o elemento mais a
 * esquerda, o central e o mais a direita do intervalo. 2. Ordenar os elemento,
 * tal que: A[left] < A[center] < A[right]. 3. Adotar o A[center] como pivô. 4.
 * Colocar o pivô na penúltima posição A[right-1]. 5. Aplicar o particionamento
 * considerando o vetor menor, de A[left+1] até A[right-1]. 6. Aplicar o
 * algoritmo na metade a esquerda e na metade a direita do pivô.
 */

public class QuickSortMedianOfThree<T extends Comparable<T>> extends AbstractSorting<T> {

	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex < 0) {
			leftIndex = 0;
		}
		if (rightIndex > array.length - 1) {
			rightIndex = array.length - 1;
		}
		if (!(array == null && leftIndex > rightIndex)) {
			quickSortMedianOfThree(array, leftIndex, rightIndex);
		}
	}

	private void quickSortMedianOfThree(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex < rightIndex) {
			medianOfThree(array, leftIndex, rightIndex);
			int positionPivot = particiona(array, leftIndex, rightIndex);
			sort(array, leftIndex, positionPivot - 1);
			sort(array, positionPivot + 1, rightIndex);
		}
	}

	private int particiona(T[] array, int leftIndex, int rightIndex) {
		T pivot = array[leftIndex];
		int left = leftIndex + 1;
		int right = rightIndex;

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

	private void medianOfThree(T[] array, int leftIndex, int rightIndex) {
		int average = (leftIndex + rightIndex) / 2;
		if (array[leftIndex].compareTo(array[average]) > 0) {
			Util.swap(array, leftIndex, average);
		}

		if (array[average].compareTo(array[rightIndex]) > 0) {
			Util.swap(array, rightIndex, average);
		}

		if (array[leftIndex].compareTo(array[rightIndex]) > 0) {
			Util.swap(array, leftIndex, rightIndex);

		}
		Util.swap(array, average, rightIndex - 1);
	}
}
