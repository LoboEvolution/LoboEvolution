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
 * The Interface HTMLMetaElement.
 */
public interface HTMLMetaElement extends HTMLElement {
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	// HTMLMetaElement
	public String getName();

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name);

	/**
	 * Gets the http equiv.
	 *
	 * @return the http equiv
	 */
	public String getHttpEquiv();

	/**
	 * Sets the http equiv.
	 *
	 * @param httpEquiv
	 *            the new http equiv
	 */
	public void setHttpEquiv(String httpEquiv);

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent();

	/**
	 * Sets the content.
	 *
	 * @param content
	 *            the new content
	 */
	public void setContent(String content);

	/**
	 * Gets the scheme.
	 *
	 * @return the scheme
	 */
	// HTMLMetaElement-22
	public String getScheme();

	/**
	 * Sets the scheme.
	 *
	 * @param scheme
	 *            the new scheme
	 */
	public void setScheme(String scheme);
}
