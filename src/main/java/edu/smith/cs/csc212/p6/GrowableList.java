package edu.smith.cs.csc212.p6;

import edu.smith.cs.csc212.p6.errors.EmptyListError;

public class GrowableList<T> implements P6List<T> {
	public static final int START_SIZE = 32;
	private Object[] array;
	private int fill;
	
	public GrowableList() {
		this.array = new Object[START_SIZE];
		this.fill = 0;
	}

	@Override
	public T removeFront() {
		return removeIndex(0);
	}

	@Override
	public T removeBack() {
		if (this.size()==-0) {
			throw new EmptyListError();
		}
		T value = this.getIndex(fill-1);
		this.array[fill-1] = null;
		fill--;
		return value;
	}

	@Override
	public T removeIndex(int index) {
		if(this.size()==0) {
			throw new EmptyListError();
		}
		T removed = this.getIndex(index);
		fill--;
		for (int i =index; i<fill; i++) {
			this.array[i] = this.array[i+1];
		}
		this.array[fill] = null;
		return removed;
	}

	@Override
	public void addFront(T item) {
		addIndex(item,0);
	}

	@Override
	public void addBack(T item) {
		
		if (fill >= this.array.length) { 
			addIndex(item,fill-1);
		}
		this.array[fill++] = item;
	}

	@Override
	public void addIndex(T item, int index) {
		if (fill < this.array.length) {
			for (int i = fill-1; i > -1; i--) {
				if (i>=index) {
					array[i+1] = array[i];
				}
			}
			this.array[index] = item;
			fill++;
		} else {
			int newSize = fill * 2;
			Object[] newArray = new Object[newSize];
			for (int i = 0; i< this.array.length; i++) {
				if (i < index) {
					newArray[i] = this.array[i];
				}else if(i >= index){
					newArray[i+1]= this.array[i];
				}
			}
			newArray[index] = item;
			this.array = newArray;
			fill++;
		}
	}
	
	@Override
	public T getFront() {
		return this.getIndex(0);
	}

	@Override
	public T getBack() {
		return this.getIndex(this.fill-1);
	}

	/**
	 * Do not allow unchecked warnings in any other method.
	 * Keep the "guessing" the objects are actually a T here.
	 * Do that by calling this method instead of using the array directly.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getIndex(int index) {
		return (T) this.array[index];
	}

	@Override
	public int size() {
		return fill;
	}

	@Override
	public boolean isEmpty() {
		return fill == 0;
	}


}
