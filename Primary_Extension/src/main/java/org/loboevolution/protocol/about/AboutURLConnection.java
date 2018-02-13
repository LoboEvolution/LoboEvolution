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
 * Created on Mar 14, 2005
 */
package org.loboevolution.protocol.about;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * The Class AboutURLConnection.
 *
 * @author J. H. S.
 */
public class AboutURLConnection extends URLConnection {

	/**
	 * Instantiates a new about url connection.
	 *
	 * @param url
	 *            the url
	 */
	public AboutURLConnection(URL url) {
		super(url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#connect()
	 */
	@Override
	public void connect() throws IOException {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getContentLength()
	 */
	@Override
	public int getContentLength() {
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getContentType()
	 */
	@Override
	public String getContentType() {
		return "text/html";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getInputStream()
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(this.getURLText(this.getURL()).getBytes("UTF-8"));
	}

	/**
	 * Gets the URL text.
	 *
	 * @param url
	 *            the url
	 * @return the URL text
	 */
	private String getURLText(URL url) {
		String path = url.getPath();
		if ("blank".equalsIgnoreCase(path)) {
			return "";
		} else if ("java-properties".equals(path)) {
			return this.getSystemProperties();
		} else {
			return "[Unknown about path: " + path + "]";
		}
	}

	/**
	 * Gets the system properties.
	 *
	 * @return the system properties
	 */
	private String getSystemProperties() {
		StringWriter swriter = new StringWriter();
		PrintWriter writer = new PrintWriter(swriter);
		writer.println("<html>");
		writer.println("<head><title>Java Properties</title></head>");
		writer.println("<body>");
		writer.println("<pre>");
		Properties properties = System.getProperties();
		properties.list(writer);
		writer.println("</pre>");
		writer.println("</body>");
		writer.println("</html>");
		writer.flush();
		return swriter.toString();
	}
}
