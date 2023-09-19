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

package org.loboevolution.html.io;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.parser.XHtmlParser;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;

/**
 * <p>LocalWritableLineReader class.</p>
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
		final XHtmlParser parser = new XHtmlParser(doc.getUcontext(), doc, true);
		final StringReader strReader = new StringReader(text);
		try {
			// This sets up another Javascript scope WindowImpl. Does it matter?
			parser.parse(strReader);
		} catch (final Exception err) {
			doc.warn("Unable to parse written HTML text. BaseURI=[" + doc.getBaseURI() + "].", err);
		}
	}
}
