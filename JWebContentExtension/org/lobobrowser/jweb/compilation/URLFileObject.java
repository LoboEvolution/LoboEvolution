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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

import javax.tools.FileObject;

import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.ua.NetworkRequest;


/**
 * The Class URLFileObject.
 */
public class URLFileObject implements FileObject {
	
	/** The context. */
	private final ClientletContext context;
	
	/** The resource url. */
	private final URL resourceURL;
	
	/** The resource path. */
	private final String resourcePath;

	/**
	 * Instantiates a new URL file object.
	 *
	 * @param context the context
	 * @param resourceURL the resource url
	 * @param resourcePath the resource path
	 */
	public URLFileObject(final ClientletContext context, final URL resourceURL,
			final String resourcePath) {
		super();
		this.context = context;
		this.resourceURL = resourceURL;
		this.resourcePath = resourcePath;
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#delete()
	 */
	public boolean delete() {
		// No deletion of URLs.
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#getCharContent(boolean)
	 */
	public CharSequence getCharContent(boolean ignoreEncodingErrors)
			throws IOException {
		InputStream in = this.openInputStream();
		try {
			return org.lobobrowser.util.io.IORoutines.loadAsText(in,
					this.context.getResponse().getCharset(), 4096);
		} finally {
			in.close();
		}
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#getLastModified()
	 */
	public long getLastModified() {
		Date date = this.context.getResponse().getDate();
		return date == null ? 0 : date.getTime();
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#getName()
	 */
	public String getName() {
		return this.resourcePath;
	}

	/** The cached response bytes. */
	private byte[] cachedResponseBytes;

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#openInputStream()
	 */
	public InputStream openInputStream() throws IOException {
		byte[] bytes = this.cachedResponseBytes;
		if (bytes != null) {
			return new java.io.ByteArrayInputStream(bytes);
		}
		NetworkRequest nw = this.context.createNetworkRequest();
		nw.open("GET", this.resourceURL, false);
		nw.send(null);
		if (nw.getReadyState() != NetworkRequest.STATE_COMPLETE) {
			throw new java.io.IOException("Request not complete: "
					+ this.resourceURL + ".");
		}
		int status = nw.getStatus();
		if (status != 0 && status != 200) {
			throw new java.io.IOException("Response status is " + status
					+ " for " + this.resourceURL + ".");
		}
		bytes = nw.getResponseBytes();
		this.cachedResponseBytes = bytes;
		return new java.io.ByteArrayInputStream(bytes);
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#openOutputStream()
	 */
	public OutputStream openOutputStream() throws IOException {
		throw new UnsupportedOperationException(
				"Writing output to URL not supported.");
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#openReader(boolean)
	 */
	public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
		return new java.io.InputStreamReader(this.openInputStream());
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#openWriter()
	 */
	public Writer openWriter() throws IOException {
		throw new UnsupportedOperationException(
				"Writing output to URL not supported.");
	}

	/* (non-Javadoc)
	 * @see javax.tools.FileObject#toUri()
	 */
	public URI toUri() {
		try {
			return this.resourceURL.toURI();
		} catch (URISyntaxException mfu) {
			throw new IllegalStateException(
					"Unexpected error converting URL to URI: "
							+ this.resourceURL + ".", mfu);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		// Relied upon by compiler error messages.
		return this.resourceURL.toExternalForm();
	}
}
