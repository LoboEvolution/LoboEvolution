/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.html.dom.nodeimpl;

import lombok.Getter;
import lombok.Setter;
import org.htmlunit.cssparser.dom.DOMException;
import org.htmlunit.cssparser.parser.selector.Selector;
import org.htmlunit.cssparser.parser.selector.SelectorList;
import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.HTMLTag;
import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.domimpl.*;
import org.loboevolution.html.dom.filter.*;
import org.loboevolution.html.dom.nodeimpl.traversal.NodeIteratorImpl;
import org.loboevolution.html.dom.nodeimpl.traversal.TreeWalkerImpl;
import org.loboevolution.html.dom.xpath.XPathEvaluatorImpl;
import org.loboevolution.html.io.LocalWritableLineReader;
import org.loboevolution.html.io.WritableLineReader;
import org.loboevolution.html.js.WindowImpl;
import org.loboevolution.html.js.events.EventFactory;
import org.loboevolution.html.node.*;
import org.loboevolution.events.Event;
import org.loboevolution.html.node.ranges.Range;
import org.loboevolution.js.Location;
import org.loboevolution.js.Window;
import org.loboevolution.traversal.NodeFilter;
import org.loboevolution.traversal.NodeIterator;
import org.loboevolution.traversal.TreeWalker;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.html.style.StyleSheetAggregator;
import org.loboevolution.html.xpath.XPathEvaluator;
import org.loboevolution.html.xpath.XPathExpression;
import org.loboevolution.html.xpath.XPathNSResolver;
import org.loboevolution.html.xpath.XPathResult;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.type.DocumentReadyState;
import org.loboevolution.type.VisibilityState;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * <p>DocumentImpl class.</p>
 */
public class DocumentImpl extends NodeImpl implements Document, XPathEvaluator {

	private boolean strictErrorChecking = true;

	private boolean xmlStandalone;

	private boolean xml = false;

	private boolean isrss = false;

	@Getter
	@Setter
	private boolean test = false;

	private String xmlVersion = null;

	private String documentURI;

	private String title;

	private String domain;

	@Getter
	@Setter
	private String referrer;

    private HTMLElement body;

	private Window window;

	public WritableLineReader reader;

	/** {@inheritDoc} */
	@Override
	public Node adoptNode(final Node source) {

		if (source instanceof DocumentType || Objects.equals(this, source)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknwon node implementation");
		}


		if (source instanceof EntityReference || source instanceof Notation) {
			throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "readonly node");
		}

		source.setOwnerDocument(this.document, true);
		return source;
	}

	@Override
	public Node renameNode(final Node node, final String namespaceURI, final String qualifiedName) {
		if (node instanceof Attr) {
			return createAttributeNS(namespaceURI, qualifiedName);
		}

		if (node instanceof Element) {
			return createElementNS(namespaceURI, qualifiedName);
		}

		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknwon node implementation");
	}

	/** {@inheritDoc} */
	@Override
	public Element createElement(final String tn) {
		String tagName = tn;
		if (Strings.isNotBlank(tagName) && tagName.equals(":")) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
		}

		if (Strings.isNotBlank(tagName) && tagName.contains(":")) {
			tagName = tagName.split(":")[1];
			if (!Strings.isXMLIdentifier(tagName)) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
			}
		}

		if (Strings.isBlank(tagName) || !Strings.isValidTag(tagName, isXml())) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
		}

		if ("rss".equalsIgnoreCase(tagName)) {
			isrss = true;
		}
		return new ElementFactory(isrss).createElement((HTMLDocumentImpl) this, tagName.toUpperCase());
	}

	@Override
	public EntityReference createEntityReference(final String entity) {
		final EntityReferenceImpl entityReference = new EntityReferenceImpl();
		entityReference.setNodeName(entity);
		entityReference.setOwnerDocument(this);
		return entityReference;
	}

	/** {@inheritDoc} */
	@Override
	public Element createElementNS(final String nUri, final String qName) {
		String prefix = null;
		final String qualifiedName = qName;
		String namespaceURI = nUri;

		if (Strings.isBlank(qualifiedName)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
		}

		if (qualifiedName.contains(":")) {

			if (qualifiedName.chars().filter(ch -> ch == ':').count() != 1) {
				throw new DOMException(DOMException.NAMESPACE_ERR, "The qualified name provided has error.");
			}

			final String[] split = qualifiedName.split(":");
			if (split.length != 2) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name provided has an empty local name.");
			}

			if (Strings.isBlank(split[0]) || Strings.isBlank(split[1])) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name provided has an empty local name.");
			}

			if (!Strings.isXMLIdentifier(split[0]) || !Strings.isXMLIdentifier(split[1])) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
			}

			if (Strings.isBlank(namespaceURI)) {
				throw new DOMException(DOMException.NAMESPACE_ERR, "The namespace URI provided is not valid");
			}

			prefix = split[0];
		} else{
			namespaceURI = Strings.isBlank(namespaceURI) ? getNamespaceURI() : namespaceURI;

			if (!Strings.isXMLIdentifier(qualifiedName)) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
			}

			if ("xmlns".equals(qualifiedName)) {
				if (!XMLNS_NAMESPACE_URI.equals(namespaceURI)) {
					throw new DOMException(DOMException.NAMESPACE_ERR, "xmlns local name but not xmlns namespace");
				}
			}
		}
		final Element elem = new ElementFactory(false).createElement((HTMLDocumentImpl) this, qualifiedName);
		elem.setNamespaceURI(namespaceURI);
		if (Strings.isNotBlank(prefix) && Strings.isNotBlank(namespaceURI)) {
			elem.setPrefix(prefix);
		}
		return elem;
	}

	/** {@inheritDoc} */
	@Override
	public Element createElementNS(final String namespaceURI, final String qualifiedName, final String options) {
		if (Strings.isBlank(qualifiedName) || !Strings.isXMLIdentifier(qualifiedName)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
		}

		if ("xmlns".equals(qualifiedName)) {
			if (!XMLNS_NAMESPACE_URI.equals(namespaceURI)) {
				throw new DOMException(DOMException.NAMESPACE_ERR, "xmlns local name but not xmlns namespace");
			}
		}

		return createElementNS(namespaceURI, qualifiedName);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByClassName(final String classNames) {
		return new HTMLCollectionImpl(this, new ClassNameFilter(classNames));
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByTagName(final String tagname) {
		if ("*".equals(tagname)) {
			return new HTMLCollectionImpl(this, new ElementFilter(null));
		} else {
			return new HTMLCollectionImpl(this, new TagNameFilter(tagname));
		}
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByTagNameNS(final String namespaceURI, final String localName) {

		if("*".equals(namespaceURI) && "*".equals(localName)) {
			return new HTMLCollectionImpl(this, new ElementFilter(null));
		}

		return new HTMLCollectionImpl(this, new TagNsNameFilter(localName, namespaceURI));
	}

	/** {@inheritDoc} */
	@Override
	public Element getFirstElementChild() {
		return (Element) nodeList.stream().filter(n -> n instanceof Element).findFirst().orElse(null);
	}

	/** {@inheritDoc} */
	@Override
	public Element getLastElementChild() {
		final long count = nodeList.stream().filter(n -> n instanceof Element).count();
		final Stream<Node> stream = nodeList.stream();
		return (Element) stream.filter(n -> n instanceof Element).skip(count - 1).findFirst().orElse(null);
	}

	/** {@inheritDoc} */
	@Override
	public int getChildElementCount() {
		return (int) nodeList.stream().
				filter(n -> n instanceof Element &&
						n.getNodeType() != PROCESSING_INSTRUCTION_NODE &&
						n.getNodeType() != DOCUMENT_TYPE_NODE &&
						!"xml".equals(n.getNodeName())).count();
	}

	/** {@inheritDoc} */
	@Override
	public Element querySelector(final String selectors) {
		try {
			final SelectorList selectorList = CSSUtilities.getSelectorList(selectors);
			final List<Element> elem = new ArrayList<>();
			if (selectorList != null) {
				final NodeListImpl childNodes = (NodeListImpl) getDescendents(new ElementFilter(null), true);
				childNodes.forEach(child -> {
					for (final Selector selector : selectorList) {
						if (child instanceof Element && StyleSheetAggregator.selects(selector, child, null)) {
							elem.add((Element) child);
						}
					}
				});
			}
			return ArrayUtilities.isNotBlank(elem) ? elem.getFirst() : null;
		} catch (final Exception e) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Is not a valid selector.");
		}
	}

	/** {@inheritDoc} */
	@Override
	public NodeList querySelectorAll(final String selector) {

		final ArrayList<Node> al = new ArrayList<>();

		if(selector == null) {
			return new NodeListImpl(al);
		}

		if(selector.isEmpty()){
			throw new DOMException(DOMException.NOT_FOUND_ERR, "The provided selector is empty.");
		}

		if(selector.trim().isEmpty()){
			throw new DOMException(DOMException.NOT_FOUND_ERR, "is not a valid selector.");
		}

		try {
			final SelectorList selectorList = CSSUtilities.getSelectorList(selector);
			if (selectorList != null) {
				final NodeListImpl childNodes = (NodeListImpl) getDescendents(new ElementFilter(null), true);
				childNodes.forEach(child -> {
					for (final Selector select : selectorList) {
						if (child instanceof Element && StyleSheetAggregator.selects(select, child, null)) {
							al.add(child);
						}
					}
				});
			}
			return new NodeListImpl(al);

		} catch (final Exception e) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Is not a valid selector.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocumentType getDoctype() {
        return (DocumentType) nodeList.
                stream().
                filter(node -> node.getNodeType() == Node.DOCUMENT_TYPE_NODE).
                findFirst().
                orElse(new DocumentTypeImpl());
	}

	/**
	 * <p>Setter for the field doctype.</p>
	 *
	 * @param doctype a {@link org.loboevolution.html.node.DocumentType} object.
	 */
	public void setDoctype(final DocumentType doctype) {
		if (doctype != null) {
			nodeList.add(doctype);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Element getDocumentElement() {
		for (final Node node : nodeList) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				return (Element) node;
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Text createTextNode(final String data) {
		if (data == null) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "null data");
		}
		final TextImpl node = new TextImpl(data);
		node.setOwnerDocument(this);
		return node;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Attr createAttribute(final String name) {
		if (!Strings.isXMLIdentifier(name)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
		}
		final AttrImpl attr = new AttrImpl(name, null, "id".equalsIgnoreCase(name), null, true);
		attr.setOwnerDocument(this);
		return attr;
	}

	/** {@inheritDoc} */
	@Override
	public Attr createAttributeNS(final String nUri, final String qName) throws DOMException {
		String prefix = null;
		String qualifiedName = qName;
		final String namespaceURI = nUri;
		if (Strings.isBlank(qualifiedName)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
		}

		if (qualifiedName.contains(":")) {
			final String[] split = qualifiedName.split(":");
			if (split.length != 2) {
				throw new DOMException(DOMException.NAMESPACE_ERR, "The qualified name provided has an empty local name.");
			}
			if (Strings.isBlank(split[0]) || Strings.isBlank(split[1])) {
				throw new DOMException(DOMException.NAMESPACE_ERR, "The qualified name provided has an empty local name.");
			}

			if (!Strings.isXMLIdentifier(split[0]) || !Strings.isXMLIdentifier(split[1])) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
			}
			prefix = split[0];
			qualifiedName = split[1];
		} else{
			if (!Strings.isXMLIdentifier(qualifiedName)) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
			}
		}

		if ("xmlns".equals(qualifiedName) && !XMLNS_NAMESPACE_URI.equals(namespaceURI)) {
			throw new DOMException(DOMException.NAMESPACE_ERR, "xmlns local name but not xmlns namespace");
		}

		final AttrImpl attr = new AttrImpl(qualifiedName, null, "id".equalsIgnoreCase(qualifiedName), null, true);
		attr.setNamespaceURI(namespaceURI);
		attr.setOwnerDocument(this);
		if (Strings.isNotBlank(prefix)) {
			attr.setPrefix(prefix);
		}
		return attr;
	}

	/** {@inheritDoc} */
	@Override
	public CDATASection createCDATASection(final String data) {
		if (data == null) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "null data");
		}
		final CDataSectionImpl node = new CDataSectionImpl(data);
		node.setOwnerDocument(this.document);
		return node;
	}

	/** {@inheritDoc} */
	@Override
	public Comment createComment(final String data) {
		if (data == null) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "null data");
		}
		final CommentImpl node = new CommentImpl(data);
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
	public ProcessingInstruction createProcessingInstruction(final String target, final String data) {

		if (!Strings.isXMLIdentifier(target)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The target contains the invalid character");
		}

		if (target.equalsIgnoreCase("xml")) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR,
					"An xml declaration is not a processing instruction");
		}

		if (data.contains("?>")) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR,
					"A processing instruction data cannot contain a '?>'");
		}

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
	public Element getElementById(final String elementId) {
		final NodeList nodeList = getNodeList(new IdFilter(elementId));
		return nodeList != null && nodeList.getLength() > 0 ? (Element)nodeList.item(0) : null;
	}

	/** {@inheritDoc} */
	@Override
	public DOMImplementation getImplementation() {
		return new DOMImplementationImpl(getUserAgentContext());
	}

	/** {@inheritDoc} */
	@Override
	public String getDocumentURI() {
		return this.documentURI;
	}

	/** {@inheritDoc} */
	@Override
	public String getBaseURI() {
		return this.documentURI;
	}

	/** {@inheritDoc} */
	@Override
	public void setDocumentURI(final String documentURI) {
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
	public void setStrictErrorChecking(final boolean strictErrorChecking) {
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
	public void setXmlStandalone(final boolean xmlStandalone) {
		this.xmlStandalone = xmlStandalone;
	}

	/** {@inheritDoc} */
	@Override
	public void setXmlVersion(final String xmlVersion) {
		this.xmlVersion = xmlVersion;
	}

	/** {@inheritDoc} */
	@Override
	public Event createEvent(final String eventType) throws DOMException {
		return EventFactory.createEvent(eventType);
	}

	/** {@inheritDoc} */
	@Override
	public XPathExpression createExpression(final String expression, final XPathNSResolver resolver) {
		final XPathEvaluatorImpl evaluator = new XPathEvaluatorImpl(document);
		return evaluator.createExpression(expression, resolver);
	}

	/** {@inheritDoc} */
	@Override
	public XPathNSResolver createNSResolver(final Node nodeResolver) {
		final XPathEvaluatorImpl evaluator = new XPathEvaluatorImpl(document);
		return evaluator.createNSResolver(nodeResolver);
	}

	/** {@inheritDoc} */
	@Override
	public XPathResult evaluate(final String expression, final Node contextNode, final XPathNSResolver resolver, final short type, final Object result) {
		return eval(expression, contextNode, resolver, type, result);
	}

	private XPathResult eval(final String expression, final Node contextNode, final XPathNSResolver resolver, final short type, final Object result) {
		final XPathEvaluatorImpl evaluator = new XPathEvaluatorImpl(document);
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
		HTMLCollectionImpl collection = (HTMLCollectionImpl) getElementsByTagName(HTMLTag.INPUT.name());
		AtomicReference<Element> rNpde = new AtomicReference<>(null);
		collection.forEach(elem -> {
			HTMLInputElementImpl input = (HTMLInputElementImpl) elem;
			if (input.isFocusable()) {
				rNpde.set((Element) elem);
			}
		});

		if (rNpde.get() == null) {
			collection = (HTMLCollectionImpl) getElementsByTagName(HTMLTag.TEXTAREA.name());
			collection.forEach(elem -> {
				HTMLTextAreaElementImpl input = (HTMLTextAreaElementImpl) elem;
				if (input.isFocusable()) {
					rNpde.set((Element) elem);
				}
			});
		}

		return rNpde.get();
	}

	/** {@inheritDoc} */
	@Override
	public String getBgColor() {
		final HTMLElement elem = getBody();
		final HTMLBodyElement body = (HTMLBodyElement) elem;
		return body.getBgColor();
	}

	/** {@inheritDoc} */
	@Override
	public void setBgColor(final String bgColor) {
		final HTMLElement elem = getBody();
		final HTMLBodyElement body = (HTMLBodyElement) elem;
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
		final DocumentType docType = getDoctype();
		if (docType != null) {
			final String systemId = docType.getSystemId();
			final String publicId = docType.getPublicId();
			if (systemId != null) {
				if ("http://www.w3.org/TR/html4/strict.dtd".equals(systemId)) {
					return CSSValues.CSS1COMPAT.getValue();
				}

				if ("http://www.w3.org/TR/html4/loose.dtd".equals(systemId)) {
					if ("-//W3C//DTD HTML 4.01 Transitional//EN".equals(publicId)) {
						return CSSValues.CSS1COMPAT.getValue();
					}
				}

				if ("http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd".equals(systemId)
						|| "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd".equals(systemId)) {
					return CSSValues.CSS1COMPAT.getValue();
				}
			}

			if (Strings.isBlank(publicId)) {
				return docType.getName() == null ? CSSValues.BACKCOMPAT.getValue() : CSSValues.CSS1COMPAT.getValue();
			}
		}
		return CSSValues.BACKCOMPAT.getValue();
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
	public void setCookie(final String cookie) {
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

	public void setWindow(final HtmlRendererContext rcontext, final UserAgentContext ucontext, final HtmlRendererConfig config){
		if (rcontext != null) {
			window = WindowImpl.getWindow(rcontext, config);
		} else {
			window = new WindowImpl(null, ucontext, config);
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
	public void setDesignMode(final String designMode) {
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
	public void setDir(final String dir) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public String getDomain() {
		return this.domain;
	}

	/** {@inheritDoc} */
	@Override
	public void setDomain(final String domain) {
		this.domain = domain;
	}

	/** {@inheritDoc} */
	@Override
	public String getFgColor() {
		final HTMLElement elem = getBody();
		final HTMLBodyElement body = (HTMLBodyElement) elem;
		return body.getText();
	}

	/** {@inheritDoc} */
	@Override
	public void setFgColor(final String fgColor) {
		final HTMLElement elem = getBody();
		final HTMLBodyElement body = (HTMLBodyElement) elem;
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
			final HTMLCollection collection =  new HTMLCollectionImpl(this, new HeadFilter());
			if(collection.getLength() > 0) return (HTMLHeadElement)collection.item(0);
			else return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public HTMLElement getBody() {
		synchronized (this) {
			if(this.body != null) return this.body;
			final HTMLCollection collection =  new HTMLCollectionImpl(this, new HeadFilter());
			if(collection.getLength() > 0) return (HTMLElement) collection.item(0);
			else return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setBody(final HTMLElement body) {
		this.body = body;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getApplets() {
		return new HTMLCollectionImpl(this, new ElementFilter("*"));
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getImages() {
		synchronized (this) {
			return  new HTMLCollectionImpl(this, new ImageFilter());
		}
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getLinks() {
		synchronized (this) {
			return  new HTMLCollectionImpl(this, new LinkFilter());
		}
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getForms() {
		synchronized (this) {
			return  new HTMLCollectionImpl(this, new FormFilter());
		}
	}


	/** {@inheritDoc} */
	@Override
	public HTMLCollection getEmbeds() {
		synchronized (this) {
			return  new HTMLCollectionImpl(this, new EmbedFilter());
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
			return  new HTMLCollectionImpl(this, new ScriptFilter());
		}
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getCommands() {
		synchronized (this) {
			return  new HTMLCollectionImpl(this, new CommandFilter());
		}
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getAnchors() {
		return new HTMLCollectionImpl(this, new AnchorFilter());
	}

	/** {@inheritDoc} */
	@Override
	public HTMLAllCollection getall() {
		final List<Node> list = new LinkedList<>(Arrays.asList(this.getNodeList(new ElementFilter(null)).toArray()));
		return new HTMLAllCollectionImpl(this, list);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByName(final String elementName) {
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
	public void setLocation(final Location location) {
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
	public Element getScrollingElement() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return this.title != null ? this.title : "";
	}

	/** {@inheritDoc} */
	@Override
	public void setTitle(final String title) {
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
	public Range caretRangeFromPoint(final double x, final double y) {
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
		synchronized (this) {
			if (this.reader instanceof LocalWritableLineReader) {
				try {
					this.reader.close();
				} catch (final IOException ioe) {
					// ignore
				}
				this.reader = null;
			}
		}
	}


	@Override
	public NodeIterator createNodeIterator(final Node root) throws DOMException {
		return new NodeIteratorImpl(root, 0,  null);
	}

	@Override
	public NodeIterator createNodeIterator(final Node root, final int whatToShow) throws DOMException {
		return new NodeIteratorImpl(root, whatToShow, null);
	}

	@Override
	public NodeIterator createNodeIterator(final Node root, final NodeFilter filter) throws DOMException {
		return new NodeIteratorImpl(root, 0, filter);
	}

	@Override
	public NodeIterator createNodeIterator(final Node root, final int whatToShow, final NodeFilter filter) throws DOMException {
		return new NodeIteratorImpl(root, whatToShow, filter);
	}

	/** {@inheritDoc} */
	@Override
	public Range createRange() {
		return new RangeImpl(this);
	}

	@Override
	public TreeWalker createTreeWalker(final Node root) throws DOMException {
		return new TreeWalkerImpl(root, 0, null);
	}

	@Override
	public TreeWalker createTreeWalker(final Node root, final int whatToShow) throws DOMException {
		return new TreeWalkerImpl(root, whatToShow, null);
	}

	@Override
	public TreeWalker createTreeWalker(final Node root, final NodeFilter filter) throws DOMException {
		return new TreeWalkerImpl(root, 0, filter);
	}

	@Override
	public TreeWalker createTreeWalker(final Node root, final int whatToShow, final NodeFilter filter) throws DOMException {
		return new TreeWalkerImpl(root, whatToShow, filter);
	}

	/** {@inheritDoc} */
	@Override
	public Element elementFromPoint(final double x, final double y) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean execCommand(final String commandId, final boolean showUI, final String value) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean execCommand(final String commandId, final boolean showUI) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean execCommand(final String commandId) {
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
	public Node importNode(final Node importedNode, final boolean deep) throws DOMException {
		switch (importedNode.getNodeType()) {
			case ATTRIBUTE_NODE:
				final Attr attr;
				if (Strings.isNotBlank(importedNode.getNamespaceURI())) {
					attr = createAttributeNS(importedNode.getNamespaceURI(), importedNode.getNodeName());
				} else {
					attr = createAttribute(importedNode.getNodeName());
				}
				attr.setValue(importedNode.getNodeValue());
				return attr;
			case ELEMENT_NODE:
				final Element foreignElm = (Element) importedNode;
				final Element elm;

				if (Strings.isNotBlank(foreignElm.getNamespaceURI())) {
					elm = createElementNS(foreignElm.getNamespaceURI(), foreignElm.getNodeName());
				} else {
					elm = createElement(foreignElm.getNodeName());
				}

				final NamedNodeMap attributes = foreignElm.getAttributes();
				for (final Node attribute : Nodes.iterable(attributes)) {
					if (!"xmlns".equals(attribute.getNodeName())) {
						final Attr attrNode = (Attr) importNode(attribute, true);
						elm.setAttributeNode(attrNode);
					}
				}
				if (deep) {
					Node node = importedNode.getFirstChild();
					while (node != null) {
						elm.appendChild(importNode(node, true));
						node = node.getNextSibling();
					}
				}
				return elm;
			case TEXT_NODE:
				return createTextNode(importedNode.getNodeValue());
			case CDATA_SECTION_NODE:
				return createCDATASection(importedNode.getNodeValue());
			case COMMENT_NODE:
				return createComment(importedNode.getNodeValue());
			case DOCUMENT_FRAGMENT_NODE:
				final DocumentFragment df = createDocumentFragment();
				if (deep) {
					Node node = importedNode.getFirstChild();
					while (node != null) {
						df.appendChild(importNode(node, true));
						node = node.getNextSibling();
					}
				}
				return df;
			case PROCESSING_INSTRUCTION_NODE:
				return createProcessingInstruction(importedNode.getNodeName(), importedNode.getNodeValue());
			case ENTITY_REFERENCE_NODE:
				getDoctype().getEntities().setNamedItem(importedNode);
				final EntityReferenceImpl reference = (EntityReferenceImpl) importedNode;
				reference.setOwnerDocument(this);
				return reference;
			case NOTATION_NODE:
				getDoctype().getNotations().setNamedItem(importedNode);
				final NotationImpl notation = (NotationImpl) importedNode;
				notation.setOwnerDocument(this);
				return notation;
			default:
				throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Node Not supported.");
		}
	}

	/** {@inheritDoc} */
	@Override
	public Document open(final String url, final String name, final String features, final boolean replace) {
		return open();
	}

	/** {@inheritDoc} */
	@Override
	public Document open(final String url, final String name, final String features) {
		return open();
	}

	/** {@inheritDoc} */
	@Override
	public Document open(final String url, final String name) {
		return open();
	}

	/** {@inheritDoc} */
	@Override
	public Document open(final String url) {
		return open();
	}

	/** {@inheritDoc} */
	@Override
	public Document open() {
		synchronized (this) {
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
	public boolean queryCommandEnabled(final String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandIndeterm(final String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandState(final String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandSupported(final String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String queryCommandValue(final String commandId) {
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
	public void write(final String text) {
		synchronized (this) {
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
	public void writeln(final String text) {
		synchronized (this) {
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

	@Override
	public boolean hasAttributes() {
		return false;
	}



	@Override
	public boolean isXml() {
		return xml;
	}

	@Override
	public void setXml(final boolean xml) {
		this.xml = xml;
	}

	@Override
	public String getLocalName() {
		return "";
	}

	@Override
	public int getNodeType() {
		return Node.DOCUMENT_NODE;
	}

	@Override
	public String getNodeValue() throws DOMException {
		return null;
	}

	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "readonly node");
	}

	@Override
	public String getNodeName() {
		return "[object HTMLDocument]";
	}
}