/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketPermission;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlCommandMapping;
import org.lobobrowser.html.HtmlEventProperties;
import org.lobobrowser.html.HtmlJsAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.dombl.DescendentHTMLCollection;
import org.lobobrowser.html.dombl.DocumentNotificationListener;
import org.lobobrowser.html.dombl.ElementFactory;
import org.lobobrowser.html.dombl.ImageEvent;
import org.lobobrowser.html.dombl.ImageListener;
import org.lobobrowser.html.dombl.LocalErrorHandler;
import org.lobobrowser.html.dombl.NodeVisitor;
import org.lobobrowser.html.dombl.QuerySelectorImpl;
import org.lobobrowser.html.domfilter.AnchorFilter;
import org.lobobrowser.html.domfilter.AppletFilter;
import org.lobobrowser.html.domfilter.ClassNameFilter;
import org.lobobrowser.html.domfilter.CommandFilter;
import org.lobobrowser.html.domfilter.ElementAttributeFilter;
import org.lobobrowser.html.domfilter.ElementFilter;
import org.lobobrowser.html.domfilter.ElementNameFilter;
import org.lobobrowser.html.domfilter.EmbedFilter;
import org.lobobrowser.html.domfilter.FormFilter;
import org.lobobrowser.html.domfilter.FrameFilter;
import org.lobobrowser.html.domfilter.ImageFilter;
import org.lobobrowser.html.domfilter.LinkFilter;
import org.lobobrowser.html.domfilter.PluginsFilter;
import org.lobobrowser.html.domfilter.ScriptFilter;
import org.lobobrowser.html.domfilter.TagNameFilter;
import org.lobobrowser.html.info.ImageInfo;
import org.lobobrowser.html.io.WritableLineReader;
import org.lobobrowser.html.js.Executor;
import org.lobobrowser.html.js.Location;
import org.lobobrowser.html.js.Window;
import org.lobobrowser.html.jsimpl.CustomEventImpl;
import org.lobobrowser.html.jsimpl.EventException;
import org.lobobrowser.html.jsimpl.EventImpl;
import org.lobobrowser.html.jsimpl.KeyboardEventImpl;
import org.lobobrowser.html.jsimpl.MouseEventImpl;
import org.lobobrowser.html.jsimpl.MutationEventImpl;
import org.lobobrowser.html.jsimpl.MutationNameEventImpl;
import org.lobobrowser.html.jsimpl.TextEventImpl;
import org.lobobrowser.html.jsimpl.UIEventImpl;
import org.lobobrowser.html.parser.HtmlParser;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.renderstate.StyleSheetRenderState;
import org.lobobrowser.html.style.StyleSheetAggregator;
import org.lobobrowser.html.xpath.XPathEvaluatorImpl;
import org.lobobrowser.html.xpath.XPathNSResolverImpl;
import org.lobobrowser.html.xpath.XPathResultImpl;
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
import org.lobobrowser.w3c.events.Event;
import org.lobobrowser.w3c.html.DOMElementMap;
import org.lobobrowser.w3c.html.HTMLCollection;
import org.lobobrowser.w3c.html.HTMLDocument;
import org.lobobrowser.w3c.html.HTMLElement;
import org.lobobrowser.w3c.html.HTMLHeadElement;
import org.lobobrowser.w3c.xpath.XPathEvaluator;
import org.lobobrowser.w3c.xpath.XPathExpression;
import org.lobobrowser.w3c.xpath.XPathNSResolver;
import org.lobobrowser.w3c.xpath.XPathResult;
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
import org.w3c.dom.stylesheets.StyleSheetList;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.views.DocumentView;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import com.steadystate.css.dom.CSSStyleSheetListImpl;

/**
 * The Class HTMLDocumentImpl.
 */
public class HTMLDocumentImpl extends DOMNodeImpl implements HTMLDocument, DocumentView, DocumentEvent, XPathEvaluator {

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
	private final ArrayList<DocumentNotificationListener> documentNotificationListeners = new ArrayList<DocumentNotificationListener>(
			1);

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

	/** The onload handler. */
	private Function onloadHandler;

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

	/** The onclick. */
	private Function onclick;

	/** The ondblclick. */
	private Function ondblclick;

	/** The onkeydown. */
	private Function onkeydown;

	/** The onkeypress. */
	private Function onkeypress;

	/** The onkeyup. */
	private Function onkeyup;

	/** The onmousedown. */
	private Function onmousedown;

	/** The onmouseup. */
	private Function onmouseup;

	/** The onunload. */
	private Function onunload;

	/** The onmouseout. */
	private Function onmouseout;

	/** The onmouseover. */
	private Function onmouseover;

	/** The oncanplay. */
	private Function oncanplay;

	/** The onabort. */
	private Function onabort;

	/** The onblur. */
	private Function onblur;

	/** The oncanplaythrough. */
	private Function oncanplaythrough;

	/** The onchange. */
	private Function onchange;

	/** The oncontextmenu. */
	private Function oncontextmenu;

	/** The oncuechange. */
	private Function oncuechange;

	/** The ondrag. */
	private Function ondrag;

	/** The ondragend. */
	private Function ondragend;

	/** The ondragenter. */
	private Function ondragenter;

	/** The ondragleave. */
	private Function ondragleave;

	/** The ondragover. */
	private Function ondragover;

	/** The ondragstart. */
	private Function ondragstart;

	/** The ondrop. */
	private Function ondrop;

	/** The ondurationchange. */
	private Function ondurationchange;

	/** The onemptied. */
	private Function onemptied;

	/** The onended. */
	private Function onended;

	/** The onerror. */
	private Function onerror;

	/** The onfocus. */
	private Function onfocus;

	/** The oninput. */
	private Function oninput;

	/** The oninvalid. */
	private Function oninvalid;

	/** The onload. */
	private Function onload;

	/** The onloadeddata. */
	private Function onloadeddata;

	/** The onloadedmetadata. */
	private Function onloadedmetadata;

	/** The onloadstart. */
	private Function onloadstart;

	/** The onmousewheel. */
	private Function onmousewheel;

	/** The onpause. */
	private Function onpause;

	/** The onplay. */
	private Function onplay;

	/** The onplaying. */
	private Function onplaying;

	/** The onprogress. */
	private Function onprogress;

	/** The onreadystatechange. */
	private Function onreadystatechange;

	/** The onreset. */
	private Function onreset;

	/** The onscroll. */
	private Function onscroll;

	/** The onseeked. */
	private Function onseeked;

	/** The onseeking. */
	private Function onseeking;

	/** The onselect. */
	private Function onselect;

	/** The onshow. */
	private Function onshow;

	/** The onstalled. */
	private Function onstalled;

	/** The onsubmit. */
	private Function onsubmit;

	/** The onsuspend. */
	private Function onsuspend;

	/** The ontimeupdate. */
	private Function ontimeupdate;

	/** The onvolumechange. */
	private Function onvolumechange;

	/** The onwaiting. */
	private Function onwaiting;

	/** The omousemove. */
	private Function onmousemove;

	/** The onratechange. */
	private Function onratechange;
	
	
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
			return (String) AccessController.doPrivileged(new PrivilegedAction<Object>() {
				@Override
				public Object run() {
					return ucontext.getCookie(documentURL);
				}
			});
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
			AccessController.doPrivileged(new PrivilegedAction<Object>() {
				@Override
				public Object run() {
					ucontext.setCookie(documentURL, cookie);
					return null;
				}
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
						logger.error(ioe);
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
					logger.error(ioe);
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
					logger.error(ioe);
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
					logger.error(ioe);
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
	 * @see org.w3c.dom.Document#createDocumentFragment()
	 */
	@Override
	public DocumentFragment createDocumentFragment() {
		DOMFragmentImpl node = new DOMFragmentImpl();
		node.setOwnerDocument(this);
		return node;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#createTextNode(java.lang.String)
	 */
	@Override
	public Text createTextNode(String data) {
		DOMTextImpl node = new DOMTextImpl(data);
		node.setOwnerDocument(this);
		return node;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#createComment(java.lang.String)
	 */
	@Override
	public Comment createComment(String data) {
		DOMCommentImpl node = new DOMCommentImpl(data);
		node.setOwnerDocument(this);
		return node;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#createCDATASection(java.lang.String)
	 */
	@Override
	public CDATASection createCDATASection(String data) throws DOMException {
		DOMCDataSectionImpl node = new DOMCDataSectionImpl(data);
		node.setOwnerDocument(this);
		return node;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#createProcessingInstruction(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
		HTMLProcessingInstruction node = new HTMLProcessingInstruction(target, data);
		node.setOwnerDocument(this);
		return node;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#createAttribute(java.lang.String)
	 */
	@Override
	public Attr createAttribute(String name) throws DOMException {
		return new DOMAttrImpl(name);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#createEntityReference(java.lang.String)
	 */
	@Override
	public EntityReference createEntityReference(String name) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}

	/**
	 * Gets all elements that match the given tag name.
	 *
	 * @param tagname
	 *            The element tag name or an asterisk character (*) to match all
	 *            elements.
	 * @return the elements by tag name
	 */
	@Override
	public NodeList getElementsByTagName(String tagname) {
		if ("*".equals(tagname)) {
			return this.getNodeList(new ElementFilter());
		} else {
			return this.getNodeList(new TagNameFilter(tagname));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#importNode(org.w3c.dom.Node, boolean)
	 */
	@Override
	public Node importNode(Node importedNode, boolean deep) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Not implemented");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#createElementNS(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#createAttributeNS(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.lobobrowser.w3c.events.DocumentEvent#createEvent(java.lang.String)
	 */
	@Override
	public Event createEvent(String eventType) throws DOMException {

		switch (eventType) {
		case HtmlEventProperties.EVENT:
			return new EventImpl();
		case HtmlEventProperties.UIEVENT:
			return new UIEventImpl();
		case HtmlEventProperties.MOUSEEVENT:
			return new MouseEventImpl();
		case HtmlEventProperties.MUTATIONEVENT:
			return new MutationEventImpl();
		case HtmlEventProperties.MUTATIONNAMEEVENT:
			return new MutationNameEventImpl();
		case HtmlEventProperties.TEXTEVENT:
			return new TextEventImpl();
		case HtmlEventProperties.KEYBOARDEVENT:
			return new KeyboardEventImpl();
		case HtmlEventProperties.CUSTOMEVENT:
			return new CustomEventImpl();
		default:
			return new EventImpl();
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#getElementsByTagNameNS(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
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
	 * @see org.w3c.dom.Document#adoptNode(org.w3c.dom.Node)
	 */
	@Override
	public Node adoptNode(Node source) throws DOMException {
		if (source instanceof DOMNodeImpl) {
			DOMNodeImpl node = (DOMNodeImpl) source;
			node.setOwnerDocument(this, true);
			return node;
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid Node implementation");
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
	 * @see org.w3c.dom.Document#normalizeDocument()
	 */
	@Override
	public void normalizeDocument() {
		// TODO: Normalization options from domConfig
		synchronized (this.getTreeLock()) {
			this.visitImpl(new NodeVisitor() {
				@Override
				public void visit(Node node) {
					node.normalize();
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Document#renameNode(org.w3c.dom.Node, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "No renaming");
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
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#getLocalName()
	 */
	@Override
	public String getLocalName() {
		// Always null for document
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#getNodeName()
	 */
	@Override
	public String getNodeName() {
		return "#document";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#getNodeType()
	 */
	@Override
	public short getNodeType() {
		return Node.DOCUMENT_NODE;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#getNodeValue()
	 */
	@Override
	public String getNodeValue() throws DOMException {
		// Always null for document
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#setNodeValue(String)
	 */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "Cannot set node value of document");
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
				logger.error(
						"Unable to create URL for URI=[" + uri + "], with base=[" + this.getBaseURI() + "].", mfu);
				return null;
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
	final void addStyleSheet(CSSStyleSheet ss) {
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
	final StyleSheetAggregator getStyleSheetAggregator() {
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
				logger.error(iob);
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
				logger.error(iob);
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
				logger.error(iob);
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
				logger.error(iob);
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
				logger.error(iob);
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
				logger.error(iob);
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
				logger.error(iob);
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
				logger.error(iob);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#createRenderState(org.
	 * lobobrowser .html.renderstate.RenderState)
	 */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new StyleSheetRenderState(this);
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
		if ((rcontext == null) || !rcontext.isImageLoadingEnabled()) {
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
				httpRequest.addReadyStateChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if (httpRequest.getReadyState() == ReadyState.COMPLETE) {
							java.awt.Image newImage = httpRequest.getResponseImage();
							ImageEvent newEvent = newImage == null ? null
									: new ImageEvent(HTMLDocumentImpl.this, newImage);
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

					}
				});
				SecurityManager sm = System.getSecurityManager();
				if (sm == null) {
					httpRequest.open(Method.GET, url, true);
					httpRequest.send();
				} else {
					AccessController.doPrivileged(new PrivilegedAction<Object>() {
						@Override
						public Object run() {
							httpRequest.open(Method.GET, url, true);
							httpRequest.send();
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.lobobrowser.html.domimpl.DOMNodeImpl#setUserData(java.lang.String,
	 * java.lang.Object, org.w3c.dom.UserDataHandler)
	 */
	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		Function onloadHandler = this.onloadHandler;
		if (onloadHandler != null) {
			boolean dataBool = (boolean)data;
			if (HtmlParser.MODIFYING_KEY.equals(key) && !dataBool) {
				Executor.executeFunction(this, onloadHandler, null);
			}
		}
		return super.setUserData(key, data, handler);
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
		if ((oldDomain != null) && Domains.isValidCookieDomain(domain, oldDomain)) {
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
	 * Gets the onload handler.
	 *
	 * @return the onload handler
	 */
	public Function getOnloadHandler() {
		return onloadHandler;
	}

	/**
	 * Sets the onload handler.
	 *
	 * @param onloadHandler
	 *            the new onload handler
	 */
	public void setOnloadHandler(Function onloadHandler) {
		this.onloadHandler = onloadHandler;
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

	/**
	 * Gets the document host.
	 *
	 * @return the document host
	 */
	String getDocumentHost() {
		URL docUrl = this.documentURL;
		return docUrl == null ? null : docUrl.getHost();
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
	void removeElementById(String id) {
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
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getCharacterSet()
	 */
	@Override
	public String getCharacterSet() {
		NodeList nodeList = getElementsByTagName(HtmlProperties.META);
		ElementAttributeFilter attr = new ElementAttributeFilter(nodeList, HtmlAttributeProperties.CHARSET);
		return attr.getAttribute();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getDefaultCharset()
	 */
	@Override
	public String getDefaultCharset() {
		return Charset.defaultCharset().displayName();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getReadyState()
	 */
	@Override
	public String getReadyState() {
		HtmlRendererContext rcontext = this.getHtmlRendererContext();
		UserAgentContext uac = rcontext.getUserAgentContext();
		HttpRequest httpRequest = uac.createHttpRequest();
		if (httpRequest.getReadyState() == ReadyState.UNINITIALIZED) {
			return "uninitialized";
		} else if (httpRequest.getReadyState() == ReadyState.LOADING) {
			return "loading";
		} else if (httpRequest.getReadyState() == ReadyState.LOADED) {
			return "loaded";
		} else if (httpRequest.getReadyState() == ReadyState.INTERACTIVE) {
			return "interactive";
		} else if (httpRequest.getReadyState() == ReadyState.COMPLETE) {
			return "complete";
		} else {
			return "";
		}
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
	 * @see
	 * org.lobobrowser.w3c.html.HTMLDocument#getElementsByClassName(java.lang.
	 * String )
	 */
	@Override
	public NodeList getElementsByClassName(String classNames) {
		return this.getNodeList(new ClassNameFilter(classNames));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#hasFocus()
	 */
	@Override
	public boolean hasFocus() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getDesignMode()
	 */
	@Override
	public String getDesignMode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.lobobrowser.w3c.html.HTMLDocument#setDesignMode(java.lang.String)
	 */
	@Override
	public void setDesignMode(String designMode) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#execCommand(java.lang.String)
	 */
	@Override
	public boolean execCommand(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#execCommand(java.lang.String,
	 * boolean)
	 */
	@Override
	public boolean execCommand(String commandId, boolean showUI) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#execCommand(java.lang.String,
	 * boolean, java.lang.String)
	 */
	@Override
	public boolean execCommand(String commandId, boolean showUI, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#queryCommandEnabled(java.lang.
	 * String)
	 */
	@Override
	public boolean queryCommandEnabled(String commandId) {
		Iterator<String> it = HtmlCommandMapping.EXECUTE_CMDS.iterator();
		while (it.hasNext()) {
			if (commandId.equalsIgnoreCase(it.next())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.lobobrowser.w3c.html.HTMLDocument#queryCommandIndeterm(java.lang.
	 * String)
	 */
	@Override
	public boolean queryCommandIndeterm(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.lobobrowser.w3c.html.HTMLDocument#queryCommandState(java.lang.String)
	 */
	@Override
	public boolean queryCommandState(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.lobobrowser.w3c.html.HTMLDocument#queryCommandSupported(java.lang.
	 * String)
	 */
	@Override
	public boolean queryCommandSupported(String commandId) {
		Iterator<String> it = HtmlCommandMapping.EXECUTE_CMDS.iterator();
		while (it.hasNext()) {
			if (commandId.equalsIgnoreCase(it.next())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.lobobrowser.w3c.html.HTMLDocument#queryCommandValue(java.lang.String)
	 */
	@Override
	public String queryCommandValue(String commandId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.lobobrowser.w3c.html.HTMLDocument#querySelector(java.lang.String)
	 */
	@Override
	public Element querySelector(String selectors) {
		QuerySelectorImpl qsel = new QuerySelectorImpl();
		return qsel.documentQuerySelector(this.document, selectors);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.lobobrowser.w3c.html.HTMLDocument#querySelectorAll(java.lang.String)
	 */
	@Override
	public NodeList querySelectorAll(String selectors) {
		QuerySelectorImpl qsel = new QuerySelectorImpl();
		return qsel.documentQuerySelectorAll(this.document, selectors);
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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getFgColor()
	 */
	@Override
	public String getFgColor() {
		NodeList nodeList = getElementsByTagName(HtmlProperties.BODY);
		ElementAttributeFilter attr = new ElementAttributeFilter(nodeList, HtmlAttributeProperties.TEXT);
		return attr.getAttribute();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#setFgColor(java.lang.String)
	 */
	@Override
	public void setFgColor(String fgColor) {
		ElementAttributeFilter attr = new ElementAttributeFilter(HtmlAttributeProperties.TEXT);
		attr.setAttribute(this, fgColor);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getBgColor()
	 */
	@Override
	public String getBgColor() {
		NodeList nodeList = getElementsByTagName(HtmlProperties.BODY);
		ElementAttributeFilter attr = new ElementAttributeFilter(nodeList, HtmlAttributeProperties.BGCOLOR);
		return attr.getAttribute();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#setBgColor(java.lang.String)
	 */
	@Override
	public void setBgColor(String bgColor) {
		ElementAttributeFilter attr = new ElementAttributeFilter(HtmlAttributeProperties.BGCOLOR);
		attr.setAttribute(this, bgColor);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getLinkColor()
	 */
	@Override
	public String getLinkColor() {
		NodeList nodeList = getElementsByTagName(HtmlProperties.BODY);
		ElementAttributeFilter attr = new ElementAttributeFilter(nodeList, HtmlAttributeProperties.LINK);
		return attr.getAttribute();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#setLinkColor(java.lang.String)
	 */
	@Override
	public void setLinkColor(String linkColor) {
		ElementAttributeFilter attr = new ElementAttributeFilter(HtmlAttributeProperties.LINK);
		attr.setAttribute(this, linkColor);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getVlinkColor()
	 */
	@Override
	public String getVlinkColor() {
		NodeList nodeList = getElementsByTagName(HtmlProperties.BODY);
		ElementAttributeFilter attr = new ElementAttributeFilter(nodeList, HtmlAttributeProperties.VLINK);
		return attr.getAttribute();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.lobobrowser.w3c.html.HTMLDocument#setVlinkColor(java.lang.String)
	 */
	@Override
	public void setVlinkColor(String vlinkColor) {
		ElementAttributeFilter attr = new ElementAttributeFilter(HtmlAttributeProperties.VLINK);
		attr.setAttribute(this, vlinkColor);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.w3c.html.HTMLDocument#getAlinkColor()
	 */
	@Override
	public String getAlinkColor() {
		NodeList nodeList = getElementsByTagName(HtmlProperties.BODY);
		ElementAttributeFilter attr = new ElementAttributeFilter(nodeList, HtmlAttributeProperties.ALINK);
		return attr.getAttribute();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.lobobrowser.w3c.html.HTMLDocument#setAlinkColor(java.lang.String)
	 */
	@Override
	public void setAlinkColor(String alinkColor) {
		ElementAttributeFilter attr = new ElementAttributeFilter(HtmlAttributeProperties.ALINK);
		attr.setAttribute(this, alinkColor);
	}

	@Override
	public void addEventListener(String script, Function function) {
		
		String key = script.toLowerCase();

		switch (key) {
		case HtmlJsAttributeProperties.CLICK:
			setOnclick(function);
			break;
		case HtmlJsAttributeProperties.DBLCLICK:
			setOndblclick(function);
			break;
		case HtmlJsAttributeProperties.MOUSEUP:
			setOnmouseup(function);
			break;
		case HtmlJsAttributeProperties.MOUSEDOWN:
			setOnmousedown(function);
			break;
		case HtmlJsAttributeProperties.MOUSEOVER:
			setOnmouseover(function);
			break;
		case HtmlJsAttributeProperties.MOUSEOUT:
			setOnmouseout(function);
			break;
		case HtmlJsAttributeProperties.KEYPRESS:
			setOnkeypress(function);
			break;
		case HtmlJsAttributeProperties.KEYUP:
			setOnkeyup(function);
			break;
		case HtmlJsAttributeProperties.KEYDOWN:
			setOnkeydown(function);
			break;
		case HtmlJsAttributeProperties.LOAD:
			setOnloadHandler(function);
			break;

		default:
			break;
		}

	}

	@Override
	public void removeEventListener(String script, Function function) {
		String key = script.toLowerCase();

		switch (key) {
		case HtmlJsAttributeProperties.CLICK:
			setOnclick(null);
			break;
		case HtmlJsAttributeProperties.DBLCLICK:
			setOndblclick(null);
			break;
		case HtmlJsAttributeProperties.MOUSEUP:
			setOnmouseup(null);
			break;
		case HtmlJsAttributeProperties.MOUSEDOWN:
			setOnmousedown(null);
			break;
		case HtmlJsAttributeProperties.MOUSEOVER:
			setOnmouseover(null);
			break;
		case HtmlJsAttributeProperties.MOUSEOUT:
			setOnmouseout(null);
			break;
		case HtmlJsAttributeProperties.KEYPRESS:
			setOnkeypress(null);
			break;
		case HtmlJsAttributeProperties.KEYUP:
			setOnkeyup(null);
			break;
		case HtmlJsAttributeProperties.KEYDOWN:
			setOnkeydown(null);
			break;
		case HtmlJsAttributeProperties.LOAD:
			setOnloadHandler(null);
			break;

		default:
			break;
		}
	}

	@Override
	public void addEventListener(String script, Function function, boolean bool) {
		addEventListener(script, function);
	}

	@Override
	public void removeEventListener(String script, Function function, boolean bool) {
		removeEventListener(script, function);
	}

	/**
	 * Gets the onclick.
	 *
	 * @return the onclick
	 */
	@Override
	public Function getOnclick() {
		return onclick;
	}

	/**
	 * Sets the onclick.
	 *
	 * @param onclick
	 *            the new onclick
	 */
	@Override
	public void setOnclick(Function onclick) {
		this.onclick = onclick;
	}

	/**
	 * Gets the onunload.
	 *
	 * @return the onunload
	 */
	public Function getOnunload() {
		return onunload;
	}

	/**
	 * Sets the onunload.
	 *
	 * @param onunload
	 *            the new onunload
	 */
	public void setOnunload(Function onunload) {
		this.onunload = onunload;
	}

	/**
	 * Gets the onmousedown.
	 *
	 * @return the onmousedown
	 */
	@Override
	public Function getOnmousedown() {
		return onmousedown;
	}

	/**
	 * Sets the onmousedown.
	 *
	 * @param onmousedown
	 *            the new onmousedown
	 */
	@Override
	public void setOnmousedown(Function onmousedown) {
		this.onmousedown = onmousedown;
	}

	/**
	 * Gets the onkeypress.
	 *
	 * @return the onkeypress
	 */
	@Override
	public Function getOnkeypress() {
		return onkeypress;
	}

	/**
	 * Sets the onkeypress.
	 *
	 * @param onkeypress
	 *            the new onkeypress
	 */
	@Override
	public void setOnkeypress(Function onkeypress) {
		this.onkeypress = onkeypress;
	}

	/**
	 * Gets the onkeydown.
	 *
	 * @return the onkeydown
	 */
	@Override
	public Function getOnkeydown() {
		return onkeydown;
	}

	/**
	 * Sets the onkeydown.
	 *
	 * @param onkeydown
	 *            the new onkeydown
	 */
	@Override
	public void setOnkeydown(Function onkeydown) {
		this.onkeydown = onkeydown;
	}

	/**
	 * Gets the onmouseup.
	 *
	 * @return the onmouseup
	 */
	@Override
	public Function getOnmouseup() {
		return onmouseup;
	}

	/**
	 * Sets the onmouseup.
	 *
	 * @param onmouseup
	 *            the new onmouseup
	 */
	@Override
	public void setOnmouseup(Function onmouseup) {
		this.onmouseup = onmouseup;
	}

	/**
	 * Gets the ondblclick.
	 *
	 * @return the ondblclick
	 */
	@Override
	public Function getOndblclick() {
		return ondblclick;
	}

	/**
	 * Sets the ondblclick.
	 *
	 * @param ondblclick
	 *            the new ondblclick
	 */
	@Override
	public void setOndblclick(Function ondblclick) {
		this.ondblclick = ondblclick;
	}

	/**
	 * Gets the onkeyup.
	 *
	 * @return the onkeyup
	 */
	@Override
	public Function getOnkeyup() {
		return onkeyup;
	}

	/**
	 * Sets the onkeyup.
	 *
	 * @param onkeyup
	 *            the new onkeyup
	 */
	@Override
	public void setOnkeyup(Function onkeyup) {
		this.onkeyup = onkeyup;
	}

	/**
	 * @return the onmouseout
	 */
	@Override
	public Function getOnmouseout() {
		return onmouseout;
	}

	/**
	 * @param onmouseout
	 *            the onmouseout to set
	 */
	@Override
	public void setOnmouseout(Function onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * @return the onmouseover
	 */
	@Override
	public Function getOnmouseover() {
		return onmouseover;
	}

	/**
	 * @param onmouseover
	 *            the onmouseover to set
	 */
	@Override
	public void setOnmouseover(Function onmouseover) {
		this.onmouseover = onmouseover;
	}
	
	

	/**
	 * @return the oncanplay
	 */
	public Function getOncanplay() {
		return oncanplay;
	}

	/**
	 * @param oncanplay the oncanplay to set
	 */
	public void setOncanplay(Function oncanplay) {
		this.oncanplay = oncanplay;
	}

	/**
	 * @return the onabort
	 */
	public Function getOnabort() {
		return onabort;
	}

	/**
	 * @param onabort the onabort to set
	 */
	public void setOnabort(Function onabort) {
		this.onabort = onabort;
	}

	/**
	 * @return the onblur
	 */
	public Function getOnblur() {
		return onblur;
	}

	/**
	 * @param onblur the onblur to set
	 */
	public void setOnblur(Function onblur) {
		this.onblur = onblur;
	}

	/**
	 * @return the oncanplaythrough
	 */
	public Function getOncanplaythrough() {
		return oncanplaythrough;
	}

	/**
	 * @param oncanplaythrough the oncanplaythrough to set
	 */
	public void setOncanplaythrough(Function oncanplaythrough) {
		this.oncanplaythrough = oncanplaythrough;
	}

	/**
	 * @return the onchange
	 */
	public Function getOnchange() {
		return onchange;
	}

	/**
	 * @param onchange the onchange to set
	 */
	public void setOnchange(Function onchange) {
		this.onchange = onchange;
	}

	/**
	 * @return the oncontextmenu
	 */
	public Function getOncontextmenu() {
		return oncontextmenu;
	}

	/**
	 * @param oncontextmenu the oncontextmenu to set
	 */
	public void setOncontextmenu(Function oncontextmenu) {
		this.oncontextmenu = oncontextmenu;
	}

	/**
	 * @return the oncuechange
	 */
	public Function getOncuechange() {
		return oncuechange;
	}

	/**
	 * @param oncuechange the oncuechange to set
	 */
	public void setOncuechange(Function oncuechange) {
		this.oncuechange = oncuechange;
	}

	/**
	 * @return the ondrag
	 */
	public Function getOndrag() {
		return ondrag;
	}

	/**
	 * @param ondrag the ondrag to set
	 */
	public void setOndrag(Function ondrag) {
		this.ondrag = ondrag;
	}

	/**
	 * @return the ondragend
	 */
	public Function getOndragend() {
		return ondragend;
	}

	/**
	 * @param ondragend the ondragend to set
	 */
	public void setOndragend(Function ondragend) {
		this.ondragend = ondragend;
	}

	/**
	 * @return the ondragenter
	 */
	public Function getOndragenter() {
		return ondragenter;
	}

	/**
	 * @param ondragenter the ondragenter to set
	 */
	public void setOndragenter(Function ondragenter) {
		this.ondragenter = ondragenter;
	}

	/**
	 * @return the ondragleave
	 */
	public Function getOndragleave() {
		return ondragleave;
	}

	/**
	 * @param ondragleave the ondragleave to set
	 */
	public void setOndragleave(Function ondragleave) {
		this.ondragleave = ondragleave;
	}

	/**
	 * @return the ondragover
	 */
	public Function getOndragover() {
		return ondragover;
	}

	/**
	 * @param ondragover the ondragover to set
	 */
	public void setOndragover(Function ondragover) {
		this.ondragover = ondragover;
	}

	/**
	 * @return the ondragstart
	 */
	public Function getOndragstart() {
		return ondragstart;
	}

	/**
	 * @param ondragstart the ondragstart to set
	 */
	public void setOndragstart(Function ondragstart) {
		this.ondragstart = ondragstart;
	}

	/**
	 * @return the ondrop
	 */
	public Function getOndrop() {
		return ondrop;
	}

	/**
	 * @param ondrop the ondrop to set
	 */
	public void setOndrop(Function ondrop) {
		this.ondrop = ondrop;
	}

	/**
	 * @return the ondurationchange
	 */
	public Function getOndurationchange() {
		return ondurationchange;
	}

	/**
	 * @param ondurationchange the ondurationchange to set
	 */
	public void setOndurationchange(Function ondurationchange) {
		this.ondurationchange = ondurationchange;
	}

	/**
	 * @return the onemptied
	 */
	public Function getOnemptied() {
		return onemptied;
	}

	/**
	 * @param onemptied the onemptied to set
	 */
	public void setOnemptied(Function onemptied) {
		this.onemptied = onemptied;
	}

	/**
	 * @return the onended
	 */
	public Function getOnended() {
		return onended;
	}

	/**
	 * @param onended the onended to set
	 */
	public void setOnended(Function onended) {
		this.onended = onended;
	}

	/**
	 * @return the onerror
	 */
	public Function getOnerror() {
		return onerror;
	}

	/**
	 * @param onerror the onerror to set
	 */
	public void setOnerror(Function onerror) {
		this.onerror = onerror;
	}

	/**
	 * @return the onfocus
	 */
	public Function getOnfocus() {
		return onfocus;
	}

	/**
	 * @param onfocus the onfocus to set
	 */
	public void setOnfocus(Function onfocus) {
		this.onfocus = onfocus;
	}

	/**
	 * @return the oninput
	 */
	public Function getOninput() {
		return oninput;
	}

	/**
	 * @param oninput the oninput to set
	 */
	public void setOninput(Function oninput) {
		this.oninput = oninput;
	}

	/**
	 * @return the oninvalid
	 */
	public Function getOninvalid() {
		return oninvalid;
	}

	/**
	 * @param oninvalid the oninvalid to set
	 */
	public void setOninvalid(Function oninvalid) {
		this.oninvalid = oninvalid;
	}

	/**
	 * @return the onload
	 */
	public Function getOnload() {
		return onload;
	}

	/**
	 * @param onload the onload to set
	 */
	public void setOnload(Function onload) {
		this.onload = onload;
	}

	/**
	 * @return the onloadeddata
	 */
	public Function getOnloadeddata() {
		return onloadeddata;
	}

	/**
	 * @param onloadeddata the onloadeddata to set
	 */
	public void setOnloadeddata(Function onloadeddata) {
		this.onloadeddata = onloadeddata;
	}

	/**
	 * @return the onloadedmetadata
	 */
	public Function getOnloadedmetadata() {
		return onloadedmetadata;
	}

	/**
	 * @param onloadedmetadata the onloadedmetadata to set
	 */
	public void setOnloadedmetadata(Function onloadedmetadata) {
		this.onloadedmetadata = onloadedmetadata;
	}

	/**
	 * @return the onloadstart
	 */
	public Function getOnloadstart() {
		return onloadstart;
	}

	/**
	 * @param onloadstart the onloadstart to set
	 */
	public void setOnloadstart(Function onloadstart) {
		this.onloadstart = onloadstart;
	}

	/**
	 * @return the onmousewheel
	 */
	public Function getOnmousewheel() {
		return onmousewheel;
	}

	/**
	 * @param onmousewheel the onmousewheel to set
	 */
	public void setOnmousewheel(Function onmousewheel) {
		this.onmousewheel = onmousewheel;
	}

	/**
	 * @return the onpause
	 */
	public Function getOnpause() {
		return onpause;
	}

	/**
	 * @param onpause the onpause to set
	 */
	public void setOnpause(Function onpause) {
		this.onpause = onpause;
	}

	/**
	 * @return the onplay
	 */
	public Function getOnplay() {
		return onplay;
	}

	/**
	 * @param onplay the onplay to set
	 */
	public void setOnplay(Function onplay) {
		this.onplay = onplay;
	}

	/**
	 * @return the onplaying
	 */
	public Function getOnplaying() {
		return onplaying;
	}

	/**
	 * @param onplaying the onplaying to set
	 */
	public void setOnplaying(Function onplaying) {
		this.onplaying = onplaying;
	}

	/**
	 * @return the onprogress
	 */
	public Function getOnprogress() {
		return onprogress;
	}

	/**
	 * @param onprogress the onprogress to set
	 */
	public void setOnprogress(Function onprogress) {
		this.onprogress = onprogress;
	}

	/**
	 * @return the onreadystatechange
	 */
	public Function getOnreadystatechange() {
		return onreadystatechange;
	}

	/**
	 * @param onreadystatechange the onreadystatechange to set
	 */
	public void setOnreadystatechange(Function onreadystatechange) {
		this.onreadystatechange = onreadystatechange;
	}

	/**
	 * @return the onreset
	 */
	public Function getOnreset() {
		return onreset;
	}

	/**
	 * @param onreset the onreset to set
	 */
	public void setOnreset(Function onreset) {
		this.onreset = onreset;
	}

	/**
	 * @return the onscroll
	 */
	public Function getOnscroll() {
		return onscroll;
	}

	/**
	 * @param onscroll the onscroll to set
	 */
	public void setOnscroll(Function onscroll) {
		this.onscroll = onscroll;
	}

	/**
	 * @return the onseeked
	 */
	public Function getOnseeked() {
		return onseeked;
	}

	/**
	 * @param onseeked the onseeked to set
	 */
	public void setOnseeked(Function onseeked) {
		this.onseeked = onseeked;
	}

	/**
	 * @return the onseeking
	 */
	public Function getOnseeking() {
		return onseeking;
	}

	/**
	 * @param onseeking the onseeking to set
	 */
	public void setOnseeking(Function onseeking) {
		this.onseeking = onseeking;
	}

	/**
	 * @return the onselect
	 */
	public Function getOnselect() {
		return onselect;
	}

	/**
	 * @param onselect the onselect to set
	 */
	public void setOnselect(Function onselect) {
		this.onselect = onselect;
	}

	/**
	 * @return the onshow
	 */
	public Function getOnshow() {
		return onshow;
	}

	/**
	 * @param onshow the onshow to set
	 */
	public void setOnshow(Function onshow) {
		this.onshow = onshow;
	}

	/**
	 * @return the onstalled
	 */
	public Function getOnstalled() {
		return onstalled;
	}

	/**
	 * @param onstalled the onstalled to set
	 */
	public void setOnstalled(Function onstalled) {
		this.onstalled = onstalled;
	}

	/**
	 * @return the onsubmit
	 */
	public Function getOnsubmit() {
		return onsubmit;
	}

	/**
	 * @param onsubmit the onsubmit to set
	 */
	public void setOnsubmit(Function onsubmit) {
		this.onsubmit = onsubmit;
	}

	/**
	 * @return the onsuspend
	 */
	public Function getOnsuspend() {
		return onsuspend;
	}

	/**
	 * @param onsuspend the onsuspend to set
	 */
	public void setOnsuspend(Function onsuspend) {
		this.onsuspend = onsuspend;
	}

	/**
	 * @return the ontimeupdate
	 */
	public Function getOntimeupdate() {
		return ontimeupdate;
	}

	/**
	 * @param ontimeupdate the ontimeupdate to set
	 */
	public void setOntimeupdate(Function ontimeupdate) {
		this.ontimeupdate = ontimeupdate;
	}

	/**
	 * @return the onvolumechange
	 */
	public Function getOnvolumechange() {
		return onvolumechange;
	}

	/**
	 * @param onvolumechange the onvolumechange to set
	 */
	public void setOnvolumechange(Function onvolumechange) {
		this.onvolumechange = onvolumechange;
	}

	/**
	 * @return the onwaiting
	 */
	public Function getOnwaiting() {
		return onwaiting;
	}

	/**
	 * @param onwaiting the onwaiting to set
	 */
	public void setOnwaiting(Function onwaiting) {
		this.onwaiting = onwaiting;
	}

	/**
	 * @return the onmousemove
	 */
	public Function getOnmousemove() {
		return onmousemove;
	}

	/**
	 * @param onmousemove the onmousemove to set
	 */
	public void setOnmousemove(Function onmousemove) {
		this.onmousemove = onmousemove;
	}

	/**
	 * @return the onratechange
	 */
	public Function getOnratechange() {
		return onratechange;
	}

	/**
	 * @param onratechange the onratechange to set
	 */
	public void setOnratechange(Function onratechange) {
		this.onratechange = onratechange;
	}

	/**
	 * Evaluate.
	 *
	 * @param expression
	 *            the expression
	 * @param contextNode
	 *            the context node
	 * @param resolver
	 *            the resolver
	 * @param type
	 *            the type
	 * @param result
	 *            the result
	 */
	public void evaluate(String expression, HTMLDocumentImpl contextNode, XPathNSResolver resolver, short type, Object result) {
		eval(expression, contextNode, resolver, type, result);
	}

	@Override
	public XPathExpression createExpression(String expression, XPathNSResolver resolver) {
		XPathEvaluatorImpl evaluator = new XPathEvaluatorImpl(document);
		return evaluator.createExpression(expression, resolver);
	}

	@Override
	public XPathNSResolver createNSResolver(Node nodeResolver) {
		XPathEvaluatorImpl evaluator = new XPathEvaluatorImpl(document);
		return evaluator.createNSResolver(nodeResolver);
	}

	@Override
	public XPathResult evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type,
			Object result) {
		return eval(expression, contextNode, resolver, type, result);
	}

	@Override
	public XPathResultImpl evaluate(String expression, HTMLElement contextNode, XPathNSResolverImpl resolver,
			Short type, Object result) {
		return eval(expression, contextNode, resolver, type, result);
	}

	/**
	 * Eval.
	 *
	 * @param expression
	 *            the expression
	 * @param contextNode
	 *            the context node
	 * @param resolver
	 *            the resolver
	 * @param type
	 *            the type
	 * @param result
	 *            the result
	 * @return the x path result
	 */
	private XPathResultImpl eval(String expression, Node contextNode, XPathNSResolver resolver, short type, Object result) {
		XPathEvaluatorImpl evaluator = new XPathEvaluatorImpl(document);
		return (XPathResultImpl) evaluator.evaluate(expression, contextNode, resolver, type, result);

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
	public DOMElementMap getCssElementMap() {
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
	public Element getActiveElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}


	@Override
	public boolean dispatchEvent(Event evt) throws EventException, DOMException {
		// TODO Auto-generated method stub
		return false;
	}
}
