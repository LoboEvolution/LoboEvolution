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
package org.loboevolution.store;

import java.util.List;

/**
 * The Class CacheInfo.
 */
public class CacheInfo {

	/** The approximate size. */
	private long approximateSize;

	/** The num entries. */
	private int numEntries;

	/** The entry info list. */
	private List entryInfoList;

	/**
	 * Instantiates a new cache info.
	 *
	 * @param approximateSize
	 *            the approximate size
	 * @param numEntries
	 *            the num entries
	 * @param entryInfoList
	 *            the entry info list
	 */
	public CacheInfo(final long approximateSize, final int numEntries, final List entryInfoList) {
		super();
		this.approximateSize = approximateSize;
		this.numEntries = numEntries;
		this.entryInfoList = entryInfoList;
	}

	/**
	 * Gets the approximate size.
	 *
	 * @return the approximate size
	 */
	public long getApproximateSize() {
		return approximateSize;
	}

	/**
	 * Sets the approximate size.
	 *
	 * @param approximateSize
	 *            the new approximate size
	 */
	public void setApproximateSize(long approximateSize) {
		this.approximateSize = approximateSize;
	}

	/**
	 * Gets the num entries.
	 *
	 * @return the num entries
	 */
	public int getNumEntries() {
		return numEntries;
	}

	/**
	 * Sets the num entries.
	 *
	 * @param numEntries
	 *            the new num entries
	 */
	public void setNumEntries(int numEntries) {
		this.numEntries = numEntries;
	}

	/**
	 * Gets the entry info list.
	 *
	 * @return the entry info list
	 */
	public List getEntryInfoList() {
		return entryInfoList;
	}

	/**
	 * Sets the entry info list.
	 *
	 * @param entryInfoList
	 *            the new entry info list
	 */
	public void setEntryInfoList(List entryInfoList) {
		this.entryInfoList = entryInfoList;
	}
}
