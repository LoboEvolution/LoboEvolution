/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

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

import org.lobobrowser.http.NameValuePair;

/**
 * The Class MemoryURLConnection.
 */
public class MemoryURLConnection extends URLConnection {

    /** The memory entry. */
    private final MemoryCacheEntry memoryEntry;

    /**
     * Instantiates a new memory url connection.
     *
     * @param url
     *            the url
     * @param memoryEntry
     *            the memory entry
     */
    public MemoryURLConnection(URL url, final MemoryCacheEntry memoryEntry) {
        super(url);
        this.memoryEntry = memoryEntry;
    }

    /** The input stream. */
    private InputStream inputStream;

    /** The headers map. */
    private final Map<String, List<String>> headersMap = new HashMap<String, List<String>>();

    /*
     * (non-Javadoc)
     * @see java.net.URLConnection#connect()
     */
    @Override
    public void connect() throws IOException {
        if (!this.connected) {
            this.readHeaders();
            InputStream in = new ByteArrayInputStream(this.memoryEntry.getContent());
            this.inputStream = in;
            this.connected = true;
        }
    }

    /**
     * Read headers.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void readHeaders() throws IOException {
        Map<String, List<String>> headersMap = this.headersMap;
        List origList = this.memoryEntry.getHeaders();
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
     * @see java.net.URLConnection#getHeaderField(int)
     */
    @Override
    public String getHeaderField(int n) {
        try {
            this.connect();
            NameValuePair pair = this.memoryEntry.getHeaders().get(n);
            return pair.value;
        } catch (IndexOutOfBoundsException iob) {
            return null;
        } catch (IOException ioe) {
            return null;
        }
    }

    /*
     * (non-Javadoc)
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
        if ((hvalues == null) || (hvalues.size() == 0)) {
            return null;
        }
        return (String) hvalues.get(0);
    }

    /*
     * (non-Javadoc)
     * @see java.net.URLConnection#getHeaderFieldKey(int)
     */
    @Override
    public String getHeaderFieldKey(int n) {
        try {
            this.connect();
            NameValuePair pair = this.memoryEntry.getHeaders().get(n);
            return pair.name;
        } catch (IndexOutOfBoundsException iob) {
            return null;
        } catch (IOException ioe) {
            return null;
        }
    }

    /*
     * (non-Javadoc)
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
     * @see java.net.URLConnection#getInputStream()
     */
    @Override
    public InputStream getInputStream() throws IOException {
        this.connect();
        return this.inputStream;
    }
}
