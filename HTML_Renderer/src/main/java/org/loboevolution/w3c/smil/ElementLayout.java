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

package org.loboevolution.w3c.smil;

import org.w3c.dom.DOMException;

/**
 * This interface is used by SMIL elements root-layout, top-layout and region.
 * 
 */
public interface ElementLayout {
	/**
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getTitle();

	public void setTitle(String title) throws DOMException;

	/**
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getBackgroundColor();

	public void setBackgroundColor(String backgroundColor) throws DOMException;

	/**
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public int getHeight();

	public void setHeight(int height) throws DOMException;

	/**
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public int getWidth();

	public void setWidth(int width) throws DOMException;

}
