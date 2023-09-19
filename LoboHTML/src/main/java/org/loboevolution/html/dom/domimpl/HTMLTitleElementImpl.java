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
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.node.Document;
import org.loboevolution.html.parser.XHtmlParser;
import org.loboevolution.html.dom.UserDataHandler;

/**
 * <p>HTMLTitleElementImpl class.</p>
 *
 *
 *
 */
public class HTMLTitleElementImpl extends HTMLElementImpl {
	/**
	 * <p>Constructor for HTMLTitleElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLTitleElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		if (XHtmlParser.MODIFYING_KEY.equals(key) && data == Boolean.FALSE) {
			final Document document = this.document;
			if (document instanceof HTMLDocumentImpl) {
				final String textContent = getTextContent();
				final String title = textContent == null ? null : textContent.trim();
				((HTMLDocumentImpl) document).setTitle(title);
			}
		}
		return super.setUserData(key, data, handler);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLTitleElement]";
	}

}
