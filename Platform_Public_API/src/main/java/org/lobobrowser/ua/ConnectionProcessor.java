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
package org.lobobrowser.ua;

import java.net.URLConnection;

/**
 * A connection pre- and post-processor.
 *
 * @see NavigatorExtensionContext#addConnectionProcessor(ConnectionProcessor)
 */
public interface ConnectionProcessor {

	/** The Constant EMPTY_ARRAY. */
	ConnectionProcessor[] EMPTY_ARRAY = new ConnectionProcessor[0];

	/**
	 * This method is called after the request method and standard headers have
	 * been set, and before a connection has been established or content has
	 * been posted. Changing request properties (headers) is permitted but any
	 * other changes could affect the operation of the platform.
	 *
	 * @param connection
	 *            A URL connection.
	 * @return It should return the <code>connection</code> object passed as a
	 *         parameter. A different connection object can be returned if
	 *         that's necessary (e.g. wrapping the original connection in order
	 *         to process its POST stream).
	 */
	URLConnection processPreConnection(URLConnection connection);

	/**
	 * This method is called after a connection has been established. At this
	 * point there should be a response code and response headers, but the input
	 * stream has not been read yet.
	 * <p>
	 * Note: Reading from the input stream of the connection must not be done,
	 * unless a replacement stream is provided in the connection that is
	 * returned by the method.
	 *
	 * @param connection
	 *            A URL connection.
	 * @return It should return the <code>connection</code> object passed as a
	 *         parameter. A different connection object can be returned if
	 *         that's necessary (e.g. wrapping the original connection in order
	 *         to process its input stream).
	 */
	URLConnection processPostConnection(URLConnection connection);
}
