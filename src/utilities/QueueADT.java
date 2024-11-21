package utilities;

/**
 * A Queue Abstract Data Type that follows First-In-First-Out (FIFO) principle.
 * 
 * @version 1.0
 * @param <E> the type of elements in this queue
 */
public interface QueueADT<E> {

	/**
	 * Adds an element to the rear of this queue.
	 * 
	 * @param element the element to be added to the rear of this queue
	 * @throws NullPointerException if the specified element is null
	 * 
	 * @precondition element != null
	 * @postcondition The size of the queue is increased by 1 and the element is at
	 *                the rear
	 */
	void enqueue(E element) throws NullPointerException;

	/**
	 * Removes and returns the element at the front of this queue.
	 * 
	 * @return the element at the front of this queue
	 * @throws java.util.NoSuchElementException if this queue is empty
	 * 
	 * @precondition The queue is not empty
	 * @postcondition The size of the queue is decreased by 1 and the front element
	 *                is removed
	 */
	E dequeue() throws java.util.NoSuchElementException;

	/**
	 * Returns the element at the front of this queue without removing it.
	 * 
	 * @return the element at the front of this queue
	 * @throws java.util.NoSuchElementException if this queue is empty
	 * 
	 * @precondition The queue is not empty
	 * @postcondition The queue remains unchanged
	 */
	E peek() throws java.util.NoSuchElementException;

	/**
	 * Returns true if this queue contains no elements.
	 * 
	 * @return true if this queue contains no elements, false otherwise
	 * 
	 * @precondition None
	 * @postcondition The queue remains unchanged
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in this queue.
	 * 
	 * @return the number of elements in this queue
	 * 
	 * @precondition None
	 * @postcondition The queue remains unchanged
	 */
	int size();

	/**
	 * Returns an iterator over the elements in this queue in proper sequence. The
	 * elements are returned in order from front to rear.
	 * 
	 * @return an iterator over the elements in this queue in proper sequence
	 * 
	 * @precondition None
	 * @postcondition The queue remains unchanged
	 */
	Iterator<E> iterator();

	/**
	 * Returns an array containing all of the elements in this queue in proper
	 * sequence. The elements are ordered from front to rear.
	 * 
	 * @return an array containing all of the elements in this queue in proper
	 *         sequence
	 * 
	 * @precondition None
	 * @postcondition The queue remains unchanged
	 */
	Object[] toArray();

	/**
	 * Removes all of the elements from this queue.
	 * 
	 * @precondition None
	 * @postcondition The queue is empty with size = 0
	 */
	void clear();
}