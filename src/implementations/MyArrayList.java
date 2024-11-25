package implementations;

import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * An implementation of the ListADT interface using a dynamic array as the
 * underlying data structure. This class provides a resizable-array
 * implementation of the ListADT interface with dynamic capacity management.
 * 
 * @param <E> The type of elements in this list
 * @author Edmund
 * @version 1.0
 */
public class MyArrayList<E> implements ListADT<E>, Serializable {
	// for classes that implement serializable, used to maintain versioning for
	// deserialization.
	private static final long serialVersionUID = 1L;
	/**
	 * Default initial capacity for the array list.
	 */
	private static final int DEFAULT_CAPACITY = 10;
	/**
	 * Array buffer into which the elements of the ArrayList are stored.
	 */
	private E[] elements;
	/**
	 * The number of elements currently stored in the list.
	 */
	private int size;

	/**
	 * Constructs an empty list with an initial capacity of DEFAULT_CAPACITY.
	 */
	// type casting a new object array to the generic array
	@SuppressWarnings("unchecked")
	public MyArrayList() {
		elements = (E[]) new Object[DEFAULT_CAPACITY];
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
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * If necessary, the capacity of the array is increased to accommodate the new
	 * element.
	 * </p>
	 */
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
	 * {@inheritDoc}
	 * <p>
	 * Appends the element at the end of the list, increasing capacity if necessary.
	 * </p>
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		return elements[index];
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(toFind)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<E> iterator() {
		return new MyArrayListIterator();
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

		E oldElement = elements[index];
		elements[index] = toChange;
		return oldElement;
	}

	/**
	 * Iterator implementation for MyArrayList.
	 */
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
