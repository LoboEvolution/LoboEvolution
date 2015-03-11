/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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


/**
 * The Class LocalJavaFileObject.
 */
public class LocalJavaFileObject implements JavaFileObject {
	
	/** The file. */
	private final File file;
	
	/** The full name. */
	private final String fullName;
	
	/** The kind. */
	private final Kind kind;
	
	/** The nesting kind. */
	private final NestingKind nestingKind;

	/**
	 * Instantiates a new local java file object.
	 *
	 * @param file the file
	 * @param fullName the full name
	 * @param kind the kind
	 * @param nestingKind the nesting kind
	 */
	public LocalJavaFileObject(File file, String fullName, Kind kind,
			NestingKind nestingKind) {
		this.file = file;
		this.kind = kind;
		this.fullName = fullName;
		this.nestingKind = nestingKind;
	}

	/* (non-Javadoc)
	 * @see javax.tools.JavaFileObject#getAccessLevel()
	 */
	public Modifier getAccessLevel() {
		return Modifier.PUBLIC;
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
		return this.nestingKind;
	}

	/* (non-Javadoc)
	 * @see javax.tools.JavaFileObject#isNameCompatible(java.lang.String, javax.tools.JavaFileObject.Kind)
	 */
	public boolean isNameCompatible(String simpleName, Kind kind) {
		if (kind == Kind.CLASS) {
			return simpleName.toLowerCase().endsWith(".class");
		} else if (kind == Kind.SOURCE) {
			String sntl = simpleName.toLowerCase();
			return sntl.endsWith(".java") || sntl.endsWith(".fx");
		} else {
			throw new UnsupportedOperationException("simpleName="
					+ simpleName + ",kind=" + kind);
		}
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#delete()
	 */
	public boolean delete() {
		return this.file.delete();
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#getCharContent(boolean)
	 */
	public CharSequence getCharContent(boolean ignoreEncodingErrors)
			throws IOException {
		InputStream in = this.openInputStream();
		try {
			return org.lobobrowser.util.io.IORoutines.loadAsText(in,
					System.getProperty("file.encoding"), 4096);
		} finally {
			in.close();
		}
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#getLastModified()
	 */
	public long getLastModified() {
		return this.file.lastModified();
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#getName()
	 */
	public String getName() {
		return this.fullName;
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#openInputStream()
	 */
	public InputStream openInputStream() throws IOException {
		return new FileInputStream(this.file);
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#openOutputStream()
	 */
	public OutputStream openOutputStream() throws IOException {
		return new FileOutputStream(this.file);
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#openReader(boolean)
	 */
	public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
		return new InputStreamReader(this.openInputStream());
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#openWriter()
	 */
	public Writer openWriter() throws IOException {
		return new OutputStreamWriter(this.openOutputStream());
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#toUri()
	 */
	public URI toUri() {
		return this.file.toURI();
	}
}
