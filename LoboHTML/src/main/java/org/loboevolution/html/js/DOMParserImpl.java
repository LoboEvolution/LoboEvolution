/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.html.js;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.js.xml.XMLDocument;
import org.loboevolution.html.node.Document;
import org.loboevolution.js.DOMParser;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.mozilla.javascript.Context;

/**
 * <p>DOMParser class.</p>
 */
public class DOMParserImpl extends AbstractScriptableDelegate implements DOMParser {

	private final HTMLDocumentImpl document;

	public DOMParserImpl(final HTMLDocumentImpl document){
		this.document = document;
	}


	/** {@inheritDoc} */
	@Override
	public Document parseFromString(final String html, final String type) {
		String xml = html.replace("<", "><").replace(">>", ">");
		xml = xml.substring(1);
		if (!"text/html".equals(type) &&
				!"text/xml".equals(type) &&
				!"application/xml".equals(type) &&
				!"application/xhtml+xml".equals(type) &&
				!"image/svg+xml".equals(type)) {
			throw Context.reportRuntimeError("Invalid 'type' parameter: " + type);
		}
		final XMLDocument document = new XMLDocument();
		document.loadXML(xml + ">");
		return document;
	}

}

