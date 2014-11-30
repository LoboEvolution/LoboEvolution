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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.tools.FileObject;

public class JarFileObject implements FileObject {
	private final JarEntry jarEntry;
	private final JarFile jarFile;
	private final URI uri;

	public JarFileObject(JarFile jarFile, JarEntry file, URI uri) {
		this.jarFile = jarFile;
		this.jarEntry = file;
		this.uri = uri;
	}

	public boolean delete() {
		throw new java.lang.UnsupportedOperationException(
				"No deletion in JAR file.");
	}

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

	public long getLastModified() {
		return this.jarEntry.getTime();
	}

	public String getName() {
		return this.jarEntry.getName();
	}

	public InputStream openInputStream() throws IOException {
		return this.jarFile.getInputStream(this.jarEntry);
	}

	public OutputStream openOutputStream() throws IOException {
		throw new java.lang.UnsupportedOperationException(
				"No writing into JAR files.");
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
