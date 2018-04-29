/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.ext.history;

import java.io.Serializable;
import java.net.URL;

/**
 * The Class TimedEntry.
 * @param <T>
 */
public class TimedEntry<T> implements Comparable<Object>, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2257845000000000200L;

	/** The timestamp. */
	private long timestamp = System.currentTimeMillis();

	/** The url. */
	private final URL url;

	/** The value. */
	private final String value;

	/** The item info. */
	private transient T itemInfo;

	/**
	 * Instantiates a new timed entry.
	 *
	 * @param url
	 *            the url
	 * @param textValue
	 *            the text value
	 * @param itemInfo
	 *            the item info
	 */
	public TimedEntry(URL url, String textValue, T itemInfo) {
		this.itemInfo = itemInfo;
		this.value = textValue;
		this.url = url;
	}

	/**
	 * Touch.
	 */
	public void touch() {
		this.setTimestamp(System.currentTimeMillis());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		TimedEntry<?> other = (TimedEntry<?>) obj;
		return other.getValue().equals(this.getValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	@Override
	public int compareTo(Object arg0) {
		if (this.equals(arg0)) {
			return 0;
		}
		TimedEntry<?> other = (TimedEntry<?>) arg0;
		long time1 = this.getTimestamp();
		long time2 = other.getTimestamp();
		if (time1 > time2) {
			// More recent goes first
			return -1;
		} else if (time2 > time1) {
			return +1;
		} else {
			return this.getValue().compareTo(other.getValue());
		}
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @return the itemInfo
	 */
	public T getItemInfo() {
		return itemInfo;
	}

	/**
	 * @param itemInfo the itemInfo to set
	 */
	public void setItemInfo(T itemInfo) {
		this.itemInfo = itemInfo;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
