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
package org.lobobrowser.html.domimpl;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.HttpRequest;
import org.lobobrowser.html.ReadyStateChangeListener;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.dombl.AttrImpl;
import org.lobobrowser.html.dombl.CDataSectionImpl;
import org.lobobrowser.html.dombl.CSSStyleSheetList;
import org.lobobrowser.html.dombl.CommentImpl;
import org.lobobrowser.html.dombl.DOMConfigurationImpl;
import org.lobobrowser.html.dombl.DOMImplementationImpl;
import org.lobobrowser.html.dombl.DescendentHTMLCollection;
import org.lobobrowser.html.dombl.DocumentFragmentImpl;
import org.lobobrowser.html.dombl.DocumentNotificationListener;
import org.lobobrowser.html.dombl.ElementFactory;
import org.lobobrowser.html.dombl.ImageEvent;
import org.lobobrowser.html.dombl.ImageInfo;
import org.lobobrowser.html.dombl.ImageListener;
import org.lobobrowser.html.dombl.LocalErrorHandler;
import org.lobobrowser.html.dombl.NodeImpl;
import org.lobobrowser.html.dombl.NodeVisitor;
import org.lobobrowser.html.dombl.TextImpl;
import org.lobobrowser.html.domfilter.AnchorFilter;
import org.lobobrowser.html.domfilter.AppletFilter;
import org.lobobrowser.html.domfilter.ElementFilter;
import org.lobobrowser.html.domfilter.ElementNameFilter;
import org.lobobrowser.html.domfilter.FormFilter;
import org.lobobrowser.html.domfilter.FrameFilter;
import org.lobobrowser.html.domfilter.ImageFilter;
import org.lobobrowser.html.domfilter.LinkFilter;
import org.lobobrowser.html.domfilter.TagNameFilter;
import org.lobobrowser.html.io.WritableLineReader;
import org.lobobrowser.html.js.Executor;
import org.lobobrowser.html.js.Location;
import org.lobobrowser.html.js.Window;
import org.lobobrowser.html.parser.HtmlParser;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.style.StyleSheetAggregator;
import org.lobobrowser.html.style.StyleSheetRenderState;
import org.lobobrowser.html.w3c.HTMLAllCollection;
import org.lobobrowser.html.w3c.HTMLCollection;
import org.lobobrowser.html.w3c.HTMLDocument;
import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.HTMLHeadElement;
import org.lobobrowser.util.Domains;
import org.lobobrowser.util.Urls;
import org.lobobrowser.util.WeakValueHashMap;
import org.lobobrowser.util.io.EmptyReader;
import org.mozilla.javascript.Function;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.views.DocumentView;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 * Implementation of the W3C <code>HTMLDocument</code> interface.
 */
public class HTMLDocumentImpl extends NodeImpl implements HTMLDocument, DocumentView {
	private static final Logger logger = Logger.getLogger(HTMLDocumentImpl.class.getName());
	private final ElementFactory factory;
	private final HtmlRendererContext rcontext;
	private final UserAgentContext ucontext;
	private final Window window;
	private final Map<String, Element> elementsById = new WeakValueHashMap();
	private final Map<String, Element> elementsByName = new HashMap<String, Element>(0);
	private final Collection styleSheets = new CSSStyleSheetList();
	private final Map<String, ImageInfo> imageInfos = new HashMap<String, ImageInfo>(4);
	private final ArrayList<DocumentNotificationListener> documentNotificationListeners = new ArrayList<DocumentNotificationListener>(1);
	private final ImageEvent BLANK_IMAGE_EVENT = new ImageEvent(this, null);
	
	private java.net.URL documentURL;
	private WritableLineReader reader;
	private DocumentType doctype;
	private HTMLElement body;
	private HTMLCollection images;
	private HTMLCollection applets;
	private HTMLCollection links;
	private HTMLCollection forms;
	private HTMLCollection anchors;
	private HTMLCollection frames;
	private StyleSheetAggregator styleSheetAggregator = null;
	private DOMConfiguration domConfig;
	private DOMImplementation domImplementation;
	private Function onloadHandler;
	
	private Set<?> locales;
	private volatile String baseURI;
	private String defaultTarget;
	private String title;
	private String documentURI;
	private String referrer;
	private String domain;
	private String inputEncoding;
	private String xmlEncoding;
	private String xmlVersion = null;
	private boolean xmlStandalone;
	private boolean strictErrorChecking = true;

	public HTMLDocumentImpl(HtmlRendererContext rcontext) {
		this(rcontext.getUserAgentContext(), rcontext, null, null);
	}

	public HTMLDocumentImpl(UserAgentContext ucontext) {
		this(ucontext, null, null, null);
	}

	public HTMLDocumentImpl(final UserAgentContext ucontext,
			final HtmlRendererContext rcontext, WritableLineReader reader,
			String documentURI) {
		this.factory = ElementFactory.getInstance();
		this.rcontext = rcontext;
		this.ucontext = ucontext;
		this.reader = reader;
		this.documentURI = documentURI;
		try {
			java.net.URL docURL = new java.net.URL(documentURI);
			SecurityManager sm = System.getSecurityManager();
			if (sm != null) {
				// Do not allow creation of HTMLDocumentImpl if there's
				// no permission to connect to the host of the URL.
				// This is so that cookies cannot be written arbitrarily
				// with setCookie() method.
				sm.checkPermission(new java.net.SocketPermission(docURL
						.getHost(), "connect"));
			}
			this.documentURL = docURL;
			this.domain = docURL.getHost();
		} catch (java.net.MalformedURLException mfu) {
			logger.warning("HTMLDocumentImpl(): Document URI [" + documentURI
					+ "] is malformed.");
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
		// Set up Javascript scope
		this.setUserData(Executor.SCOPE_KEY, window.getWindowScope(), null);
	}
	
	public String getCookie() {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			return (String) AccessController
					.doPrivileged(new PrivilegedAction<Object>() {
						// Justification: A caller (e.g. Google Analytics
						// script)
						// might want to get cookies from the parent document.
						// If the caller has access to the document, it appears
						// they should be able to get cookies on that document.
						// Note that this Document instance cannot be created
						// with an arbitrary URL.

						// TODO: Security: Review rationale.
						public Object run() {
							return ucontext.getCookie(documentURL);
						}
					});
		} else {
			return this.ucontext.getCookie(this.documentURL);
		}
	}

	public void setCookie(final String cookie) throws DOMException {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			AccessController.doPrivileged(new PrivilegedAction<Object>() {
				// Justification: A caller (e.g. Google Analytics script)
				// might want to set cookies on the parent document.
				// If the caller has access to the document, it appears
				// they should be able to set cookies on that document.
				// Note that this Document instance cannot be created
				// with an arbitrary URL.
				public Object run() {
					ucontext.setCookie(documentURL, cookie);
					return null;
				}
			});
		} else {
			this.ucontext.setCookie(this.documentURL, cookie);
		}
	}

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
	 * @throws SAXException
	 * @throws UnsupportedEncodingException
	 */
	public void load() throws IOException, SAXException,
			UnsupportedEncodingException {
		this.load(true);
	}

	public void load(boolean closeReader) throws IOException, SAXException,
			UnsupportedEncodingException {
		WritableLineReader reader;
		synchronized (this.getTreeLock()) {
			this.removeAllChildrenImpl();
			this.setTitle(null);
			this.setBaseURI(null);
			this.setDefaultTarget(null);
			this.styleSheets.clear();
			this.styleSheetAggregator = null;
			reader = this.reader;
		}
		if (reader != null) {
			try {
				ErrorHandler errorHandler = new LocalErrorHandler();
				String systemId = this.documentURI;
				String publicId = systemId;
				HtmlParser parser = new HtmlParser(this.ucontext, this,
						errorHandler, publicId, systemId);
				parser.parse(reader);
			} finally {
				if (closeReader) {
					try {
						reader.close();
					} catch (Exception err) {
						logger.log(Level.WARNING,
								"load(): Unable to close stream", err);
					}
					synchronized (this.getTreeLock()) {
						this.reader = null;
					}
				}
			}
		}
	}

	public void close() {
		synchronized (this.getTreeLock()) {
			if (this.reader instanceof LocalWritableLineReader) {
				try {
					this.reader.close();
				} catch (java.io.IOException ioe) {
					// ignore
				}
				this.reader = null;
			} else {
				// do nothing - could be parsing document off the web.
			}
			// TODO: cause it to render
		}
	}

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

	private void openBufferChanged(String text) {
		// Assumed to execute in a lock
		// Assumed that text is not broken up HTML.
		ErrorHandler errorHandler = new LocalErrorHandler();
		String systemId = this.documentURI;
		String publicId = systemId;
		HtmlParser parser = new HtmlParser(this.ucontext, this, errorHandler,
				publicId, systemId);
		StringReader strReader = new StringReader(text);
		try {
			// This sets up another Javascript scope Window. Does it matter?
			parser.parse(strReader);
		} catch (Exception err) {
			this.warn(
					"Unable to parse written HTML text. BaseURI=["
							+ this.getBaseURI() + "].", err);
		}
	}

	/**
	 * Gets the collection of elements whose <code>name</code> attribute is
	 * <code>elementName</code>.
	 */
	public NodeList getElementsByName(String elementName) {
		return this.getNodeList(new ElementNameFilter(elementName));
	}
	
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

	public Element createElement(String tagName) throws DOMException {
		return this.factory.createElement(this, tagName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#createDocumentFragment()
	 */
	public DocumentFragment createDocumentFragment() {
		// TODO: According to documentation, when a document
		// fragment is added to a node, its children are added,
		// not itself.
		DocumentFragmentImpl node = new DocumentFragmentImpl();
		node.setOwnerDocument(this);
		return node;
	}

	public Text createTextNode(String data) {
		TextImpl node = new TextImpl(data);
		node.setOwnerDocument(this);
		return node;
	}

	public Comment createComment(String data) {
		CommentImpl node = new CommentImpl(data);
		node.setOwnerDocument(this);
		return node;
	}

	public CDATASection createCDATASection(String data) throws DOMException {
		CDataSectionImpl node = new CDataSectionImpl(data);
		node.setOwnerDocument(this);
		return node;
	}

	public ProcessingInstruction createProcessingInstruction(String target,
			String data) throws DOMException {
		HTMLProcessingInstruction node = new HTMLProcessingInstruction(target,
				data);
		node.setOwnerDocument(this);
		return node;
	}

	public Attr createAttribute(String name) throws DOMException {
		return new AttrImpl(name);
	}

	public EntityReference createEntityReference(String name)
			throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}

	/**
	 * Gets all elements that match the given tag name.
	 * 
	 * @param tagname
	 *            The element tag name or an asterisk character (*) to match all
	 *            elements.
	 */
	public NodeList getElementsByTagName(String tagname) {
		if ("*".equals(tagname)) {
			return this.getNodeList(new ElementFilter());
		} else {
			return this.getNodeList(new TagNameFilter(tagname));
		}
	}

	public Node importNode(Node importedNode, boolean deep) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
				"Not implemented");
	}

	public Element createElementNS(String namespaceURI, String qualifiedName)
			throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}

	public Attr createAttributeNS(String namespaceURI, String qualifiedName)
			throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}

	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}

	public Element getElementById(String elementId) {
		Element element;
		synchronized (this) {
			element = (Element) this.elementsById.get(elementId);
		}
		return element;
	}

	

	public Element namedItem(String name) {
		Element element;
		synchronized (this) {
			element = (Element) this.elementsByName.get(name);
		}
		return element;
	}

	public void setNamedItem(String name, Element element) {
		synchronized (this) {
			this.elementsByName.put(name, element);
		}
	}

	public void removeNamedItem(String name) {
		synchronized (this) {
			this.elementsByName.remove(name);
		}
	}
	
	public Node adoptNode(Node source) throws DOMException {
		if (source instanceof NodeImpl) {
			NodeImpl node = (NodeImpl) source;
			node.setOwnerDocument(this, true);
			return node;
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
					"Invalid Node implementation");
		}
	}

	public DOMConfiguration getDomConfig() {
		synchronized (this) {
			if (this.domConfig == null) {
				this.domConfig = new DOMConfigurationImpl();
			}
			return this.domConfig;
		}
	}

	public void normalizeDocument() {
		// TODO: Normalization options from domConfig
		synchronized (this.getTreeLock()) {
			this.visitImpl(new NodeVisitor() {
				public void visit(Node node) {
					node.normalize();
				}
			});
		}
	}

	public Node renameNode(Node n, String namespaceURI, String qualifiedName)
			throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "No renaming");
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#getImplementation()
	 */
	public DOMImplementation getImplementation() {
		synchronized (this) {
			if (this.domImplementation == null) {
				this.domImplementation = new DOMImplementationImpl(
						this.ucontext);
			}
			return this.domImplementation;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.NodeImpl#getLocalName()
	 */
	public String getLocalName() {
		// Always null for document
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.NodeImpl#getNodeName()
	 */
	public String getNodeName() {
		return "#document";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.NodeImpl#getNodeType()
	 */
	public short getNodeType() {
		return Node.DOCUMENT_NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.NodeImpl#getNodeValue()
	 */
	public String getNodeValue() throws DOMException {
		// Always null for document
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.NodeImpl#setNodeValue(java.lang.String)
	 */
	public void setNodeValue(String nodeValue) throws DOMException {
		throw new DOMException(DOMException.INVALID_MODIFICATION_ERR,
				"Cannot set node value of document");
	}

	public final HtmlRendererContext getHtmlRendererContext() {
		return this.rcontext;
	}

	public UserAgentContext getUserAgentContext() {
		return this.ucontext;
	}

	public final URL getFullURL(String uri) {
		try {
			String baseURI = this.getBaseURI();
			URL documentURL = baseURI == null ? null : new URL(baseURI);
			return Urls.createURL(documentURL, uri);
		} catch (MalformedURLException mfu) {
			// Try agan, without the baseURI.
			try {
				return new URL(uri);
			} catch (MalformedURLException mfu2) {
				logger.log(Level.WARNING, "Unable to create URL for URI=["
						+ uri + "], with base=[" + this.getBaseURI() + "].",
						mfu);
				return null;
			}
		}
	}

	public final Location getLocation() {
		return this.window.getLocation();
	}

	public void setLocation(String location) {
		this.getLocation().setHref(location);
	}

	public String getURL() {
		return this.documentURI;
	}
	
  final void addStyleSheet(CSSStyleSheet ss) {
		synchronized (this.getTreeLock()) {
			this.styleSheets.add(ss);
			this.styleSheetAggregator = null;
			// Need to invalidate all children up to
			// this point.
			this.forgetRenderState();
			// TODO: this might be ineffcient.
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

	public void allInvalidated(boolean forgetRenderStates) {
		if (forgetRenderStates) {
			synchronized (this.getTreeLock()) {
				this.styleSheetAggregator = null;
				// Need to invalidate all children up to
				// this point.
				this.forgetRenderState();
				// TODO: this might be ineffcient.
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

	public Collection<CSSStyleSheet> getStyleSheets() {
		return this.styleSheets;
	}

	final StyleSheetAggregator getStyleSheetAggregator() {
		synchronized (this.getTreeLock()) {
			StyleSheetAggregator ssa = this.styleSheetAggregator;
			if (ssa == null) {
				ssa = new StyleSheetAggregator(this);
				try {
					ssa.addStyleSheets(this.styleSheets);
				} catch (MalformedURLException mfu) {
					logger.log(Level.WARNING, "getStyleSheetAggregator()", mfu);
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
	public void addDocumentNotificationListener(
			DocumentNotificationListener listener) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		synchronized (listenersList) {
			listenersList.add(listener);
		}
	}

	public void removeDocumentNotificationListener(
			DocumentNotificationListener listener) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		synchronized (listenersList) {
			listenersList.remove(listener);
		}
	}

	public void sizeInvalidated(NodeImpl node) {
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
				DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList
						.get(i);
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
	 */
	public void lookInvalidated(NodeImpl node) {
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
				DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList.get(i);
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
	 */
	public void positionInParentInvalidated(NodeImpl node) {
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
				DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList
						.get(i);
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
	 */
	public void invalidated(NodeImpl node) {
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
				DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList
						.get(i);
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
	 */
	public void structureInvalidated(NodeImpl node) {
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
				DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList
						.get(i);
				dnl.structureInvalidated(node);
			} catch (IndexOutOfBoundsException iob) {
				// ignore
			}
		}
	}

	public void nodeLoaded(NodeImpl node) {
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
				DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList
						.get(i);
				dnl.nodeLoaded(node);
			} catch (IndexOutOfBoundsException iob) {
				// ignore
			}
		}
	}

	public void externalScriptLoading(NodeImpl node) {
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
				DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList
						.get(i);
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
				DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList
						.get(i);
				dnl.allInvalidated();
			} catch (IndexOutOfBoundsException iob) {
				// ignore
			}
		}
	}

	protected RenderState createRenderState(RenderState prevRenderState) {
		return new StyleSheetRenderState(this);
	}

	/**
	 * Loads images asynchronously such that they are shared if loaded
	 * simultaneously from the same URI. Informs the listener immediately if an
	 * image is already known.
	 * 
	 * @param relativeUri
	 * @param imageListener
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
			ImageInfo info = (ImageInfo) map.get(urlText);
			if (info != null) {
				if (info.loaded) {
					// TODO: This can't really happen because ImageInfo
					// is removed right after image is loaded.
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
				httpRequest
						.addReadyStateChangeListener(new ReadyStateChangeListener() {
							public void readyStateChanged() {
								if (httpRequest.getReadyState() == HttpRequest.STATE_COMPLETE) {
									java.awt.Image newImage = httpRequest
											.getResponseImage();
									ImageEvent newEvent = newImage == null ? null
											: new ImageEvent(
													HTMLDocumentImpl.this,
													newImage);
									ImageListener[] listeners;
									synchronized (map) {
										newInfo.imageEvent = newEvent;
										newInfo.loaded = true;
										listeners = newEvent == null ? null
												: newInfo.getListeners();
										// Must remove from map in the locked
										// block
										// that got the listeners. Otherwise a
										// new
										// listener might miss the event??
										map.remove(urlText);
									}
									if (listeners != null) {
										int llength = listeners.length;
										for (int i = 0; i < llength; i++) {
											// Call holding no locks
											listeners[i].imageLoaded(newEvent);
										}
									}
								}
							}
						});
				SecurityManager sm = System.getSecurityManager();
				if (sm == null) {
					try {
						httpRequest.open("GET", url, true);
						httpRequest.send(null);
					} catch (java.io.IOException thrown) {
						logger.log(Level.WARNING, "loadImage()", thrown);
					}
				} else {
					AccessController
							.doPrivileged(new PrivilegedAction<Object>() {
								public Object run() {
									// Code might have restrictions on accessing
									// items from elsewhere.
									try {
										httpRequest.open("GET", url, true);
										httpRequest.send(null);
									} catch (java.io.IOException thrown) {
										logger.log(Level.WARNING,
												"loadImage()", thrown);
									}
									return null;
								}
							});
				}
			}
		}
		if (event != null) {
			// Call holding no locks.
			imageListener.imageLoaded(event);
		}
	}
	
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		Function onloadHandler = this.onloadHandler;
		if (onloadHandler != null) {
			if (org.lobobrowser.html.parser.HtmlParser.MODIFYING_KEY
					.equals(key) && data == Boolean.FALSE) {
				// TODO: onload event object?
				Executor.executeFunction(this, onloadHandler, null);
			}
		}
		return super.setUserData(key, data, handler);
	}

	protected Node createSimilarNode() {
		return new HTMLDocumentImpl(this.ucontext, this.rcontext, this.reader,
				this.documentURI);
	}

	/**
	 * Tag class that also notifies document when text is written to an open
	 * buffer.
	 * 
	 * @author J. H. S.
	 */
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

		public void write(String text) throws IOException {
			super.write(text);
			if ("".equals(text)) {
				openBufferChanged(text);
			}
		}
	}
	
	@Override
	public HTMLElement getBody() {
		synchronized (this) {
			return this.body;
		}
	}

	@Override
	public void setBody(HTMLElement body) {
		synchronized (this) {
			this.body = body;
		}
	}
	
	public String getReferrer() {
		return this.referrer;
	}

	public void setReferrer(String value) {
		this.referrer = value;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		String oldDomain = this.domain;
		if (oldDomain != null && Domains.isValidCookieDomain(domain, oldDomain)) {
			this.domain = domain;
		} else {
			throw new SecurityException("Cannot set domain to '" + domain
					+ "' when current domain is '" + oldDomain + "'");
		}
	}

	public HTMLCollection getImages() {
		synchronized (this) {
			if (this.images == null) {
				this.images = new DescendentHTMLCollection(this,
						new ImageFilter(), this.getTreeLock());
			}
			return this.images;
		}
	}

	public HTMLCollection getApplets() {
		synchronized (this) {
			if (this.applets == null) {
				// TODO: Should include OBJECTs that are applets?
				this.applets = new DescendentHTMLCollection(this,
						new AppletFilter(), this.getTreeLock());
			}
			return this.applets;
		}
	}

	public HTMLCollection getLinks() {
		synchronized (this) {
			if (this.links == null) {
				this.links = new DescendentHTMLCollection(this,
						new LinkFilter(), this.getTreeLock());
			}
			return this.links;
		}
	}

	public HTMLCollection getForms() {
		synchronized (this) {
			if (this.forms == null) {
				this.forms = new DescendentHTMLCollection(this,
						new FormFilter(), this.getTreeLock());
			}
			return this.forms;
		}
	}

	public HTMLCollection getFrames() {
		synchronized (this) {
			if (this.frames == null) {
				this.frames = new DescendentHTMLCollection(this,
						new FrameFilter(), this.getTreeLock());
			}
			return this.frames;
		}
	}

	public HTMLCollection getAnchors() {
		synchronized (this) {
			if (this.anchors == null) {
				this.anchors = new DescendentHTMLCollection(this,
						new AnchorFilter(), this.getTreeLock());
			}
			return this.anchors;
		}
	}
	
	public DocumentType getDoctype() {
		return this.doctype;
	}

	public void setDoctype(DocumentType doctype) {
		this.doctype = doctype;
	}
	
	public String getInputEncoding() {
		return this.inputEncoding;
	}

	public String getXmlEncoding() {
		return this.xmlEncoding;
	}
	
	public boolean getXmlStandalone() {
		return this.xmlStandalone;
	}

	public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
		this.xmlStandalone = xmlStandalone;
	}

	

	public String getXmlVersion() {
		return this.xmlVersion;
	}

	public void setXmlVersion(String xmlVersion) throws DOMException {
		this.xmlVersion = xmlVersion;
	}

	

	public boolean getStrictErrorChecking() {
		return this.strictErrorChecking;
	}

	public void setStrictErrorChecking(boolean strictErrorChecking) {
		this.strictErrorChecking = strictErrorChecking;
	}

	public String getDocumentURI() {
		return this.documentURI;
	}
	
	public Function getOnloadHandler() {
		return onloadHandler;
	}

	public void setOnloadHandler(Function onloadHandler) {
		this.onloadHandler = onloadHandler;
	}
	
	/**
	 * Gets an <i>immutable</i> set of locales previously set for this document.
	 */
	public Set<?> getLocales() {
		return locales;
	}

	/**
	 * Sets the locales of the document. This helps determine whether specific
	 * fonts can display text in the languages of all the locales.
	 * 
	 * @param locales
	 *            An <i>immutable</i> set of <code>java.util.Locale</code>
	 *            instances.
	 */
	public void setLocales(Set<?> locales) {
		this.locales = locales;
	}

	String getDocumentHost() {
		URL docUrl = this.documentURL;
		return docUrl == null ? null : docUrl.getHost();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.NodeImpl#getbaseURI()
	 */
	public String getBaseURI() {
		String buri = this.baseURI;
		return buri == null ? this.documentURI : buri;
	}

	public void setBaseURI(String value) {
		this.baseURI = value;
	}


	public String getDefaultTarget() {
		return this.defaultTarget;
	}

	public void setDefaultTarget(String value) {
		this.defaultTarget = value;
	}

	public AbstractView getDefaultView() {
		return this.window;
	}

	public String getTextContent() throws DOMException {
		return null;
	}

	public void setTextContent(String textContent) throws DOMException {
		// NOP, per spec
	}
	
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public URL getDocumentURL() {
		// TODO: Security considerations?
		return this.documentURL;
	}

	/**
	 * Caller should synchronize on document.
	 */
	public void setElementById(String id, Element element) {
		synchronized (this) {
			this.elementsById.put(id, element);
		}
	}

	void removeElementById(String id) {
		synchronized (this) {
			this.elementsById.remove(id);
		}
	}

	public void setDocumentURI(String documentURI) {
		// TODO: Security considerations? Chaging documentURL?
		this.documentURI = documentURI;
	}

	@Override
	public String getLastModified() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCompatMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCharset() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCharset(String charset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCharacterSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultCharset() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReadyState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getElement(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDir(String dir) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HTMLHeadElement getHead() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HTMLCollection getEmbeds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HTMLCollection getPlugins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HTMLCollection getScripts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeList getElementsByClassName(String classNames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInnerHTML(String innerHTML) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HTMLDocument open(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HTMLDocument open(String type, String replace) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void write(String... text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeln(String... text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasFocus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDesignMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDesignMode(String designMode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean execCommand(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execCommand(String commandId, boolean showUI) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execCommand(String commandId, boolean showUI, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean queryCommandEnabled(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean queryCommandIndeterm(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean queryCommandState(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean queryCommandSupported(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String queryCommandValue(String commandId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HTMLCollection getCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFgColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFgColor(String fgColor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBgColor() {
		return null;
	}

	@Override
	public void setBgColor(String bgColor) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getLinkColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLinkColor(String linkColor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getVlinkColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVlinkColor(String vlinkColor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAlinkColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAlinkColor(String alinkColor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HTMLAllCollection getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeList getItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeList getItems(String typeNames) {
		// TODO Auto-generated method stub
		return null;
	}
}
