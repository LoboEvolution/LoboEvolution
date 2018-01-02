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
/*
 * Created on Oct 8, 2005
 */
package org.loboevolution.html.builder;

import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.w3c.html.HTMLDocument;
import org.loboevolution.w3c.html.HTMLElement;

/**
 * The Class HTMLElementBuilder.
 */
public abstract class HTMLElementBuilder {

	/**
	 * Creates the.
	 *
	 * @param document
	 *            the document
	 * @param name
	 *            the name
	 * @return the HTML element
	 */
	public final HTMLElement create(HTMLDocument document, String name) {
		HTMLElementImpl element = this.build(name);
		element.setOwnerDocument(document);
		return element;
	}

	/**
	 * Builds the.
	 *
	 * @param name
	 *            the name
	 * @return the HTML element impl
	 */
	protected abstract HTMLElementImpl build(String name);
}
