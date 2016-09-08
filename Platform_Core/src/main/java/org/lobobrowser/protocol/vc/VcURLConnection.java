/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
 * The Class VcURLConnection.
 *
 * @author J. H. S.
 */
public class VcURLConnection extends URLConnection {

    /** The vc. */
    private final VolatileContentImpl vc;

    /**
     * Instantiates a new vc url connection.
     *
     * @param url
     *            the url
     */
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
     * @see java.net.URLConnection#connect()
     */
    @Override
    public void connect() throws IOException {
    }

    /*
     * (non-Javadoc)
     * @see java.net.URLConnection#getContentLength()
     */
    @Override
    public int getContentLength() {
        return this.vc.getBytes().length;
    }

    /*
     * (non-Javadoc)
     * @see java.net.URLConnection#getContentType()
     */
    @Override
    public String getContentType() {
        return this.vc.getContentType();
    }

    /*
     * (non-Javadoc)
     * @see java.net.URLConnection#getInputStream()
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.vc.getBytes());
    }
}
