/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.filter.BodyFilter;
import org.loboevolution.html.dom.filter.HeadFilter;
import org.loboevolution.html.dom.nodeimpl.DocumentImpl;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.dom.nodeimpl.event.DocumentNotificationListener;
import org.loboevolution.html.io.WritableLineReader;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.js.WindowImpl;
import org.loboevolution.html.js.css.CSSStyleSheetImpl;
import org.loboevolution.html.js.css.StyleSheetListImpl;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.css.StyleSheetList;
import org.loboevolution.html.node.views.DocumentView;
import org.loboevolution.html.parser.XHtmlParser;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.StyleSheetRenderState;
import org.loboevolution.html.style.StyleSheetAggregator;
import org.loboevolution.http.UserAgentContext;
import org.mozilla.javascript.Function;
import org.loboevolution.html.dom.UserDataHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
/**
 * Implementation of the W3C HTMLDocument interface.
 */
@Slf4j
public class HTMLDocumentImpl extends DocumentImpl implements HTMLDocument, DocumentView {

	@Setter
	private volatile String baseURI;

	private HTMLElement body;

	@Getter
	@Setter
	private String defaultTarget;

	private final List<DocumentNotificationListener> documentNotificationListeners = new ArrayList<>();

	private URL documentURL;

	private final Map<String, Element> elementsById = new HashMap<>();

	private final Map<String, Element> elementsByName = new HashMap<>();

	@Getter
	@Setter
	private Set<Locale> locales;

	@Getter
	@Setter
	private Function onloadHandler;

	@Getter
	private final HtmlRendererContext rcontext;

	private StyleSheetAggregator styleSheetAggregator = null;

	private final StyleSheetListImpl styleSheets = new StyleSheetListImpl();

	@Getter
	private final UserAgentContext ucontext;

	@Getter
	private final HtmlRendererConfig config;

	/**
	 * <p>Constructor for HTMLDocumentImpl.</p>
	 *
	 * @param rcontext a {@link HtmlRendererContext} object.
	 */
	public HTMLDocumentImpl(final HtmlRendererContext rcontext) {
		this(rcontext.getUserAgentContext(), rcontext, null, null, null);
	}

	/**
	 * <p>Constructor for HTMLDocumentImpl.</p>
	 *
	 * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
	 */
	public HTMLDocumentImpl(final UserAgentContext ucontext) {
		this(ucontext, null, null, null, null);
	}

	/**
	 * <p>Constructor for HTMLDocumentImpl.</p>
	 *
	 * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param rcontext a {@link HtmlRendererContext} object.
	 * @param config a {@link HtmlRendererConfig} object.
	 * @param reader a {@link org.loboevolution.html.io.WritableLineReader} object.
	 * @param documentURI a {@link java.lang.String} object.
	 */
	public HTMLDocumentImpl(final UserAgentContext ucontext, final HtmlRendererContext rcontext, final HtmlRendererConfig config,
							final WritableLineReader reader, final String documentURI) {
		this.rcontext = rcontext;
		this.ucontext = ucontext;
		this.reader = reader;
		this.config = config;
		try {
			if (Strings.isNotBlank(documentURI)) {
				final URL docURL = new URL(documentURI);
				this.documentURL = docURL;
				setDomain(docURL.getHost());
				this.setDocumentURI(documentURI);
			}
		} catch (final MalformedURLException mfu) {
			log.warn("HTMLDocumentImpl(): Document URI {} is malformed.",  documentURI);
		}
		this.document = this;
		setWindow(rcontext, ucontext, config);
		final WindowImpl window = (WindowImpl)getDefaultView();
		window.setDocument(this);
	}

	/**
	 * Adds a document notification listener, which is informed about changes to the
	 * document.
	 *
	 * @param listener An instance of {@link DocumentNotificationListener}.
	 */
	public void addDocumentNotificationListener(final DocumentNotificationListener listener) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		synchronized (listenersList) {
			listenersList.add(listener);
		}
	}

	final void addStyleSheet(final CSSStyleSheetImpl ss) {
		synchronized (this) {
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
		for (final DocumentNotificationListener dnl : listenersList) {
			dnl.allInvalidated();
		}
	}

	/**
	 * <p>allInvalidated.</p>
	 *
	 * @param forgetRenderStates a boolean.
	 */
	public void allInvalidated(final boolean forgetRenderStates) {
		if (forgetRenderStates) {
			synchronized (this) {
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
	protected RenderState createRenderState(final RenderState prevRenderState) {
		return new StyleSheetRenderState(this);
	}


	/**
	 * <p>externalScriptLoading.</p>
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public void externalScriptLoading(final NodeImpl node) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (final DocumentNotificationListener dnl : listenersList) {
			dnl.externalScriptLoading(node);
		}
	}

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

	/** {@inheritDoc} */
	@Override
	public URL getDocumentURL() {
		return this.documentURL;
	}

	/** {@inheritDoc} */
	@Override
	public final URL getFullURL(final String uri) {
		try {
			final String baseURI = getBaseURI();
			final URL documentURL = baseURI == null ? null : new URL(baseURI);
			return Urls.createURL(documentURL, uri);
		} catch (final Exception mfu) {
			// Try agan, without the baseURI.
			try {
				return new URL(uri);
			} catch (final Exception mfu2) {
				log.error("Unable to create URL for URI= {} with base {} ", uri, baseURI);
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
	public final URL getFullURL(final String uri, final String baseURI) {
		try {
			final URL documentURL = baseURI == null ? null : new URL(baseURI);
			return Urls.createURL(documentURL, uri);
		} catch (final Exception mfu) {
			// Try agan, without the baseURI.
			try {
				return new URL(uri);
			} catch (final Exception mfu2) {
				log.error("Unable to create URL for URI= {} with base {}", uri, baseURI);
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
			final HTMLCollection collection = new HTMLCollectionImpl(this, new HeadFilter());
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
				final HTMLCollection collection = new HTMLCollectionImpl(this, new BodyFilter());
				if (collection.getLength() > 0) {
					return (HTMLElement) collection.item(0);
				} else {
					return null;
				}
			}
			return this.body;
		}
	}

	final StyleSheetAggregator getStyleSheetAggregator() {
		synchronized (this) {
			StyleSheetAggregator ssa = this.styleSheetAggregator;
			if (ssa == null) {
				ssa = new StyleSheetAggregator();
				try {
					ssa.setDoc(this);
					ssa.addStyleSheets(this.styleSheets);

				} catch (final Exception mfu) {
					log.error(mfu.getMessage(), mfu);
				}
				this.styleSheetAggregator = ssa;
			}
			return ssa;
		}
	}

	/**
	 * <p>Getter for the field styleSheets.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.css.StyleSheetList} object.
	 */
	public StyleSheetList getStyleSheets() {
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

	/**
	 * This is called when the node has changed, but it is unclear if it's a size
	 * change or a look change. An attribute change should trigger this.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public void invalidated(final NodeImpl node) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (final DocumentNotificationListener dnl : listenersList) {
			dnl.invalidated(node);
		}
	}

	/**
	 * Loads the document from the reader provided when the current instance of
	 * HTMLDocumentImpl was constructed.
	 * @throws java.io.IOException if any.
	 * @throws org.xml.sax.SAXException if any.
	 * @throws java.io.UnsupportedEncodingException if any.
	 */
	public void load() throws IOException, SAXException, UnsupportedEncodingException {
		removeAllChildrenImpl();
		setTitle(null);
		setBaseURI(null);
		setDefaultTarget(null);
		this.styleSheets.clear();
		this.styleSheetAggregator = null;

		if (this.reader != null) {
			final XHtmlParser parser = new XHtmlParser(getUcontext(), document, true);
			parser.parse(reader);
		}
	}

	/**
	 * Called if something such as a color or decoration has changed. This would be
	 * something which does not affect the rendered size, and can be revalidated
	 * with a simple repaint.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public void lookInvalidated(final NodeImpl node) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (final DocumentNotificationListener dnl : listenersList) {
			dnl.lookInvalidated(node);
		}
	}

	/**
	 * <p>namedItem.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Element} object.
	 */
	public Element namedItem(final String name) {
		final Element element;
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
	public void nodeLoaded(final NodeImpl node) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (final DocumentNotificationListener dnl : listenersList) {
			dnl.nodeLoaded(node);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void normalizeDocument() {
		synchronized (this) {
			visitImpl(Node::normalize);
		}
	}

	/**
	 * Changed if the position of the node in a parent has changed.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public void positionInParentInvalidated(final NodeImpl node) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (final DocumentNotificationListener dnl : listenersList) {
			dnl.positionInvalidated(node);
		}
	}

	/**
	 * <p>removeDocumentNotificationListener.</p>
	 *
	 * @param listener a {@link DocumentNotificationListener} object.
	 */
	public void removeDocumentNotificationListener(final DocumentNotificationListener listener) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		synchronized (listenersList) {
			listenersList.remove(listener);
		}
	}

	public void removeNamedItem(final String name) {
		synchronized (this) {
			this.elementsByName.remove(name);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setBody(final HTMLElement body) {
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
	 * Caller should synchronize on document.
	 */
	public void setElementById(final String id, final Element element) {
		synchronized (this) {
			this.elementsById.put(id, element);
		}
	}


	public void setNamedItem(final String name, final Element element) {
		synchronized (this) {
			this.elementsByName.put(name, element);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setTextContent(final String textContent) {
		// NOP, per spec
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(final String key, final Object data, final UserDataHandler handler) {
		final Function onloadHandler = this.onloadHandler;
		if (onloadHandler != null) {
			if (XHtmlParser.MODIFYING_KEY.equals(key) && data == Boolean.FALSE) {
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
	public void sizeInvalidated(final NodeImpl node) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (final DocumentNotificationListener dnl : listenersList) {
			dnl.sizeInvalidated(node);
		}
	}

	/**
	 * This is called when children of the node might have changed.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public void structureInvalidated(final NodeImpl node) {
		final List<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (final DocumentNotificationListener dnl : listenersList) {
			dnl.structureInvalidated(node);
		}
	}
}
