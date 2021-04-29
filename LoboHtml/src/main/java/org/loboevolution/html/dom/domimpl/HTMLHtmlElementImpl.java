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
/*
 * Created on Oct 8, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLHtmlElement;

/**
 * <p>HTMLHtmlElementImpl class.</p>
 *
 *
 *
 */
public class HTMLHtmlElementImpl extends HTMLElementImpl implements HTMLHtmlElement {
	/**
	 * <p>Constructor for HTMLHtmlElementImpl.</p>
	 */
	public HTMLHtmlElementImpl() {
		super("HTML");
	}

	/**
	 * <p>Constructor for HTMLHtmlElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLHtmlElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public String getVersion() {
		return getAttribute("version");
	}

	/** {@inheritDoc} */
	@Override
	public void setVersion(String version) {
		setAttribute("version", version);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLHtmlElement]";
	}
}
