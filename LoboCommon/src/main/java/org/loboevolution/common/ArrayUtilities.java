package org.loboevolution.common;

import java.awt.Color;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * <p>ArrayUtilities class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ArrayUtilities {

	/**
	 * <p>isBlank.</p>
	 *
	 * @param collection a {@link java.util.Collection} object.
	 * @return a boolean.
	 */
	public static boolean isBlank(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	/**
	 * <p>isNotBlank.</p>
	 *
	 * @param collection a {@link java.util.Collection} object.
	 * @return a boolean.
	 */
	public static boolean isNotBlank(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}

	/**
	 * <p>iterator.</p>
	 *
	 * @param array an array of {@link java.lang.Object} objects.
	 * @param offset a int.
	 * @param length a int.
	 * @return a {@link java.util.Iterator} object.
	 */
	public static Iterator iterator(Object[] array, int offset, int length) {
		return new ArrayIterator(array, offset, length);
	}
	
	/**
	 * <p>moveItem.</p>
	 *
	 * @param sourceIndex a int.
	 * @param targetIndex a int.
	 * @param list a {@link java.util.List} object.
	 * @param <T> a T object.
	 */
	public static <T> void moveItem(int sourceIndex, int targetIndex, List<T> list) {
	    if (sourceIndex <= targetIndex) {
	        Collections.rotate(list.subList(sourceIndex, targetIndex + 1), -1);
	    } else {
	        Collections.rotate(list.subList(targetIndex, sourceIndex + 1), 1);
	    }
	}
	
	/**
	 * <p>singletonIterator.</p>
	 *
	 * @param item a {@link java.lang.Object} object.
	 * @return a {@link java.util.Iterator} object.
	 */
	public static Iterator singletonIterator(final Object item) {
		return new Iterator() {
			private boolean gotItem = false;

			@Override
			public boolean hasNext() {
				return !this.gotItem;
			}

			@Override
			public Object next() {
				if (this.gotItem) {
					throw new NoSuchElementException();
				}
				this.gotItem = true;
				return item;
			}

			@Override
			public void remove() {
				if (!this.gotItem) {
					this.gotItem = true;
				} else {
					throw new NoSuchElementException();
				}
			}
		};
	}
	

	/**
	 * <p>removeColor.</p>
	 *
	 * @param arr an array of {@link java.awt.Color} objects.
	 * @param index a int.
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public static Color[] removeColor(Color[] arr, int index) {
		if (arr == null || index < 0 || index >= arr.length) {
			return arr;
		}
		Color[] anotherArray = new Color[arr.length - 1];
		System.arraycopy(arr, 0, anotherArray, 0, index);
		System.arraycopy(arr, index + 1, anotherArray, index, arr.length - index - 1);
		return anotherArray;
	}
	
	/**
	 * <p>contains.</p>
	 *
	 * @param ts an array of T[] objects.
	 * @param t a T object.
	 * @param <T> a T object.
	 * @return a boolean.
	 */
	public static <T> boolean contains(final T[] ts, final T t) {
		for (final T e : ts) {
			if (Objects.equals(e, t)) {
				return true;
			}
		}
		return false;
	}
	
	private static class ArrayIterator<T> implements Iterator<T> {
		private final T[] array;
		private final int top;
		private int offset;

		public ArrayIterator(final T[] array, final int offset, final int length) {
			this.array = array;
			this.offset = offset;
			this.top = offset + length;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return this.offset < this.top;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.Iterator#next()
		 */
		public T next() {
			return this.array[this.offset++];
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
