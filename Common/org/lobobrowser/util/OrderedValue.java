package org.lobobrowser.util;

public class OrderedValue implements Comparable<Object>, java.io.Serializable {
	private static final long serialVersionUID = 340227625744215821L;
	private long timestamp;
	private int approximateSize;
	private Object key;
	private Object value;
	
	public OrderedValue(Object key, Object value, int approxSize) {
		this.value = value;
		this.touch();
	}

	final void touch() {
		this.timestamp = System.currentTimeMillis();
	}

	public int compareTo(Object arg0) {
		if (this == arg0) {
			return 0;
		}
		OrderedValue other = (OrderedValue) arg0;
		long diff = this.timestamp - other.timestamp;
		if (diff > 0) {
			return +1;
		} else if (diff < 0) {
			return -1;
		}
		int hc1 = System.identityHashCode(this);
		int hc2 = System.identityHashCode(other);
		if (hc1 == hc2) {
			hc1 = System.identityHashCode(this.value);
			hc2 = System.identityHashCode(other.value);
		}
		return hc1 - hc2;
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public int getApproximateSize() {
		return approximateSize;
	}

	public void setApproximateSize(int approximateSize) {
		this.approximateSize = approximateSize;
	}
}