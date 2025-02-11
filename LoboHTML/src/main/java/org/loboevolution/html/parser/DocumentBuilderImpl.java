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
/*
 * Created on Oct 15, 2005
 */
package org.loboevolution.html.parser;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Urls;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.io.WritableLineReader;
import org.loboevolution.html.node.Document;
import org.loboevolution.http.UserAgentContext;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.URI;
import java.net.URLConnection;

/**
 * The DocumentBuilderImpl class is an HTML DOM parser that
 * implements the standard W3C DocumentBuilder interface.
 */
@Slf4j
public class DocumentBuilderImpl {
	private final UserAgentContext bcontext;
	private final HtmlRendererContext rcontext;
	private final HtmlRendererConfig config;

	/**
	 * Constructs a DocumentBuilderImpl. This constructor should be
	 * used when rendering is expected.
	 *
	 * @param ucontext An instance of {@link org.loboevolution.http.UserAgentContext}
	 * @param rcontext An instance of {@link HtmlRendererContext}
	 * @param config a {@link HtmlRendererConfig} object.
	 */
	public DocumentBuilderImpl(final UserAgentContext ucontext, final HtmlRendererContext rcontext, final HtmlRendererConfig config) {
		this.rcontext = rcontext;
		this.bcontext = ucontext;
		this.config = config;
	}

	/**
	 * Creates a document without parsing the input provided, so the document object
	 * can be used for incremental rendering.
	 *
	 * @param is The input source, which may be an instance of
	 *           {@link org.loboevolution.html.parser.InputSourceImpl}. The input
	 *           source must provide either an input stream or a reader.
	 * @see HTMLDocumentImpl#load()
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 * @throws java.io.IOException if any.
	 */
	public Document createDocument(final InputSource is) throws Exception {
		final String uri = is.getSystemId();
		if (uri == null) {
			log.warn("parse(): InputSource has no SystemId (URI); document item URLs will not be resolvable.");
		}

		try (final WritableLineReader wis = writableLineReader(is, uri)) {
			final HTMLDocumentImpl document = new HTMLDocumentImpl(this.bcontext, this.rcontext, this.config, wis, uri);
			document.load();
			return document;
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
    }

	/**
	 * {@inheritDoc}
	 *
	 * Parses an HTML document. Note that this method will read the entire input
	 * source before returning a Document instance.
	 *
	 * @param is a {@link org.xml.sax.InputSource} object.
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 * @throws java.lang.Exception if any.
	 */
	public Document parse(final InputSource is) throws Exception {
		return createDocument(is);
	}

	private WritableLineReader writableLineReader(final InputSource is, final String uri) throws Exception {
		final String encoding = is.getEncoding();
		String charset = encoding;
		if (charset == null) {
			charset = "US-ASCII";
		}

		final WritableLineReader wis;
		final Reader reader = is.getCharacterStream();
		if (reader != null) {
			wis = new WritableLineReader(reader);
		} else {
			InputStream in = is.getByteStream();
			if (in != null) {
				wis = new WritableLineReader(new InputStreamReader(in, charset));
			} else if (uri != null) {
				final URLConnection connection = new URI(uri).toURL().openConnection();
				in = connection.getInputStream();
				if (encoding == null) {
					charset = Urls.getCharset(connection);
				}
				wis = new WritableLineReader(new InputStreamReader(in, charset));
			} else {
				throw new IllegalArgumentException(
						"The InputSource must have either a reader, an input stream or a URI.");
			}
		}

		return wis;
	}
}
