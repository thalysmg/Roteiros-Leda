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

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		int tamanho = array.length;

		if (leftIndex >= 0 && rightIndex >= 0 && rightIndex < tamanho && tamanho > 1 && leftIndex < rightIndex
				&& leftIndex < tamanho) {
			int m = partition(array, leftIndex, rightIndex);
			sort(array, leftIndex, m - 1);
			sort(array, m + 1, rightIndex);

		}

	}

	private int partition(T[] array, int leftIndex, int rightIndex) {
		int i = leftIndex + 1;
		int j = rightIndex;
		T p = array[leftIndex];

		while (i <= j) {
			if (array[i].compareTo(p) <= 0) {
				i++;
			} else if (array[j].compareTo(p) > 0) {
				j--;

			} else {
				Util.swap(array, i, j);
			}
		}
		Util.swap(array, leftIndex, j);

		return j;

	}
}
