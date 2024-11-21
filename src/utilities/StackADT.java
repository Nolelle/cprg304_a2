package utilities;

import java.util.NoSuchElementException;

/**
 * A Stack Abstract Data Type that follows Last-In-First-Out (LIFO) principle.
 * 
 * @param <E> the type of elements in this stack
 */

public interface StackADT<E> {

	/**
	 * Pushes an element onto the top of this stack.
	 * 
	 * @param element the element to be pushed onto this stack
	 * @throws NullPointerException if the specified element is null
	 * 
	 * @precondition element != null
	 * @postcondition The size of the stack is increased by 1 and the element is at
	 *                the top
	 */
	void push(E element) throws NullPointerException;

	/**
	 * Removes and returns the element at the top of this stack.
	 * 
	 * @return the element at the top of this stack
	 * @throws java.util.NoSuchElementException if this stack is empty
	 * 
	 * @precondition The stack is not empty
	 * @postcondition The size of the stack is decreased by 1 and the top element is
	 *                removed
	 */
	E pop() throws NoSuchElementException;

	/**
	 * Returns the element at the top of this stack without removing it.
	 * 
	 * @return the element at the top of this stack
	 * @throws java.util.NoSuchElementException if this stack is empty
	 * 
	 * @precondition The stack is not empty
	 * @postcondition The stack remains unchanged
	 */
	E peek() throws NoSuchElementException;

	/**
	 * Returns true if this stack contains no elements.
	 * 
	 * @return true if this stack contains no elements, false otherwise
	 * 
	 * @precondition None
	 * @postcondition The stack remains unchanged
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in this stack.
	 * 
	 * @return the number of elements in this stack
	 * 
	 * @precondition None
	 * @postcondition The stack remains unchanged
	 */
	int size();

	/**
	 * Returns an iterator over the elements in this stack in proper sequence. The
	 * elements are returned in order from top to bottom.
	 * 
	 * @return an iterator over the elements in this stack in proper sequence
	 * 
	 * @precondition None
	 * @postcondition The stack remains unchanged
	 */
	Iterator<E> iterator();

	/**
	 * Returns an array containing all of the elements in this stack in proper
	 * sequence. The elements are ordered from top to bottom.
	 * 
	 * @return an array containing all of the elements in this stack in proper
	 *         sequence
	 * 
	 * @precondition None
	 * @postcondition The stack remains unchanged
	 */
	Object[] toArray();

	/**
	 * Removes all of the elements from this stack.
	 * 
	 * @precondition None
	 * @postcondition The stack is empty with size = 0
	 */
	void clear();
}

