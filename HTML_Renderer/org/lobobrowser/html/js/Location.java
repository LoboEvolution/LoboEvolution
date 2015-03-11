package org.lobobrowser.html.js;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.js.AbstractScriptableDelegate;
import org.w3c.dom.Document;


/**
 * The Class Location.
 */
public class Location extends AbstractScriptableDelegate {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(Location.class.getName());
	
	/** The window. */
	private final Window window;

	/**
	 * Instantiates a new location.
	 *
	 * @param window the window
	 */
	Location(final Window window) {
		this.window = window;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	private URL getURL() {
		URL url;
		try {
			Document document = this.window.getDocumentNode();
			url = document == null ? null : new URL(document.getDocumentURI());
		} catch (MalformedURLException mfu) {
			url = null;
		}
		return url;
	}

	/**
	 * Gets the hash.
	 *
	 * @return the hash
	 */
	public String getHash() {
		URL url = this.getURL();
		return url == null ? null : url.getRef();
	}

	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	public String getHost() {
		URL url = this.getURL();
		if (url == null) {
			return null;
		}
		return url.getHost() + (url.getPort() == -1 ? "" : ":" + url.getPort());
	}

	/**
	 * Gets the hostname.
	 *
	 * @return the hostname
	 */
	public String getHostname() {
		URL url = this.getURL();
		if (url == null) {
			return null;
		}
		return url.getHost();
	}

	/**
	 * Gets the pathname.
	 *
	 * @return the pathname
	 */
	public String getPathname() {
		URL url = this.getURL();
		return url == null ? null : url.getPath();
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public String getPort() {
		URL url = this.getURL();
		if (url == null) {
			return null;
		}
		int port = url.getPort();
		return port == -1 ? null : String.valueOf(port);
	}

	/**
	 * Gets the protocol.
	 *
	 * @return the protocol
	 */
	public String getProtocol() {
		URL url = this.getURL();
		if (url == null) {
			return null;
		}
		return url.getProtocol() + ":";
	}

	/**
	 * Gets the search.
	 *
	 * @return the search
	 */
	public String getSearch() {
		URL url = this.getURL();
		String query = url == null ? null : url.getQuery();
		// Javascript requires "?" in its search string.
		return query == null ? "" : "?" + query;
	}

	/** The target. */
	private String target;

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	public String getTarget() {
		return this.target;
	}

	/**
	 * Sets the target.
	 *
	 * @param value the new target
	 */
	public void setTarget(String value) {
		this.target = value;
	}

	/**
	 * Gets the href.
	 *
	 * @return the href
	 */
	public String getHref() {
		Document document = this.window.getDocumentNode();
		return document == null ? null : document.getDocumentURI();
	}

	/**
	 * Sets the href.
	 *
	 * @param uri the new href
	 */
	public void setHref(String uri) {
		HtmlRendererContext rcontext = this.window.getHtmlRendererContext();
		if (rcontext != null) {
			try {
				URL url;
				Document document = this.window.getDocumentNode();
				if (document instanceof HTMLDocumentImpl) {
					HTMLDocumentImpl docImpl = (HTMLDocumentImpl) document;
					url = docImpl.getFullURL(uri);
				} else {
					url = new URL(uri);
				}
				rcontext.navigate(url, this.target);
			} catch (MalformedURLException mfu) {
				logger.log(Level.WARNING, "setHref(): Malformed location: ["
						+ uri + "].", mfu);
			}
		}
	}

	/**
	 * Reload.
	 */
	public void reload() {
		Document document = this.window.getDocumentNode();
		if (document instanceof HTMLDocumentImpl) {
			HTMLDocumentImpl docImpl = (HTMLDocumentImpl) document;
			HtmlRendererContext rcontext = docImpl.getHtmlRendererContext();
			if (rcontext != null) {
				rcontext.reload();
			} else {
				docImpl.warn("reload(): No renderer context in Location's document.");
			}
		}
	}

	/**
	 * Replace.
	 *
	 * @param href the href
	 */
	public void replace(String href) {
		this.setHref(href);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getHref();
	}

	/**
	 * Assign.
	 *
	 * @param url the url
	 */
	public void assign(String url) {
		this.setHref(url);
	}
}
