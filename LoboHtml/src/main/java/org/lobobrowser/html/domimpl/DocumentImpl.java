package org.lobobrowser.html.domimpl;

import java.util.HashMap;
import java.util.Map;

import org.lobo.common.Nodes;
import org.lobo.common.Strings;
import org.lobobrowser.html.dom.xpath.XPathEvaluatorImpl;
import org.lobobrowser.html.dom.xpath.XPathResultImpl;
import org.lobobrowser.html.js.EventImpl;
import org.lobobrowser.http.UserAgentContext;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.events.DocumentEvent;
import org.w3c.dom.events.Event;
import org.w3c.dom.xpath.XPathEvaluator;
import org.w3c.dom.xpath.XPathExpression;
import org.w3c.dom.xpath.XPathNSResolver;
import org.w3c.dom.xpath.XPathResult;

public class DocumentImpl extends DOMFunctionImpl implements Document, DocumentEvent, XPathEvaluator {

	private boolean strictErrorChecking = true;

	private boolean xmlStandalone;

	private String xmlVersion = null;

	private String documentURI;

	private DocumentType doctype;

	private final Map<String, Element> elementById = new HashMap<String, Element>(0);

	@Override
	public Node adoptNode(Node source) throws DOMException {
		NodeImpl node = (NodeImpl) source;
		node.setOwnerDocument(this.document, true);
		return node;
	}

	@Override
	public Element createElement(String tagName) throws DOMException {
		return ElementFactory.getInstance().createElement((HTMLDocumentImpl) this, tagName);
	}

	@Override
	public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
		if (Strings.isBlank(namespaceURI) || "http://www.w3.org/1999/xhtml".equalsIgnoreCase(namespaceURI)) {
			return createElement(qualifiedName);
		} else if ("http://www.w3.org/2000/svg".equalsIgnoreCase(namespaceURI)) {
			return createElement(qualifiedName);
		}
		return null;
	}

	@Override
	public DocumentType getDoctype() {
		return this.doctype;
	}

	public void setDoctype(DocumentType doctype) {
		this.doctype = doctype;
	}

	@Override
	public Element getDocumentElement() {
		for (Node node : Nodes.iterable(nodeList)) {
			if (node instanceof Element) {
				return (Element) node;
			}
		}
		return null;
	}

	@Override
	public Text createTextNode(String data) {
		final TextImpl node = new TextImpl(data);
		node.setOwnerDocument(this);
		return node;
	}

	@Override
	public Attr createAttribute(String name) throws DOMException {
		return new AttrImpl(name);
	}

	@Override
	public CDATASection createCDATASection(String data) throws DOMException {
		CDataSectionImpl node = new CDataSectionImpl(data);
		node.setOwnerDocument(this.document);
		return node;
	}

	@Override
	public Comment createComment(String data) {
		CommentImpl node = new CommentImpl(data);
		node.setOwnerDocument(this.document);
		return node;
	}

	@Override
	public DocumentFragment createDocumentFragment() {
		final DocumentFragmentImpl node = new DocumentFragmentImpl();
		node.setOwnerDocument(this);
		return node;
	}

	@Override
	public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
		final HTMLProcessingInstruction node = new HTMLProcessingInstruction(target, data);
		node.setOwnerDocument(this);
		return node;
	}

	@Override
	public DOMConfiguration getDomConfig() {
		return new DOMConfigurationImpl();
	}

	@Override
	public Element getElementById(String elementId) {
		if (Strings.isNotBlank(elementId)) {
			synchronized (this) {
				return (Element) this.elementById.get(elementId);
			}
		} else {
			return null;
		}
	}

	public Map<String, Element> getElementById() {
		return elementById;
	}

	@Override
	public DOMImplementation getImplementation() {
		return new DOMImplementationImpl(new UserAgentContext());
	}

	@Override
	public String getDocumentURI() {
		return this.documentURI;
	}

	@Override
	public void setDocumentURI(String documentURI) {
		this.documentURI = documentURI;
	}

	@Override
	public String getInputEncoding() {
		return "UTF-8";
	}

	@Override
	public String getXmlEncoding() {
		return "UTF-8";
	}

	@Override
	public void normalizeDocument() {
		visitImpl(Node::normalize);
	}

	@Override
	public boolean getStrictErrorChecking() {
		return this.strictErrorChecking;
	}

	@Override
	public void setStrictErrorChecking(boolean strictErrorChecking) {
		this.strictErrorChecking = strictErrorChecking;
	}

	@Override
	public boolean getXmlStandalone() {
		return this.xmlStandalone;
	}

	@Override
	public String getXmlVersion() {
		return this.xmlVersion == null ? "1.0" : this.xmlVersion;
	}

	@Override
	public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
		this.xmlStandalone = xmlStandalone;
	}

	@Override
	public void setXmlVersion(String xmlVersion) throws DOMException {
		this.xmlVersion = xmlVersion;
	}

	@Override
	public Event createEvent(String eventType) throws DOMException {
		return new EventImpl(eventType, this);
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
	public XPathResult evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type, Object result) {
		return eval(expression, contextNode, resolver, type, result);
	}

	private XPathResult eval(String expression, Node contextNode, XPathNSResolver resolver, short type, Object result) {
		XPathEvaluatorImpl evaluator = new XPathEvaluatorImpl(document);
		return (XPathResultImpl) evaluator.evaluate(expression, contextNode, resolver, type, result);
	}
}
