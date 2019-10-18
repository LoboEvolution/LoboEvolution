package org.loboevolution.common;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


public class ArrayUtilities {

	public static boolean isBlank(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isNotBlank(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}

	public static Iterator iterator(Object[] array, int offset, int length) {
		return new ArrayIterator(array, offset, length);
	}
	
	public static <T> void moveItem(int sourceIndex, int targetIndex, List<T> list) {
	    if (sourceIndex <= targetIndex) {
	        Collections.rotate(list.subList(sourceIndex, targetIndex + 1), -1);
	    } else {
	        Collections.rotate(list.subList(targetIndex, sourceIndex + 1), 1);
	    }
	}
	
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

	public static <T> boolean contains(final T[] ts, final T t) {
		for (final T e : ts) {
			if (Objects.equals(e, t)) {
				return true;
			}
		}
		return false;
	}

}
