/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jun 22, 2005
 */
package org.loboevolution.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Map;

/**
 * The Class GenericURLConnection.
 *
 * @author J. H. S.
 */
public class GenericURLConnection extends URLConnection {
	/** The input stream. */
	private final InputStream inputStream;

	/**
	 * Instantiates a new generic url connection.
	 *
	 * @param url
	 *            the url
	 * @param in
	 *            the in
	 */
	public GenericURLConnection(URL url, InputStream in) {
		super(url);
		this.inputStream = in;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#connect()
	 */
	@Override
	public void connect() throws IOException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getInputStream()
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		return this.inputStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getHeaderField(int)
	 */
	@Override
	public String getHeaderField(int n) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getHeaderField(String)
	 */
	@Override
	public String getHeaderField(String name) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getHeaderFieldKey(int)
	 */
	@Override
	public String getHeaderFieldKey(int n) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getHeaderFields()
	 */
	@Override
	public Map getHeaderFields() {
		return Collections.EMPTY_MAP;
	}
}
