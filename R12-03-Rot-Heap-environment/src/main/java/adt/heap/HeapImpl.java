package adt.heap;

import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Ou seja, admitindo um comparador normal (responde corretamente 3 > 2),
 * essa heap deixa os elementos maiores no topo. Essa comparação não é feita
 * diretamente com os elementos armazenados, mas sim usando um comparator. Dessa
 * forma, dependendo do comparator, a heap pode funcionar como uma max-heap ou
 * min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

	protected T[] heap;
	protected int index = -1;
	/**
	 * O comparador é utilizado para fazer as comparações da heap. O ideal é mudar
	 * apenas o comparator e mandar reordenar a heap usando esse comparator. Assim
	 * os metodos da heap não precisam saber se vai funcionar como max-heap ou
	 * min-heap.
	 */
	protected Comparator<T> comparator;

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;
	private static final int INDEX_ROOT = 0;

	/**
	 * Construtor da classe. Note que de inicio a heap funciona como uma min-heap.
	 */
	@SuppressWarnings("unchecked")
	public HeapImpl(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
		this.comparator = comparator;
	}

	// /////////////////// METODOS IMPLEMENTADOS
	private int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * Deve retornar o indice que representa o filho a esquerda do elemento indexado
	 * pela posicao i no vetor
	 */
	private int left(int i) {
		return (i * 2 + 1);
	}

	/**
	 * Deve retornar o indice que representa o filho a direita do elemento indexado
	 * pela posicao i no vetor
	 */
	private int right(int i) {
		return (i * 2 + 1) + 1;
	}

	@Override
	public boolean isEmpty() {
		return (index == -1);
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] resp = Util.makeArrayOfComparable(index + 1);
		for (int i = 0; i <= index; i++) {
			resp[i] = this.heap[i];
		}
		return resp;
	}

	// ///////////// METODOS A IMPLEMENTAR
	/**
	 * Valida o invariante de uma heap a partir de determinada posicao, que pode ser
	 * a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
	 * (comparados usando o comparator) elementos na parte de cima da heap.
	 */
	private void heapify(int position) {
		if (position >= 0 && !this.isEmpty()) {

			int left = this.left(position);
			int right = this.right(position);
			int largest;

			if (left < this.size() && this.getComparator().compare(this.heap[left], this.heap[position]) > 0) {
				largest = left;
			} else {
				largest = position;
			}

			if (right < this.size() && this.getComparator().compare(this.heap[right], this.heap[largest]) > 0) {
				largest = right;
			}

			if (largest != position) {
				Util.swap(this.heap, largest, position);
				this.heapify(largest);
			}
		}
	}

	@Override
	public void insert(T element) {
		// ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
		if (this.index == this.heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
		}

		if (element != null) {
			this.index++;
			this.heap[this.index] = element;

			int i = this.index;

			while (i > 0 && this.getComparator().compare(this.heap[i], this.heap[this.parent(i)]) > 0) {
				Util.swap(this.heap, i, parent(i));
				i = parent(i);
			}
		}
	}

	@Override
	public void buildHeap(T[] array) {
		this.heap = array;
		this.index = (array.length - 1);

		for (int i = size() / 2; i >= 0; i--) {
			heapify(i);
		}
	}

	@Override
	public T extractRootElement() {
		T removed = null;
		
		if (this.isEmpty()) {
			//does not do anything
		
		} else {
			removed = heap[INDEX_ROOT];
			Util.swap(heap, INDEX_ROOT, this.index);

			this.index--;

			heapify(INDEX_ROOT);
		}
		return removed;
	}

	@Override
	public T rootElement() {
		T rootElement = null;
		if (this.isEmpty()) {
			//does not do anything
		} else {
			rootElement = this.heap[INDEX_ROOT];
		}
		return rootElement;
	}

	@Override
	public T[] heapsort(T[] array) {

		Comparator<T> newComparator = getComparator();

		setComparator((o1, o2) -> o1.compareTo(o2));

		buildHeap(array);
		T[] sorted = (T[]) new Comparable[array.length];

		for (int i = this.index; i >= 0; i--) {
			sorted[i] = extractRootElement();
		}

		setComparator(newComparator);
		return sorted;

	}

	@Override
	public int size() {
		return this.index + 1;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public T[] getHeap() {
		return heap;
	}
}
