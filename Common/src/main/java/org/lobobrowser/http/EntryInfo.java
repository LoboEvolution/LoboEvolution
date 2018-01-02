/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
 * The Class EntryInfo.
 */
public class EntryInfo {
	/** The value class. */
	private Class<?> valueClass;
	/** The approximate size. */
	private int approximateSize;

	/**
	 * Instantiates a new entry info.
	 *
	 * @param valueClass
	 *            the value class
	 * @param approximateSize
	 *            the approximate size
	 */
	public EntryInfo(final Class<?> valueClass, final int approximateSize) {
		super();
		this.valueClass = valueClass;
		this.approximateSize = approximateSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		Class<?> vc = this.valueClass;
		String vcName = vc == null ? "<none>" : vc.getName();
		return "[class=" + vcName + ",approx-size=" + this.approximateSize + "]";
	}

	/**
	 * Gets the value class.
	 *
	 * @return the value class
	 */
	public Class<?> getValueClass() {
		return valueClass;
	}

	/**
	 * Sets the value class.
	 *
	 * @param valueClass
	 *            the new value class
	 */
	public void setValueClass(Class<?> valueClass) {
		this.valueClass = valueClass;
	}

	/**
	 * Gets the approximate size.
	 *
	 * @return the approximate size
	 */
	public int getApproximateSize() {
		return approximateSize;
	}

	/**
	 * Sets the approximate size.
	 *
	 * @param approximateSize
	 *            the new approximate size
	 */
	public void setApproximateSize(int approximateSize) {
		this.approximateSize = approximateSize;
	}
}
