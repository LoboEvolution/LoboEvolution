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
 * Created on Oct 23, 2005
 */
package org.loboevolution.html.parser;

import org.w3c.dom.Element;

/**
 * The Class StopException.
 */
public class StopException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The element. */
	private transient final Element element;

	/**
	 * Instantiates a new stop exception.
	 *
	 * @param element
	 *            the element
	 */
	public StopException(Element element) {
		super();
		this.element = element;
	}

	/**
	 * Gets the element.
	 *
	 * @return the element
	 */
	public Element getElement() {
		return this.element;
	}
}
