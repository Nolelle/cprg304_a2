package implementations;

import java.io.Serializable;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

/**
 * An implementation of the QueueADT interface using a doubly-linked list
 * (MyDLL) as the underlying data structure. This class provides a
 * First-In-First-Out (FIFO) queue implementation.
 * 
 * @param <E> The type of elements in this queue
 * @author Ed
 * @version 1.0
 */
public class MyQueue<E> implements QueueADT<E>, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The underlying doubly-linked list used to store queue elements.
	 */
	private MyDLL<E> list;

	/**
	 * Constructs an empty queue.
	 */
	public MyQueue() {
		list = new MyDLL<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enqueue(E toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null element to queue.");
		}
		list.add(toAdd);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E dequeue() throws EmptyQueueException {
		if (isEmpty()) {
			throw new EmptyQueueException("Queue is empty.");
		}
		return list.remove(0); // Remove the first element
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E peek() throws EmptyQueueException {
		if (isEmpty()) {
			throw new EmptyQueueException("Queue is empty.");
		}
		return list.get(0); // Get the first element without removing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dequeueAll() {
		list.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(E toFind) throws NullPointerException {
		return list.contains(toFind);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int search(E toFind) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(toFind)) {
				return i + 1; // 1-based index
			}
		}
		return -1; // Not found
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E[] toArray(E[] holder) throws NullPointerException {
		return list.toArray(holder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFull() {
		// Since the queue is dynamic, it is never "full."
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return list.size();
	}
}
