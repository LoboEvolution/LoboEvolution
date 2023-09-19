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
/*
 * Created on Oct 22, 2005
 */
package org.loboevolution.html.parser;

import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * The InputSourceImpl class implements the
 * InputSource interface.
 *
 * Author J. H. S.
 *
 */
public class InputSourceImpl extends InputSource {

	/**
	 * Constructs an InputSourceImpl.
	 *
	 * @param byteStream The input stream where content can be read.
	 * @param uri        The URI that identifies the content.
	 * @param charset    The character set of the input stream.
	 */
	public InputSourceImpl(InputStream byteStream, String uri, Charset charset) {
		super(byteStream);
		setEncoding(charset.displayName());
		setSystemId(uri);
		setPublicId(uri);
	}
	/**
	 * Constructs an InputSourceImpl.
	 *
	 * @param characterStream The Reader where characters can be read.
	 * @param uri             The URI of the document.
	 */
	public InputSourceImpl(Reader characterStream, String uri) {
		super(characterStream);
		setSystemId(uri);
	}
}
