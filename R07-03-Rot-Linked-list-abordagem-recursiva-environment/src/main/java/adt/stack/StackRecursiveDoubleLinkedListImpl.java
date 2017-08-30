package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.RecursiveDoubleLinkedListImpl;
import adt.linkedList.RecursiveSingleLinkedListImpl;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;

	public StackRecursiveDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new RecursiveDoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (element != null) {
			if (this.isFull()) {
				throw new StackOverflowException();

			} else {
				top.insert(element);
			}
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (this.isEmpty()) {
			throw new StackUnderflowException();

		} else {
			T popped = top();
			top.removeLast();
			return popped;
		}
	}

	@Override
	public T top() {
		return ((RecursiveSingleLinkedListImpl<T>) top).getLast();
	}

	@Override
	public boolean isEmpty() {
		return top.isEmpty();
	}

	@Override
	public boolean isFull() {
		return this.size == top.size();
	}
//	public static void main(String[] args) throws Exception {
//		StackRecursiveDoubleLinkedListImpl stack = new StackRecursiveDoubleLinkedListImpl<Integer>(5);
//		stack.push(2);
//		stack.push(3);
//		stack.push(1);
//		stack.push(4);
//		stack.push(8);
//		System.out.println(stack.isFull());
//	}
}
