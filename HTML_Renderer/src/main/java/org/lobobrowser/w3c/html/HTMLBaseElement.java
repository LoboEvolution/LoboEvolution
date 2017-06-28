/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
 * The Interface HTMLBaseElement.
 */
public interface HTMLBaseElement extends HTMLElement {

	/**
	 * Gets the href.
	 *
	 * @return the href
	 */
	// HTMLBaseElement
	public String getHref();

	/**
	 * Sets the href.
	 *
	 * @param href
	 *            the new href
	 */
	public void setHref(String href);

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	public String getTarget();

	/**
	 * Sets the target.
	 *
	 * @param target
	 *            the new target
	 */
	public void setTarget(String target);
}
