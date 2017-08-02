package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = 0; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	@Override
	public void insert(int key, T newValue, int height) {
		if (newValue != null) {
			SkipListNode<T> node = this.root;
			SkipListNode<T> newNode = new SkipListNode<T>(key, height, newValue);
			SkipListNode<T>[] update = new SkipListNode[maxHeight];

			for (int i = maxHeight - 1; i >= 0; i--) {
				if (node.forward[i] != null) {
					while (node.forward[i].key < key) {
						node = node.forward[i];
					}
				}
				update[i] = node;
			}
			node = node.getForward(0);
			if (node.key == key) {
				node.setValue(newValue);

			} else {
				for (int i = 0; i < height; i++) {
					if (update[i] != null) {
						newNode.forward[i] = update[i].forward[i];
						update[i].forward[i] = newNode;
					}
				}
			}
		}
	}

	@Override
	public void remove(int key) {
		SkipListNode<T> node = this.root;
		SkipListNode<T>[] update = new SkipListNode[maxHeight];

		for (int i = maxHeight - 1; i >= 0; i--) {
			if (node.forward[i] != null) {
				while (node.forward[i].key < key) {
					node = node.getForward(i);
				}
			}
			update[i] = node;
		}	
		node = node.getForward(0);
		if (node.getKey() == key) {
			for (int i = 0; i < maxHeight ; i++) {
				if (update[i].getForward(i) != node) {
					break;
				}
				update[i].forward[i] = node.getForward(i);
			}
		}
	
	}

	@Override
	public int height() {
		 return recursiveHeight(this.root, maxHeight - 1);
//		if (this.size() == 0) {
//			return 0;
//		}
//		SkipListNode<T> node = this.root;
//		int altura = 0;
//		
//		while (!node.getForward(0).equals(NIL)) {
//			if (node.getForward(0).height() > altura) {
//				altura = node.getForward(0).height();	
//				
//			}
//			node = node.getForward(0);
//		}
//		return altura;
	}
	
	private int recursiveHeight(SkipListNode<T> node, int height) {
		int result = height;

	      if (this.size() == 0)
	         result = 0;
	      else if (!node.forward[height].equals(NIL)) {
	         result = height + 1;
	      } else {
	         if (height == 0) {
	            result = 0;
	         }
	         height--;
	         result = recursiveHeight(node, height);
	      }
	      return result;
	   }
	

	@Override
	public SkipListNode<T> search(int key) {
		return recursiveSearch(this.root, maxHeight, key);
//		SkipListNode<T> node = this.root;
//		
//		for (int i = maxHeight - 1; i >= 0; i--) {
//			if (node.forward[i] != null) {
//				while (node.forward[i].key < key) {
//					node = node.forward[i];
//				}
//			}
//		}
//		node = node.getForward(0);
//		
//		if (node.key == key) {
//			return node;
//		}
//		return null;
	}
	
	private SkipListNode<T> recursiveSearch(SkipListNode<T> node, int level, int key) {
	      SkipListNode<T> result = node;

	      if (level == 0) {
	         if (node.forward[0].key == key) {
	            result = result.forward[0];
	         } else {
	            result = null;
	         }
	         return result;
	      }
	      level--;
	      if (node.forward[level].key < key) {
	         result = recursiveSearch(node.forward[level], level, key);
	      } else {
	         result = recursiveSearch(node, level, key);
	      }
	      return result;
	   }
	
	@Override
	public int size() {
		SkipListNode<T> node = this.root.getForward(0);
		int size = 0;

		while (!node.equals(NIL)) {
			node = node.getForward(0);
			size++;
		}
		return size;
	}

	@Override
	public SkipListNode<T>[] toArray() {
		SkipListNode[] result = new SkipListNode[this.size() + 2];
		SkipListNode<T> node = this.root.getForward(0);

		if (this.size() == 0) {
			result[0] = this.root;
			result[1] = NIL;
		}

		result[0] = this.root;

		int i = 1;

		while (!node.equals(NIL)) {
			result[i] = node;
			node = node.getForward(0);
			i++;
		}
		result[i] = NIL;

		return result;
	}

}
