package org.loboevolution.html.node.js;

import org.loboevolution.html.node.DOMStringList;

/**
 * The location (URL) of the object it is linked to. Changes done on it are
 * reflected on the object it relates to. Both the Document and Window interface
 * have such a linked Location, accessible via Document.location and
 * Window.location respectively.
 */
public interface Location {

	DOMStringList getAncestorOrigins();

	/**
	 * Returns the Location object's URL's fragment (includes leading "#" if
	 * non-empty).
	 * <p>
	 * Can be set, to navigate to the same URL with a changed fragment (ignores
	 * leading "#").
	 */

	String getHash();

	void setHash(String hash);

	/**
	 * Returns the Location object's URL's host and port (if different from the
	 * default port for the scheme).
	 * <p>
	 * Can be set, to navigate to the same URL with a changed host and port.
	 */

	String getHost();

	void setHost(String host);

	/**
	 * Returns the Location object's URL's host.
	 * <p>
	 * Can be set, to navigate to the same URL with a changed host.
	 */

	String getHostname();

	void setHostname(String hostname);

	/**
	 * Returns the Location object's URL.
	 * <p>
	 * Can be set, to navigate to the given URL.
	 */

	String getHref();

	void setHref(String href);

	/**
	 * Returns the Location object's URL's origin.
	 */

	String getOrigin();

	/**
	 * Returns the Location object's URL's path.
	 * <p>
	 * Can be set, to navigate to the same URL with a changed path.
	 */

	String getPathname();

	void setPathname(String pathname);

	/**
	 * Returns the Location object's URL's port.
	 * <p>
	 * Can be set, to navigate to the same URL with a changed port.
	 */

	String getPort();

	void setPort(String port);

	/**
	 * Returns the Location object's URL's scheme.
	 * <p>
	 * Can be set, to navigate to the same URL with a changed scheme.
	 */

	String getProtocol();

	void setProtocol(String protocol);

	/**
	 * Returns the Location object's URL's query (includes leading "?" if
	 * non-empty).
	 * <p>
	 * Can be set, to navigate to the same URL with a changed query (ignores leading
	 * "?").
	 */

	String getSearch();

	void setSearch(String search);

	/**
	 * Navigates to the given URL.
	 */
	void assign(String url);

	/**
	 * Reloads the current page.
	 */
	void reload();

	@Deprecated
	void reload(boolean forcedReload);

	/**
	 * Removes the current page from the session history and navigates to the given
	 * URL.
	 */
	void replace(String url);

}
