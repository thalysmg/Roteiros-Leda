package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return this.head.isNIL();
	}

	@Override
	public int size() {
		int size = 0;
		SingleLinkedListNode<T> auxNode = this.head;
		while (!auxNode.isNIL()) {
			size++;
			auxNode = auxNode.getNext();
		}
		return size;
	}

	@Override
	public T search(T element) {
		if (element == null) {
			return null;
		}
		
		SingleLinkedListNode<T> result = this.head;
		while (!result.isNIL() && !result.getData().equals(element)) {
			result = result.getNext();
		}
		return result.getData();
	}

	@Override
	public void insert(T element) {
		if (element == null) {
			return;
		}
		
		SingleLinkedListNode<T> auxHead = this.head;
		if (auxHead.isNIL()) {
			SingleLinkedListNode<T> newHead = new SingleLinkedListNode<T>(element, this.head);
			this.head = newHead;
		} else {
			while (!auxHead.getNext().isNIL()) {
				auxHead = auxHead.getNext();
			}
			SingleLinkedListNode<T> newNode = new SingleLinkedListNode<T>(element, auxHead.getNext());
			auxHead.next = newNode;
		}
	}

	@Override
	public void remove(T element) {
		if (element == null || this.isEmpty()) {
			return;
		}
		
		if (head.getData().equals(element)) {
			this.head = this.head.getNext();
		} else {
			SingleLinkedListNode<T> aux = this.head;
			SingleLinkedListNode<T> previous = new SingleLinkedListNode<>();
			while (!aux.isNIL() && !aux.getData().equals(element)) {
				previous = aux;
				aux = aux.getNext();
			}
			if (!aux.isNIL()) {
				previous.next = aux.getNext();
			}
		}
	}

	@Override
	public T[] toArray() {
		int LLSize = this.size();
		T[] result = (T[]) new Comparable[LLSize];
		if (LLSize > 0) {
			SingleLinkedListNode<T> aux = this.head;

			for (int i = 0; i < result.length; i++) {
				result[i] = aux.getData();
				aux = aux.getNext();
			}
		}
		return result;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
