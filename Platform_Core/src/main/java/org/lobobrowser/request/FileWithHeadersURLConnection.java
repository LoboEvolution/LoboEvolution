/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jun 12, 2005
 */
package org.lobobrowser.request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lobobrowser.http.NameValuePair;
import org.lobobrowser.util.io.IORoutines;

/**
 * The Class FileWithHeadersURLConnection.
 *
 * @author J. H. S.
 */
public class FileWithHeadersURLConnection extends HttpURLConnection {

	/** The content. */
	private final byte[] content;

	/** The connected. */
	private boolean connected = false;

	/** The input stream. */
	private InputStream inputStream;

	/** The headers map. */
	private final Map<String, List<String>> headersMap = new HashMap<String, List<String>>();

	/** The headers list. */
	private final List<NameValuePair> headersList = new ArrayList<NameValuePair>();

	/**
	 * Instantiates a new file with headers url connection.
	 *
	 * @param url
	 *            the url
	 * @param content
	 *            the content
	 */
	public FileWithHeadersURLConnection(URL url, byte[] content) {
		super(url);
		this.content = content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.HttpURLConnection#disconnect()
	 */
	@Override
	public void disconnect() {
		if (this.connected) {
			InputStream in = this.inputStream;
			if (in != null) {
				try {
					in.close();
				} catch (IOException ioe) {
					// ignore
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#connect()
	 */
	@Override
	public void connect() throws IOException {
		if (!this.connected) {
			InputStream in = new ByteArrayInputStream(this.content);
			try {
				this.inputStream = in;
				readHeaders(in);
			} finally {
				// Do not close inputStream. Needed later.
				this.connected = true;
			}
		}
	}

	/**
	 * Read headers.
	 *
	 * @param in
	 *            the in
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void readHeaders(InputStream in) throws IOException {
		String line;
		List<NameValuePair> headersList = this.headersList;
		Map<String, List<String>> headersMap = this.headersMap;
		while ((line = IORoutines.readLine(in)) != null) {
			if ("".equals(line)) {
				break;
			}
			int colonIdx = line.indexOf(':');
			String name = colonIdx == -1 ? "" : line.substring(0, colonIdx).trim().toLowerCase();
			String value = colonIdx == -1 ? line.trim() : line.substring(colonIdx + 1).trim();
			List<String> hvalues = headersMap.get(name);
			if (hvalues == null) {
				hvalues = new ArrayList<String>(1);
				headersMap.put(name, hvalues);
			}
			hvalues.add(value);
			headersList.add(new NameValuePair(name, value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getInputStream()
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		this.connect();
		return this.inputStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getHeaderField(int)
	 */
	@Override
	public String getHeaderField(int n) {
		try {
			this.connect();
			NameValuePair pair = this.headersList.get(n);
			return pair.value;
		} catch (IndexOutOfBoundsException iob) {
			return null;
		} catch (IOException ioe) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getHeaderField(String)
	 */
	@Override
	public String getHeaderField(String name) {
		try {
			this.connect();
		} catch (IOException ioe) {
			return null;
		}
		List hvalues = this.headersMap.get(name.toLowerCase());
		if (hvalues == null || hvalues.size() == 0) {
			return null;
		}
		return (String) hvalues.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getHeaderFieldKey(int)
	 */
	@Override
	public String getHeaderFieldKey(int n) {
		try {
			this.connect();
			NameValuePair pair = this.headersList.get(n);
			return pair.name;
		} catch (IndexOutOfBoundsException iob) {
			return null;
		} catch (IOException ioe) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getHeaderFields()
	 */
	@Override
	public Map<String, List<String>> getHeaderFields() {
		try {
			this.connect();
		} catch (IOException ioe) {
			return null;
		}
		return this.headersMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.HttpURLConnection#usingProxy()
	 */
	@Override
	public boolean usingProxy() {
		return false;
	}
}
