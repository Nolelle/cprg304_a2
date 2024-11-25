package implementations;

import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.StackADT;

/**
 * MyStack implementation using MyArrayList as the underlying data structure.
 *
 * @param <E> The type of elements in this stack.
 */
public class MyStack<E> implements StackADT<E>, Serializable {

	private static final long serialVersionUID = 1L;
	private MyArrayList<E> stackList;

	public MyStack() {
		stackList = new MyArrayList<>();
	}

	@Override
	public void push(E toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot push null element onto stack.");
		}
		stackList.add(toAdd);
	}

	@Override
	public E pop() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return stackList.remove(stackList.size() - 1);
	}

	@Override
	public E peek() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return stackList.get(stackList.size() - 1);
	}

	@Override
	public void clear() {
		stackList.clear();
	}

	@Override
	public boolean isEmpty() {
		return stackList.isEmpty();
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[stackList.size()];
		for (int i = 0; i < stackList.size(); i++) {
			array[i] = stackList.get(stackList.size() - 1 - i); // Reverse the order
		}
		return array;
	}

	@Override
	public E[] toArray(E[] holder) throws NullPointerException {
		if (holder == null) {
			throw new NullPointerException("Cannot convert to a null array.");
		}
		if (holder.length < stackList.size()) {
			holder = (E[]) java.lang.reflect.Array.newInstance(holder.getClass().getComponentType(), stackList.size());
		}
		for (int i = 0; i < stackList.size(); i++) {
			holder[i] = stackList.get(stackList.size() - 1 - i); // Reverse the order
		}
		if (holder.length > stackList.size()) {
			holder[stackList.size()] = null;
		}
		return holder;
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException {
		return stackList.contains(toFind);
	}

	@Override
	public int search(E toFind) {
		if (toFind == null) {
			throw new NullPointerException("Cannot search for null element.");
		}
		for (int i = stackList.size() - 1; i >= 0; i--) {
			if (stackList.get(i).equals(toFind)) {
				return stackList.size() - i;
			}
		}
		return -1;
	}

	@Override
	public Iterator<E> iterator() {
		return new MyStackIterator();
	}

	@Override
	public boolean equals(StackADT<E> that) {
		if (that == null || this.size() != that.size()) {
			return false;
		}
		Iterator<E> thisIterator = this.iterator();
		Iterator<E> thatIterator = that.iterator();
		while (thisIterator.hasNext() && thatIterator.hasNext()) {
			if (!thisIterator.next().equals(thatIterator.next())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int size() {
		return stackList.size();
	}

	@Override
	public boolean stackOverflow() {
		return false; // MyArrayList is dynamic, so overflow is not applicable.
	}

	private class MyStackIterator implements Iterator<E> {
		private int currentIndex = stackList.size() - 1;

		@Override
		public boolean hasNext() {
			return currentIndex >= 0;
		}

		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements in the stack.");
			}
			return stackList.get(currentIndex--);
		}
	}
}
