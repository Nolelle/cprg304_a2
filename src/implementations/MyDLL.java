package implementations;

import java.io.Serializable;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * An implementation of the ListADT interface using a doubly-linked list as the
 * underlying data structure. This class provides a linked list implementation
 * where each node contains references to both the next and previous nodes.
 *
 * @param <E> The type of elements in this list
 * @author Ed
 * @version 1.0
 */
public class MyDLL<E> implements ListADT<E>, Serializable {
	// for classes that implement serializable, used to maintain versioning for
	// deserialization.
	private static final long serialVersionUID = 1L;
	/**
	 * Reference to the first node in the list.
	 */
	private MyDLLNode<E> head;
	/**
	 * Reference to the last node in the list.
	 */
	private MyDLLNode<E> tail;
	/**
	 * The number of elements currently stored in the list.
	 */
	private int size;

	/**
	 * Constructs an empty doubly-linked list.
	 */
	public MyDLL() {
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null element to list.");
		}
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}

		MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
		if (index == 0) {
			if (head == null) {
				head = newNode;
				tail = newNode;
			} else {
				newNode.next = head;
				head.prev = newNode;
				head = newNode;
			}
		} else if (index == size) {
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		} else {
			MyDLLNode<E> current = getNode(index);
			newNode.prev = current.prev;
			newNode.next = current;
			if (current.prev != null) {
				current.prev.next = newNode;
			}
			current.prev = newNode;
		}
		size++;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(E toAdd) throws NullPointerException {
		return add(size, toAdd);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null list to list.");
		}
		for (int i = 0; i < toAdd.size(); i++) {
			add(toAdd.get(i));
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		return getNode(index).data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}

		MyDLLNode<E> toRemove = getNode(index);
		if (toRemove.prev != null) {
			toRemove.prev.next = toRemove.next;
		} else {
			head = toRemove.next;
		}

		if (toRemove.next != null) {
			toRemove.next.prev = toRemove.prev;
		} else {
			tail = toRemove.prev;
		}
		size--;
		return toRemove.data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E remove(E toRemove) throws NullPointerException {
		if (toRemove == null) {
			throw new NullPointerException("Cannot remove null element from list.");
		}
		MyDLLNode<E> current = head;
		while (current != null) {
			if (current.data.equals(toRemove)) {
				if (current == head) {
					head = current.next;
					if (head != null) {
						head.prev = null;
					}
				} else if (current == tail) {
					tail = current.prev;
					if (tail != null) {
						tail.next = null;
					}
				} else {
					current.prev.next = current.next;
					current.next.prev = current.prev;
				}
				size--;
				return current.data;
			}
			current = current.next;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if (toFind == null) {
			throw new NullPointerException("Cannot find null element in list.");
		}
		MyDLLNode<E> current = head;
		while (current != null) {
			if (current.data.equals(toFind)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E[] toArray(E[] toHold) throws NullPointerException {
		if (toHold == null) {
			throw new NullPointerException("Cannot copy elements to null array.");
		}
		if (toHold.length < size) {
			toHold = (E[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
		}
		MyDLLNode<E> current = head;
		for (int i = 0; i < size; i++) {
			toHold[i] = current.data;
			current = current.next;
		}
		if (toHold.length > size) {
			toHold[size] = null;
		}
		return toHold;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		Object[] result = new Object[size];
		MyDLLNode<E> current = head;
		for (int i = 0; i < size; i++) {
			result[i] = current.data;
			current = current.next;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<E> iterator() {
		return new MyDLLIterator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
		if (toChange == null) {
			throw new NullPointerException("Cannot set null element in list.");
		}
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		MyDLLNode<E> current = getNode(index);
		E oldData = current.data;
		current.data = toChange;
		return oldData;
	}

	/**
	 * Returns the node at the specified position.
	 * <p>
	 * This helper method optimizes traversal by starting from the closer end of the
	 * list based on the specified index.
	 * </p>
	 *
	 * @param index the position of the desired node
	 * @return the node at the specified position
	 */
	private MyDLLNode<E> getNode(int index) {
		MyDLLNode<E> current;
		if (index < size / 2) {
			current = head;
			for (int i = 0; i < index; i++) {
				current = current.next;
			}
		} else {
			current = tail;
			for (int i = size - 1; i > index; i--) {
				current = current.prev;
			}
		}
		return current;
	}

	/**
	 * Iterator implementation for MyDLL.
	 */
	private class MyDLLIterator implements Iterator<E> {
		private MyDLLNode<E> current = head;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements in list.");
			}
			E data = current.data;
			current = current.next;
			return data;
		}
	}

	/**
	 * Node class for the doubly-linked list.
	 */
	private static class MyDLLNode<E> {
		E data;
		MyDLLNode<E> next;
		MyDLLNode<E> prev;

		public MyDLLNode(E data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}
}