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
/*
 * Created on Sep 3, 2005
 */
package org.lobobrowser.html.domimpl;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketPermission;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.dombl.DescendentHTMLCollection;
import org.lobobrowser.html.dombl.DocumentNotificationListener;
import org.lobobrowser.html.dombl.ElementFactory;
import org.lobobrowser.html.dombl.ImageEvent;
import org.lobobrowser.html.dombl.ImageListener;
import org.lobobrowser.html.dombl.LocalErrorHandler;
import org.lobobrowser.html.domfilter.AnchorFilter;
import org.lobobrowser.html.domfilter.AppletFilter;
import org.lobobrowser.html.domfilter.CommandFilter;
import org.lobobrowser.html.domfilter.ElementNameFilter;
import org.lobobrowser.html.domfilter.EmbedFilter;
import org.lobobrowser.html.domfilter.FormFilter;
import org.lobobrowser.html.domfilter.FrameFilter;
import org.lobobrowser.html.domfilter.ImageFilter;
import org.lobobrowser.html.domfilter.LinkFilter;
import org.lobobrowser.html.domfilter.PluginsFilter;
import org.lobobrowser.html.domfilter.ScriptFilter;
import org.lobobrowser.html.info.ImageInfo;
import org.lobobrowser.html.io.WritableLineReader;
import org.lobobrowser.html.js.Executor;
import org.lobobrowser.html.js.Location;
import org.lobobrowser.html.js.Window;
import org.lobobrowser.html.parser.HtmlParser;
import org.lobobrowser.html.style.StyleSheetAggregator;
import org.lobobrowser.http.HttpRequest;
import org.lobobrowser.http.Method;
import org.lobobrowser.http.ReadyState;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.util.Domains;
import org.lobobrowser.util.SSLCertificate;
import org.lobobrowser.util.Urls;
import org.lobobrowser.util.WeakValueHashMap;
import org.lobobrowser.util.io.EmptyReader;
import org.lobobrowser.w3c.events.DocumentEvent;
import org.lobobrowser.w3c.html.HTMLCollection;
import org.lobobrowser.w3c.html.HTMLDocument;
import org.lobobrowser.w3c.html.HTMLElement;
import org.lobobrowser.w3c.html.HTMLHeadElement;
import org.lobobrowser.w3c.xpath.XPathEvaluator;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.StyleSheetList;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.views.DocumentView;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import com.steadystate.css.dom.CSSStyleSheetListImpl;

/**
 * The Class HTMLDocumentImpl.
 */
public class HTMLDocumentImpl extends DOMFunctionImpl implements HTMLDocument, DocumentView, DocumentEvent, XPathEvaluator {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(HTMLDocumentImpl.class.getName());

	/** The factory. */
	private final ElementFactory factory;

	/** The rcontext. */
	private final HtmlRendererContext rcontext;

	/** The ucontext. */
	private final UserAgentContext ucontext;

	/** The window. */
	private final Window window;

	/** The elements by id. */
	private final Map<Object, Object> elementsById = new WeakValueHashMap();

	/** The elements by name. */
	private final Map<String, Element> elementsByName = new HashMap<String, Element>(0);

	/** The style sheets. */
	private final CSSStyleSheetListImpl styleSheets = new CSSStyleSheetListImpl();

	/** The image infos. */
	private final Map<String, ImageInfo> imageInfos = new HashMap<String, ImageInfo>(4);

	/** The document notification listeners. */
	private final ArrayList<DocumentNotificationListener> documentNotificationListeners = new ArrayList<DocumentNotificationListener>(1);

	/** The blank image event. */
	private final ImageEvent BLANK_IMAGE_EVENT = new ImageEvent(this, null);

	/** The document url. */
	private URL documentURL;

	/** The reader. */
	private WritableLineReader reader;

	/** The doctype. */
	private DocumentType doctype;

	/** The body. */
	private HTMLElement body;

	/** The images. */
	private HTMLCollection images;

	/** The applets. */
	private HTMLCollection applets;

	/** The links. */
	private HTMLCollection links;

	/** The forms. */
	private HTMLCollection forms;

	/** The anchors. */
	private HTMLCollection anchors;

	/** The frames. */
	private HTMLCollection frames;

	/** The embeds. */
	private HTMLCollection embeds;

	/** The scripts. */
	private HTMLCollection scripts;

	/** The plugins. */
	private HTMLCollection plugins;

	/** The commands. */
	private HTMLCollection commands;

	/** The style sheet aggregator. */
	private StyleSheetAggregator styleSheetAggregator = null;

	/** The dom config. */
	private DOMConfiguration domConfig;

	/** The dom implementation. */
	private DOMImplementation domImplementation;

	/** The locales. */
	private Set<?> locales;

	/** The base uri. */
	private volatile String baseURI;

	/** The default target. */
	private String defaultTarget;

	/** The title. */
	private String title;

	/** The document uri. */
	private String documentURI;

	/** The referrer. */
	private String referrer;

	/** The domain. */
	private String domain;

	/** The input encoding. */
	private String inputEncoding;

	/** The xml encoding. */
	private String xmlEncoding;

	/** The xml version. */
	private String xmlVersion = null;

	/** The xml standalone. */
	private boolean xmlStandalone;

	/** The strict error checking. */
	private boolean strictErrorChecking = true;
	
	/**
	 * Instantiates a new HTML document impl.
	 *
	 * @param rcontext
	 *            the rcontext
	 */
	public HTMLDocumentImpl(HtmlRendererContext rcontext) {
		this(rcontext.getUserAgentContext(), rcontext, null, null);
	}

	/**
	 * Instantiates a new HTML document impl.
	 *
	 * @param ucontext
	 *            the ucontext
	 */
	public HTMLDocumentImpl(UserAgentContext ucontext) {
		this(ucontext, null, null, null);
	}

	/**
	 * Instantiates a new HTML document impl.
	 *
	 * @param ucontext
	 *            the ucontext
	 * @param rcontext
	 *            the rcontext
	 * @param reader
	 *            the reader
	 * @param documentURI
	 *            the document uri
	 */
	public HTMLDocumentImpl(final UserAgentContext ucontext, final HtmlRendererContext rcontext,
			WritableLineReader reader, String documentURI) {
		this.factory = ElementFactory.getInstance();
		this.rcontext = rcontext;
		this.ucontext = ucontext;
		this.reader = reader;
		this.documentURI = documentURI;

		if (documentURI != null) {
			try {
				URL docURL = new URL(documentURI);
				SecurityManager sm = System.getSecurityManager();
				if (sm != null) {
					// Do not allow creation of HTMLDocumentImpl if there's
					// no permission to connect to the host of the URL.
					// This is so that cookies cannot be written arbitrarily
					// with setCookie() method.
					sm.checkPermission(new SocketPermission(docURL.getHost(), "connect"));
				}
				this.documentURL = docURL;
				this.domain = docURL.getHost();
			} catch (MalformedURLException mfu) {
				logger.warn("HTMLDocumentImpl(): Document URI [" + documentURI + "] is malformed.");
			}
		}
		this.document = this;
		// Get Window object
		Window window;
		if (rcontext != null) {
			window = Window.getWindow(rcontext);
			window.setDocument(this);
			// Set up Javascript scope
			this.setUserData(Executor.SCOPE_KEY, window.getWindowScope(), null);
		} else {
			// Plain parsers may use Javascript too.
			window = null;// new Window(null, ucontext);
		}
		this.window = window;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getCookie()
	 */
	@Override
	public String getCookie() {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			return (String) AccessController
					.doPrivileged((PrivilegedAction<Object>) () -> ucontext.getCookie(documentURL));
		} else {
			return this.ucontext.getCookie(this.documentURL);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#setCookie(java.lang.String)
	 */
	@Override
	public void setCookie(final String cookie) throws DOMException {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
				ucontext.setCookie(documentURL, cookie);
				return null;
			});
		} else {
			this.ucontext.setCookie(this.documentURL, cookie);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#open()
	 */
	@Override
	public void open() {
		synchronized (this.getTreeLock()) {
			if (this.reader != null) {
				if (this.reader instanceof LocalWritableLineReader) {
					try {
						this.reader.close();
					} catch (IOException ioe) {
						// ignore
					}
					this.reader = null;
				} else {
					// Already open, return.
					// Do not close http/file documents in progress.
					return;
				}
			}
			this.removeAllChildrenImpl();
			this.reader = new LocalWritableLineReader(new EmptyReader());
		}
	}

	/**
	 * Loads the document from the reader provided when the current instance of
	 * <code>HTMLDocumentImpl</code> was constructed. It then closes the reader.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SAXException
	 *             the SAX exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public void load() throws IOException, SAXException, UnsupportedEncodingException {
		this.load(true);
	}

	/**
	 * Load.
	 *
	 * @param closeReader
	 *            the close reader
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SAXException
	 *             the SAX exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public void load(boolean closeReader) throws IOException, SAXException, UnsupportedEncodingException {
		WritableLineReader reader;
		synchronized (this.getTreeLock()) {
			this.removeAllChildrenImpl();
			this.setTitle(null);
			this.setBaseURI(null);
			this.setDefaultTarget(null);
			this.styleSheets.getCSSStyleSheets().clear();
			this.styleSheetAggregator = null;
			reader = this.reader;
		}
		if (reader != null) {
			try {
				ErrorHandler errorHandler = new LocalErrorHandler();
				String systemId = this.documentURI;
				String publicId = systemId;
				HtmlParser parser = new HtmlParser(this.ucontext, this, errorHandler, publicId, systemId);
				parser.parse(reader);
			} finally {
				if (closeReader) {
					try {
						reader.close();
					} catch (Exception err) {
						logger.error("load(): Unable to close stream", err);
					}
					synchronized (this.getTreeLock()) {
						this.reader = null;
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#close()
	 */
	@Override
	public void close() {
		synchronized (this.getTreeLock()) {
			if (this.reader instanceof LocalWritableLineReader) {
				try {
					this.reader.close();
				} catch (IOException ioe) {
					// ignore
				}
				this.reader = null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#write(java.lang.String)
	 */
	@Override
	public void write(String text) {
		synchronized (this.getTreeLock()) {
			if (this.reader != null) {
				try {
					// This can end up in openBufferChanged
					this.reader.write(text);
				} catch (IOException ioe) {
					// ignore
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#writeln(java.lang.String)
	 */
	@Override
	public void writeln(String text) {
		synchronized (this.getTreeLock()) {
			if (this.reader != null) {
				try {
					// This can end up in openBufferChanged
					this.reader.write(text + "\r\n");
				} catch (IOException ioe) {
					// ignore
				}
			}
		}
	}

	/**
	 * Open buffer changed.
	 *
	 * @param text
	 *            the text
	 */
	private void openBufferChanged(String text) {
		// Assumed to execute in a lock
		// Assumed that text is not broken up HTML.
		ErrorHandler errorHandler = new LocalErrorHandler();
		String systemId = this.documentURI;
		String publicId = systemId;
		HtmlParser parser = new HtmlParser(this.ucontext, this, errorHandler, publicId, systemId);
		StringReader strReader = new StringReader(text);
		try {
			// This sets up another Javascript scope Window. Does it matter?
			parser.parse(strReader);
		} catch (Exception err) {
			logger.error("Unable to parse written HTML text. BaseURI=[" + this.getBaseURI() + "].", err);
		}
	}

	/**
	 * Gets the collection of elements whose <code>name</code> attribute is
	 * <code>elementName</code>.
	 *
	 * @param elementName
	 *            the element name
	 * @return the elements by name
	 */
	@Override
	public NodeList getElementsByName(String elementName) {
		return this.getNodeList(new ElementNameFilter(elementName));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#getDocumentElement()
	 */
	@Override
	public Element getDocumentElement() {
		synchronized (this.getTreeLock()) {
			ArrayList<?> nl = this.nodeList;
			if (nl != null) {
				Iterator<?> i = nl.iterator();
				while (i.hasNext()) {
					Object node = i.next();
					if (node instanceof Element) {
						return (Element) node;
					}
				}
			}
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#createElement(java.lang.String)
	 */
	@Override
	public Element createElement(String tagName) throws DOMException {
		return this.factory.createElement(this, tagName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#getElementById(java.lang.String)
	 */
	@Override
	public Element getElementById(String elementId) {
		if (elementId != null && elementId.length() > 0) {
			synchronized (this) {
				return (Element) this.elementsById.get(elementId);
			}
		} else {
			return null;
		}
	}

	/**
	 * Named item.
	 *
	 * @param name
	 *            the name
	 * @return the element
	 */
	public Element namedItem(String name) {
		Element element;
		synchronized (this) {
			element = this.elementsByName.get(name);
		}
		return element;
	}

	/**
	 * Sets the named item.
	 *
	 * @param name
	 *            the name
	 * @param element
	 *            the element
	 */
	public void setNamedItem(String name, Element element) {
		synchronized (this) {
			this.elementsByName.put(name, element);
		}
	}

	/**
	 * Removes the named item.
	 *
	 * @param name
	 *            the name
	 */
	public void removeNamedItem(String name) {
		synchronized (this) {
			this.elementsByName.remove(name);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#getDomConfig()
	 */
	@Override
	public DOMConfiguration getDomConfig() {
		synchronized (this) {
			if (this.domConfig == null) {
				this.domConfig = new DOMConfigurationImpl();
			}
			return this.domConfig;
		}
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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getHtmlRendererContext()
	 */
	@Override
	public final HtmlRendererContext getHtmlRendererContext() {
		return this.rcontext;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getUserAgentContext()
	 */
	@Override
	public UserAgentContext getUserAgentContext() {
		return this.ucontext;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.lobobrowser.html.domimpl.DOMNodeImpl#getFullURL(java.lang.String)
	 */
	@Override
	public final URL getFullURL(String uri) {
		try {
			String baseURI = this.getBaseURI();
			URL documentURL = baseURI == null ? null : new URL(baseURI);
			return Urls.createURL(documentURL, uri);
		} catch (MalformedURLException | UnsupportedEncodingException mfu) {
			// Try agan, without the baseURI.
			try {
				return new URL(uri);
			} catch (MalformedURLException mfu2) {
				logger.error("Unable to create URL for URI=[" + uri + "], with base=[" + this.getBaseURI() + "].", mfu);
				return null;
			}
		}
	}
	
	public static void main(String[] args) {
		String uri ="smiley.gif";
		try {
			String baseURI = "prova";
			URL documentURL = baseURI == null ? null : new URL(baseURI);
			Urls.createURL(documentURL, uri);
		} catch (MalformedURLException | UnsupportedEncodingException mfu) {
			// Try agan, without the baseURI.
			try {
				System.out.println(new URL(uri));
			} catch (MalformedURLException mfu2) {
				System.out.println(mfu2);
			}
		}
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	@Override
	public final Location getLocation() {
		return this.window.getLocation();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#setLocation(java.lang.String)
	 */
	@Override
	public void setLocation(String location) {
		this.getLocation().setHref(location);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getURL()
	 */
	@Override
	public String getURL() {
		return this.documentURI;
	}

	/**
	 * Adds the style sheet.
	 *
	 * @param ss
	 *            the ss
	 */
	public final void addStyleSheet(CSSStyleSheet ss) {
		synchronized (this.getTreeLock()) {
			this.styleSheets.add(ss);
			this.styleSheetAggregator = null;
			this.forgetRenderState();
			ArrayList<?> nl = this.nodeList;
			if (nl != null) {
				Iterator<?> i = nl.iterator();
				while (i.hasNext()) {
					Object node = i.next();
					if (node instanceof HTMLElementImpl) {
						((HTMLElementImpl) node).forgetStyle(true);
					}
				}
			}
		}
		this.allInvalidated();
	}

	/**
	 * All invalidated.
	 *
	 * @param forgetRenderStates
	 *            the forget render states
	 */
	public void allInvalidated(boolean forgetRenderStates) {
		if (forgetRenderStates) {
			synchronized (this.getTreeLock()) {
				this.styleSheetAggregator = null;
				this.forgetRenderState();
				ArrayList<?> nl = this.nodeList;
				if (nl != null) {
					Iterator<?> i = nl.iterator();
					while (i.hasNext()) {
						Object node = i.next();
						if (node instanceof HTMLElementImpl) {
							((HTMLElementImpl) node).forgetStyle(true);
						}
					}
				}
			}
		}
		this.allInvalidated();
	}

	/**
	 * Gets the style sheets.
	 *
	 * @return the style sheets
	 */
	public StyleSheetList getStyleSheets() {
		return this.styleSheets;
	}

	/**
	 * Gets the style sheet aggregator.
	 *
	 * @return the style sheet aggregator
	 */
	public final StyleSheetAggregator getStyleSheetAggregator() {
		synchronized (this.getTreeLock()) {
			StyleSheetAggregator ssa = this.styleSheetAggregator;
			if (ssa == null) {
				ssa = new StyleSheetAggregator(this);
				try {
					ssa.addStyleSheets(this.styleSheets.getCSSStyleSheets());
				} catch (MalformedURLException | UnsupportedEncodingException mfu) {
					logger.error("getStyleSheetAggregator()", mfu);
				}
				this.styleSheetAggregator = ssa;
			}
			return ssa;
		}
	}

	/**
	 * Adds a document notification listener, which is informed about changes to
	 * the document.
	 *
	 * @param listener
	 *            An instance of {@link DocumentNotificationListener}.
	 */
	public void addDocumentNotificationListener(DocumentNotificationListener listener) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		synchronized (listenersList) {
			listenersList.add(listener);
		}
	}

	/**
	 * Removes the document notification listener.
	 *
	 * @param listener
	 *            the listener
	 */
	public void removeDocumentNotificationListener(DocumentNotificationListener listener) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		synchronized (listenersList) {
			listenersList.remove(listener);
		}
	}

	/**
	 * Size invalidated.
	 *
	 * @param node
	 *            the node
	 */
	public void sizeInvalidated(DOMNodeImpl node) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		int size;
		synchronized (listenersList) {
			size = listenersList.size();
		}
		// Traverse list outside synchronized block.
		// (Shouldn't call listener methods in synchronized block.
		// Deadlock is possible). But assume list could have
		// been changed.
		for (int i = 0; i < size; i++) {
			try {
				DocumentNotificationListener dnl = listenersList.get(i);
				dnl.sizeInvalidated(node);
			} catch (IndexOutOfBoundsException iob) {
				// ignore
			}
		}
	}

	/**
	 * Called if something such as a color or decoration has changed. This would
	 * be something which does not affect the rendered size, and can be
	 * revalidated with a simple repaint.
	 *
	 * @param node
	 *            the node
	 */
	public void lookInvalidated(DOMNodeImpl node) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		int size;
		synchronized (listenersList) {
			size = listenersList.size();
		}
		// Traverse list outside synchronized block.
		// (Shouldn't call listener methods in synchronized block.
		// Deadlock is possible). But assume list could have
		// been changed.
		for (int i = 0; i < size; i++) {
			try {
				DocumentNotificationListener dnl = listenersList.get(i);
				dnl.lookInvalidated(node);
			} catch (IndexOutOfBoundsException iob) {
				// ignore
			}
		}

	}

	/**
	 * Changed if the position of the node in a parent has changed.
	 *
	 * @param node
	 *            the node
	 */
	public void positionInParentInvalidated(DOMNodeImpl node) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		int size;
		synchronized (listenersList) {
			size = listenersList.size();
		}
		// Traverse list outside synchronized block.
		// (Shouldn't call listener methods in synchronized block.
		// Deadlock is possible). But assume list could have
		// been changed.
		for (int i = 0; i < size; i++) {
			try {
				DocumentNotificationListener dnl = listenersList.get(i);
				dnl.positionInvalidated(node);
			} catch (IndexOutOfBoundsException iob) {
				// ignore
			}
		}
	}

	/**
	 * This is called when the node has changed, but it is unclear if it's a
	 * size change or a look change. An attribute change should trigger this.
	 *
	 * @param node
	 *            the node
	 */
	public void invalidated(DOMNodeImpl node) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		int size;
		synchronized (listenersList) {
			size = listenersList.size();
		}
		// Traverse list outside synchronized block.
		// (Shouldn't call listener methods in synchronized block.
		// Deadlock is possible). But assume list could have
		// been changed.
		for (int i = 0; i < size; i++) {
			try {
				DocumentNotificationListener dnl = listenersList.get(i);
				dnl.invalidated(node);
			} catch (IndexOutOfBoundsException iob) {
				// ignore
			}
		}
	}

	/**
	 * This is called when children of the node might have changed.
	 *
	 * @param node
	 *            the node
	 */
	public void structureInvalidated(DOMNodeImpl node) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		int size;
		synchronized (listenersList) {
			size = listenersList.size();
		}
		// Traverse list outside synchronized block.
		// (Shouldn't call listener methods in synchronized block.
		// Deadlock is possible). But assume list could have
		// been changed.
		for (int i = 0; i < size; i++) {
			try {
				DocumentNotificationListener dnl = listenersList.get(i);
				dnl.structureInvalidated(node);
			} catch (IndexOutOfBoundsException iob) {
				// ignore
			}
		}
	}

	/**
	 * Node loaded.
	 *
	 * @param node
	 *            the node
	 */
	public void nodeLoaded(DOMNodeImpl node) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		int size;
		synchronized (listenersList) {
			size = listenersList.size();
		}
		// Traverse list outside synchronized block.
		// (Shouldn't call listener methods in synchronized block.
		// Deadlock is possible). But assume list could have
		// been changed.
		for (int i = 0; i < size; i++) {
			try {
				DocumentNotificationListener dnl = listenersList.get(i);
				dnl.nodeLoaded(node);
			} catch (IndexOutOfBoundsException iob) {
				// ignore
			}
		}
	}

	/**
	 * External script loading.
	 *
	 * @param node
	 *            the node
	 */
	public void externalScriptLoading(DOMNodeImpl node) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		int size;
		synchronized (listenersList) {
			size = listenersList.size();
		}
		// Traverse list outside synchronized block.
		// (Shouldn't call listener methods in synchronized block.
		// Deadlock is possible). But assume list could have
		// been changed.
		for (int i = 0; i < size; i++) {
			try {
				DocumentNotificationListener dnl = listenersList.get(i);
				dnl.externalScriptLoading(node);
			} catch (IndexOutOfBoundsException iob) {
				// ignore
			}
		}
	}

	/**
	 * Informs listeners that the whole document has been invalidated.
	 */
	public void allInvalidated() {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		int size;
		synchronized (listenersList) {
			size = listenersList.size();
		}
		// Traverse list outside synchronized block.
		// (Shouldn't call listener methods in synchronized block.
		// Deadlock is possible). But assume list could have
		// been changed.
		for (int i = 0; i < size; i++) {
			try {
				DocumentNotificationListener dnl = listenersList.get(i);
				dnl.allInvalidated();
			} catch (IndexOutOfBoundsException iob) {
				// ignore
			}
		}
	}

	/**
	 * Loads images asynchronously such that they are shared if loaded
	 * simultaneously from the same URI. Informs the listener immediately if an
	 * image is already known.
	 *
	 * @param relativeUri
	 *            the relative uri
	 * @param imageListener
	 *            the image listener
	 */
	protected void loadImage(String relativeUri, ImageListener imageListener) {
		HtmlRendererContext rcontext = this.getHtmlRendererContext();
		if (rcontext == null || !rcontext.isImageLoadingEnabled()) {
			// Ignore image loading when there's no renderer context.
			// Consider Cobra users who are only using the parser.
			imageListener.imageLoaded(BLANK_IMAGE_EVENT);
			return;
		}
		final URL url = this.getFullURL(relativeUri);
		if (url == null) {
			imageListener.imageLoaded(BLANK_IMAGE_EVENT);
			return;
		}
		final String urlText = url.toExternalForm();
		final Map<String, ImageInfo> map = this.imageInfos;
		ImageEvent event = null;
		synchronized (map) {
			ImageInfo info = map.get(urlText);
			if (info != null) {
				if (info.loaded) {
					event = info.imageEvent;
				} else {
					info.addListener(imageListener);
				}
			} else {
				UserAgentContext uac = rcontext.getUserAgentContext();
				final HttpRequest httpRequest = uac.createHttpRequest();
				final ImageInfo newInfo = new ImageInfo();
				map.put(urlText, newInfo);
				newInfo.addListener(imageListener);
				httpRequest.addReadyStateChangeListener(evt -> {
					if (httpRequest.getReadyState() == ReadyState.COMPLETE) {
						java.awt.Image newImage = httpRequest.getResponseImage();
						ImageEvent newEvent = newImage == null ? null : new ImageEvent(HTMLDocumentImpl.this, newImage);
						ImageListener[] listeners;
						synchronized (map) {
							newInfo.imageEvent = newEvent;
							newInfo.loaded = true;
							listeners = newEvent == null ? null : newInfo.getListeners();
							map.remove(urlText);
						}
						if (listeners != null) {
							int llength = listeners.length;
							for (int i = 0; i < llength; i++) {
								listeners[i].imageLoaded(newEvent);
							}
						}
					}

				});
				SecurityManager sm = System.getSecurityManager();
				if (sm == null) {
					httpRequest.open(Method.GET, url, true);
					httpRequest.send();
				} else {
					AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
						httpRequest.open(Method.GET, url, true);
						httpRequest.send();
						return null;
					});
				}
			}
		}
		if (event != null) {
			// Call holding no locks.
			imageListener.imageLoaded(event);
		}
	}
	

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#createSimilarNode()
	 */
	@Override
	protected Node createSimilarNode() {
		return new HTMLDocumentImpl(this.ucontext, this.rcontext, this.reader, this.documentURI);
	}

	/**
	 * The Class LocalWritableLineReader.
	 */
	private class LocalWritableLineReader extends WritableLineReader {

		/**
		 * Instantiates a new local writable line reader.
		 *
		 * @param reader
		 *            the reader
		 */
		public LocalWritableLineReader(LineNumberReader reader) {
			super(reader);
		}

		/**
		 * Instantiates a new local writable line reader.
		 *
		 * @param reader
		 *            the reader
		 */
		public LocalWritableLineReader(Reader reader) {
			super(reader);
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * org.lobobrowser.html.io.WritableLineReader#write(java.lang.String)
		 */
		@Override
		public void write(String text) throws IOException {
			super.write(text);
			if ("".equals(text)) {
				openBufferChanged(text);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getBody()
	 */
	@Override
	public HTMLElement getBody() {
		synchronized (this) {
			return this.body;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.lobobrowser.w3c.html.HTMLDocument#setBody(org.lobobrowser.w3c.html.
	 * HTMLElement)
	 */
	@Override
	public void setBody(HTMLElement body) {
		synchronized (this) {
			this.body = body;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getReferrer()
	 */
	@Override
	public String getReferrer() {
		return this.referrer;
	}

	/**
	 * Sets the referrer.
	 *
	 * @param value
	 *            the new referrer
	 */
	public void setReferrer(String value) {
		this.referrer = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getDomain()
	 */
	@Override
	public String getDomain() {
		return this.domain;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#setDomain(java.lang.String)
	 */
	@Override
	public void setDomain(String domain) {
		String oldDomain = this.domain;
		if (oldDomain != null && Domains.isValidCookieDomain(domain, oldDomain)) {
			this.domain = domain;
		} else {
			throw new SecurityException(
					"Cannot set domain to '" + domain + "' when current domain is '" + oldDomain + "'");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getImages()
	 */
	@Override
	public HTMLCollection getImages() {
		synchronized (this) {
			if (this.images == null) {
				this.images = new DescendentHTMLCollection(this, new ImageFilter(), this.getTreeLock());
			}
			return this.images;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getApplets()
	 */
	@Override
	public HTMLCollection getApplets() {
		synchronized (this) {
			if (this.applets == null) {
				this.applets = new DescendentHTMLCollection(this, new AppletFilter(), this.getTreeLock());
			}
			return this.applets;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getLinks()
	 */
	@Override
	public HTMLCollection getLinks() {
		synchronized (this) {
			if (this.links == null) {
				this.links = new DescendentHTMLCollection(this, new LinkFilter(), this.getTreeLock());
			}
			return this.links;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getForms()
	 */
	@Override
	public HTMLCollection getForms() {
		synchronized (this) {
			if (this.forms == null) {
				this.forms = new DescendentHTMLCollection(this, new FormFilter(), this.getTreeLock());
			}
			return this.forms;
		}
	}

	/**
	 * Gets the frames.
	 *
	 * @return the frames
	 */
	public HTMLCollection getFrames() {
		synchronized (this) {
			if (this.frames == null) {
				this.frames = new DescendentHTMLCollection(this, new FrameFilter(), this.getTreeLock());
			}
			return this.frames;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getAnchors()
	 */
	@Override
	public HTMLCollection getAnchors() {
		synchronized (this) {
			if (this.anchors == null) {
				this.anchors = new DescendentHTMLCollection(this, new AnchorFilter(), this.getTreeLock());
			}
			return this.anchors;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#getDoctype()
	 */
	@Override
	public DocumentType getDoctype() {
		return this.doctype;
	}

	/**
	 * Sets the doctype.
	 *
	 * @param doctype
	 *            the new doctype
	 */
	public void setDoctype(DocumentType doctype) {
		this.doctype = doctype;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#getInputEncoding()
	 */
	@Override
	public String getInputEncoding() {
		return this.inputEncoding;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#getXmlEncoding()
	 */
	@Override
	public String getXmlEncoding() {
		return this.xmlEncoding;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#getXmlStandalone()
	 */
	@Override
	public boolean getXmlStandalone() {
		return this.xmlStandalone;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#setXmlStandalone(boolean)
	 */
	@Override
	public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
		this.xmlStandalone = xmlStandalone;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#getXmlVersion()
	 */
	@Override
	public String getXmlVersion() {
		return this.xmlVersion;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#setXmlVersion(java.lang.String)
	 */
	@Override
	public void setXmlVersion(String xmlVersion) throws DOMException {
		this.xmlVersion = xmlVersion;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#getStrictErrorChecking()
	 */
	@Override
	public boolean getStrictErrorChecking() {
		return this.strictErrorChecking;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#setStrictErrorChecking(boolean)
	 */
	@Override
	public void setStrictErrorChecking(boolean strictErrorChecking) {
		this.strictErrorChecking = strictErrorChecking;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#getDocumentURI()
	 */
	@Override
	public String getDocumentURI() {
		return this.documentURI;
	}

	/**
	 * Gets the locales.
	 *
	 * @return the locales
	 */
	public Set<?> getLocales() {
		return locales;
	}

	/**
	 * Sets the locales.
	 *
	 * @param locales
	 *            the new locales
	 */
	public void setLocales(Set<?> locales) {
		this.locales = locales;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#getbaseURI()
	 */
	@Override
	public String getBaseURI() {
		String buri = this.baseURI;
		return buri == null ? this.documentURI : buri;
	}

	/**
	 * Sets the base uri.
	 *
	 * @param value
	 *            the new base uri
	 */
	public void setBaseURI(String value) {
		this.baseURI = value;
	}

	/**
	 * Gets the default target.
	 *
	 * @return the default target
	 */
	public String getDefaultTarget() {
		return this.defaultTarget;
	}

	/**
	 * Sets the default target.
	 *
	 * @param value
	 *            the new default target
	 */
	public void setDefaultTarget(String value) {
		this.defaultTarget = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.views.DocumentView#getDefaultView()
	 */
	@Override
	public AbstractView getDefaultView() {
		return this.window;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getTitle()
	 */
	@Override
	public String getTitle() {
		return this.title;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getDocumentURL()
	 */
	@Override
	public URL getDocumentURL() {
		return this.documentURL;
	}

	/**
	 * Caller should synchronize on document.
	 *
	 * @param id
	 *            the id
	 * @param element
	 *            the element
	 */
	public void setElementById(String id, Element element) {
		synchronized (this) {
			this.elementsById.put(id, element);
		}
	}

	/**
	 * Removes the element by id.
	 *
	 * @param id
	 *            the id
	 */
	public void removeElementById(String id) {
		synchronized (this) {
			this.elementsById.remove(id);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#setDocumentURI(java.lang.String)
	 */
	@Override
	public void setDocumentURI(String documentURI) {
		this.documentURI = documentURI;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getLastModified()
	 */
	@Override
	public String getLastModified() {

		String result = "";
		try {
			SSLCertificate.setCertificate();
			URL docURL = new URL(documentURI);
			URLConnection connection = docURL.openConnection();
			result = connection.getHeaderField("Last-Modified");
		} catch (NullPointerException npe) {
			logger.error("Header not found");
		} catch (Exception e) {
			logger.error("Connection error: " + e);
		}

		return result;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getCompatMode()
	 */
	@Override
	public String getCompatMode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getHead()
	 */
	@Override
	public HTMLHeadElement getHead() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getEmbeds()
	 */
	@Override
	public HTMLCollection getEmbeds() {
		synchronized (this) {
			if (this.embeds == null) {
				this.embeds = new DescendentHTMLCollection(this, new EmbedFilter(), this.getTreeLock());
			}
			return this.embeds;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getPlugins()
	 */
	@Override
	public HTMLCollection getPlugins() {
		synchronized (this) {
			if (this.plugins == null) {
				this.plugins = new DescendentHTMLCollection(this, new PluginsFilter(), this.getTreeLock());
			}
			return this.plugins;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getScripts()
	 */
	@Override
	public HTMLCollection getScripts() {
		synchronized (this) {
			if (this.scripts == null) {
				this.scripts = new DescendentHTMLCollection(this, new ScriptFilter(), this.getTreeLock());
			}
			return this.scripts;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getCommands()
	 */
	@Override
	public HTMLCollection getCommands() {
		synchronized (this) {
			if (this.commands == null) {
				this.commands = new DescendentHTMLCollection(this, new CommandFilter(), this.getTreeLock());
			}
			return this.commands;
		}
	}
}
