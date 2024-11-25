package exceptions;

/**
 * An exception thrown when attempting to access or remove an element from an
 * empty queue. This was created from ChatGPT as this was not provided in the
 * original code.
 */
public class EmptyQueueException extends RuntimeException {
	public EmptyQueueException() {
		super("Queue is empty.");
	}

	public EmptyQueueException(String message) {
		super(message);
	}
}
