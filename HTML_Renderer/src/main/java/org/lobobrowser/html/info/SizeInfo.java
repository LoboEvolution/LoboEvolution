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
package org.lobobrowser.html.info;

import java.io.Serializable;

import org.lobobrowser.html.style.HtmlLength;

/**
 * The Class SizeInfo.
 */
public class SizeInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3550617662873087308L;

	/** The html length. */
	private HtmlLength htmlLength;

	/** The actual size. */
	private int actualSize;

	/** The layout size. */
	private int layoutSize;

	/** The min size. */
	private int minSize;

	/** The offset. */
	private int offset;

	/**
	 * Gets the html length.
	 *
	 * @return the html length
	 */
	public HtmlLength getHtmlLength() {
		return htmlLength;
	}

	/**
	 * Sets the html length.
	 *
	 * @param htmlLength
	 *            the new html length
	 */
	public void setHtmlLength(HtmlLength htmlLength) {
		this.htmlLength = htmlLength;
	}

	/**
	 * Gets the actual size.
	 *
	 * @return the actual size
	 */
	public int getActualSize() {
		return actualSize;
	}

	/**
	 * Sets the actual size.
	 *
	 * @param actualSize
	 *            the new actual size
	 */
	public void setActualSize(int actualSize) {
		this.actualSize = actualSize;
	}

	/**
	 * Gets the layout size.
	 *
	 * @return the layout size
	 */
	public int getLayoutSize() {
		return layoutSize;
	}

	/**
	 * Sets the layout size.
	 *
	 * @param layoutSize
	 *            the new layout size
	 */
	public void setLayoutSize(int layoutSize) {
		this.layoutSize = layoutSize;
	}

	/**
	 * Gets the min size.
	 *
	 * @return the min size
	 */
	public int getMinSize() {
		return minSize;
	}

	/**
	 * Sets the min size.
	 *
	 * @param minSize
	 *            the new min size
	 */
	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	/**
	 * Gets the offset.
	 *
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Sets the offset.
	 *
	 * @param offset
	 *            the new offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
}
