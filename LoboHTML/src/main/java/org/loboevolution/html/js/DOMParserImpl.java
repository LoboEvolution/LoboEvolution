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

package org.loboevolution.html.js;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.js.xml.XMLDocument;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.js.DOMParser;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.mozilla.javascript.Context;

/**
 * <p>DOMParser class.</p>
 */
public class DOMParserImpl extends AbstractScriptableDelegate implements DOMParser {

	private HTMLDocumentImpl document;

	public DOMParserImpl(HTMLDocumentImpl document){
		this.document = document;
	}


	/** {@inheritDoc} */
	@Override
	public Document parseFromString(String html, String type) {
		Element element = document.createElement("DIV");
		element.setInnerHTML(html);
		if (!"text/html".equals(type) &&
				!"text/xml".equals(type) &&
				!"application/xml".equals(type) &&
				!"application/xhtml+xml".equals(type) &&
				!"image/svg+xml".equals(type)) {
			throw Context.reportRuntimeError("Invalid 'type' parameter: " + type);
		}
		XMLDocument document = new XMLDocument();
		document.loadXML(element.getInnerHTML());
		return document;
	}

}

