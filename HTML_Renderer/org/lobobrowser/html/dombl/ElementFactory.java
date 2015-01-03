/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.html.dombl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.lobobrowser.html.HtmlMapping;
import org.lobobrowser.html.builder.*;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.w3c.HTMLElement;
import org.w3c.dom.DOMException;

public class ElementFactory {
	private Map<String, Object> builders = new HashMap<String, Object>();

	private ElementFactory() {
		this.builders = HtmlMapping.mappingHtml();
	}

	private static ElementFactory instance = new ElementFactory();

	public static ElementFactory getInstance() {
		return instance;
	}

	public final HTMLElement createElement(HTMLDocumentImpl document,String name) throws DOMException {
		String normalName = name.toUpperCase(Locale.ENGLISH);
		// No need to synchronize; read-only map at this point.
		HTMLElementBuilder builder = (HTMLElementBuilder) this.builders.get(normalName);
		if (builder == null) {
			// TODO: IE would assume name is html text here?
			HTMLElementImpl element = new HTMLElementImpl(name);
			element.setOwnerDocument(document);
			return element;
		} else {
			return builder.create(document, name);
		}
	}
}
