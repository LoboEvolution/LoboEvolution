/*    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketPermission;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.common.Domains;
import org.loboevolution.common.Nodes;
import org.loboevolution.common.Urls;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.filter.AnchorFilter;
import org.loboevolution.html.dom.filter.BodyFilter;
import org.loboevolution.html.dom.filter.CommandFilter;
import org.loboevolution.html.dom.filter.ElementNameFilter;
import org.loboevolution.html.dom.filter.EmbedFilter;
import org.loboevolution.html.dom.filter.FormFilter;
import org.loboevolution.html.dom.filter.HeadFilter;
import org.loboevolution.html.dom.filter.ImageFilter;
import org.loboevolution.html.dom.filter.LinkFilter;
import org.loboevolution.html.dom.filter.ScriptFilter;
import org.loboevolution.html.io.WritableLineReader;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.js.Location;
import org.loboevolution.html.js.Window;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.StyleSheetRenderState;
import org.loboevolution.html.style.StyleSheetAggregator;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.mozilla.javascript.Function;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.views.DocumentView;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;
import com.gargoylesoftware.css.dom.CSSStyleSheetListImpl;

/**
 * Implementation of the W3C <code>HTMLDocument</code> interface.
 */
public class HTMLDocumentImpl extends DocumentImpl implements HTMLDocument, DocumentView {

	private class LocalWritableLineReader extends WritableLineReader {
		/**
		 * @param reader
		 */
		public LocalWritableLineReader(LineNumberReader reader) {
			super(reader);
		}

		/**
		 * @param reader
		 */
		public LocalWritableLineReader(Reader reader) {
			super(reader);
		}

		@Override
		public void write(String text) throws IOException {
			super.write(text);
			if ("".equals(text)) {
				openBufferChanged(text);
			}
		}
	}

	private static final Logger logger = Logger.getLogger(HTMLDocumentImpl.class.getName());

	private HTMLCollection anchors;

	private volatile String baseURI;

	private HTMLElement body;

	private String defaultTarget;

	private final ArrayList<DocumentNotificationListener> documentNotificationListeners = new ArrayList<DocumentNotificationListener>();

	private URL documentURL;

	private String domain;

	private DOMImplementation domImplementation;
	
    private final Map<String, Element> elementsById = new HashMap<String, Element>();

	private final Map<String, Element> elementsByName = new HashMap<String, Element>();

	private String inputEncoding;

	private Set<Locale> locales;

	private Function onloadHandler;

	private final HtmlRendererContext rcontext;

	private WritableLineReader reader;
	private String referrer;

	private StyleSheetAggregator styleSheetAggregator = null;
    private final CSSStyleSheetListImpl styleSheets = new CSSStyleSheetListImpl();
	private String title;

	private final UserAgentContext ucontext;

	private final Window window;

	private String xmlEncoding;

	public HTMLDocumentImpl(HtmlRendererContext rcontext) {
		this(rcontext.getUserAgentContext(), rcontext, null, null);
	}

	public HTMLDocumentImpl(UserAgentContext ucontext) {
		this(ucontext, null, null, null);
	}

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
			this.domain = docURL.getHost();
			this. setDocumentURI(documentURI);
		} catch (final MalformedURLException mfu) {
			logger.warning("HTMLDocumentImpl(): Document URI [" + documentURI + "] is malformed.");
		}
		this.document = this;
		// Get Window object
		Window window;
		if (rcontext != null) {
			window = Window.getWindow(rcontext);
		} else {
			// Plain parsers may use Javascript too.
			window = new Window(null, ucontext);
		}
		// Window must be retained or it will be garbage collected.
		this.window = window;
		window.setDocument(this);
	}

	/**
	 * Adds a document notification listener, which is informed about changes to the
	 * document.
	 * 
	 * @param listener An instance of {@link DocumentNotificationListener}.
	 */
	public void addDocumentNotificationListener(DocumentNotificationListener listener) {
		final ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
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
			for (Node node : Nodes.iterable(nodeList)) {
				if (node instanceof HTMLElementImpl) {
					((HTMLElementImpl) node).forgetStyle(true);
				}
			}
		}
		this.allInvalidated();
	}

	/**
	 * Informs listeners that the whole document has been invalidated.
	 */
	public void allInvalidated() {
		final ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.allInvalidated();
		}
	}

	public void allInvalidated(boolean forgetRenderStates) {
		if (forgetRenderStates) {
			synchronized (this.treeLock) {
				this.styleSheetAggregator = null;
				forgetRenderState();
				for (Node node : Nodes.iterable(nodeList)) {
					if (node instanceof HTMLElementImpl) {
						((HTMLElementImpl) node).forgetStyle(true);
					}
				}
			}
		}
		this.allInvalidated();
	}

	@Override
	public void close() {
		synchronized (this.treeLock) {
			if (this.reader instanceof LocalWritableLineReader) {
				try {
					this.reader.close();
				} catch (final java.io.IOException ioe) {
					// ignore
				}
				this.reader = null;
			} else {
				// do nothing - could be parsing document off the web.
			}
			// TODO: cause it to render
		}
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new StyleSheetRenderState(this);
	}


	public void externalScriptLoading(NodeImpl node) {
		final ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.externalScriptLoading(node);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getbaseURI()
	 */
	@Override
	public String getBaseURI() {
		final String buri = this.baseURI;
		return buri == null ? this.getDocumentURI() : buri;
	}

	@Override
	public String getCookie() {
		final SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			return (String) AccessController.doPrivileged((PrivilegedAction<Object>) () -> HTMLDocumentImpl.this.ucontext.getCookie(HTMLDocumentImpl.this.documentURL));
		} else {
			return this.ucontext.getCookie(this.documentURL);
		}
	}

	public String getDefaultTarget() {
		return this.defaultTarget;
	}

	@Override
	public AbstractView getDefaultView() {
		return this.window;
	}


	String getDocumentHost() {
		final URL docUrl = this.documentURL;
		return docUrl == null ? null : docUrl.getHost();
	}

	@Override
	public URL getDocumentURL() {
		return this.documentURL;
	}

	@Override
	public String getDomain() {
		return this.domain;
	}
	
	public String getReadyState() {
		return "complete"; //TODO stub
	}


	/**
	 * Gets the collection of elements whose <code>name</code> attribute is
	 * <code>elementName</code>.
	 */
	@Override
	public NodeList getElementsByName(String elementName) {
		return getNodeList(new ElementNameFilter(elementName));
	}

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

	@Override
	public final HtmlRendererContext getHtmlRendererContext() {
		return this.rcontext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#getImplementation()
	 */
	@Override
	public DOMImplementation getImplementation() {
		synchronized (this) {
			if (this.domImplementation == null) {
				this.domImplementation = new DOMImplementationImpl(this.ucontext);
			}
			return this.domImplementation;
		}
	}

	@Override
	public String getInputEncoding() {
		return this.inputEncoding;
	}

    
    @Override
    public HTMLCollection getForms() {
        synchronized (this) {
            return new HTMLCollectionImpl(this, new FormFilter());
        }
    }
    
    @Override
    public HTMLCollection getImages() {
        synchronized (this) {
            return new HTMLCollectionImpl(this, new ImageFilter());
        }
    }

    @Override
    public HTMLCollection getLinks() {
        synchronized (this) {
            return new HTMLCollectionImpl(this, new LinkFilter());
        }
    }
    
    @Override
    public HTMLCollection getEmbeds() {
        synchronized (this) {
            return new HTMLCollectionImpl(this, new EmbedFilter());
        }
    }

    @Override
    public HTMLCollection getPlugins() {
        return getEmbeds();
    }

    @Override
    public HTMLCollection getScripts() {
        synchronized (this) {
            return new HTMLCollectionImpl(this, new ScriptFilter());
        }
    }

    @Override
    public HTMLCollection getCommands() {
        synchronized (this) {
            return new HTMLCollectionImpl(this, new CommandFilter());
        }
    }
    
    @Override
	public HTMLCollection getAnchors() {
		synchronized (this) {
			if (this.anchors == null) {
				this.anchors = new HTMLCollectionImpl(this, new AnchorFilter());
			}
			return this.anchors;
		}
	}
    
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
	 */
	public Set<Locale> getLocales() {
		return this.locales;
	}

	
	public final Location getLocation() {
		return this.window.getLocation();
	}

	public Function getOnloadHandler() {
		return this.onloadHandler;
	}

	@Override
	public String getReferrer() {
		return this.referrer;
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

    public CSSStyleSheetListImpl getStyleSheets() {
		return this.styleSheets;
	}

	@Override
	public String getTextContent() throws DOMException {
		return null;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getURL() {
		return this.getDocumentURI();
	}

	@Override
	public UserAgentContext getUserAgentContext() {
		return this.ucontext;
	}

	@Override
	public String getXmlEncoding() {
		return this.xmlEncoding;
	}

	@Override
	public Node importNode(Node importedNode, boolean deep) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Not implemented");
	}

	/**
	 * This is called when the node has changed, but it is unclear if it's a size
	 * change or a look change. An attribute change should trigger this.
	 * 
	 * @param node
	 */
	public void invalidated(NodeImpl node) {
		final ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.invalidated(node);
		}
	}

	/**
	 * Loads the document from the reader provided when the current instance of
	 * <code>HTMLDocumentImpl</code> was constructed. It then closes the reader.
	 * 
	 * @throws IOException
	 * @throws SAXException
	 * @throws UnsupportedEncodingException
	 */
	public void load() throws IOException, SAXException, UnsupportedEncodingException {
		this.load(true);
	}

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
				final HtmlParser parser = new HtmlParser(ucontext, document, errorHandler, true);
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
	 * @param node
	 */
	public void lookInvalidated(NodeImpl node) {
		final ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.lookInvalidated(node);
		}
	}

	public Element namedItem(String name) {
		Element element;
		synchronized (this) {
			element = (Element) this.elementsByName.get(name);
		}
		return element;
	}

	public void nodeLoaded(NodeImpl node) {
		final ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.nodeLoaded(node);
		}
	}

	@Override
	public void normalizeDocument() {
		// TODO: Normalization options from domConfig
		synchronized (this.treeLock) {
			visitImpl(node -> node.normalize());
		}
	}

	@Override
	public void open() {
		synchronized (this.treeLock) {
			if (this.reader != null) {
				if (this.reader instanceof LocalWritableLineReader) {
					try {
						this.reader.close();
					} catch (final IOException ioe) {
						// ignore
					}
					this.reader = null;
				} else {
					// Already open, return.
					// Do not close http/file documents in progress.
					return;
				}
			}
			removeAllChildrenImpl();
			this.reader = new LocalWritableLineReader(new LineNumberReader(this.reader));
		}
	}

	private void openBufferChanged(String text) {
		// Assumed to execute in a lock
		// Assumed that text is not broken up HTML.
		final ErrorHandler errorHandler = new LocalErrorHandler();
		final HtmlParser parser = new HtmlParser(this.ucontext, this, errorHandler, true);
		final StringReader strReader = new StringReader(text);
		try {
			// This sets up another Javascript scope Window. Does it matter?
			parser.parse(strReader);
		} catch (final Exception err) {
			this.warn("Unable to parse written HTML text. BaseURI=[" + getBaseURI() + "].", err);
		}
	}

	/**
	 * Changed if the position of the node in a parent has changed.
	 * 
	 * @param node
	 */
	public void positionInParentInvalidated(NodeImpl node) {
		final ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.positionInvalidated(node);
		}
	}

	public void removeDocumentNotificationListener(DocumentNotificationListener listener) {
		final ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		synchronized (listenersList) {
			listenersList.remove(listener);
		}
	}

	void removeElementById(String id) {
		synchronized (this) {
			this.elementsById.remove(id);
		}
	}

	void removeNamedItem(String name) {
		synchronized (this) {
			this.elementsByName.remove(name);
		}
	}

	@Override
	public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "No renaming");
	}

	public void setBaseURI(String value) {
		this.baseURI = value;
	}

	@Override
	public void setBody(HTMLElement body) {
		synchronized (this) {
			this.body = body;
		}
	}

	@Override
	public void setCookie(final String cookie) throws DOMException {
		final SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
				HTMLDocumentImpl.this.ucontext.setCookie(HTMLDocumentImpl.this.documentURL, cookie);
				return null;
			});
		} else {
			this.ucontext.setCookie(this.documentURL, cookie);
		}
	}

	public void setDefaultTarget(String value) {
		this.defaultTarget = value;
	}


	public void setDomain(String domain) {
		final String oldDomain = this.domain;
		if (oldDomain != null && Domains.isValidCookieDomain(domain, oldDomain)) {
			this.domain = domain;
		} else {
			throw new SecurityException(
					"Cannot set domain to '" + domain + "' when current domain is '" + oldDomain + "'");
		}
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
	 * @param locales An <i>immutable</i> set of <code>java.util.Locale</code>
	 *                instances.
	 */
	public void setLocales(Set<Locale> locales) {
		this.locales = locales;
	}

	public void setLocation(String location) {
		getLocation().setHref(location);
	}

	void setNamedItem(String name, Element element) {
		synchronized (this) {
			this.elementsByName.put(name, element);
		}
	}

	public void setOnloadHandler(Function onloadHandler) {
		this.onloadHandler = onloadHandler;
	}

	public void setReferrer(String value) {
		this.referrer = value;
	}
	
	@Override
	public void setTextContent(String textContent) throws DOMException {
		// NOP, per spec
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

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

	public void sizeInvalidated(NodeImpl node) {
		final ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.sizeInvalidated(node);
		}
	}

	/**
	 * This is called when children of the node might have changed.
	 * 
	 * @param node
	 */
	public void structureInvalidated(NodeImpl node) {
		final ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.structureInvalidated(node);
		}
	}

	@Override
	public void write(String text) {
		synchronized (this.treeLock) {
			if (this.reader != null) {
				try {
					// This can end up in openBufferChanged
					this.reader.write(text);
				} catch (final IOException ioe) {
					// ignore
				}
			}
		}
	}

	@Override
	public void writeln(String text) {
		synchronized (this.treeLock) {
			if (this.reader != null) {
				try {
					// This can end up in openBufferChanged
					this.reader.write(text + "\r\n");
				} catch (final IOException ioe) {
					// ignore
				}
			}
		}
	}
}
