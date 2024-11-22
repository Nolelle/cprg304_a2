package implementations;

import java.util.Iterator;

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

	private Node head, tail;
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
		if (toAdd == null) {
			throw new NullPointerException();
		}
		Node newNode = new Node(toAdd);
		if (size == 0) {
			head = newNode;
			tail = newNode;
		} else {
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		size++;
		return true;
	}

	@Override
	public boolean add(int index, E toAdd) {
		if (toAdd == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (index == size) {
			return add(toAdd);
		}
		Node newNode = new Node(toAdd);
		if (index == 0) {
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		} else {
			Node current = getNodeAt(index);
			newNode.next = current;
			newNode.prev = current.prev;
			if (current.prev != null) {
				current.prev.next = newNode;
				current.prev = newNode;
			}
		}
		size++;
		return true;
	}

	@Override
	public E get(int index) {
		return getNodeAt(index).data;
	}

	@Override

	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Node current = getNodeAt(index);
		if (size == 1) {
			head = null;
			tail = null;
		} else if (index == 0) {
			head = head.next;
			head.prev = null;
		} else if (index == size - 1) {
			tail = tail.prev;
			tail.next = null;
		} else {
			current.prev.next = current.next;
			current.next.prev = current.prev;
		}
		size--;
		return current.data;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private Node current = head;

			@Override
			public boolean hasNext() {
				return current != null;
			}

			@Override
			public E next() {
				if (!hasNext()) {
					throw new IndexOutOfBoundsException();
				}
				E data = current.data;
				current = current.next;
				return data;
			}
		};
	}

	private Node getNodeAt(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		Node current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}
}
