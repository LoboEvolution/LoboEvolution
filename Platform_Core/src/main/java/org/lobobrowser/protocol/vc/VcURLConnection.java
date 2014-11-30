/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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
/*
 * Created on Mar 14, 2005
 */
package org.lobobrowser.protocol.vc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.lobobrowser.context.VolatileContentImpl;

/**
 * @author J. H. S.
 */
public class VcURLConnection extends URLConnection {
	private final VolatileContentImpl vc;

	public VcURLConnection(URL url) {
		super(url);
		String file = url.getPath();
		try {
			long id = Long.parseLong(file.trim());
			this.vc = VolatileContentImpl.getVolatileContent(id);
			if (this.vc == null) {
				throw new IllegalArgumentException(
						"Content either invalid or no longer available");
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Bad path: " + file);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#connect()
	 */
	public void connect() throws IOException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getContentLength()
	 */
	public int getContentLength() {
		return this.vc.getBytes().length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getContentType()
	 */
	public String getContentType() {
		return this.vc.getContentType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(this.vc.getBytes());
	}
}
