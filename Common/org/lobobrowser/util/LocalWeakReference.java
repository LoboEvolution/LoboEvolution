package org.lobobrowser.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;


/**
 * The Class LocalWeakReference.
 */
public class LocalWeakReference extends WeakReference {
	
	/** The key. */
	private final Object key;

	/**
	 * Instantiates a new local weak reference.
	 *
	 * @param key the key
	 * @param target the target
	 * @param queue the queue
	 */
	public LocalWeakReference(Object key, Object target, ReferenceQueue queue) {
		super(target, queue);
		this.key = key;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public Object getKey() {
		return key;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		Object target1 = this.get();
		Object target2 = other instanceof LocalWeakReference ? ((LocalWeakReference) other)
				.get() : null;
		return Objects.equals(target1, target2);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		Object target = this.get();
		return target == null ? 0 : target.hashCode();
	}
}