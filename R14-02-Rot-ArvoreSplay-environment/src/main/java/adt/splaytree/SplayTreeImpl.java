package adt.splaytree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

public class SplayTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements SplayTree<T> {

	private void splay(BSTNode<T> node) {
		if (node == null || node.isEmpty() || node.equals(super.root))
			return;

		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		BSTNode<T> grandPa = (BSTNode<T>) parent.getParent();

		if (parent.equals(this.root)) {
			if (node.equals(parent.getRight())) {
				zigLeft(node);
						
			} else {
				zigRight(node);

			}
			this.root = node;

		} else if (!parent.equals(this.root)) {
			if (parent.equals(grandPa.getLeft()) && node.equals(parent.getLeft())) {
				this.zigZigRight(node);

			} else if (parent.equals(grandPa.getRight()) && node.equals(parent.getRight())) {
				this.zigZigLeft(node);

			} else if (parent.equals(grandPa.getLeft()) && node.equals(parent.getRight())) {
				this.zigZagLeftRight(node);

			} else {
				this.zigZagRightLeft(node);

			}
		}

		splay(node);

	}

	private void zigLeft(BSTNode<T> node) {
		Util.leftRotation((BSTNode<T>) node.getParent());
	}

	private void zigRight(BSTNode<T> node) {
		Util.rightRotation((BSTNode<T>) node.getParent());
	}

	private void zigZigLeft(BSTNode<T> node) {
		Util.leftRotation((BSTNode<T>) node.getParent().getParent());
		Util.leftRotation((BSTNode<T>) node.getParent());
	}

	private void zigZigRight(BSTNode<T> node) {
		Util.rightRotation((BSTNode<T>) node.getParent().getParent());
		Util.rightRotation((BSTNode<T>) node.getParent());
	}

	private void zigZagLeftRight(BSTNode<T> node) {
		Util.leftRotation((BSTNode<T>) node.getParent());
		Util.rightRotation((BSTNode<T>) node.getParent());

	}

	private void zigZagRightLeft(BSTNode<T> node) {
		Util.rightRotation((BSTNode<T>) node.getParent());
		Util.leftRotation((BSTNode<T>) node.getParent());

	}

	@Override
	public BSTNode<T> search(T element) {

		BSTNode<T> node = super.search(element);

		if (!node.isEmpty())
			splay(node);
		else
			splay((BSTNode<T>) node.getParent());

		return node;
	}

	@Override
	public void insert(T element) {

		if (element != null) {
			super.insert(element);
			splay(super.search(element));
		}
	}

	@Override
	public void remove(T element) {

		if (element != null) {

			BSTNode<T> node = super.search(element);
			if (node.isEmpty()) {
				this.splay((BSTNode<T>) node.getParent());
			} else {
				BSTNode<T> parent = (BSTNode<T>) node.getParent();
				super.remove(node.getData());
				this.splay(parent);
			}

		}

	}

}
