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
 * Created on Oct 22, 2005
 */
package org.loboevolution.html.parser;

import java.io.InputStream;
import java.io.Reader;

import org.xml.sax.InputSource;

/**
 * The InputSourceImpl class implements the
 * InputSource interface.
 *
 * @author J. H. S.
 * @version $Id: $Id
 */
public class InputSourceImpl extends InputSource {



	/**
	 * Constructs an InputSourceImpl.
	 *
	 * @param byteStream The input stream where content can be read.
	 * @param uri        The URI that identifies the content.
	 * @param charset    The character set of the input stream.
	 */
	public InputSourceImpl(InputStream byteStream, String uri, String charset) {
		super(byteStream);
		setEncoding(charset);
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

	/**
	 * Constructs an InputSourceImpl.
	 * <p>
	 * It is valid to use this constructor, but it is generally recommended that
	 * callers use one of the constructors that take a reader or an input stream
	 * instead.
	 *
	 * @param uri The URI (or systemID) of the document.
	 */
	public InputSourceImpl(String uri) {
		super(uri);
	}
}
