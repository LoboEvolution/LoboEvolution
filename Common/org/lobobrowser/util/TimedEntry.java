package org.lobobrowser.util;

public class TimedEntry implements Comparable<Object>, java.io.Serializable {
	private static final long serialVersionUID = 2257845000000000200L;
	private long timestamp = System.currentTimeMillis();
	final String value;

	/**
	 * @param value
	 */
	public TimedEntry(String value) {
		this.value = value;
	}

	public void touch() {
		this.timestamp = System.currentTimeMillis();
	}

	public boolean equals(Object obj) {
		TimedEntry other = (TimedEntry) obj;
		return other.value.equals(this.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object arg0) {
		if (this.equals(arg0)) {
			return 0;
		}
		TimedEntry other = (TimedEntry) arg0;
		long time1 = this.timestamp;
		long time2 = other.timestamp;
		if (time1 > time2) {
			// More recent goes first
			return -1;
		} else if (time2 > time1) {
			return +1;
		} else {
			int diff = System.identityHashCode(this)
					- System.identityHashCode(other);
			if (diff == 0) {
				return +1;
			}
			return diff;
		}
	}
}