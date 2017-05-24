package sorting.variationsOfInsertionsort;

import sorting.AbstractSorting;

public class RecursiveInsertionSort<T extends Comparable<T>> extends
		AbstractSorting<T> {

	/**
	 * Implementacaoo RECURSIVA do insertion sort. Para isso, tente definir o 
	 * caso base do algoritmo e depois o caso recursivo, que reduz o problema 
	 * para uma entrada menor em uma chamada recursiva. Seu algoritmo deve 
	 * ter complexidade quadratica O(n^2).
	 * 
	 * Restrições:
	 *  - voce nao pode utilizar arry auxiliar
	 *  - voce pode utilizar variaveis temporarias
	 *  - voce nao pode declarar novos atributos na classe
	 *  - para as trocas no array, utilize o metodo Util.swap
	 */
	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		for (int i = leftIndex + 1; i <= rightIndex; i++) {
			T actualElement = array[i];

			int j = i;
			if (j > 0 && (array[j - 1]).compareTo(actualElement) > 0) {
				array[j] = array[j - 1]; 	// shifting the elements which are
											// greater than the actualElement to
											// right
				j--;
			}
			array[j] = actualElement;   	//inserting the actualElement in it's correct place
		}
	}
}
