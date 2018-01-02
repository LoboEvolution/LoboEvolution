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

package org.loboevolution.w3c.html;

/**
 * The Interface HTMLFontElement.
 */
public interface HTMLFontElement extends HTMLElement {

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	// HTMLFontElement
	public String getColor();

	/**
	 * Sets the color.
	 *
	 * @param color
	 *            the new color
	 */
	public void setColor(String color);

	/**
	 * Gets the face.
	 *
	 * @return the face
	 */
	public String getFace();

	/**
	 * Sets the face.
	 *
	 * @param face
	 *            the new face
	 */
	public void setFace(String face);

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public String getSize();

	/**
	 * Sets the size.
	 *
	 * @param size
	 *            the new size
	 */
	public void setSize(String size);
}
