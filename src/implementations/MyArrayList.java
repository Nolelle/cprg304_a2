package implementations;

import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * Implementation of MyArrayList which provides a dynamic array-based list using
 * the ListADT interface and Iterator interface.
 *
 * @param <E> The type of elements in this list.
 */
public class MyArrayList<E> implements ListADT<E>, Serializable {
	// for classes that implement serializable, used to maintain versioning for
	// deserialization.
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_CAPACITY = 10;
	private E[] elements;
	private int size;

	// type casting a new object array to the generic array
	// is not good for type safety but necessary here
	@SuppressWarnings("unchecked")
	public MyArrayList() {
		elements = (E[]) new Object[DEFAULT_CAPACITY];
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null element to list.");
		}

		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}

		if (size >= elements.length) {
			elements = Arrays.copyOf(elements, elements.length * 2);
		}

		// Copying into the array to make space for the new element
		System.arraycopy(elements, index, elements, index + 1, size - index);
		elements[index] = toAdd;
		size++;

		return true;
	}

	/**
	 * Appends the specified element to the end of the list, resizing if necessary.
	 */
	@Override
	public boolean add(E toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null element to list.");
		}

		if (size >= elements.length) {
			elements = Arrays.copyOf(elements, elements.length * 2);
		}

		elements[size++] = toAdd;
		return true;
	}

	// ? extends E so we can use subtypes of E when adding to the list.
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

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		return elements[index];
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}

		E removedElement = elements[index];
		int numMoved = size - index - 1;
		if (numMoved > 0) {
			System.arraycopy(elements, index + 1, elements, index, numMoved);
		}
		elements[--size] = null;

		return removedElement;
	}

	@Override
	public E remove(E toRemove) throws NullPointerException {
		if (toRemove == null) {
			throw new NullPointerException("Cannot remove null element from list.");
		}

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(toRemove)) {
				return remove(i);
			}
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if (toFind == null) {
			throw new NullPointerException("Cannot find null element in list.");
		}

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(toFind)) {
				return true;
			}
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E[] toArray(E[] toHold) throws NullPointerException {
		if (toHold == null) {
			throw new NullPointerException("Cannot copy elements to null array.");
		}

		if (toHold.length < size) {
			return (E[]) Arrays.copyOf(elements, size, toHold.getClass());
		}

		System.arraycopy(elements, 0, toHold, 0, size);
		if (toHold.length > size) {
			toHold[size] = null;
		}
		return toHold;
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}

	@Override
	public Iterator<E> iterator() {
		return new MyArrayListIterator();
	}

	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
		if (toChange == null) {
			throw new NullPointerException("Cannot set null element in list.");
		}

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}

		E oldElement = elements[index];
		elements[index] = toChange;
		return oldElement;
	}

	private class MyArrayListIterator implements Iterator<E> {
		private int currentIndex = 0;

		@Override
		public boolean hasNext() {
			return currentIndex < size;
		}

		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements in list.");
			}
			return elements[currentIndex++];
		}
	}
}
