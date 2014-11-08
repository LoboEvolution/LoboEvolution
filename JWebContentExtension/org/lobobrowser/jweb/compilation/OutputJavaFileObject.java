/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

public class OutputJavaFileObject implements JavaFileObject {
	private final String fileName;
	private final Kind kind;
	private final NestingKind nestingKind;
	private final URI uri;
	private final long createTime = System.currentTimeMillis();

	public OutputJavaFileObject(URI uri, final String fileName,
			final Kind kind, final NestingKind nestingKind) {
		this.fileName = fileName;
		this.kind = kind;
		this.nestingKind = nestingKind;
		this.uri = uri;
	}

	public Modifier getAccessLevel() {
		return Modifier.PUBLIC;
	}

	public Kind getKind() {
		return this.kind;
	}

	public NestingKind getNestingKind() {
		return this.nestingKind;
	}

	public boolean isNameCompatible(String simpleName, Kind kind) {
		return PathManager.isNameCompatible(simpleName, kind);
	}

	public boolean delete() {
		return false;
	}

	public CharSequence getCharContent(boolean ignoreEncodingErrors)
			throws IOException {
		throw new java.lang.UnsupportedOperationException();
	}

	public long getLastModified() {
		return this.createTime;
	}

	public String getName() {
		return this.fileName;
	}

	public InputStream openInputStream() throws IOException {
		synchronized (this) {
			ByteArrayOutputStream out = this.out;
			if (out == null) {
				throw new java.io.FileNotFoundException();
			} else {
				return new ByteArrayInputStream(out.toByteArray());
			}
		}
	}

	private ByteArrayOutputStream out;

	public OutputStream openOutputStream() throws IOException {
		synchronized (this) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			this.out = out;
			return out;
		}
	}

	public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
		return new InputStreamReader(this.openInputStream());
	}

	public Writer openWriter() throws IOException {
		return new OutputStreamWriter(this.openOutputStream());
	}

	public URI toUri() {
		return this.uri;
	}

}
