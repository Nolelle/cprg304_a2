package implementations;

import utilities.ListADT;

public class MyDLL<E> implements ListADT<E> {
	private class Node {
		E data;
		Node next;
		Node prev;

		Node(E data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}

	private Node head;
	private int size;

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}

	@Override
	public boolean add(E toAdd) {
	}

}
