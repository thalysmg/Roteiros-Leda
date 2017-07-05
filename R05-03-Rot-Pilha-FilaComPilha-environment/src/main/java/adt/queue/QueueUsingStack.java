package adt.queue;

import adt.stack.Stack;
import adt.stack.StackImpl;
import adt.stack.StackOverflowException;
import adt.stack.StackUnderflowException;

public class QueueUsingStack<T> implements Queue<T> {

	private Stack<T> stack1;
	private Stack<T> stack2;

	public QueueUsingStack(int size) {
		stack1 = new StackImpl<T>(size);
		stack2 = new StackImpl<T>(size);
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (stack1.isFull()) {
			throw new QueueOverflowException();
		}
		try {
			stack1.push(element);
		} catch (StackOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (stack1.isEmpty()) {
			throw new QueueUnderflowException();
		}
		T result = null;
		try {
			transferElements(stack1, stack2);
			result = stack2.pop();
			transferElements(stack2, stack1);
		} catch (StackOverflowException | StackUnderflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public T head() {
		T result = null;
		try {
			transferElements(stack1, stack2);
			result = stack2.top();
			transferElements(stack2, stack1);
		} catch (StackOverflowException | StackUnderflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		boolean isEmpty = false;
		if (stack1.isEmpty() && stack2.isEmpty()) {
			isEmpty = true;
		}
		return isEmpty;
	}

	@Override
	public boolean isFull() {
		boolean isFull = false;
		if (stack1.isFull() || stack2.isFull()) {
			isFull = true;
		}
		return isFull;
	}

	/**
	 * Esse método transfere todos os elementos da pilha1 para a pilha2
	 * 
	 * @param stack1 pilha1
	 * @param stack2 pilha2
	 * @throws StackOverflowException
	 * @throws StackUnderflowException
	 */
	private void transferElements(Stack<T> stack1, Stack<T> stack2)
			throws StackOverflowException, StackUnderflowException {
		
		while (!stack1.isEmpty()) {
			stack2.push(stack1.pop());
		}
	}

}
