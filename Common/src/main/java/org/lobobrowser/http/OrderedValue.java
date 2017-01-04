/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.http;

/**
 * The Class OrderedValue.
 */
public class OrderedValue implements Comparable<Object>, java.io.Serializable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 340227625744215821L;
    /** The timestamp. */
    private long timestamp;
    /** The approximate size. */
    private int approximateSize;
    /** The key. */
    private Object key;
    /** The value. */
    private Object value;
    
    /**
     * Instantiates a new ordered value.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     * @param approxSize
     *            the approx size
     */
    public OrderedValue(Object key, Object value, int approxSize) {
        this.value = value;
        this.touch();
    }
    
    /**
     * Touch.
     */
    final void touch() {
        this.timestamp = System.currentTimeMillis();
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
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
    
    /** Gets the timestamp.
	 *
	 * @return the timestamp
	 */
    public long getTimestamp() {
        return timestamp;
    }
    
    /** Sets the timestamp.
	 *
	 * @param timestamp
	 *            the new timestamp
	 */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    /** Gets the value.
	 *
	 * @return the value
	 */
    public Object getValue() {
        return value;
    }
    
    /** Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
    public void setValue(Object value) {
        this.value = value;
    }
    
    /** Gets the key.
	 *
	 * @return the key
	 */
    public Object getKey() {
        return key;
    }
    
    /** Sets the key.
	 *
	 * @param key
	 *            the new key
	 */
    public void setKey(Object key) {
        this.key = key;
    }
    
    /** Gets the approximate size.
	 *
	 * @return the approximate size
	 */
    public int getApproximateSize() {
        return approximateSize;
    }
    
    /** Sets the approximate size.
	 *
	 * @param approximateSize
	 *            the new approximate size
	 */
    public void setApproximateSize(int approximateSize) {
        this.approximateSize = approximateSize;
    }
}
