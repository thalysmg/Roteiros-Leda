package adt.queue;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class QueueDoubleLinkedListImpl<T> implements Queue<T> {

   protected DoubleLinkedList<T> list;
   protected int size;

   public QueueDoubleLinkedListImpl(int size) {
      this.size = size;
      this.list = new DoubleLinkedListImpl<T>();
   }

   @Override
   public void enqueue(T element) throws QueueOverflowException {
      if (list.size() == size) {
         throw new QueueOverflowException();
      }
      list.insert(element);
   }

   @Override
   public T dequeue() throws QueueUnderflowException {
      if (list.isEmpty()) {
         throw new QueueUnderflowException();
      }
      T dequeued = this.list.toArray()[0];
      this.list.removeFirst();
      return dequeued;
   }

   @Override
   public T head() {
      if (this.list.isEmpty()) {
         return null;
      }
      return this.list.toArray()[0];
   }

   @Override
   public boolean isEmpty() {
      return this.list.isEmpty();
   }

   @Override
   public boolean isFull() {
      return this.list.size() == this.size;
   }

}
