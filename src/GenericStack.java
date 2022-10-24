import java.util.ArrayList;
import java.util.EmptyStackException;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericStack.
 *
 * @param <E> the element type
 */

/**
 * @author Your name here
 *
 */
public class GenericStack<E> {

	/** Using an ArrayList as the data structure for the stack */
	private ArrayList<E> stack;
	
	/**
	 * Instantiates a new generic stack.
	 */
	public GenericStack() {
		stack = new ArrayList<E>();
	}
	
	/**
	 * You need to implement the following functions
	 * a) empty() - returns true if the element is empty
	 
	 * b) size() - returns the size of the Stack
	 * c) peek() - returns the object that is at the top of the stack. Must throw
	 *             appropriate exception if attempt to peek empty stack
	 * d) pop() - gets the object at the top of stack, then removes it from 
	 *            the stack and returns the object. Must throw appropriate exception if
	 *            attempt to pop from empty stack.
	 * e) push(o) - adds the object to the top of stack/
	 * 
	 */
	public boolean empty() {
		if (stack.size() ==0) {
			return true;
		}
		else {
			return false;
		}
	}
	public int size() {
		return stack.size();
	}
	public E peek() {
		if (empty()) {
			throw new EmptyStackException();
		}
		else {
			return stack.get(size()-1);
		}
	}
	public E pop() {
		if (empty()) {
			throw new EmptyStackException();
		}
		else {
			E x = stack.remove(size()-1);
			return x;
		}
	}
	public void push(E o) {
		stack.add(o);
	}
	/**
	 * 	 * To string
	 * 	 	 *
	 * 	 	 	 * @return the string
	 * 	 	 	 	 */
	@Override
   	public String toString() {
	   return("stack: "+stack.toString());
	}
}
