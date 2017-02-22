package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable>
		extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressQuadraticProbingImpl(int size,
			HashFunctionClosedAddressMethod method, int c1, int c2) {
		super(size);
		hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
		this.initiateInternalTable(size);
	}

	private int getHashKey(T element, int probe) {
		return ((HashFunctionQuadraticProbing<T>) hashFunction).hash(element, probe);
	}

	@Override
	public void insert(T element) {
		if (super.capacity() == super.size()) {
			throw new HashtableOverflowException();
		}

		int probe = 0;

		while (probe < super.capacity()) {
			int hashKey = this.getHashKey(element, probe);
			if (super.table[hashKey] == null || super.table[hashKey] == new DELETED()) {
				super.table[hashKey] = element;
				break;

			} else {
				probe++;
				super.COLLISIONS++;
			}
		}
		super.elements++;
	}

	@Override
	public void remove(T element) {
		int probe = 0;
		int hashKey = getHashKey(element, probe);
		
		while (!(super.table[hashKey] == null) && probe <= super.capacity()) {

			if (super.table[hashKey].equals(element)) {
				super.table[hashKey] = new DELETED();
				super.elements--;
				break;
			
			} else {
				probe++;
			}
			hashKey = getHashKey(element, probe);
		}
	}

	@Override
	public T search(T element) {
		int probe = 0;
		int hashKey = this.getHashKey(element, probe);

		while (probe < super.capacity() && super.table[hashKey] != null) {
			if (super.table[hashKey].equals(element)) {
				return element;
			} else {
				probe++;
			}
			hashKey = this.getHashKey(element, probe);
		}
		return null;
	}

	@Override
	public int indexOf(T element) {
		int probe = 0;
		int hashKey = getHashKey(element, probe);

		while (probe < super.capacity()) {
			hashKey = getHashKey(element, probe);

			if (super.table[hashKey].equals(element)) {
				return hashKey;

			} else {
				probe++;
			}
		}
		return -1;
	}
}
