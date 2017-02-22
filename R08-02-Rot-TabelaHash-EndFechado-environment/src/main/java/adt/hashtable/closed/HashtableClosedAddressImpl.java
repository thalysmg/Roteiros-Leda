package adt.hashtable.closed;

import java.util.LinkedList;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionDivisionMethod;
import adt.hashtable.hashfunction.HashFunctionFactory;
import util.Util;

public class HashtableClosedAddressImpl<T> extends AbstractHashtableClosedAddress<T> {

	/**
	 * A hash table with closed address works with a hash function with closed
	 * address. Such a function can follow one of these methods: DIVISION or
	 * MULTIPLICATION. In the DIVISION method, it is useful to change the size
	 * of the table to an integer that is prime. This can be achieved by
	 * producing such a prime number that is bigger and close to the desired
	 * size.
	 * 
	 * For doing that, you have auxiliary methods: Util.isPrime and
	 * getPrimeAbove as documented bellow.
	 * 
	 * The length of the internal table must be the immediate prime number
	 * greater than the given size. For example, if size=10 then the length must
	 * be 11. If size=20, the length must be 23. You must implement this idea in
	 * the auxiliary method getPrimeAbove(int size) and use it.
	 * 
	 * @param desiredSize
	 * @param method
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashtableClosedAddressImpl(int desiredSize, HashFunctionClosedAddressMethod method) {
		int realSize = desiredSize;

		if (method == HashFunctionClosedAddressMethod.DIVISION) {
			realSize = this.getPrimeAbove(desiredSize); // real size must the
			// the immediate prime
			// above
		}
		initiateInternalTable(realSize);
		HashFunction function = HashFunctionFactory.createHashFunction(method, realSize);
		this.hashFunction = function;
	}

	// AUXILIARY
	/**
	 * It returns the prime number that is closest (and greater) to the given
	 * number. You can use the method Util.isPrime to check if a number is
	 * prime.
	 */
	public int getPrimeAbove(int number) {
		int tableSize = number + 1;

		while (!Util.isPrime(tableSize)) {
			tableSize++;
		}
		return tableSize;
	}

	@Override
	public void insert(T element) {
		int hashKey = getHashKey(element);

		if (element == null || super.isFull()) {
			return;
		}

		if (super.table[hashKey] == null) {
			LinkedList<T> list = new LinkedList<T>();
			list.add(element);
			super.table[hashKey] = list;
		}

		else {
			((LinkedList<T>) super.table[hashKey]).add(element);
			super.COLLISIONS++;
		}
		super.elements++;
	}

	// method that gives a hashKey based on the element
	private int getHashKey(T element) {
		return ((HashFunctionClosedAddress<T>) hashFunction).hash(element);
	}

	@Override
	public void remove(T element) {
		int hashKey = getHashKey(element);

		if (super.table[hashKey] == null || hashKey > table.length - 1) {
			return;
		}
		if (((LinkedList<T>) super.table[hashKey]).size() == 1) {
			super.table[hashKey] = null;
		} else {
			((LinkedList<T>) super.table[hashKey]).remove(element);
			super.COLLISIONS--;
		}
		super.elements--;
	}

	@Override
   public T search(T element) {
      int hashKey = getHashKey(element);
      
      LinkedList<T> list = (LinkedList<T>) super.table[hashKey];
      
      if (((LinkedList<T>) super.table[hashKey]) != null && list.contains(element)) {
    	  return element;
      }
      return null;
   }

	@Override
	public int indexOf(T element) {
		if (search(element) == null) {
			return -1;
		}
		return getHashKey(element);
	}

}
