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
/*
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;
import com.gargoylesoftware.css.dom.CSSStyleSheetListImpl;
import org.loboevolution.common.Urls;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.filter.BodyFilter;
import org.loboevolution.html.dom.filter.HeadFilter;
import org.loboevolution.html.dom.nodeimpl.DOMException;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.io.LocalErrorHandler;
import org.loboevolution.html.io.WritableLineReader;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.js.WindowImpl;
import org.loboevolution.html.node.Code;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.views.DocumentView;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.StyleSheetRenderState;
import org.loboevolution.html.style.StyleSheetAggregator;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.mozilla.javascript.Function;
import org.w3c.dom.UserDataHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketPermission;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the W3C HTMLDocument interface.
 */
public class HTMLDocumentImpl extends DocumentImpl implements HTMLDocument, DocumentView {

	private static final Logger logger = Logger.getLogger(HTMLDocumentImpl.class.getName());

	private volatile String baseURI;

	private HTMLElement body;

	private String defaultTarget;

	private final List<DocumentNotificationListener> documentNotificationListeners = new ArrayList<>();

	private URL documentURL;

    private final Map<String, Element> elementsById = new HashMap<>();

	private final Map<String, Element> elementsByName = new HashMap<>();

	private Set<Locale> locales;

	private Function onloadHandler;

	private final HtmlRendererContext rcontext;

	private StyleSheetAggregator styleSheetAggregator = null;

    private final CSSStyleSheetListImpl styleSheets = new CSSStyleSheetListImpl();

	private final UserAgentContext ucontext;

	/**
	 * <p>Constructor for HTMLDocumentImpl.</p>
	 *
	 * @param rcontext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 */
	public HTMLDocumentImpl(HtmlRendererContext rcontext) {
		this(rcontext.getUserAgentContext(), rcontext, null, null);
	}

	/**
	 * <p>Constructor for HTMLDocumentImpl.</p>
	 *
	 * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
	 */
	public HTMLDocumentImpl(UserAgentContext ucontext) {
		this(ucontext, null, null, null);
	}

	/**
	 * <p>Constructor for HTMLDocumentImpl.</p>
	 *
	 * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param rcontext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 * @param reader a {@link org.loboevolution.html.io.WritableLineReader} object.
	 * @param documentURI a {@link java.lang.String} object.
	 */
	public HTMLDocumentImpl(final UserAgentContext ucontext, final HtmlRendererContext rcontext,
			WritableLineReader reader, String documentURI) {
		this.rcontext = rcontext;
		this.ucontext = ucontext;
		this.reader = reader;
		try {
			final URL docURL = new URL(documentURI);
			final SecurityManager sm = System.getSecurityManager();
			if (sm != null) {
				sm.checkPermission(new SocketPermission(docURL.getHost(), "connect"));
			}
			this.documentURL = docURL;
			setDomain(docURL.getHost());
			this.setDocumentURI(documentURI);
		} catch (final MalformedURLException mfu) {
			logger.warning("HTMLDocumentImpl(): Document URI [" + documentURI + "] is malformed.");
		}
		this.document = this;
		// Get WindowImpl object
		setWindow(rcontext, ucontext);
		WindowImpl window = (WindowImpl)getDefaultView();
		window.setDocument(this);
	}

	/**
	 * Adds a document notification listener, which is informed about changes to the
	 * document.
	 *
	 * @param listener An instance of {@link org.loboevolution.html.dom.domimpl.DocumentNotificationListener}.
	 */
	public void addDocumentNotificationListener(DocumentNotificationListener listener) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		synchronized (listenersList) {
			listenersList.add(listener);
		}
	}

	final void addStyleSheet(CSSStyleSheetImpl ss) {
		synchronized (this.treeLock) {
			this.styleSheets.add(ss);
			this.styleSheetAggregator = null;
			// Need to invalidate all children up to
			// this point.
			forgetRenderState();
			nodeList.forEach(node -> {
				if (node instanceof HTMLElementImpl) {
					((HTMLElementImpl) node).forgetStyle(true);
				}
			});
		}
		this.allInvalidated();
	}

	/**
	 * Informs listeners that the whole document has been invalidated.
	 */
	public void allInvalidated() {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.allInvalidated();
		}
	}

	/**
	 * <p>allInvalidated.</p>
	 *
	 * @param forgetRenderStates a boolean.
	 */
	public void allInvalidated(boolean forgetRenderStates) {
		if (forgetRenderStates) {
			synchronized (this.treeLock) {
				this.styleSheetAggregator = null;
				forgetRenderState();
				nodeList.forEach(node -> {
					if (node instanceof HTMLElementImpl) {
						((HTMLElementImpl) node).forgetStyle(true);
					}
				});
			}
		}
		this.allInvalidated();
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new StyleSheetRenderState(this);
	}


	/**
	 * <p>externalScriptLoading.</p>
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public void externalScriptLoading(NodeImpl node) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.externalScriptLoading(node);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getbaseURI()
	 */
	/** {@inheritDoc} */
	@Override
	public String getBaseURI() {
		final String buri = this.baseURI;
		return buri == null ? this.getDocumentURI() : buri;
	}

	/** {@inheritDoc} */
	@Override
	public String getCookie() {
		final SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			return (String) AccessController.doPrivileged((PrivilegedAction<Object>) () -> HTMLDocumentImpl.this.getUcontext().getCookie(HTMLDocumentImpl.this.documentURL));
		} else {
			return this.getUcontext().getCookie(this.documentURL);
		}
	}

	/**
	 * <p>Getter for the field defaultTarget.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getDefaultTarget() {
		return this.defaultTarget;
	}

	/** {@inheritDoc} */
	@Override
	public URL getDocumentURL() {
		return this.documentURL;
	}

	/** {@inheritDoc} */
	@Override
	public final URL getFullURL(String uri) {
		try {
			final String baseURI = getBaseURI();
			final URL documentURL = baseURI == null ? null : new URL(baseURI);
			return Urls.createURL(documentURL, uri);
		} catch (Exception mfu) {
			// Try agan, without the baseURI.
			try {
				return new URL(uri);
			} catch (Exception mfu2) {
				logger.log(Level.WARNING,
						"Unable to create URL for URI=[" + uri + "], with base=[" + getBaseURI() + "].", mfu);
				return null;
			}
		}
	}

	/**
	 * <p>getFullURL.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 * @param baseURI a {@link java.lang.String} object.
	 * @return a {@link java.net.URL} object.
	 */
	public final URL getFullURL(String uri, String baseURI) {
		try {
			final URL documentURL = baseURI == null ? null : new URL(baseURI);
			return Urls.createURL(documentURL, uri);
		} catch (Exception mfu) {
			// Try agan, without the baseURI.
			try {
				return new URL(uri);
			} catch (Exception mfu2) {
				logger.log(Level.WARNING,
						"Unable to create URL for URI=[" + uri + "], with base=[" + getBaseURI() + "].", mfu);
				return null;
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public final HtmlRendererContext getHtmlRendererContext() {
		return this.rcontext;
	}

	/** {@inheritDoc} */
	@Override
	public String getInputEncoding() {
		return "UTF-8";
	}
	
	/** {@inheritDoc} */
	@Override
	public String getCharacterSet() {
		return "UTF-8";
	}
	
	/** {@inheritDoc} */
	@Override
	public String getCharset() {
		return "UTF-8";
	}
    
	/** {@inheritDoc} */
    @Override
	public HTMLHeadElementImpl getHead() {
		synchronized (this) {
			HTMLCollection collection = new HTMLCollectionImpl(this, new HeadFilter());
			if (collection.getLength() > 0) {
				return (HTMLHeadElementImpl) collection.item(0);
			} else {
				return null;
			}
		}
	}
    
	/** {@inheritDoc} */
    @Override
	public HTMLElement getBody() {
		synchronized (this) {
			if (this.body == null) {
				HTMLCollection collection = new HTMLCollectionImpl(this, new BodyFilter());
				if (collection.getLength() > 0) {
					return (HTMLElement) collection.item(0);
				} else {
					return null;
				}
			}
            return this.body;
		}
	}

	/**
	 * Gets an <i>immutable</i> set of locales previously set for this document.
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set<Locale> getLocales() {
		return this.locales;
	}

	/**
	 * <p>Getter for the field onloadHandler.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnloadHandler() {
		return this.onloadHandler;
	}

	final StyleSheetAggregator getStyleSheetAggregator() {
		synchronized (this.treeLock) {
			StyleSheetAggregator ssa = this.styleSheetAggregator;
			if (ssa == null) {
				ssa = new StyleSheetAggregator();
				try {
                    ssa.addStyleSheets(this.styleSheets.getCSSStyleSheets());
				} catch (Exception mfu) {
					logger.log(Level.WARNING, "getStyleSheetAggregator()", mfu);
				}
				this.styleSheetAggregator = ssa;
			}
			return ssa;
		}
	}

    /**
     * <p>Getter for the field styleSheets.</p>
     *
     * @return a {@link com.gargoylesoftware.css.dom.CSSStyleSheetListImpl} object.
     */
    public CSSStyleSheetListImpl getStyleSheets() {
		return this.styleSheets;
	}

	/** {@inheritDoc} */
	@Override
	public String getTextContent() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getURL() {
		return this.getDocumentURI();
	}

	/** {@inheritDoc} */
	@Override
	public UserAgentContext getUserAgentContext() {
		return this.getUcontext();
	}

	/** {@inheritDoc} */
	@Override
	public Node importNode(Node importedNode, boolean deep) {
		throw new DOMException(Code.NOT_SUPPORTED_ERR, "Not implemented");
	}

	/**
	 * This is called when the node has changed, but it is unclear if it's a size
	 * change or a look change. An attribute change should trigger this.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public void invalidated(NodeImpl node) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.invalidated(node);
		}
	}

	/**
	 * Loads the document from the reader provided when the current instance of
	 * HTMLDocumentImpl was constructed. It then closes the reader.
	 *
	 * @throws java.io.IOException if any.
	 * @throws org.xml.sax.SAXException if any.
	 * @throws java.io.UnsupportedEncodingException if any.
	 */
	public void load() throws IOException, SAXException, UnsupportedEncodingException {
		this.load(true);
	}

	/**
	 * <p>load.</p>
	 *
	 * @param closeReader a boolean.
	 * @throws java.io.IOException if any.
	 * @throws org.xml.sax.SAXException if any.
	 * @throws java.io.UnsupportedEncodingException if any.
	 */
	public void load(boolean closeReader) throws IOException, SAXException, UnsupportedEncodingException {
		WritableLineReader reader;
		synchronized (this.treeLock) {
			removeAllChildrenImpl();
			setTitle(null);
			setBaseURI(null);
			setDefaultTarget(null);
            this.styleSheets.getCSSStyleSheets().clear();
			this.styleSheetAggregator = null;
			reader = this.reader;
		}
		if (reader != null) {
			try {
				final ErrorHandler errorHandler = new LocalErrorHandler();
				final HtmlParser parser = new HtmlParser(getUcontext(), document, errorHandler, true);
				parser.parse(reader);
			} finally {
				if (closeReader) {
					try {
						reader.close();
					} catch (final Exception err) {
						logger.log(Level.WARNING, "load(): Unable to close stream", err);
					}
					synchronized (this.treeLock) {
						this.reader = null;
					}
				}
			}
		}
	}

	/**
	 * Called if something such as a color or decoration has changed. This would be
	 * something which does not affect the rendered size, and can be revalidated
	 * with a simple repaint.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public void lookInvalidated(NodeImpl node) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.lookInvalidated(node);
		}
	}

	/**
	 * <p>namedItem.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.Element} object.
	 */
	public Element namedItem(String name) {
		Element element;
		synchronized (this) {
			element = this.elementsByName.get(name);
		}
		return element;
	}

	/**
	 * <p>nodeLoaded.</p>
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public void nodeLoaded(NodeImpl node) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.nodeLoaded(node);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void normalizeDocument() {
		// TODO: Normalization options from domConfig
		synchronized (this.treeLock) {
			visitImpl(Node::normalize);
		}
	}
	
	/**
	 * Changed if the position of the node in a parent has changed.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public void positionInParentInvalidated(NodeImpl node) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.positionInvalidated(node);
		}
	}

	/**
	 * <p>removeDocumentNotificationListener.</p>
	 *
	 * @param listener a {@link org.loboevolution.html.dom.domimpl.DocumentNotificationListener} object.
	 */
	public void removeDocumentNotificationListener(DocumentNotificationListener listener) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		synchronized (listenersList) {
			listenersList.remove(listener);
		}
	}

	void removeNamedItem(String name) {
		synchronized (this) {
			this.elementsByName.remove(name);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Node renameNode(Node n, String namespaceURI, String qualifiedName) {
		throw new DOMException(Code.NOT_SUPPORTED_ERR, "No renaming");
	}

	/**
	 * <p>Setter for the field baseURI.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setBaseURI(String value) {
		this.baseURI = value;
	}

	/** {@inheritDoc} */
	@Override
	public void setBody(HTMLElement body) {
		synchronized (this) {
			this.body = body;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setCookie(final String cookie) {
		final SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
				HTMLDocumentImpl.this.getUcontext().setCookie(HTMLDocumentImpl.this.documentURL, cookie);
				return null;
			});
		} else {
			this.getUcontext().setCookie(this.documentURL, cookie);
		}
	}

	/**
	 * <p>Setter for the field defaultTarget.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setDefaultTarget(String value) {
		this.defaultTarget = value;
	}

	/**
	 * Caller should synchronize on document.
	 */
	void setElementById(String id, Element element) {
		synchronized (this) {
			this.elementsById.put(id, element);
		}
	}

	/**
	 * Sets the locales of the document. This helps determine whether specific fonts
	 * can display text in the languages of all the locales.
	 *
	 * @param locales An <i>immutable</i> set of java.util.Locale
	 *                instances.
	 */
	public void setLocales(Set<Locale> locales) {
		this.locales = locales;
	}


	void setNamedItem(String name, Element element) {
		synchronized (this) {
			this.elementsByName.put(name, element);
		}
	}

	/**
	 * <p>Setter for the field onloadHandler.</p>
	 *
	 * @param onloadHandler a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnloadHandler(Function onloadHandler) {
		this.onloadHandler = onloadHandler;
	}

	/** {@inheritDoc} */
	@Override
	public void setTextContent(String textContent) {
		// NOP, per spec
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		final Function onloadHandler = this.onloadHandler;
		if (onloadHandler != null) {
			if (HtmlParser.MODIFYING_KEY.equals(key) && data == Boolean.FALSE) {
				Executor.executeFunction(this, onloadHandler, null, new Object[0]);
			}
		}
		return super.setUserData(key, data, handler);
	}

	/**
	 * <p>sizeInvalidated.</p>
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public void sizeInvalidated(NodeImpl node) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.sizeInvalidated(node);
		}
	}

	/**
	 * This is called when children of the node might have changed.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public void structureInvalidated(NodeImpl node) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.structureInvalidated(node);
		}
	}

	/**
	 * <p>Getter for the field <code>ucontext</code>.</p>
	 *
	 * @return the ucontext
	 */
	public UserAgentContext getUcontext() {
		return ucontext;
	}
}
