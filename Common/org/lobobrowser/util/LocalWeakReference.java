package org.lobobrowser.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class LocalWeakReference extends WeakReference {
	private final Object key;

	public LocalWeakReference(Object key, Object target, ReferenceQueue queue) {
		super(target, queue);
		this.key = key;
	}

	public Object getKey() {
		return key;
	}

	public boolean equals(Object other) {
		Object target1 = this.get();
		Object target2 = other instanceof LocalWeakReference ? ((LocalWeakReference) other)
				.get() : null;
		return Objects.equals(target1, target2);
	}

	public int hashCode() {
		Object target = this.get();
		return target == null ? 0 : target.hashCode();
	}
}