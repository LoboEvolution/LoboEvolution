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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.util.Urls;

public class ResponseJavaInputFile implements JavaFileObject {
	private static final Logger logger = Logger
			.getLogger(ResponseJavaInputFile.class.getName());
	private final ClientletResponse response;
	private final String fileName;
	private final Kind kind;
	private final String sourceCode;

	public ResponseJavaInputFile(final ClientletResponse response,
			String sourceCode, final String fileName, final Kind kind) {
		super();
		this.response = response;
		this.fileName = fileName;
		this.kind = kind;
		this.sourceCode = sourceCode;
	}

	public Modifier getAccessLevel() {
		return Modifier.FINAL;
	}

	public Kind getKind() {
		return this.kind;
	}

	public NestingKind getNestingKind() {
		return NestingKind.TOP_LEVEL;
	}

	public boolean isNameCompatible(String simpleName, Kind kind) {
		return true;
	}

	public boolean delete() {
		return false;
	}

	public CharSequence getCharContent(boolean ignoreEncodingError)
			throws IOException {
		return this.sourceCode;
	}

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

	public String getName() {
		return this.fileName;
	}

	public InputStream openInputStream() throws IOException {
		return response.getInputStream();
	}

	public OutputStream openOutputStream() throws IOException {
		throw new java.lang.UnsupportedOperationException();
	}

	public Reader openReader(boolean arg0) throws IOException {
		return new InputStreamReader(this.openInputStream());
	}

	public Writer openWriter() throws IOException {
		throw new java.lang.UnsupportedOperationException();
	}

	public URI toUri() {
		try {
			return this.response.getResponseURL().toURI();
		} catch (java.net.URISyntaxException use) {
			logger.log(Level.WARNING, "toUri(): Bad URI syntax.", use);
			return null;
		}
	}

	public boolean equals(Object other) {
		return other instanceof ResponseJavaInputFile
				&& ((ResponseJavaInputFile) other).toUri().equals(this.toUri());
	}

	public String toString() {
		return "JavaInputFile[name=" + this.getName() + "]";
	}
}
