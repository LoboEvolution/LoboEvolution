/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Oct 8, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLHtmlElement;

/**
 * <p>HTMLHtmlElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLHtmlElementImpl extends HTMLElementImpl implements HTMLHtmlElement {
	/**
	 * <p>Constructor for HTMLHtmlElementImpl.</p>
	 */
	public HTMLHtmlElementImpl() {
		super("HTML", true);
	}

	/**
	 * <p>Constructor for HTMLHtmlElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLHtmlElementImpl(String name) {
		super(name, true);
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
}
