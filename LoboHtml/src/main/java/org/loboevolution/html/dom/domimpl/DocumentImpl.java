/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.filter.IdFilter;
import org.loboevolution.html.dom.xpath.XPathEvaluatorImpl;
import org.loboevolution.html.dom.xpath.XPathResultImpl;
import org.loboevolution.html.js.events.EventFactory;
import org.loboevolution.http.UserAgentContext;
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
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.events.DocumentEvent;
import org.w3c.dom.events.Event;
import org.w3c.dom.xpath.XPathEvaluator;
import org.w3c.dom.xpath.XPathExpression;
import org.w3c.dom.xpath.XPathNSResolver;
import org.w3c.dom.xpath.XPathResult;

/**
 * <p>DocumentImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DocumentImpl extends DOMFunctionImpl implements Document, DocumentEvent, XPathEvaluator {

	private boolean strictErrorChecking = true;

	private boolean xmlStandalone;

	private String xmlVersion = null;

	private String documentURI;

	private DocumentType doctype;
	
	private boolean isrss = false;

	/** {@inheritDoc} */
	@Override
	public Node adoptNode(Node source) throws DOMException {
		NodeImpl node = (NodeImpl) source;
		node.setOwnerDocument(this.document, true);
		return node;
	}

	/** {@inheritDoc} */
	@Override
	public Element createElement(String tagName) throws DOMException {
		if("rss".equalsIgnoreCase(tagName)) {isrss = true;}
		return new ElementFactory(isrss).createElement((HTMLDocumentImpl) this, tagName);
	}

	/** {@inheritDoc} */
	@Override
	public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
		if (Strings.isBlank(namespaceURI) || "http://www.w3.org/1999/xhtml".equalsIgnoreCase(namespaceURI)) {
			return createElement(qualifiedName);
		} else if ("http://www.w3.org/2000/svg".equalsIgnoreCase(namespaceURI)) {
			return createElement(qualifiedName);
		}
		return null;
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
		for (Node node : Nodes.iterable(nodeList)) {
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
	public Attr createAttribute(String name) throws DOMException {
		return new AttrImpl(name);
	}

	/** {@inheritDoc} */
	@Override
	public CDATASection createCDATASection(String data) throws DOMException {
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
	public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
		final HTMLProcessingInstruction node = new HTMLProcessingInstruction(target, data);
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
	public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
		this.xmlStandalone = xmlStandalone;
	}

	/** {@inheritDoc} */
	@Override
	public void setXmlVersion(String xmlVersion) throws DOMException {
		this.xmlVersion = xmlVersion;
	}

	/** {@inheritDoc} */
	@Override
	public Event createEvent(String eventType) throws DOMException {
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
		return (XPathResultImpl) evaluator.evaluate(expression, contextNode, resolver, type, result);
	}
}
