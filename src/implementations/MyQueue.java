package implementations;

import java.io.Serializable;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

/**
 * MyQueue implementation using MyDLL as the underlying data structure.
 * 
 * @param <E> the type of elements in this queue.
 */
public class MyQueue<E> implements QueueADT<E>, Serializable {

	private static final long serialVersionUID = 1L;
	private MyDLL<E> list;

	public MyQueue() {
		list = new MyDLL<>();
	}

	@Override
	public void enqueue(E toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null element to queue.");
		}
		list.add(toAdd);
	}

	@Override
	public E dequeue() throws EmptyQueueException {
		if (isEmpty()) {
			throw new EmptyQueueException("Queue is empty.");
		}
		return list.remove(0); // Remove the first element
	}

	@Override
	public E peek() throws EmptyQueueException {
		if (isEmpty()) {
			throw new EmptyQueueException("Queue is empty.");
		}
		return list.get(0); // Get the first element without removing
	}

	@Override
	public void dequeueAll() {
		list.clear();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException {
		return list.contains(toFind);
	}

	@Override
	public int search(E toFind) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(toFind)) {
				return i + 1; // 1-based index
			}
		}
		return -1; // Not found
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public boolean equals(QueueADT<E> that) {
		if (that == null || this.size() != that.size()) {
			return false;
		}
		Iterator<E> thisIter = this.iterator();
		Iterator<E> thatIter = that.iterator();
		while (thisIter.hasNext() && thatIter.hasNext()) {
			if (!thisIter.next().equals(thatIter.next())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public E[] toArray(E[] holder) throws NullPointerException {
		return list.toArray(holder);
	}

	@Override
	public boolean isFull() {
		// Since the queue is dynamic, it is never "full."
		return false;
	}

	@Override
	public int size() {
		return list.size();
	}
}
