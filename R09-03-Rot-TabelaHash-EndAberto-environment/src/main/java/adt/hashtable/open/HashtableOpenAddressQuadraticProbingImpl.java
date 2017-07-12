package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressQuadraticProbingImpl(int size, HashFunctionClosedAddressMethod method, int c1, int c2) {
		super(size);
		hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
		this.initiateInternalTable(size);
	}

	private int getHashKey(T element, int probe) {
		return ((HashFunctionQuadraticProbing<T>) hashFunction).hash(element, probe);
	}

	@Override
	public void insert(T element) {
		if (element == null || super.isFull() || this.search(element) != null) {
			return;
		}
		int probe = 0;
		boolean notInserted = true;

		while (probe <= super.capacity() && notInserted) {
			int hashKey = getHashKey(element, probe);

			if (super.table[hashKey] == null || super.table[hashKey].equals(new DELETED())) {
				super.table[hashKey] = element;
				super.elements++;
				notInserted = false;
			} else {
				super.COLLISIONS++;
				probe++;
			}
		}
	}

	@Override
	public void remove(T element) {
		if (element == null || super.isEmpty()) {
			return;
		}
		int probe = 0;
		int hashKey = getHashKey(element, probe);
		boolean notRemoved = true;

		while (probe <= super.capacity() && super.table[hashKey] != null && notRemoved) {

			if (super.table[hashKey].equals(element)) {
				super.table[hashKey] = new DELETED();
				super.elements--;
				notRemoved = false;
			} else {
				probe++;
			}
			hashKey = getHashKey(element, probe);
		}
	}

	@Override
	public T search(T element) {
		T result = null;
		if (element == null || super.isEmpty()) {
			return result;
		}

		int probe = 0;
		int hashKey = getHashKey(element, probe);
		boolean notFound = true;
		
		while (probe <= super.capacity() && super.table[hashKey] != null && notFound) {
			if (super.table[hashKey].equals(element) && !(super.table[hashKey] == null)) {
				result = element;
				notFound = false;
			} else {
				probe++;
			}
			hashKey = getHashKey(element, probe);
		}
		return result;
	}

	@Override
	public int indexOf(T element) {
		int index = -1;
		if (element == null || super.isEmpty()) {
			index = -1;
		}
		int probe = 0;

		while (probe <= super.capacity()) {
			int hashKey = getHashKey(element, probe);

			if (element.equals(super.table[hashKey])) {
				index =  hashKey;
				break;
			} else {
				probe++;
			}
		}
		return index;
	}
}