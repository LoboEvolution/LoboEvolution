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

package org.loboevolution.html.io;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.parser.HtmlParser;
import org.xml.sax.ErrorHandler;

/**
 * <p>LocalWritableLineReader class.</p>
 *
 *
 *
 */
public class LocalWritableLineReader extends WritableLineReader {
	
	private HTMLDocumentImpl doc;
	
	/**
	 * <p>Constructor for LocalWritableLineReader.</p>
	 *
	 * @param reader a {@link java.io.LineNumberReader} object.
	 * @param doc a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 */
	public LocalWritableLineReader(HTMLDocumentImpl doc, LineNumberReader reader) {
		super(reader);
		this.doc = doc;
	}

	/**
	 * <p>Constructor for LocalWritableLineReader.</p>
	 *
	 * @param reader a {@link java.io.Reader} object.
	 */
	public LocalWritableLineReader(Reader reader) {
		super(reader);
	}

	/** {@inheritDoc} */
	@Override
	public void write(String text) throws IOException {
		super.write(text);
		if ("".equals(text)) {
			openBufferChanged(text);
		}
	}
	
	private void openBufferChanged(String text) {
		// Assumed to execute in a lock
		// Assumed that text is not broken up HTML.
		final ErrorHandler errorHandler = new LocalErrorHandler();
		final HtmlParser parser = new HtmlParser(doc.getUcontext(), doc, errorHandler, true);
		final StringReader strReader = new StringReader(text);
		try {
			// This sets up another Javascript scope WindowImpl. Does it matter?
			parser.parse(strReader);
		} catch (final Exception err) {
			doc.warn("Unable to parse written HTML text. BaseURI=[" + doc.getBaseURI() + "].", err);
		}
	}
}
