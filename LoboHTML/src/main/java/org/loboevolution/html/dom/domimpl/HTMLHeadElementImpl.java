/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
/*
 * Created on Oct 29, 2005
 */
package org.loboevolution.html.dom.domimpl;

import com.gargoylesoftware.css.dom.DOMException;
import org.loboevolution.html.dom.HTMLHeadElement;
import org.loboevolution.html.dom.HTMLHtmlElement;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Node;

/**
 * <p>HTMLHeadElementImpl class.</p>
 */
public class HTMLHeadElementImpl extends HTMLElementImpl implements HTMLHeadElement {

	/**
	 * <p>Constructor for HTMLHeadElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLHeadElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public String getProfile() {
		return this.getAttribute("profile");
	}

	/** {@inheritDoc} */
	@Override
	public void setProfile(String profile) {
		this.setAttribute("profile", profile);
	}

	@Override
	public Node appendChild(Node newChild) {

		if (newChild instanceof HTMLHtmlElement) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot append html");
		}

		if (newChild instanceof DocumentType) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot append doc type");
		}

		if (newChild instanceof Document) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot append doc");
		}
		return super.appendChild(newChild);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLHeadElement]";
	}
}

