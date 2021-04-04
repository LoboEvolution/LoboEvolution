/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.info;

/**
 * <p>SizeInfo class.</p>
 *
 *
 *
 */
public class SizeInfo {
	
	private int actualSize;
	
	private int layoutSize;
	
	private int minSize;
	
	private int offset;
	
	private Object htmlLength;

	/**
	 * <p>Getter for the field actualSize.</p>
	 *
	 * @return the actualSize
	 */
	public int getActualSize() {
		return actualSize;
	}

	/**
	 * <p>Getter for the field layoutSize.</p>
	 *
	 * @return the layoutSize
	 */
	public int getLayoutSize() {
		return layoutSize;
	}

	/**
	 * <p>Getter for the field minSize.</p>
	 *
	 * @return the minSize
	 */
	public int getMinSize() {
		return minSize;
	}

	/**
	 * <p>Getter for the field offset.</p>
	 *
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * <p>Getter for the field htmlLength.</p>
	 *
	 * @return the htmlLength
	 */
	public Object getHtmlLength() {
		return htmlLength;
	}

	/**
	 * <p>Setter for the field actualSize.</p>
	 *
	 * @param actualSize the actualSize to set
	 */
	public void setActualSize(int actualSize) {
		this.actualSize = actualSize;
	}

	/**
	 * <p>Setter for the field layoutSize.</p>
	 *
	 * @param layoutSize the layoutSize to set
	 */
	public void setLayoutSize(int layoutSize) {
		this.layoutSize = layoutSize;
	}

	/**
	 * <p>Setter for the field minSize.</p>
	 *
	 * @param minSize the minSize to set
	 */
	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	/**
	 * <p>Setter for the field offset.</p>
	 *
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * <p>Setter for the field htmlLength.</p>
	 *
	 * @param htmlLength the htmlLength to set
	 */
	public void setHtmlLength(Object htmlLength) {
		this.htmlLength = htmlLength;
	}
	
}
