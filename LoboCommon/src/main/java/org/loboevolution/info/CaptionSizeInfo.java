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

import java.io.Serializable;

/**
 * The Class CaptionSizeInfo.
 *
 *
 *
 */
public class CaptionSizeInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -699151415264714503L;

	/** The height. */
	private int height;

	/** The height offset. */
	private int heightOffset;

	/** The width. */
	private int width;

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Gets the height offset.
	 *
	 * @return the height offset
	 */
	public int getHeightOffset() {
		return heightOffset;
	}

	/**
	 * Sets the height offset.
	 *
	 * @param heightOffset
	 *            the new height offset
	 */
	public void setHeightOffset(int heightOffset) {
		this.heightOffset = heightOffset;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the new width
	 */
	public void setWidth(final int width) {
		this.width = width;
	}
}
