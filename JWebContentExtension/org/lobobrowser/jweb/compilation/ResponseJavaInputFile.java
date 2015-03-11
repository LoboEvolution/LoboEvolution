/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.jweb.compilation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.util.Urls;


/**
 * The Class ResponseJavaInputFile.
 */
public class ResponseJavaInputFile implements JavaFileObject {
	
	/** The Constant logger. */
	private static final Logger logger = Logger
			.getLogger(ResponseJavaInputFile.class.getName());
	
	/** The response. */
	private final ClientletResponse response;
	
	/** The file name. */
	private final String fileName;
	
	/** The kind. */
	private final Kind kind;
	
	/** The source code. */
	private final String sourceCode;

	/**
	 * Instantiates a new response java input file.
	 *
	 * @param response the response
	 * @param sourceCode the source code
	 * @param fileName the file name
	 * @param kind the kind
	 */
	public ResponseJavaInputFile(final ClientletResponse response,
			String sourceCode, final String fileName, final Kind kind) {
		super();
		this.response = response;
		this.fileName = fileName;
		this.kind = kind;
		this.sourceCode = sourceCode;
	}

	/* (non-Javadoc)
	 * @see javax.tools.JavaFileObject#getAccessLevel()
	 */
	public Modifier getAccessLevel() {
		return Modifier.FINAL;
	}

	/* (non-Javadoc)
	 * @see javax.tools.JavaFileObject#getKind()
	 */
	public Kind getKind() {
		return this.kind;
	}

	/* (non-Javadoc)
	 * @see javax.tools.JavaFileObject#getNestingKind()
	 */
	public NestingKind getNestingKind() {
		return NestingKind.TOP_LEVEL;
	}

	/* (non-Javadoc)
	 * @see javax.tools.JavaFileObject#isNameCompatible(java.lang.String, javax.tools.JavaFileObject.Kind)
	 */
	public boolean isNameCompatible(String simpleName, Kind kind) {
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#delete()
	 */
	public boolean delete() {
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#getCharContent(boolean)
	 */
	public CharSequence getCharContent(boolean ignoreEncodingError)
			throws IOException {
		return this.sourceCode;
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#getLastModified()
	 */
	public long getLastModified() {
		String dateText = this.response.getHeader("Date");
		if (dateText == null) {
			return 0;
		}
		try {
			return Urls.PATTERN_RFC1123.parse(dateText).getTime();
		} catch (java.text.ParseException pe) {
			logger.warning("getLastModified(): Bad date: " + dateText);
			return System.currentTimeMillis();
		}
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#getName()
	 */
	public String getName() {
		return this.fileName;
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#openInputStream()
	 */
	public InputStream openInputStream() throws IOException {
		return response.getInputStream();
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#openOutputStream()
	 */
	public OutputStream openOutputStream() throws IOException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#openReader(boolean)
	 */
	public Reader openReader(boolean arg0) throws IOException {
		return new InputStreamReader(this.openInputStream());
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#openWriter()
	 */
	public Writer openWriter() throws IOException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#toUri()
	 */
	public URI toUri() {
		try {
			return this.response.getResponseURL().toURI();
		} catch (URISyntaxException use) {
			logger.log(Level.WARNING, "toUri(): Bad URI syntax.", use);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return other instanceof ResponseJavaInputFile
				&& ((ResponseJavaInputFile) other).toUri().equals(this.toUri());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "JavaInputFile[name=" + this.getName() + "]";
	}
}
