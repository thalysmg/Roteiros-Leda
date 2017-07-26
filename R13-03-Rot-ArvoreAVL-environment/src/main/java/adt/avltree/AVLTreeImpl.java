package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements
		AVLTree<T> {
	private static final int MAXIMUM_BALANCE = 1;
	private static final int IS_UNBALANCED_LEFT = 1;
	private static final int CHILD_IS_LEFT_PENDING = 1;
	private static final int CHILD_IS_RIGHT_PENDING = -1;
	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		if ((node == null) || (node.isEmpty()))
			return -1;

		int heightDifference = calculateHeightDifference((BSTNode<T>) node.getLeft(), (BSTNode<T>) node.getRight());

		return Math.abs(heightDifference);
	}

	protected void rebalance(BSTNode<T> node) {
		if ((node == null) || (node.isEmpty()))
			return;

		// chama o rotation pra ajustar a arvore
		BSTNode<T> rotatedNode = rotation(node);

		if (rotatedNode.getParent() == null) {
			root = rotatedNode;
		}
	}

	protected void rebalanceUp(BSTNode<T> node) {
		if (node == null)
			return;

		if (calculateBalance(node) > MAXIMUM_BALANCE) {
			rebalance(node);
		}

		// vai chamando recursivamente para o pai do no para ir balanceando
		rebalanceUp((BSTNode<T>) node.getParent());
	}

	private int calculateHeightDifference(BSTNode<T> leftNode, BSTNode<T> rightNode) {
		int heightLeftNode = recursiveHeight(leftNode, 0);
		int heightRightNode = recursiveHeight(rightNode, 0);

		// se o retorno for positivo a altura a esquerda ta sendo maior e vice
		// versa
		return (heightLeftNode - heightRightNode);
	}
	
	protected void leftRotation(BSTNode<T> node) {
		Util.leftRotation(node);
	}

	protected void rightRotation(BSTNode<T> node) {
		Util.rightRotation(node);
	}
	
	@Override
	public void insert(T element) {
		if (element != null) {
			insertRecursive(element, null, this.root);
		}
	}
	
	private void insertRecursive(T element, BSTNode<T> parentNode, BSTNode<T> node) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parentNode);
			rebalanceUp(node);
		
		} else {
			if (element.compareTo(node.getData()) > 0) {
				insertRecursive(element, node, (BSTNode<T>) node.getRight());

			} else {
				insertRecursive(element, node, (BSTNode<T>) node.getLeft());
			}
		}
	}
	
	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);

		if (node == null || node.isEmpty()) {
			return;
		}
		this.remove(node);
	}

	private void remove(BSTNode<T> node) {

		if (!node.getLeft().isEmpty() && !node.getRight().isEmpty()) { // node has 2 children
			BSTNode<T> newNode = sucessor(node.getData());
			node.setData(newNode.getData());
			remove(newNode);
			rebalanceUp(node);
		} else {
			BSTNode<T> newNode = (BSTNode<T>) node.getLeft();

			if (newNode.isEmpty()) { // left child is null
				newNode = (BSTNode<T>) node.getRight();
			}

			node.setData(newNode.getData());
			node.setRight(newNode.getRight());
			node.setLeft(newNode.getLeft());
			
			if (!node.isEmpty() && node.getRight() != null) {
				node.getRight().setParent(node);
			}
			
			if (!node.isEmpty() && node.getLeft() != null) {
				node.getLeft().setParent(node);
			}
			rebalanceUp(node);
		}
	}
	
	private BSTNode<T> rotation(BSTNode<T> node) {
		int heightDifference = calculateHeightDifference((BSTNode<T>) node.getLeft(), (BSTNode<T>) node.getRight());

		// se o no estiver desbalanceado para a esquerda (mais nos a esquerda)
		if (heightDifference >= IS_UNBALANCED_LEFT) {
			int differenceChildren = calculateHeightDifference((BSTNode<T>) node.getLeft().getLeft(), (BSTNode<T>) node.getLeft().getRight());

			// se o filho do no passado estiver pendendo para direita
			// (se colocar mais um no a direita desse filho ele fica
			// desbalanceado)
			if (differenceChildren <= CHILD_IS_RIGHT_PENDING) { /*
																 * zigue-zague
																 */
				Util.leftRotation((BSTNode<T>) node.getLeft());
				return Util.rightRotation(node);
			} else {
				return Util.rightRotation(node);
			}

			// se o no estiver desbalanceado para a direita (mais nos a direita)
		} else { /* IS_UNBALANCED_RIGHT */
			int differenceChildren = calculateHeightDifference((BSTNode<T>) node.getRight().getLeft(),
					(BSTNode<T>) node.getRight().getRight());

			// se o filho do no passado estiver pendendo para esquerda
			// (se colocar mais um no a esquerda desse filho ele fica
			// desbalanceado)
			if (differenceChildren >= CHILD_IS_LEFT_PENDING) { /* zigue-zague */
				Util.rightRotation((BSTNode<T>) node.getRight());
				return Util.leftRotation(node);
			} else {
				return Util.leftRotation(node);
			}
		}
	}	
}