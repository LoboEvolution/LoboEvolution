/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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

package org.loboevolution.html.dom;

/**
 * The Interface HTMLCanvasElement.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLCanvasElement extends HTMLElement {
	
	/** The fill. */
	int FILL = 0;

	/** The fill rect. */
	int FILL_RECT = 1;

	/** The fill text. */
	int FILL_TEXT = 2;

	/** The circle. */
	int CIRCLE = 3;

	/** The stroke. */
	int STROKE = 4;

	/** The stroke rect. */
	int STROKE_RECT = 5;

	/** The stroke text. */
	int STROKE_TEXT = 6;

	/** The rect. */
	int RECT = 7;

	/** The image. */
	int IMAGE = 8;

	/** The image clip. */
	int IMAGE_CLIP = 9;

	/** The clear rect. */
	int CLEAR_RECT = 10;

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
    int getWidth();

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the new width
	 */
    void setWidth(int width);

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
    int getHeight();

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
    void setHeight(int height);

	/**
	 * To data url.
	 *
	 * @return the string
	 */
    String toDataURL();

	/**
	 * To data url.
	 *
	 * @param type
	 *            the type
	 * @param args
	 *            the args
	 * @return the string
	 */
    String toDataURL(String type, Object... args);

	/**
	 * To blob.
	 *
	 * @param callback
	 *            the callback
	 */
    void toBlob(FileCallback callback);

	/**
	 * To blob.
	 *
	 * @param callback
	 *            the callback
	 * @param type
	 *            the type
	 * @param args
	 *            the args
	 */
    void toBlob(FileCallback callback, String type, Object... args);

	/**
	 * Gets the context.
	 *
	 * @param contextId
	 *            the context id
	 * @return the context
	 */
    CanvasRenderingContext2D getContext(String contextId);
}
