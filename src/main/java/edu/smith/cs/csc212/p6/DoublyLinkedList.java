package edu.smith.cs.csc212.p6;

import edu.smith.cs.csc212.p6.errors.BadIndexError;
import edu.smith.cs.csc212.p6.errors.EmptyListError;


public class DoublyLinkedList<T> implements P6List<T> {
	private Node<T> start;
	private Node<T> end;

	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}


	@Override
	public T removeFront() {
		checkNotEmpty();
		
		T before = start.value;
		start = start.after;
		return before;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		
		//If only one element
		T removed = null;
		if (this.size() == 1) {
			removed = start.value;
			this.start = null;
		} else {
			// Two or more
			removed = end.value;
			this.end = end.before;
			end.after = null;
		}
		
		return removed;
		
	}

	@Override
	public T removeIndex(int index) {
		// Empty list
		checkNotEmpty();
		
		
		//One element
		if (this.size() == 1) {
			if (index == 0) {
				return removeFront();
			}
		}else {
			//Remove first node
			if (index == 0) {
				return removeFront();
			}else if (index == this.size()-1) {
				//Remove last node
				return removeBack();
			} else {
				// Remove from the middle
				int at = 0;
				T removed = null;
				for (Node<T> current = start; current != null; current = current.after) {
					if (at == index) {
						removed = current.value;
						current.before.after = current.after;
						current.after.before = current.before;
						current = null;
						return removed;
					}
					at++;
				}
				
			}
			
		}
		
		
		// Index not found
		throw new BadIndexError();
		
	}

	@Override
	public void addFront(T item) {

		Node<T> newNode = new Node<T>(item);
		newNode.after = start;
		newNode.before = null;

		if (start != null) {
			start.before = newNode;
		}

		this.start = newNode;
	}

	@Override
	public void addBack(T item) {
		
		
		if (start == null) {
			addFront(item);
		}
		else {
			Node<T> back = new Node<T>(item);
			back.after = null;
			if (this.size() == 1) {
				back.before = start;
				start.after = back;
			} else {
				back.before = end;
				end.after = back;
			}
			
			this.end = back;
		}


	}

	@Override
	public void addIndex(T item, int index) {
		// TODO implement
		// First and last
		if (index == 0) {
			addFront(item);
		}else if (index == this.size()) {
			addBack(item);
		}else if (index < this.size() && index > 0) {
			//Add to the middle
			Node<T> added = new Node<T>(item);
			int at = 0;
			for (Node<T> current = start; current !=null; current=current.after) {
				if (at == index) {
					added.after = current;
					added.before = current.before;
					current.before.after = added;
					current.before = added;
				}
				at++;
			}
			
		}else {
			// Bad index ( <0 or >size )
			throw new BadIndexError();
		}
	}

	@Override
	public T getFront() {
		return start.value;
	}

	@Override
	public T getBack() {
		return end.value;
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		// From SList.java in the test lab
		int at = 0;
		for (Node<T> current = start; current != null; current = current.after) {
			if (at == index) {
				return current.value;
			}
			at++;
		}
		// We couldn't find it, throw an exception!
		throw new BadIndexError();
	}

	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		if (this.size() == 0) {
			return true;
		}else {
			return false;
		}
	}

	private void checkNotEmpty() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
	}

	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of DoublyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}
}
