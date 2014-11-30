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
package org.lobobrowser.request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lobobrowser.util.NameValuePair;

public class MemoryURLConnection extends URLConnection {
	private final MemoryCacheEntry memoryEntry;

	public MemoryURLConnection(URL url, final MemoryCacheEntry memoryEntry) {
		super(url);
		this.memoryEntry = memoryEntry;
	}

	private InputStream inputStream;
	private final Map<String, List<String>> headersMap = new HashMap<String, List<String>>();

	@Override
	public void connect() throws IOException {
		if (!this.connected) {
			this.readHeaders();
			InputStream in = new ByteArrayInputStream(this.memoryEntry.content);
			this.inputStream = in;
			this.connected = true;
		}
	}

	private void readHeaders() throws IOException {
		Map<String, List<String>> headersMap = this.headersMap;
		List origList = this.memoryEntry.headers;
		Iterator i = origList.iterator();
		while (i.hasNext()) {
			NameValuePair pair = (NameValuePair) i.next();
			String name = pair.name;
			if (name != null) {
				name = name.toLowerCase();
			}
			String value = pair.value;
			List<String> hvalues = headersMap.get(name);
			if (hvalues == null) {
				hvalues = new ArrayList<String>(1);
				headersMap.put(name, hvalues);
			}
			hvalues.add(value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getHeaderField(int)
	 */
	public String getHeaderField(int n) {
		try {
			this.connect();
			NameValuePair pair = (NameValuePair) this.memoryEntry.headers
					.get(n);
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
	 * @see java.net.URLConnection#getHeaderField(java.lang.String)
	 */
	public String getHeaderField(String name) {
		try {
			this.connect();
		} catch (IOException ioe) {
			return null;
		}
		List hvalues = (List) this.headersMap.get(name.toLowerCase());
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
	public String getHeaderFieldKey(int n) {
		try {
			this.connect();
			NameValuePair pair = (NameValuePair) this.memoryEntry.headers
					.get(n);
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
	public Map<String, List<String>> getHeaderFields() {
		try {
			this.connect();
		} catch (IOException ioe) {
			return null;
		}
		return this.headersMap;
	}

	public InputStream getInputStream() throws IOException {
		this.connect();
		return this.inputStream;
	}
}
