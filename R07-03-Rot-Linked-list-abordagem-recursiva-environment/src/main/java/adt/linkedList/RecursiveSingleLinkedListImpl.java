package adt.linkedList;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {

	}

	public RecursiveSingleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next) {
		this.data = data;
		this.next = next;
	}

	// Tratar cada no como se fosse uma lista
	// Caso base - Lista vazia // Caso recursivo - Lista nao vazia

	@Override
	public boolean isEmpty() {
		return this.data == null;
	}

	@Override
	public int size() {
		int result = 0;
		if (this.isEmpty()) { // Caso base
			
		} else {
			result = 1 + this.getNext().size(); // Caso recursivo
		}
		return result;
	}

	@Override
	public T search(T element) {
		T result = null;
		if (this.isEmpty()) {

		} else { // Caso recursivo
			if (this.getData() == element) {
				result = this.getData();
			} else {
				result = this.getNext().search(element);
			}
		}
		return result;
	}

	@Override
	public void insert(T element) {

		// Elementos null devem ser ignorados
		if (element != null) {

			// Tratar cada nó como se fosse uma lista.
			// Se for sentinela, add, senao, chama insert para o prox
			if (this.isEmpty()) {
				this.setData(element);
				this.setNext(new RecursiveSingleLinkedListImpl<T>());

			} else {
				this.getNext().insert(element);
			}

		}
	}

	@Override
	public void remove(T element) {
		// Se o no atual e null, nao faz nada
		if (this.isEmpty()) {

		} else {
			if (this.getData() == element) {
				this.setData(this.getNext().getData());
				this.setNext(this.getNext().getNext());

			} else {
				this.getNext().remove(element);
			}
		}
	}

	@Override
	public T[] toArray() {
		int count = 0; // Contador para adicionar o elemento na posicao correta.
		T[] array = (T[]) new Object[this.size()];
		this.toArray(array, this, count);
		return array;

	}

	public void toArray(T[] array, RecursiveSingleLinkedListImpl<T> node, int index) {

		if (node.isEmpty()) {

		} else {
			array[index] = node.getData();
			index = index + 1; // Atualiza a posicao no array a ser
									// adicionado um elemento
			toArray(array, node.getNext(), index);
		}

	}
	
	public T getLast() {
		T result = null;
		if (this.getNext().isEmpty()) {
			result = this.data;
		} else { // Caso recursivo
			result = this.getNext().getLast();
		}
		return result;
	}
	
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}

}