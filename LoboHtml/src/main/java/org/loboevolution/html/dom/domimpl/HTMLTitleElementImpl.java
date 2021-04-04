/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.parser.HtmlParser;
import org.w3c.dom.UserDataHandler;
import org.loboevolution.html.node.Document;

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
	public HTMLTitleElementImpl(String name) {
		super(name, true);
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		if (HtmlParser.MODIFYING_KEY.equals(key) && data == Boolean.FALSE) {
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
