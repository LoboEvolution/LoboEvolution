/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.js;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.w3c.dom.Document;

/**
 * <p>Location class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class Location extends AbstractScriptableDelegate {
	private static final Logger logger = Logger.getLogger(Location.class.getName());
	private String target;

	private final Window window;

	Location(final Window window) {
		this.window = window;
	}

	/**
	 * <p>getHash.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getHash() {
		final URL url = getURL();
		return url == null ? null : url.getRef();
	}

	/**
	 * <p>getHost.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getHost() {
		final URL url = getURL();
		if (url == null) {
			return null;
		}
		return url.getHost() + (url.getPort() == -1 ? "" : ":" + url.getPort());
	}

	/**
	 * <p>getHostname.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getHostname() {
		final URL url = getURL();
		if (url == null) {
			return null;
		}
		return url.getHost();
	}

	/**
	 * <p>getHref.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getHref() {
		final Document document = this.window.getDocumentNode();
		return document == null ? null : document.getDocumentURI();
	}

	/**
	 * <p>getPathname.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPathname() {
		final URL url = getURL();
		return url == null ? null : url.getPath();
	}

	/**
	 * <p>getPort.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPort() {
		final URL url = getURL();
		if (url == null) {
			return null;
		}
		final int port = url.getPort();
		return port == -1 ? null : String.valueOf(port);
	}

	/**
	 * <p>getProtocol.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getProtocol() {
		final URL url = getURL();
		if (url == null) {
			return null;
		}
		return url.getProtocol() + ":";
	}

	/**
	 * <p>getSearch.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSearch() {
		final URL url = getURL();
		final String query = url == null ? null : url.getQuery();
		// Javascript requires "?" in its search string.
		return query == null ? "" : "?" + query;
	}

	/**
	 * <p>Getter for the field target.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getTarget() {
		return this.target;
	}

	private URL getURL() {
		URL url;
		try {
			final Document document = this.window.getDocumentNode();
			url = document == null ? null : new URL(document.getDocumentURI());
		} catch (final java.net.MalformedURLException mfu) {
			url = null;
		}
		return url;
	}

	/**
	 * <p>reload.</p>
	 */
	public void reload() {
		// TODO: This is not really reload.
		final Document document = this.window.getDocumentNode();
		if (document instanceof HTMLDocumentImpl) {
			final HTMLDocumentImpl docImpl = (HTMLDocumentImpl) document;
			final HtmlRendererContext rcontext = docImpl.getHtmlRendererContext();
			if (rcontext != null) {
				rcontext.reload();
			} else {
				docImpl.warn("reload(): No renderer context in Location's document.");
			}
		}
	}

	/**
	 * <p>replace.</p>
	 *
	 * @param href a {@link java.lang.String} object.
	 */
	public void replace(String href) {
		setHref(href);
	}

	/**
	 * <p>setHref.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 */
	public void setHref(String uri) {
		final HtmlRendererContext rcontext = this.window.getHtmlRendererContext();
		if (rcontext != null) {
			try {
				URL url;
				final Document document = this.window.getDocumentNode();
				if (document instanceof HTMLDocumentImpl) {
					final HTMLDocumentImpl docImpl = (HTMLDocumentImpl) document;
					url = docImpl.getFullURL(uri);
				} else {
					url = new URL(uri);
				}
				rcontext.navigate(url, this.target);
			} catch (final java.net.MalformedURLException mfu) {
				logger.log(Level.WARNING, "setHref(): Malformed location: [" + uri + "].", mfu);
			}
		}
	}

	/**
	 * <p>Setter for the field target.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setTarget(String value) {
		this.target = value;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		// This needs to be href. Callers
		// rely on that.
		return getHref();
	}
}
