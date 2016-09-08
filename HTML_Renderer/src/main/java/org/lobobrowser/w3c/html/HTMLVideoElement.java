/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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

package org.lobobrowser.w3c.html;


/**
 * The Interface HTMLVideoElement.
 */
public interface HTMLVideoElement extends HTMLMediaElement {
	
	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	// HTMLVideoElement
	public int getWidth();

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the new width
	 */
	public void setWidth(int width);

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight();

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(int height);

	/**
	 * Gets the video width.
	 *
	 * @return the video width
	 */
	public int getVideoWidth();

	/**
	 * Gets the video height.
	 *
	 * @return the video height
	 */
	public int getVideoHeight();

	/**
	 * Gets the poster.
	 *
	 * @return the poster
	 */
	public String getPoster();

	/**
	 * Sets the poster.
	 *
	 * @param poster
	 *            the new poster
	 */
	public void setPoster(String poster);
}
