package org.lobo.common;

import java.util.Collection;
import java.util.Iterator;

public class ArrayUtilities {

	public static boolean isBlank(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isNotBlank(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}

	public static <T> Iterator<T> iterator(final T[] array, final int offset, final int length) {
		return new ArrayIterator<>(array, offset, length);
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
