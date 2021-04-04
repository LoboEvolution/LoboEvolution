/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.node.js;

import org.loboevolution.html.node.DOMStringList;

/**
 * The location (URL) of the object it is linked to. Changes done on it are
 * reflected on the object it relates to. Both the Document and Window interface
 * have such a linked Location, accessible via Document.location and
 * Window.location respectively.
 */
public interface Location {

	/**
	 * <p>getAncestorOrigins.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.DOMStringList} object.
	 */
	DOMStringList getAncestorOrigins();

	/**
	 * Returns the Location object's URL's fragment (includes leading "#" if
	 * non-empty).
	 * <p>
	 * Can be set, to navigate to the same URL with a changed fragment (ignores
	 * leading "#").
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHash();

	/**
	 * <p>setHash.</p>
	 *
	 * @param hash a {@link java.lang.String} object.
	 */
	void setHash(String hash);

	/**
	 * Returns the Location object's URL's host and port (if different from the
	 * default port for the scheme).
	 * <p>
	 * Can be set, to navigate to the same URL with a changed host and port.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHost();

	/**
	 * <p>setHost.</p>
	 *
	 * @param host a {@link java.lang.String} object.
	 */
	void setHost(String host);

	/**
	 * Returns the Location object's URL's host.
	 * <p>
	 * Can be set, to navigate to the same URL with a changed host.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHostname();

	/**
	 * <p>setHostname.</p>
	 *
	 * @param hostname a {@link java.lang.String} object.
	 */
	void setHostname(String hostname);

	/**
	 * Returns the Location object's URL.
	 * <p>
	 * Can be set, to navigate to the given URL.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHref();

	/**
	 * <p>setHref.</p>
	 *
	 * @param href a {@link java.lang.String} object.
	 */
	void setHref(String href);

	/**
	 * Returns the Location object's URL's origin.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getOrigin();

	/**
	 * Returns the Location object's URL's path.
	 * <p>
	 * Can be set, to navigate to the same URL with a changed path.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getPathname();

	/**
	 * <p>setPathname.</p>
	 *
	 * @param pathname a {@link java.lang.String} object.
	 */
	void setPathname(String pathname);

	/**
	 * Returns the Location object's URL's port.
	 * <p>
	 * Can be set, to navigate to the same URL with a changed port.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getPort();

	/**
	 * <p>setPort.</p>
	 *
	 * @param port a {@link java.lang.String} object.
	 */
	void setPort(String port);

	/**
	 * Returns the Location object's URL's scheme.
	 * <p>
	 * Can be set, to navigate to the same URL with a changed scheme.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getProtocol();

	/**
	 * <p>setProtocol.</p>
	 *
	 * @param protocol a {@link java.lang.String} object.
	 */
	void setProtocol(String protocol);

	/**
	 * Returns the Location object's URL's query (includes leading "?" if
	 * non-empty).
	 * <p>
	 * Can be set, to navigate to the same URL with a changed query (ignores leading
	 * "?").
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getSearch();

	/**
	 * <p>setSearch.</p>
	 *
	 * @param search a {@link java.lang.String} object.
	 */
	void setSearch(String search);

	/**
	 * Navigates to the given URL.
	 *
	 * @param url a {@link java.lang.String} object.
	 */
	void assign(String url);

	/**
	 * Reloads the current page.
	 */
	void reload();

	/**
	 * <p>reload.</p>
	 *
	 * @param forcedReload a boolean.
	 */
	@Deprecated
	void reload(boolean forcedReload);

	/**
	 * Removes the current page from the session history and navigates to the given
	 * URL.
	 *
	 * @param url a {@link java.lang.String} object.
	 */
	void replace(String url);

}
