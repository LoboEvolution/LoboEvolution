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

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Domains;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.filter.*;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.dom.nodeimpl.TextImpl;
import org.loboevolution.html.dom.xpath.XPathEvaluatorImpl;
import org.loboevolution.html.io.LocalWritableLineReader;
import org.loboevolution.html.io.WritableLineReader;
import org.loboevolution.html.js.WindowImpl;
import org.loboevolution.html.js.events.EventFactory;
import org.loboevolution.html.node.*;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.js.Location;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.xpath.XPathEvaluator;
import org.loboevolution.html.xpath.XPathExpression;
import org.loboevolution.html.xpath.XPathNSResolver;
import org.loboevolution.html.xpath.XPathResult;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.jsenum.DocumentReadyState;
import org.loboevolution.jsenum.VisibilityState;

import java.io.IOException;
import java.io.LineNumberReader;

/**
 * <p>DocumentImpl class.</p>
 */
public class DocumentImpl extends GlobalEventHandlersImpl implements Document, XPathEvaluator {

	private boolean strictErrorChecking = true;

	private boolean xmlStandalone;

	private boolean isrss = false;

	private String xmlVersion = null;

	private String documentURI;

	private String title;

	private String domain;

	private String referrer;

	private DocumentType doctype;

	private HTMLElement body;

	private Window window;

	public WritableLineReader reader;

	/** {@inheritDoc} */
	@Override
	public Node adoptNode(Node source) {
		NodeImpl node = (NodeImpl) source;
		node.setOwnerDocument(this.document, true);
		return node;
	}

	/** {@inheritDoc} */
	@Override
	public Element createElement(String tagName) {
		if ("rss".equalsIgnoreCase(tagName)) {isrss = true;}
		return new ElementFactory(isrss).createElement((HTMLDocumentImpl) this, tagName);
	}

	/** {@inheritDoc} */
	@Override
	public Element createElementNS(String namespaceURI, String qualifiedName) {
		if (Strings.isBlank(namespaceURI) || "http://www.w3.org/1999/xhtml".equalsIgnoreCase(namespaceURI)) {
			return createElement(qualifiedName);
		} else if ("http://www.w3.org/2000/svg".equalsIgnoreCase(namespaceURI)) {
			return createElement(qualifiedName);
		}
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public Element createElementNS(String namespaceURI, String qualifiedName, String options) {
		return createElementNS(namespaceURI, qualifiedName);
	}

	/** {@inheritDoc} */
	@Override
	public DocumentType getDoctype() {
		return this.doctype;
	}

	/**
	 * <p>Setter for the field doctype.</p>
	 *
	 * @param doctype a {@link org.w3c.dom.DocumentType} object.
	 */
	public void setDoctype(DocumentType doctype) {
		this.doctype = doctype;
	}

	/** {@inheritDoc} */
	@Override
	public Element getDocumentElement() {
		for (Node node : nodeList) {
			if (node instanceof Element) {
				return (Element) node;
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Text createTextNode(String data) {
		final TextImpl node = new TextImpl(data);
		node.setOwnerDocument(this);
		return node;
	}

	/** {@inheritDoc} */
	@Override
	public Attr createAttribute(String name) {
		return new AttrImpl(name);
	}

	/** {@inheritDoc} */
	@Override
	public CDATASection createCDATASection(String data) {
		CDataSectionImpl node = new CDataSectionImpl(data);
		node.setOwnerDocument(this.document);
		return node;
	}

	/** {@inheritDoc} */
	@Override
	public Comment createComment(String data) {
		CommentImpl node = new CommentImpl(data);
		node.setOwnerDocument(this.document);
		return node;
	}

	/** {@inheritDoc} */
	@Override
	public DocumentFragment createDocumentFragment() {
		final DocumentFragmentImpl node = new DocumentFragmentImpl();
		node.setOwnerDocument(this);
		return node;
	}

	/** {@inheritDoc} */
	@Override
	public ProcessingInstruction createProcessingInstruction(String target, String data) {
		final HTMLProcessingInstruction node = new HTMLProcessingInstruction("");
		node.setData(data);
		node.setTarget(target);
		node.setOwnerDocument(this);
		return node;
	}

	/** {@inheritDoc} */
	@Override
	public DOMConfiguration getDomConfig() {
		return new DOMConfigurationImpl();
	}

	/** {@inheritDoc} */
	@Override
	public Element getElementById(String elementId) {
		NodeList nodeList = getNodeList(new IdFilter(elementId));
		return nodeList != null && nodeList.getLength() > 0 ? (Element)nodeList.item(0) : null;
	}

	/** {@inheritDoc} */
	@Override
	public DOMImplementation getImplementation() {
		return new DOMImplementationImpl(new UserAgentContext());
	}

	/** {@inheritDoc} */
	@Override
	public String getDocumentURI() {
		return this.documentURI;
	}

	/** {@inheritDoc} */
	@Override
	public void setDocumentURI(String documentURI) {
		this.documentURI = documentURI;
	}

	/** {@inheritDoc} */
	@Override
	public String getInputEncoding() {
		return "UTF-8";
	}

	/** {@inheritDoc} */
	@Override
	public String getXmlEncoding() {
		return "UTF-8";
	}
	
	/** {@inheritDoc} */
	@Override
	public void normalizeDocument() {
		visitImpl(Node::normalize);
	}

	/** {@inheritDoc} */
	@Override
	public boolean getStrictErrorChecking() {
		return this.strictErrorChecking;
	}

	/** {@inheritDoc} */
	@Override
	public void setStrictErrorChecking(boolean strictErrorChecking) {
		this.strictErrorChecking = strictErrorChecking;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getXmlStandalone() {
		return this.xmlStandalone;
	}

	/** {@inheritDoc} */
	@Override
	public String getXmlVersion() {
		return this.xmlVersion == null ? "1.0" : this.xmlVersion;
	}

	/** {@inheritDoc} */
	@Override
	public void setXmlStandalone(boolean xmlStandalone) {
		this.xmlStandalone = xmlStandalone;
	}

	/** {@inheritDoc} */
	@Override
	public void setXmlVersion(String xmlVersion) {
		this.xmlVersion = xmlVersion;
	}

	/** {@inheritDoc} */
	@Override
	public Event createEvent(String eventType) {
		return EventFactory.createEvent(eventType);
	}

	/** {@inheritDoc} */
	@Override
	public XPathExpression createExpression(String expression, XPathNSResolver resolver) {
		XPathEvaluatorImpl evaluator = new XPathEvaluatorImpl(document);
		return evaluator.createExpression(expression, resolver);
	}

	/** {@inheritDoc} */
	@Override
	public XPathNSResolver createNSResolver(Node nodeResolver) {
		XPathEvaluatorImpl evaluator = new XPathEvaluatorImpl(document);
		return evaluator.createNSResolver(nodeResolver);
	}

	/** {@inheritDoc} */
	@Override
	public XPathResult evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type, Object result) {
		return eval(expression, contextNode, resolver, type, result);
	}

	private XPathResult eval(String expression, Node contextNode, XPathNSResolver resolver, short type, Object result) {
		XPathEvaluatorImpl evaluator = new XPathEvaluatorImpl(document);
		return (XPathResult) evaluator.evaluate(expression, contextNode, resolver, type, result);
	}
	
	/** {@inheritDoc} */
	@Override
	public XPathExpression createExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getURL() {
		return getDocumentURI();
	}

	/** {@inheritDoc} */
	@Override
	public Element getActiveElement() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getAlinkColor() {
		final HTMLElement elem = getBody();
		HTMLBodyElement body = (HTMLBodyElement) elem;
		return body.getALink();
}

	/** {@inheritDoc} */
	@Override
	public void setAlinkColor(String alinkColor) {
		final HTMLElement elem = getBody();
		HTMLBodyElement body = (HTMLBodyElement) elem;
		body.setALink(alinkColor);
	}

	/** {@inheritDoc} */
	@Override
	public String getBgColor() {
		final HTMLElement elem = getBody();
		HTMLBodyElement body = (HTMLBodyElement) elem;
		return body.getBgColor();
	}

	/** {@inheritDoc} */
	@Override
	public void setBgColor(String bgColor) {
		final HTMLElement elem = getBody();
		HTMLBodyElement body = (HTMLBodyElement) elem;
		body.setBgColor(bgColor);
	}

	/** {@inheritDoc} */
	@Override
	public String getCharacterSet() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getCharset() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getCompatMode() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getCookie() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setCookie(String cookie) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public HTMLScriptElement getCurrentScript() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Window getDefaultView() {
		return this.window;
	}
	public void setWindow(final HtmlRendererContext rcontext, final UserAgentContext ucontext){
		if (rcontext != null) {
			window = WindowImpl.getWindow(rcontext);
		} else {
			// Plain parsers may use Javascript too.
			window = new WindowImpl(null, ucontext);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getDesignMode() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setDesignMode(String designMode) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getDir() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setDir(String dir) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getDomain() {
		return this.domain;
	}

	/** {@inheritDoc} */
	@Override
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/** {@inheritDoc} */
	@Override
	public String getFgColor() {
		final HTMLElement elem = getBody();
		HTMLBodyElement body = (HTMLBodyElement) elem;
		return body.getText();
	}

	/** {@inheritDoc} */
	@Override
	public void setFgColor(String fgColor) {
		final HTMLElement elem = getBody();
		HTMLBodyElement body = (HTMLBodyElement) elem;
		body.setText(fgColor);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isFullscreen() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isFullscreenEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLHeadElement getHead() {
		synchronized (this) {
			HTMLCollection collection = new HTMLCollectionImpl(this, new HeadFilter());
			if(collection.getLength() > 0) return (HTMLHeadElement)collection.item(0);
			else return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public HTMLElement getBody() {
		synchronized (this) {
			if(this.body != null) return this.body;
			HTMLCollection collection = new HTMLCollectionImpl(this, new HeadFilter());
			if(collection.getLength() > 0) return (HTMLElement) collection.item(0);
			else return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setBody(HTMLElement body) {
		this.body = body;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getApplets() {
		synchronized (this) {
			return new HTMLCollectionImpl(this, new ElementFilter("APPLET"));
		}
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getImages() {
        synchronized (this) {
            return new HTMLCollectionImpl(this, new ImageFilter());
        }
    }
	
	/** {@inheritDoc} */
	@Override
	public HTMLCollection getLinks() {
        synchronized (this) {
            return new HTMLCollectionImpl(this, new LinkFilter());
        }
    }
	
    /** {@inheritDoc} */
    @Override
    public HTMLCollection getForms() {
        synchronized (this) {
            return new HTMLCollectionImpl(this, new FormFilter());
        }
    }
        
    
    /** {@inheritDoc} */
    @Override
    public HTMLCollection getEmbeds() {
        synchronized (this) {
            return new HTMLCollectionImpl(this, new EmbedFilter());
        }
    }

    /** {@inheritDoc} */
    @Override
    public HTMLCollection getPlugins() {
        return getEmbeds();
    }

    /** {@inheritDoc} */
    @Override
    public HTMLCollection getScripts() {
        synchronized (this) {
            return new HTMLCollectionImpl(this, new ScriptFilter());
        }
    }

    /** {@inheritDoc} */
    @Override
    public HTMLCollection getCommands() {
        synchronized (this) {
            return new HTMLCollectionImpl(this, new CommandFilter());
        }
    }
    
	/** {@inheritDoc} */
    @Override
	public HTMLCollection getAnchors() {
    	return new HTMLCollectionImpl(this, new AnchorFilter());
	}
    
	/** {@inheritDoc} */
    @Override
	public HTMLCollection getAll() {
    	 return new HTMLCollectionImpl(this, new ElementFilter(null));
	}
    
    /** {@inheritDoc} */
    @Override
	public HTMLCollection getElementsByName(String elementName) {
		return new HTMLCollectionImpl(this, new ElementNameFilter(elementName));
	}

	/** {@inheritDoc} */
	@Override
	public String getLastModified() {
		// TODO Auto-generated method stub
		return null;
	}  

	/** {@inheritDoc} */
	@Override
	public Location getLocation() {
		return this.getDefaultView().getLocation();
	}

	/** {@inheritDoc} */
	@Override
	public void setLocation(Location location) {
		getDefaultView().setLocation(location);
	}

	/** {@inheritDoc} */
	@Override
	public String getOrigin() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public DocumentReadyState getReadyState() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getReferrer() {
		return this.referrer;
	}

	/**
	 * <p>Setter for the field referrer.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setReferrer(String value) {
		this.referrer = value;
	}

	/** {@inheritDoc} */
	@Override
	public Element getScrollingElement() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return this.title;
	}

	/** {@inheritDoc} */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/** {@inheritDoc} */
	@Override
	public VisibilityState getVisibilityState() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void captureEvents() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public Range caretRangeFromPoint(double x, double y) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void close() {
		synchronized (this.treeLock) {
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


	/** {@inheritDoc} */
	@Override
	public NodeIterator createNodeIterator(Node root) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Range createRange() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public TreeWalker createTreeWalker(Node root) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Element elementFromPoint(double x, double y) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean execCommand(String commandId, boolean showUI, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean execCommand(String commandId, boolean showUI) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean execCommand(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public Selection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasFocus() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public Document open(String url, String name, String features, boolean replace) {
		return open();
	}

	/** {@inheritDoc} */
	@Override
	public Document open(String url, String name, String features) {
		return open();
	}

	/** {@inheritDoc} */
	@Override
	public Document open(String url, String name) {
		return open();
	}

	/** {@inheritDoc} */
	@Override
	public Document open(String url) {
		return open();
	}

	/** {@inheritDoc} */
	@Override
	public Document open() {
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
					return this;
				}
			}
			removeAllChildrenImpl();
			this.reader = new LocalWritableLineReader((HTMLDocumentImpl)this, new LineNumberReader(this.reader));
		}
		return this;
	}

	public void removeAllChildrenImpl() {
		this.nodeList.clear();
		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandEnabled(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandIndeterm(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandState(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandSupported(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String queryCommandValue(String commandId) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void releaseEvents() {
		// TODO Auto-generated method stub
		
	}
	
	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
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
