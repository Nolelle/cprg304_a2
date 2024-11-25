package appDomain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import exceptions.EmptyQueueException;
import implementations.MyQueue;
import implementations.MyStack;

public class Parser {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java Parser <xml-file>");
			return;
		}

		File xmlFile = new File(args[0]);
		if (!xmlFile.exists() || !xmlFile.isFile()) {
			System.out.println("Error: File not found or invalid.");
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(xmlFile))) {
			parseXML(reader);
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}

	private static void parseXML(BufferedReader reader) throws IOException {
		MyStack<String> stack = new MyStack<>();
		MyQueue<String> errorQueue = new MyQueue<>();
		MyQueue<String> extrasQueue = new MyQueue<>();

		String line;
		int lineNumber = 0;

		while ((line = reader.readLine()) != null) {
			lineNumber++;
			line = line.trim();
			if (line.isEmpty() || line.startsWith("<?xml")) {
				continue; // Ignore empty lines and processing instructions
			}

			// Split line into tokens based on tags
			String[] tokens = line.split("(?=<)|(?<=>)");
			for (String token : tokens) {
				if (token.matches("<[^/][^>]*?/?>")) {
					// Self-Closing Tag
					if (token.endsWith("/>")) {
						// Ignore self-closing tags
					} else {
						// Start Tag
						stack.push(token);
					}
				} else if (token.matches("</[^>]+>")) {
					// End Tag
					if (!stack.isEmpty() && matchesTopOfStack(stack, token)) {
						stack.pop();
					} else if (!errorQueue.isEmpty() && matchesHeadOfQueue(errorQueue, token)) {
						errorQueue.dequeue();
					} else if (stack.isEmpty()) {
						errorQueue.enqueue("Line " + lineNumber + ": Unmatched closing tag: " + token);
					} else {
						handleEndTagMismatch(stack, errorQueue, extrasQueue, token, lineNumber);
					}
				}
			}
		}

		// Handle remaining items in the stack
		while (!stack.isEmpty()) {
			errorQueue.enqueue("Unclosed tag: " + stack.pop());
		}

		// Reconcile error and extras queues
		reconcileQueues(errorQueue, extrasQueue);

		// Output errors
		if (errorQueue.isEmpty() && extrasQueue.isEmpty()) {
			System.out.println("XML document is well-formed.");
		} else {
			System.out.println("XML document contains errors:");
			while (!errorQueue.isEmpty()) {
				System.out.println(errorQueue.dequeue());
			}
		}
	}

	private static boolean matchesTopOfStack(MyStack<String> stack, String endTag) {
		String topTag = stack.peek();
		String tagName = extractTagName(endTag);
		return extractTagName(topTag).equals(tagName);
	}

	private static boolean matchesHeadOfQueue(MyQueue<String> queue, String endTag) {
		try {
			String head = queue.peek();
			String tagName = extractTagName(endTag);
			return head != null && extractTagName(head).equals(tagName);
		} catch (EmptyQueueException e) {
			return false;
		}
	}

	private static void handleEndTagMismatch(MyStack<String> stack, MyQueue<String> errorQueue,
			MyQueue<String> extrasQueue, String endTag, int lineNumber) {
		boolean matchFound = false;
		MyStack<String> tempStack = new MyStack<>();

		// Search for matching start tag in the stack
		while (!stack.isEmpty()) {
			String topTag = stack.pop();
			tempStack.push(topTag);
			if (matchesTopOfStack(tempStack, endTag)) {
				matchFound = true;
				break;
			}
		}

		// Restore unmatched tags back to the stack
		while (!tempStack.isEmpty()) {
			String unmatchedTag = tempStack.pop();
			if (!matchFound) {
				extrasQueue.enqueue(unmatchedTag);
			} else {
				errorQueue.enqueue("Line " + lineNumber + ": Unmatched start tag: " + unmatchedTag);
			}
		}

		if (!matchFound) {
			extrasQueue.enqueue("Line " + lineNumber + ": Extra closing tag: " + endTag);
		}
	}

	private static void reconcileQueues(MyQueue<String> errorQueue, MyQueue<String> extrasQueue) {
		while (!errorQueue.isEmpty() || !extrasQueue.isEmpty()) {
			if (errorQueue.isEmpty() || extrasQueue.isEmpty()) {
				// One queue is empty, report all remaining items as errors
				while (!errorQueue.isEmpty()) {
					System.out.println(errorQueue.dequeue());
				}
				while (!extrasQueue.isEmpty()) {
					System.out.println(extrasQueue.dequeue());
				}
			} else {
				// Both queues have elements, match them
				try {
					String error = errorQueue.peek();
					String extra = extrasQueue.peek();

					if (!matches(error, extra)) {
						System.out.println(errorQueue.dequeue());
					} else {
						errorQueue.dequeue();
						extrasQueue.dequeue();
					}
				} catch (EmptyQueueException e) {
					break;
				}
			}
		}
	}

	private static boolean matches(String error, String extra) {
		return extractTagName(error).equals(extractTagName(extra));
	}

	private static String extractTagName(String tag) {
		return tag.replaceAll("<|>|/", "").split("\\s+")[0]; // Remove <, >, / and attributes
	}
}
