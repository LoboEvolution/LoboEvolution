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

import org.lobo.common.Strings;
import org.lobo.common.Urls;
import org.lobobrowser.html.dom.HTMLCollection;
import org.lobobrowser.html.dom.HTMLDocument;
import org.lobobrowser.html.dom.HTMLElement;
import org.lobobrowser.html.dom.HTMLLinkElement;
import org.lobobrowser.html.io.WritableLineReader;
import org.lobobrowser.html.js.Executor;
import org.lobobrowser.html.js.Location;
import org.lobobrowser.html.js.Window;
import org.lobobrowser.html.parser.HtmlParser;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.style.StyleSheetAggregator;
import org.lobobrowser.html.style.StyleSheetRenderState;
import org.lobobrowser.http.HtmlRendererContext;
import org.lobobrowser.http.HttpRequest;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.util.Domains;
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
public class HTMLDocumentImpl extends DOMFunctionImpl implements HTMLDocument, DocumentView {
	private class AnchorFilter implements NodeFilter {
		@Override
		public boolean accept(Node node) {
			final String nodeName = node.getNodeName();
			return "A".equalsIgnoreCase(nodeName) || "ANCHOR".equalsIgnoreCase(nodeName);
		}
	}

	private class AppletFilter implements NodeFilter {
		@Override
		public boolean accept(Node node) {
			// TODO: "OBJECT" elements that are applets too.
			return "APPLET".equalsIgnoreCase(node.getNodeName());
		}
	}

	public class CSSStyleSheetList extends ArrayList {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public int getLength() {
			return size();
		}

		public CSSStyleSheet item(int index) {
			return (CSSStyleSheet) get(index);
		}
	}

	private class ElementNameFilter implements NodeFilter {
		private final String name;

		public ElementNameFilter(String name) {
			this.name = name;
		}

		@Override
		public boolean accept(Node node) {
			// TODO: Case sensitive?
			return node instanceof Element && this.name.equals(((Element) node).getAttribute("name"));
		}
	}

	private class FormFilter implements NodeFilter {
		@Override
		public boolean accept(Node node) {
			final String nodeName = node.getNodeName();
			return "FORM".equalsIgnoreCase(nodeName);
		}
	}

	private class FrameFilter implements NodeFilter {
		@Override
		public boolean accept(Node node) {
			return node instanceof org.lobobrowser.html.dom.HTMLFrameElement
					|| node instanceof org.lobobrowser.html.dom.HTMLIFrameElement;
		}
	}

	private class ImageFilter implements NodeFilter {
		@Override
		public boolean accept(Node node) {
			return "IMG".equalsIgnoreCase(node.getNodeName());
		}
	}

	private static class ImageInfo {
		// Access to this class is synchronized on imageInfos.
		public ImageEvent imageEvent;
		private final ArrayList listeners = new ArrayList(1);
		public boolean loaded;

		void addListener(ImageListener listener) {
			this.listeners.add(listener);
		}

		ImageListener[] getListeners() {
			return (ImageListener[]) this.listeners.toArray(ImageListener.EMPTY_ARRAY);
		}
	}

	private class LinkFilter implements NodeFilter {
		@Override
		public boolean accept(Node node) {
			return node instanceof HTMLLinkElement;
		}
	}

	/**
	 * Tag class that also notifies document when text is written to an open buffer.
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

	private HTMLCollection applets;

	private volatile String baseURI;

	private final ImageEvent BLANK_IMAGE_EVENT = new ImageEvent(this, null);

	private HTMLElement body;

	private String defaultTarget;

	private DocumentType doctype;

	private final ArrayList documentNotificationListeners = new ArrayList(1);

	private String documentURI;

	private java.net.URL documentURL;

	private String domain;

	private DOMConfiguration domConfig;

	private DOMImplementation domImplementation;

	private final Map elementsById = new WeakValueHashMap();

	private final Map elementsByName = new HashMap(0);

	private final ElementFactory factory;

	private HTMLCollection forms;

	private HTMLCollection frames;

	private final Map imageInfos = new HashMap(4);

	private HTMLCollection images;

	private String inputEncoding;

	private HTMLCollection links;

	private Set locales;

	private Function onloadHandler;

	private final HtmlRendererContext rcontext;

	private WritableLineReader reader;
	private String referrer;
	private boolean strictErrorChecking = true;
	private StyleSheetAggregator styleSheetAggregator = null;
	private final Collection styleSheets = new CSSStyleSheetList();
	private String title;

	private final UserAgentContext ucontext;

	private final Window window;

	private String xmlEncoding;

	private boolean xmlStandalone;

	private String xmlVersion = null;

	public HTMLDocumentImpl(HtmlRendererContext rcontext) {
		this(rcontext.getUserAgentContext(), rcontext, null, null);
	}

	public HTMLDocumentImpl(UserAgentContext ucontext) {
		this(ucontext, null, null, null);
	}

	public HTMLDocumentImpl(final UserAgentContext ucontext, final HtmlRendererContext rcontext,
			WritableLineReader reader, String documentURI) {
		this.factory = ElementFactory.getInstance();
		this.rcontext = rcontext;
		this.ucontext = ucontext;
		this.reader = reader;
		this.documentURI = documentURI;
		try {
			final java.net.URL docURL = new java.net.URL(documentURI);
			final SecurityManager sm = System.getSecurityManager();
			if (sm != null) {
				// Do not allow creation of HTMLDocumentImpl if there's
				// no permission to connect to the host of the URL.
				// This is so that cookies cannot be written arbitrarily
				// with setCookie() method.
				sm.checkPermission(new java.net.SocketPermission(docURL.getHost(), "connect"));
			}
			this.documentURL = docURL;
			this.domain = docURL.getHost();
		} catch (final java.net.MalformedURLException mfu) {
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
		final ArrayList listenersList = this.documentNotificationListeners;
		synchronized (listenersList) {
			listenersList.add(listener);
		}
	}

	final void addStyleSheet(CSSStyleSheet ss) {
		synchronized (this.treeLock) {
			this.styleSheets.add(ss);
			this.styleSheetAggregator = null;
			// Need to invalidate all children up to
			// this point.
			forgetRenderState();
			// TODO: this might be ineffcient.
			final ArrayList nl = this.nodeList;
			if (nl != null) {
				final Iterator i = nl.iterator();
				while (i.hasNext()) {
					final Object node = i.next();
					if (node instanceof HTMLElementImpl) {
						((HTMLElementImpl) node).forgetStyle(true);
					}
				}
			}
		}
		this.allInvalidated();
	}

	@Override
	public Node adoptNode(Node source) throws DOMException {
		if (source instanceof NodeImpl) {
			final NodeImpl node = (NodeImpl) source;
			node.setOwnerDocument(this, true);
			return node;
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid Node implementation");
		}
	}

	/**
	 * Informs listeners that the whole document has been invalidated.
	 */
	public void allInvalidated() {
		final ArrayList listenersList = this.documentNotificationListeners;
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
				final DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList.get(i);
				dnl.allInvalidated();
			} catch (final IndexOutOfBoundsException iob) {
				// ignore
			}
		}
	}

	public void allInvalidated(boolean forgetRenderStates) {
		if (forgetRenderStates) {
			synchronized (this.treeLock) {
				this.styleSheetAggregator = null;
				// Need to invalidate all children up to
				// this point.
				forgetRenderState();
				// TODO: this might be ineffcient.
				final ArrayList nl = this.nodeList;
				if (nl != null) {
					final Iterator i = nl.iterator();
					while (i.hasNext()) {
						final Object node = i.next();
						if (node instanceof HTMLElementImpl) {
							((HTMLElementImpl) node).forgetStyle(true);
						}
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
	public Attr createAttribute(String name) throws DOMException {
		return new AttrImpl(name);
	}

	@Override
	public CDATASection createCDATASection(String data) throws DOMException {
		final CDataSectionImpl node = new CDataSectionImpl(data);
		node.setOwnerDocument(this);
		return node;
	}

	@Override
	public Comment createComment(String data) {
		final CommentImpl node = new CommentImpl(data);
		node.setOwnerDocument(this);
		return node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#createDocumentFragment()
	 */
	@Override
	public DocumentFragment createDocumentFragment() {
		// TODO: According to documentation, when a document
		// fragment is added to a node, its children are added,
		// not itself.
		final DocumentFragmentImpl node = new DocumentFragmentImpl();
		node.setOwnerDocument(this);
		return node;
	}

	@Override
	public Element createElement(String tagName) throws DOMException {
		return this.factory.createElement(this, tagName);
	}

	@Override
	public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
		final HTMLProcessingInstruction node = new HTMLProcessingInstruction(target, data);
		node.setOwnerDocument(this);
		return node;
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new StyleSheetRenderState(this);
	}

	@Override
	public Text createTextNode(String data) {
		final TextImpl node = new TextImpl(data);
		node.setOwnerDocument(this);
		return node;
	}

	public void externalScriptLoading(NodeImpl node) {
		final ArrayList listenersList = this.documentNotificationListeners;
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
				final DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList.get(i);
				dnl.externalScriptLoading(node);
			} catch (final IndexOutOfBoundsException iob) {
				// ignore
			}
		}
	}

	@Override
	public HTMLCollection getAnchors() {
		synchronized (this) {
			if (this.anchors == null) {
				this.anchors = new DescendentHTMLCollection(this, new AnchorFilter(), this.treeLock);
			}
			return this.anchors;
		}
	}

	@Override
	public HTMLCollection getApplets() {
		synchronized (this) {
			if (this.applets == null) {
				// TODO: Should include OBJECTs that are applets?
				this.applets = new DescendentHTMLCollection(this, new AppletFilter(), this.treeLock);
			}
			return this.applets;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.NodeImpl#getbaseURI()
	 */
	@Override
	public String getBaseURI() {
		final String buri = this.baseURI;
		return buri == null ? this.documentURI : buri;
	}

	@Override
	public HTMLElement getBody() {
		synchronized (this) {
			return this.body;
		}
	}

	@Override
	public String getCookie() {
		final SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			return (String) AccessController.doPrivileged((PrivilegedAction) () -> HTMLDocumentImpl.this.ucontext
					.getCookie(HTMLDocumentImpl.this.documentURL));
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

	@Override
	public DocumentType getDoctype() {
		return this.doctype;
	}

	@Override
	public Element getDocumentElement() {
		synchronized (this.treeLock) {
			final ArrayList nl = this.nodeList;
			if (nl != null) {
				final Iterator i = nl.iterator();
				while (i.hasNext()) {
					final Object node = i.next();
					if (node instanceof Element) {
						return (Element) node;
					}
				}
			}
			return null;
		}
	}

	String getDocumentHost() {
		final URL docUrl = this.documentURL;
		return docUrl == null ? null : docUrl.getHost();
	}

	@Override
	public String getDocumentURI() {
		return this.documentURI;
	}

	@Override
	public URL getDocumentURL() {
		// TODO: Security considerations?
		return this.documentURL;
	}

	@Override
	public String getDomain() {
		return this.domain;
	}

	@Override
	public DOMConfiguration getDomConfig() {
		synchronized (this) {
			if (this.domConfig == null) {
				this.domConfig = new DOMConfigurationImpl();
			}
			return this.domConfig;
		}
	}

	@Override
	public Element getElementById(String elementId) {
        if (Strings.isNotBlank(elementId)) {
            synchronized (this) {
                return (Element) this.elementsById.get(elementId);
            }
        } else {
            return null;
        }
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
	public HTMLCollection getForms() {
		synchronized (this) {
			if (this.forms == null) {
				this.forms = new DescendentHTMLCollection(this, new FormFilter(), this.treeLock);
			}
			return this.forms;
		}
	}

	public HTMLCollection getFrames() {
		synchronized (this) {
			if (this.frames == null) {
				this.frames = new DescendentHTMLCollection(this, new FrameFilter(), this.treeLock);
			}
			return this.frames;
		}
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

	@Override
	public HTMLCollection getImages() {
		synchronized (this) {
			if (this.images == null) {
				this.images = new DescendentHTMLCollection(this, new ImageFilter(), this.treeLock);
			}
			return this.images;
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

	@Override
	public String getInputEncoding() {
		return this.inputEncoding;
	}

	@Override
	public HTMLCollection getLinks() {
		synchronized (this) {
			if (this.links == null) {
				this.links = new DescendentHTMLCollection(this, new LinkFilter(), this.treeLock);
			}
			return this.links;
		}
	}

	/**
	 * Gets an <i>immutable</i> set of locales previously set for this document.
	 */
	public Set getLocales() {
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

	@Override
	public boolean getStrictErrorChecking() {
		return this.strictErrorChecking;
	}

	final StyleSheetAggregator getStyleSheetAggregator() {
		synchronized (this.treeLock) {
			StyleSheetAggregator ssa = this.styleSheetAggregator;
			if (ssa == null) {
				ssa = new StyleSheetAggregator(this);
				try {
					ssa.addStyleSheets(this.styleSheets);
				} catch (Exception mfu) {
					logger.log(Level.WARNING, "getStyleSheetAggregator()", mfu);
				}
				this.styleSheetAggregator = ssa;
			}
			return ssa;
		}
	}

	public Collection getStyleSheets() {
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
		return this.documentURI;
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
	public boolean getXmlStandalone() {
		return this.xmlStandalone;
	}

	@Override
	public String getXmlVersion() {
		return this.xmlVersion;
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
		final ArrayList listenersList = this.documentNotificationListeners;
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
				final DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList.get(i);
				dnl.invalidated(node);
			} catch (final IndexOutOfBoundsException iob) {
				// ignore
			}
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
			this.styleSheets.clear();
			this.styleSheetAggregator = null;
			reader = this.reader;
		}
		if (reader != null) {
			try {
				final ErrorHandler errorHandler = new LocalErrorHandler();
				final String systemId = this.documentURI;
				final String publicId = systemId;
				final HtmlParser parser = new HtmlParser(this.ucontext, this, errorHandler, publicId, systemId);
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
	 * Loads images asynchronously such that they are shared if loaded
	 * simultaneously from the same URI. Informs the listener immediately if an
	 * image is already known.
	 * 
	 * @param relativeUri
	 * @param imageListener
	 */
	protected void loadImage(String relativeUri, ImageListener imageListener) {
		final HtmlRendererContext rcontext = getHtmlRendererContext();
		if (rcontext == null || !rcontext.isImageLoadingEnabled()) {
			// Ignore image loading when there's no renderer context.
			// Consider Cobra users who are only using the parser.
			imageListener.imageLoaded(this.BLANK_IMAGE_EVENT);
			return;
		}
		final URL url = getFullURL(relativeUri);
		if (url == null) {
			imageListener.imageLoaded(this.BLANK_IMAGE_EVENT);
			return;
		}
		final String urlText = url.toExternalForm();
		final Map map = this.imageInfos;
		ImageEvent event = null;
		synchronized (map) {
			final ImageInfo info = (ImageInfo) map.get(urlText);
			if (info != null) {
				if (info.loaded) {
					// TODO: This can't really happen because ImageInfo
					// is removed right after image is loaded.
					event = info.imageEvent;
				} else {
					info.addListener(imageListener);
				}
			} else {
				final UserAgentContext uac = rcontext.getUserAgentContext();
				final HttpRequest httpRequest = uac.createHttpRequest();
				final ImageInfo newInfo = new ImageInfo();
				map.put(urlText, newInfo);
				newInfo.addListener(imageListener);
				httpRequest.addReadyStateChangeListener(() -> {
					if (httpRequest.getReadyState() == HttpRequest.STATE_COMPLETE) {
						final java.awt.Image newImage = httpRequest.getResponseImage();
						final ImageEvent newEvent = newImage == null ? null
								: new ImageEvent(HTMLDocumentImpl.this, newImage);
						ImageListener[] listeners;
						synchronized (map) {
							newInfo.imageEvent = newEvent;
							newInfo.loaded = true;
							listeners = newEvent == null ? null : newInfo.getListeners();
							// Must remove from map in the locked block
							// that got the listeners. Otherwise a new
							// listener might miss the event??
							map.remove(urlText);
						}
						if (listeners != null) {
							final int llength = listeners.length;
							for (int i = 0; i < llength; i++) {
								// Call holding no locks
								listeners[i].imageLoaded(newEvent);
							}
						}
					}
				});
				final SecurityManager sm = System.getSecurityManager();
				if (sm == null) {
					try {
						httpRequest.open("GET", url, true);
						httpRequest.send(null);
					} catch (Exception thrown) {
						logger.log(Level.WARNING, "loadImage()", thrown);
					}
				} else {
					AccessController.doPrivileged((PrivilegedAction) () -> {
						// Code might have restrictions on accessing
						// items from elsewhere.
						try {
							httpRequest.open("GET", url, true);
							httpRequest.send(null);
						} catch (Exception thrown) {
							logger.log(Level.WARNING, "loadImage()", thrown);
						}
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

	/**
	 * Called if something such as a color or decoration has changed. This would be
	 * something which does not affect the rendered size, and can be revalidated
	 * with a simple repaint.
	 * 
	 * @param node
	 */
	public void lookInvalidated(NodeImpl node) {
		final ArrayList listenersList = this.documentNotificationListeners;
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
				final DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList.get(i);
				dnl.lookInvalidated(node);
			} catch (final IndexOutOfBoundsException iob) {
				// ignore
			}
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
		final ArrayList listenersList = this.documentNotificationListeners;
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
				final DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList.get(i);
				dnl.nodeLoaded(node);
			} catch (final IndexOutOfBoundsException iob) {
				// ignore
			}
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
			this.reader = new LocalWritableLineReader(new EmptyReader());
		}
	}

	private void openBufferChanged(String text) {
		// Assumed to execute in a lock
		// Assumed that text is not broken up HTML.
		final ErrorHandler errorHandler = new LocalErrorHandler();
		final String systemId = this.documentURI;
		final String publicId = systemId;
		final HtmlParser parser = new HtmlParser(this.ucontext, this, errorHandler, publicId, systemId);
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
		final ArrayList listenersList = this.documentNotificationListeners;
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
				final DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList.get(i);
				dnl.positionInvalidated(node);
			} catch (final IndexOutOfBoundsException iob) {
				// ignore
			}
		}
	}

	public void removeDocumentNotificationListener(DocumentNotificationListener listener) {
		final ArrayList listenersList = this.documentNotificationListeners;
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
			AccessController.doPrivileged((PrivilegedAction) () -> {
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

	public void setDoctype(DocumentType doctype) {
		this.doctype = doctype;
	}

	@Override
	public void setDocumentURI(String documentURI) {
		// TODO: Security considerations? Chaging documentURL?
		this.documentURI = documentURI;
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
	public void setLocales(Set locales) {
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
	public void setStrictErrorChecking(boolean strictErrorChecking) {
		this.strictErrorChecking = strictErrorChecking;
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
			if (org.lobobrowser.html.parser.HtmlParser.MODIFYING_KEY.equals(key) && data == Boolean.FALSE) {
				// TODO: onload event object?
				Executor.executeFunction(this, onloadHandler, null);
			}
		}
		return super.setUserData(key, data, handler);
	}

	@Override
	public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
		this.xmlStandalone = xmlStandalone;
	}

	@Override
	public void setXmlVersion(String xmlVersion) throws DOMException {
		this.xmlVersion = xmlVersion;
	}

//	private class BodyFilter implements NodeFilter {
//		public boolean accept(Node node) {
//			return node instanceof org.lobobrowser.html.dom.HTMLBodyElement;
//		}
//	}

	public void sizeInvalidated(NodeImpl node) {
		final ArrayList listenersList = this.documentNotificationListeners;
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
				final DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList.get(i);
				dnl.sizeInvalidated(node);
			} catch (final IndexOutOfBoundsException iob) {
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
		final ArrayList listenersList = this.documentNotificationListeners;
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
				final DocumentNotificationListener dnl = (DocumentNotificationListener) listenersList.get(i);
				dnl.structureInvalidated(node);
			} catch (final IndexOutOfBoundsException iob) {
				// ignore
			}
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
