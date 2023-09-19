/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Oct 29, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.htmlunit.cssparser.dom.DOMException;
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

