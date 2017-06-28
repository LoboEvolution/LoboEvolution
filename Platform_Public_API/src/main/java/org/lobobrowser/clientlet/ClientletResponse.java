/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
package org.lobobrowser.clientlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;

import org.lobobrowser.ua.RequestType;

/**
 * Represents a URL response such as an HTTP or file protocol response.
 */
public interface ClientletResponse {

	/**
	 * Gets the response url.
	 *
	 * @return the response url
	 */
	URL getResponseURL();

	/**
	 * Gets the last request method.
	 *
	 * @return the last request method
	 */
	String getLastRequestMethod();

	/**
	 * Gets a response header.
	 *
	 * @param name
	 *            The header name.
	 * @return the header
	 */
	String getHeader(String name);

	/**
	 * Gets all values for a particular header.
	 *
	 * @param name
	 *            The header name.
	 * @return the headers
	 */
	String[] getHeaders(String name);

	/**
	 * Gets the header names.
	 *
	 * @return the header names
	 */
	Iterator getHeaderNames();

	/**
	 * Gets the input stream.
	 *
	 * @return the input stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	InputStream getInputStream() throws IOException;

	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	String getContentType();

	/**
	 * Gets the mime type.
	 *
	 * @return the mime type
	 */
	String getMimeType();

	/**
	 * A convenience method used to match parameters provided against the
	 * response mime-type or the "file extension" of the response URL's file
	 * path. The file extension is matched only when the mime type of the
	 * response is either <code>application/octet-stream</code>,
	 * <code>content/unknown</code>, or not provided.
	 *
	 * @param mimeType
	 *            A mime type, e.g. <em>application/x-acme</em>.
	 * @param fileExtension
	 *            A collection of file extensions, each starting with a dot,
	 *            e.g. <em>new String[] {".acme", ".acm"}</em>.
	 * @return True if the navigator considers there is a match.
	 */
	boolean matches(String mimeType, String[] fileExtension);

	/**
	 * Gets the content length.
	 *
	 * @return the content length
	 */
	int getContentLength();

	/**
	 * Checks if is from cache.
	 *
	 * @return true, if is from cache
	 */
	boolean isFromCache();

	/**
	 * Gets the charset.
	 *
	 * @return the charset
	 */
	String getCharset();

	/**
	 * Checks if is charset provided.
	 *
	 * @return true, if is charset provided
	 */
	boolean isCharsetProvided();

	/**
	 * Gets the response code.
	 *
	 * @return the response code
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	int getResponseCode() throws IOException;

	/**
	 * Gets the response message.
	 *
	 * @return the response message
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	String getResponseMessage() throws IOException;

	/**
	 * Checks if is cacheable.
	 *
	 * @return true, if is cacheable
	 */
	boolean isCacheable();

	/**
	 * Checks if is new navigation action.
	 *
	 * @return true, if is new navigation action
	 */
	boolean isNewNavigationAction();

	/**
	 * If available, gets an object previously persisted along with the cached
	 * document.
	 *
	 * @param classLoader
	 *            A class loader that can load an object of the type expected.
	 * @return the persistent cached object
	 * @see #setNewPersistentCachedObject(Serializable)
	 */
	Object getPersistentCachedObject(ClassLoader classLoader);

	/**
	 * Sets the new persistent cached object.
	 *
	 * @param object
	 *            the new new persistent cached object
	 */
	void setNewPersistentCachedObject(Serializable object);

	/**
	 * Gets the transient cached object.
	 *
	 * @return the transient cached object
	 */
	Object getTransientCachedObject();

	/**
	 * Caches an object in main memory, provided caching is allowed and there's
	 * enough memory to do so. The object is associated with the current
	 * response URL.
	 *
	 * @param object
	 *            An object.
	 * @param approxSize
	 *            The approximate byte size the object occupies in memory. Note
	 *            that values less than the size of the response in bytes are
	 *            assumed to be in error.
	 */
	void setNewTransientCachedObject(Object object, int approxSize);

	/**
	 * Gets the transient cached object size.
	 *
	 * @return the transient cached object size
	 */
	int getTransientCachedObjectSize();

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	Date getDate();

	/**
	 * Gets the request type.
	 *
	 * @return the request type
	 */
	RequestType getRequestType();
}
