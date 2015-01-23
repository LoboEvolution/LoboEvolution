package org.lobobrowser.util;

import java.lang.ref.WeakReference;

public class LocalFilter implements ObjectFilter {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.util.ObjectFilter#decode(Object)
	 */
	public Object decode(Object source) {
		WeakReference wf = (WeakReference) source;
		return wf == null ? null : wf.get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.util.ObjectFilter#encode(Object)
	 */
	public Object encode(Object source) {
		throw new UnsupportedOperationException(
				"Read-only collection.");
	}
}