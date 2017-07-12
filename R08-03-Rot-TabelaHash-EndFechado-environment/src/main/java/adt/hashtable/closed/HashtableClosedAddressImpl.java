package adt.hashtable.closed;

import util.Util;

import java.util.LinkedList;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionFactory;

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
	int getPrimeAbove(int number) {
		int result = number + 1;
		while (!Util.isPrime(result)) {
			result++;
		}
		return result;
	}

	@Override
	public void insert(T element) {
		int hashKey = getHashKey(element);
		
		if (element == null || super.isFull() || search(element) != null) {
			return;
		}
		if (super.table[hashKey] == null) { // in this case there is no element inserted in the current index of the table (hashKey)
			LinkedList<T> list = new LinkedList<T>();
			list.addFirst(element);
			super.table[hashKey] = list;
		} else { // in this case we already have one or more elements inserted
					// in the current index of the table, so we just add
					// "element" to the linked list
			((LinkedList<T>) super.table[hashKey]).addFirst(element);
			super.COLLISIONS++;
		}
		super.elements++;
	}

	// this method return the hashKey, using the element tu calculate it
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
		T searched = null;

		if (((LinkedList<T>) super.table[hashKey]) != null && list.contains(element)) {
			searched = element;
		}
		return searched;
	}

	@Override
	public int indexOf(T element) {
		int index = -1;
		if (search(element) == null) {
			index = -1;
		
		} else {
			index = getHashKey(element);
		}
		return index;
	}
}
