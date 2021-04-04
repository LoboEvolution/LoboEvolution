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

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Iterator;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLHeadElement;
import org.loboevolution.html.dom.HTMLScriptElement;
import org.loboevolution.html.dom.filter.AnchorFilter;
import org.loboevolution.html.dom.filter.CommandFilter;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.loboevolution.html.dom.filter.ElementNameFilter;
import org.loboevolution.html.dom.filter.EmbedFilter;
import org.loboevolution.html.dom.filter.FormFilter;
import org.loboevolution.html.dom.filter.IdFilter;
import org.loboevolution.html.dom.filter.ImageFilter;
import org.loboevolution.html.dom.filter.LinkFilter;
import org.loboevolution.html.dom.filter.ScriptFilter;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.dom.nodeimpl.TextImpl;
import org.loboevolution.html.dom.xpath.XPathEvaluatorImpl;
import org.loboevolution.html.io.LocalWritableLineReader;
import org.loboevolution.html.io.WritableLineReader;
import org.loboevolution.html.js.events.EventFactory;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.jsenum.DocumentReadyState;
import org.loboevolution.jsenum.VisibilityState;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.CDATASection;
import org.loboevolution.html.node.Comment;
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeIterator;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ProcessingInstruction;
import org.loboevolution.html.node.Range;
import org.loboevolution.html.node.Selection;
import org.loboevolution.html.node.Text;
import org.loboevolution.html.node.TreeWalker;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.js.Location;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.xpath.XPathExpression;
import org.loboevolution.html.xpath.XPathNSResolver;
import org.loboevolution.html.xpath.XPathResult;
import org.loboevolution.html.xpath.XPathEvaluator;

/**
 * <p>DocumentImpl class.</p>
 *
 *
 *
 */
public class DocumentImpl extends GlobalEventHandlersImpl implements Document, XPathEvaluator {

	private boolean strictErrorChecking = true;

	private boolean xmlStandalone;

	private String xmlVersion = null;

	private String documentURI;

	private DocumentType doctype;
	
	private boolean isrss = false;
	
	private String title;
	
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
		if("rss".equalsIgnoreCase(tagName)) {isrss = true;}
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
		for (Iterator<Node> i = nodeList.iterator(); i.hasNext();) {
			Node node = i.next();
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
	public int getChildElementCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public Element getFirstElementChild() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Element getLastElementChild() {
		// TODO Auto-generated method stub
		return null;
	}	

	/** {@inheritDoc} */
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setAlinkColor(String alinkColor) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getApplets() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getBgColor() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setBgColor(String bgColor) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public HTMLElement getBody() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setBody(HTMLElement body) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setDomain(String domain) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getFgColor() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setFgColor(String fgColor) {
		// TODO Auto-generated method stub
		
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
	public HTMLHeadElement getHead() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setLocation(Location location) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
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
					// Already open, return.
					// Do not close http/file documents in progress.
					return this;
				}
			}
			removeAllChildrenImpl();
			this.reader = new LocalWritableLineReader((HTMLDocumentImpl)this, new LineNumberReader(this.reader));
		}
		return this;
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
