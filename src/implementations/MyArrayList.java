package implementations;

import java.util.Arrays;

import utilities.Iterator;
import utilities.ListADT;

public class MyArrayList<E> implements ListADT<E> {
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

	// type casting a new object array to the generic array
	// is not good for type safety but necessary here
	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		size = 0;

		elements = (E[]) new Object[DEFAULT_CAPACITY];
	}

	@Override
	public boolean add(int index, E toAdd) {
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null element to list.");
		}

		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}

		if (size == elements.length) {
			elements = Arrays.copyOf(elements, elements.length * 2);
		}
		// Copying into the array to make space for the new element
		// System array copy instead of array.copy is more efficient (native to c/c++
		System.arraycopy(elements, index, elements, index + 1, size - index);
		elements[index] = toAdd;
		size++;

		return true;
	}

	@Override
	public boolean add(E toAdd) {
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null element to list.");
		}

		if (size == elements.length) {
			elements = Arrays.copyOf(elements, elements.length * 2);
		}

		elements[size] = toAdd;
		size++;

		return true;
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		return elements[index];
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		E removedElement = elements[index];
		System.arraycopy(elements, index + 1, elements, index, size - index - 1);
		size--;
		return removedElement;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return currentIndex < size;
			}

			@Override
			public E next() {
				if (!hasNext()) {
					throw new IndexOutOfBoundsException("No more elements to iterate over.");
				}

				return elements[currentIndex++];
			}
		};
	}
}
