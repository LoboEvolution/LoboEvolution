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
package org.lobobrowser.protocol.data;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandler;

/**
 * The Class Handler.
 */
public class Handler extends URLStreamHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLStreamHandler#openConnection(java.net.URL)
	 */
	@Override
	protected HttpURLConnection openConnection(URL url) throws IOException {
		return new DataURLConnection(url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLStreamHandler#parseURL(java.net.URL, java.lang.String,
	 * int, int)
	 */
	@Override
	protected void parseURL(URL u, String spec, int start, int limit) {
		String sub = spec.substring(start, limit);
		setURL(u, "data", "", -1, "", "", sub, "", "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLStreamHandler#toExternalForm(java.net.URL)
	 */
	@Override
	protected String toExternalForm(URL u) {
		return u.getProtocol() + ":" + u.getPath();
	}

}
