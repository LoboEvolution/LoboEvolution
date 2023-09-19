/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.html.js.xml;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.nodeimpl.traversal.TreeWalkerImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.js.Location;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.node.traversal.NodeFilter;
import org.loboevolution.html.node.traversal.NodeIterator;
import org.loboevolution.html.node.traversal.TreeWalker;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.html.xpath.XPathExpression;
import org.loboevolution.html.xpath.XPathNSResolver;
import org.loboevolution.html.xpath.XPathResult;
import org.loboevolution.type.DocumentReadyState;
import org.loboevolution.type.VisibilityState;
import org.mozilla.javascript.Function;
import org.loboevolution.html.dom.UserDataHandler;
import org.w3c.dom.events.EventException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * XMLDocument class.
 */
public class XMLDocument implements Document {

	/** The Constant logger. */
	protected static final Logger logger = Logger.getLogger(XMLDocument.class.getName());

	private static Document doc;

	/**
	 * Load XML file
	 *
	 * @param fileName name of file in file system
	 * @return XMLDocument
	 */
	public static XMLDocument load(String fileName) {
		XMLDocument ret = new XMLDocument();
		ret.loadFile(fileName);
		return ret;
	}

	/**
	 * Load XML file
	 *
	 * @param fileName name of file in file system
	 * @return XML String
	 */
	public String loadXML(String fileName) {
		loadFile(fileName);
		return getXML();
	}

	/**
	 * Load XML file
	 * 
	 * @param fileStr name of in file system
	 */
	private synchronized void loadFile(String fileStr) {
		try {
			File f = new File(fileStr);
			XMLDocumentBuilder builder = new XMLDocumentBuilder();
			if (f.isFile()) {
				doc = builder.parse(new InputSourceImpl(new FileInputStream(f), "", StandardCharsets.UTF_8));
			} else {
				InputStream in = new ByteArrayInputStream(fileStr.getBytes());
				doc = builder.parse(new InputSourceImpl(in, "", StandardCharsets.UTF_8));
			}
		} catch (SAXException e) {
			logger.warning("A DOCTYPE was passed into the XML document");
		} catch (IOException e) {
			logger.severe("IOException occurred, XXE may still possible: " + e.getMessage());
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}

	/**
	 * <p>
	 * getXML.
	 * </p>
	 *
	 * @return XML String
	 */
	public String getXML() {
		return getXmlEncoding();
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByTagName(String tagname) {
		return doc.getElementsByTagName(tagname);
	}

	/** {@inheritDoc} */
	@Override
	public Element getElementById(String id) {
		return doc.getElementById(id);
	}

	/** {@inheritDoc} */
	@Override
	public Node appendChild(Node newChild) {
		return doc.appendChild(newChild);
	}

	/**
	 * <p>prependChild.</p>
	 *
	 * @param newChild a {@link Node} object.
	 * @return a {@link Node} object.
	 */
	@Override
	public Node prependChild(Node newChild) {
		return doc.prependChild(newChild);
	}


	/**
	 * <p>prepend.</p>
	 *
	 * @param newChild a {@link Node} object.
	 * @return a {@link Node} object.
	 */
	@Override
	public Node prepend(Node newChild) {
		return doc.prepend(newChild);
	}

	/** {@inheritDoc} */
	@Override
	public Node cloneNode(boolean deep) {
		return doc.cloneNode(deep);
	}

	/** {@inheritDoc} */
	@Override
	public short compareDocumentPosition(Node other) {
		return doc.compareDocumentPosition(other);
	}

	@Override
	public boolean isSupported(String xml, String s) {
		return false;
	}

	@Override
	public boolean hasAttributes() {
		return doc.hasAttributes();
	}

	@Override
	public Node getFeature(String name, String version) {
		return doc.getFeature(name, version);
	}

	/** {@inheritDoc} */
	@Override
	public String getBaseURI() {
		return doc.getBaseURI();
	}

	/** {@inheritDoc} */
	@Override
	public NodeList getChildNodes() {
		return doc.getChildNodes();
	}

	/** {@inheritDoc} */
	@Override
	public Node getFirstChild() {
		return doc.getFirstChild();
	}

	/** {@inheritDoc} */
	@Override
	public Node getLastChild() {
		return doc.getLastChild();
	}

	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		return doc.getLocalName();
	}

	/** {@inheritDoc} */
	@Override
	public String getNamespaceURI() {
		return doc.getNamespaceURI();
	}

	/** {@inheritDoc} */
	@Override
	public Node getNextSibling() {
		return doc.getNextSibling();
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return doc.getNodeName();
	}

	/** {@inheritDoc} */
	@Override
	public int getNodeType() {
		return doc.getNodeType();
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() {
		return doc.getNodeValue();
	}

	/** {@inheritDoc} */
	@Override
	public Document getOwnerDocument() {
		return doc.getOwnerDocument();
	}

	/** {@inheritDoc} */
	@Override
	public Node getParentNode() {
		return doc.getParentNode();
	}

	/** {@inheritDoc} */
	@Override
	public String getPrefix() {
		return doc.getPrefix();
	}

	/** {@inheritDoc} */
	@Override
	public Node getPreviousSibling() {
		return doc.getPreviousSibling();
	}

	/** {@inheritDoc} */
	@Override
	public String getTextContent() {
		return doc.getTextContent();
	}

	/** {@inheritDoc} */
	@Override
	public Object getUserData(String key) {
		return doc.getUserData(key);
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasChildNodes() {
		return doc.hasChildNodes();
	}

	/** {@inheritDoc} */
	@Override
	public Node insertBefore(Node newChild, Node refChild) {
		return doc.insertBefore(newChild, refChild);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDefaultNamespace(String namespaceURI) {
		return doc.isDefaultNamespace(namespaceURI);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEqualNode(Node arg) {
		return doc.isEqualNode(arg);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSameNode(Node other) {
		return doc.isSameNode(other);
	}

	/** {@inheritDoc} */
	@Override
	public String lookupNamespaceURI(String prefix) {
		return doc.lookupNamespaceURI(prefix);
	}

	/** {@inheritDoc} */
	@Override
	public String lookupPrefix(String namespaceURI) {
		return doc.lookupPrefix(namespaceURI);
	}

	/** {@inheritDoc} */
	@Override
	public void normalize() {
		doc.normalize();
	}

	/** {@inheritDoc} */
	@Override
	public Node removeChild(Node oldChild) {
		return doc.removeChild(oldChild);
	}

	/** {@inheritDoc} */
	@Override
	public Node replaceChild(Node newChild, Node oldChild) {
		return doc.replaceChild(newChild, oldChild);
	}

	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setNodeValue(String nodeValue) {
		doc.setNodeValue(nodeValue);
	}

	/** {@inheritDoc} */
	@Override
	public void setPrefix(String prefix) {
		doc.setPrefix(prefix);
	}

	/** {@inheritDoc} */
	@Override
	public void setTextContent(String textContent) {
		doc.setTextContent(textContent);
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return doc.setUserData(key, data, handler);
	}

	/** {@inheritDoc} */
	@Override
	public Node adoptNode(Node source) {
		return doc.adoptNode(source);
	}

	@Override
	public EntityReference createEntityReference(String ent1) {
		return doc.createEntityReference(ent1);
	}

	@Override
	public Node renameNode(Node node, String namespaceURI, String qualifiedName) {
		return doc.renameNode(node, namespaceURI, qualifiedName);
	}

	/** {@inheritDoc} */
	@Override
	public Attr createAttribute(String name) {
		return doc.createAttribute(name);
	}

	/** {@inheritDoc} */
	@Override
	public Attr createAttributeNS(String namespaceURI, String qualifiedName) {
		return doc.createAttributeNS(namespaceURI, qualifiedName);
	}

	/** {@inheritDoc} */
	@Override
	public CDATASection createCDATASection(String data) {
		return doc.createCDATASection(data);
	}

	/** {@inheritDoc} */
	@Override
	public Comment createComment(String data) {
		return doc.createComment(data);
	}

	/** {@inheritDoc} */
	@Override
	public DocumentFragment createDocumentFragment() {
		return doc.createDocumentFragment();
	}

	/** {@inheritDoc} */
	@Override
	public Element createElement(String tagName) {
		return doc.createElement(tagName);
	}

	/** {@inheritDoc} */
	@Override
	public Element createElementNS(String namespaceURI, String qualifiedName) {
		return doc.createElementNS(namespaceURI, qualifiedName);
	}

	/** {@inheritDoc} */
	@Override
	public ProcessingInstruction createProcessingInstruction(String target, String data) {
		return doc.createProcessingInstruction(target, data);
	}

	/** {@inheritDoc} */
	@Override
	public Text createTextNode(String data) {
		return doc.createTextNode(data);
	}

	/** {@inheritDoc} */
	@Override
	public DocumentType getDoctype() {
		return doc.getDoctype();
	}

	/** {@inheritDoc} */
	@Override
	public Element getDocumentElement() {
		return doc.getDocumentElement();
	}

	/** {@inheritDoc} */
	@Override
	public String getDocumentURI() {
		return doc.getDocumentURI();
	}

	/** {@inheritDoc} */
	@Override
	public DOMConfiguration getDomConfig() {
		return doc.getDomConfig();
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByTagNameNS(String namespaceURI, String localName) {
		return doc.getElementsByTagNameNS(namespaceURI, localName);
	}

	/** {@inheritDoc} */
	@Override
	public DOMImplementation getImplementation() {
		return doc.getImplementation();
	}

	/** {@inheritDoc} */
	@Override
	public String getInputEncoding() {

		return doc.getInputEncoding();
	}

	/** {@inheritDoc} */
	@Override
	public boolean getStrictErrorChecking() {
		return doc.getStrictErrorChecking();
	}

	/** {@inheritDoc} */
	@Override
	public String getXmlEncoding() {

		return doc.getXmlEncoding();
	}

	/** {@inheritDoc} */
	@Override
	public boolean getXmlStandalone() {
		return doc.getXmlStandalone();
	}

	/** {@inheritDoc} */
	@Override
	public String getXmlVersion() {
		return doc.getXmlVersion();
	}

	/** {@inheritDoc} */
	@Override
	public Node importNode(Node importedNode, boolean deep) {
		return doc.importNode(importedNode, deep);
	}

	/** {@inheritDoc} */
	@Override
	public void normalizeDocument() {
		doc.normalizeDocument();
	}

	/** {@inheritDoc} */
	@Override
	public void setDocumentURI(String documentURI) {
		doc.setDocumentURI(documentURI);
	}

	/** {@inheritDoc} */
	@Override
	public void setStrictErrorChecking(boolean strictErrorChecking) {
		doc.setStrictErrorChecking(strictErrorChecking);
	}

	/** {@inheritDoc} */
	@Override
	public void setXmlStandalone(boolean xmlStandalone) {
		doc.setXmlStandalone(xmlStandalone);
	}

	/** {@inheritDoc} */
	@Override
	public void setXmlVersion(String xmlVersion) {
		doc.setXmlVersion(xmlVersion);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isIsConnected() {

		return doc.isIsConnected();
	}

	/** {@inheritDoc} */
	@Override
	public HTMLElement getParentElement() {

		return doc.getParentElement();
	}

	/** {@inheritDoc} */
	@Override
	public Node getRootNode() {

		return doc.getRootNode();
	}

	/** {@inheritDoc} */
	@Override
	public Node cloneNode() {

		return doc.cloneNode();
	}

	@Override
	public void setOwnerDocument(Document document) {
		doc.setOwnerDocument(document);
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(Node other) {

		return doc.contains(other);
	}

	/** {@inheritDoc} */
	@Override
	public void addEventListener(String type, Function listener) {
		doc.addEventListener(type, listener);
	}

	/** {@inheritDoc} */
	@Override
	public void addEventListener(String type, Function listener, boolean useCapture) {
		doc.addEventListener(type, listener, useCapture);
	}

	/** {@inheritDoc} */
	@Override
	public void removeEventListener(String script, Function function) {
		doc.removeEventListener(script, function);
	}

	/** {@inheritDoc} */
	@Override
	public void removeEventListener(String type, Function listener, boolean useCapture) {
		doc.removeEventListener(type, listener, useCapture);
	}

	/** {@inheritDoc} */
	@Override
	public boolean dispatchEvent(Node element, Event evt) {

		return doc.dispatchEvent(element, evt);
	}

	@Override
	public boolean dispatchEvent(Event evt) throws EventException {
		return doc.dispatchEvent(evt);
	}

	/** {@inheritDoc} */
	@Override
	public int getChildElementCount() {

		return doc.getChildElementCount();
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getChildren() {

		return doc.getChildren();
	}

	/** {@inheritDoc} */
	@Override
	public Element getFirstElementChild() {

		return doc.getFirstElementChild();
	}

	/** {@inheritDoc} */
	@Override
	public Element getLastElementChild() {

		return doc.getLastElementChild();
	}

	/** {@inheritDoc} */
	@Override
	public Element querySelector(String selectors) {

		return doc.querySelector(selectors);
	}

	/** {@inheritDoc} */
	@Override
	public NodeList querySelectorAll(String selectors) {

		return doc.querySelectorAll(selectors);
	}

	/** {@inheritDoc} */
	@Override
	public String getURL() {

		return doc.getURL();
	}

	/** {@inheritDoc} */
	@Override
	public Element getActiveElement() {

		return doc.getActiveElement();
	}

	/** {@inheritDoc} */
	@Override
	public String getAlinkColor() {

		return doc.getAlinkColor();
	}

	/** {@inheritDoc} */
	@Override
	public void setAlinkColor(String alinkColor) {
		doc.setAlinkColor(alinkColor);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLAllCollection getAll() {
		return doc.getAll();
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getAnchors() {

		return doc.getAnchors();
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getApplets() {

		return doc.getApplets();
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getCommands() {

		return doc.getCommands();
	}

	/** {@inheritDoc} */
	@Override
	public String getBgColor() {

		return doc.getBgColor();
	}

	/** {@inheritDoc} */
	@Override
	public void setBgColor(String bgColor) {
		doc.setBgColor(bgColor);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLElement getBody() {

		return doc.getBody();
	}

	/** {@inheritDoc} */
	@Override
	public void setBody(HTMLElement body) {
		doc.setBody(body);
	}

	/** {@inheritDoc} */
	@Override
	public String getCharacterSet() {

		return doc.getCharacterSet();
	}

	/** {@inheritDoc} */
	@Override
	public String getCharset() {

		return doc.getCharset();
	}

	/** {@inheritDoc} */
	@Override
	public String getCompatMode() {

		return doc.getCompatMode();
	}

	/** {@inheritDoc} */
	@Override
	public String getContentType() {

		return doc.getContentType();
	}

	/** {@inheritDoc} */
	@Override
	public String getCookie() {

		return doc.getCookie();
	}

	/** {@inheritDoc} */
	@Override
	public void setCookie(String cookie) {
		doc.setCookie(cookie);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLScriptElement getCurrentScript() {

		return doc.getCurrentScript();
	}

	/** {@inheritDoc} */
	@Override
	public Window getDefaultView() {
		return doc.getDefaultView();
	}

	/** {@inheritDoc} */
	@Override
	public String getDesignMode() {

		return doc.getDesignMode();
	}

	/** {@inheritDoc} */
	@Override
	public void setDesignMode(String designMode) {
		doc.setDesignMode(designMode);
	}

	/** {@inheritDoc} */
	@Override
	public String getDir() {

		return doc.getDesignMode();
	}

	/** {@inheritDoc} */
	@Override
	public void setDir(String dir) {
		doc.setDir(dir);
	}

	/** {@inheritDoc} */
	@Override
	public String getDomain() {

		return doc.getDomain();
	}

	/** {@inheritDoc} */
	@Override
	public void setDomain(String domain) {
		doc.setDomain(domain);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getEmbeds() {

		return doc.getEmbeds();
	}

	/** {@inheritDoc} */
	@Override
	public String getFgColor() {

		return doc.getFgColor();
	}

	/** {@inheritDoc} */
	@Override
	public void setFgColor(String fgColor) {
		doc.setFgColor(fgColor);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getForms() {

		return doc.getForms();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isFullscreen() {

		return doc.isFullscreen();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isFullscreenEnabled() {

		return doc.isFullscreenEnabled();
	}

	/** {@inheritDoc} */
	@Override
	public HTMLHeadElement getHead() {

		return doc.getHead();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isHidden() {

		return doc.isHidden();
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getImages() {

		return doc.getImages();
	}

	/** {@inheritDoc} */
	@Override
	public String getLastModified() {

		return doc.getLastModified();
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getLinks() {

		return doc.getLinks();
	}

	/** {@inheritDoc} */
	@Override
	public Location getLocation() {

		return doc.getLocation();
	}

	/** {@inheritDoc} */
	@Override
	public void setLocation(Location location) {
		doc.setLocation(location);
	}

	/** {@inheritDoc} */
	@Override
	public String getOrigin() {

		return doc.getOrigin();
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getPlugins() {

		return doc.getPlugins();
	}

	/** {@inheritDoc} */
	@Override
	public DocumentReadyState getReadyState() {

		return doc.getReadyState();
	}

	/** {@inheritDoc} */
	@Override
	public String getReferrer() {

		return doc.getReferrer();
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getScripts() {

		return doc.getScripts();
	}

	/** {@inheritDoc} */
	@Override
	public Element getScrollingElement() {

		return doc.getScrollingElement();
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle() {

		return doc.getTitle();
	}

	/** {@inheritDoc} */
	@Override
	public void setTitle(String title) {
		doc.setTitle(title);
	}

	/** {@inheritDoc} */
	@Override
	public VisibilityState getVisibilityState() {

		return doc.getVisibilityState();
	}

	/** {@inheritDoc} */
	@Override
	public void captureEvents() {
		doc.captureEvents();
	}

	/** {@inheritDoc} */
	@Override
	public Range caretRangeFromPoint(double x, double y) {

		return doc.caretRangeFromPoint(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public void clear() {
		doc.clear();

	}

	/** {@inheritDoc} */
	@Override
	public void close() {
		doc.close();

	}

	/** {@inheritDoc} */
	@Override
	public Element createElementNS(String namespace, String qualifiedName, String options) {

		return doc.createElementNS(namespace, qualifiedName, options);
	}

	/** {@inheritDoc} */
	@Override
	public Event createEvent(String eventInterface) {

		return doc.createEvent(eventInterface);
	}

	/** {@inheritDoc} */
	@Override
	public NodeIterator createNodeIterator(Node root) throws DOMException {
		return doc.createNodeIterator(root);
	}

	/** {@inheritDoc} */
	@Override
	public NodeIterator createNodeIterator(Node root, int whatToShow) throws DOMException {
		return doc.createNodeIterator(root, whatToShow);
	}

	/** {@inheritDoc} */
	@Override
	public NodeIterator createNodeIterator(Node root, NodeFilter filter) throws DOMException {
		return doc.createNodeIterator(root, filter);
	}

	/** {@inheritDoc} */
	@Override
	public NodeIterator createNodeIterator(Node root, int whatToShow, NodeFilter filter) throws DOMException {
		return doc.createNodeIterator(root, whatToShow, filter);
	}

	/** {@inheritDoc} */
	@Override
	public Range createRange() {
		return doc.createRange();
	}

	/** {@inheritDoc} */
	@Override
	public TreeWalker createTreeWalker(Node root) throws DOMException {
		return doc.createTreeWalker(root);
	}

	/** {@inheritDoc} */
	@Override
	public TreeWalker createTreeWalker(Node root, int whatToShow) throws DOMException {
		return doc.createTreeWalker(root, whatToShow);
	}

	/** {@inheritDoc} */
	@Override
	public TreeWalker createTreeWalker(Node root, NodeFilter filter) throws DOMException {
		return doc.createTreeWalker(root, filter);
	}

	@Override
	public TreeWalker createTreeWalker(Node root, int whatToShow, NodeFilter filter) throws DOMException {
		return new TreeWalkerImpl(root, whatToShow, filter);
	}

	/** {@inheritDoc} */
	@Override
	public Element elementFromPoint(double x, double y) {
		return doc.elementFromPoint(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public boolean execCommand(String commandId, boolean showUI, String value) {
		return doc.execCommand(commandId, showUI, value);
	}

	/** {@inheritDoc} */
	@Override
	public boolean execCommand(String commandId, boolean showUI) {
		return doc.execCommand(commandId, showUI);
	}

	/** {@inheritDoc} */
	@Override
	public boolean execCommand(String commandId) {
		return doc.execCommand(commandId);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByClassName(String classNames) {
		return doc.getElementsByClassName(classNames);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByName(String elementName) {
		return doc.getElementsByName(elementName);
	}

	/** {@inheritDoc} */
	@Override
	public Selection getSelection() {
		return doc.getSelection();
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasFocus() {
		return doc.hasFocus();
	}

	/** {@inheritDoc} */
	@Override
	public Document open(String url, String name, String features, boolean replace) {
		return doc.open(url, name, features, replace);
	}

	/** {@inheritDoc} */
	@Override
	public Document open(String url, String name, String features) {
		return doc.open(url, name, features);
	}

	/** {@inheritDoc} */
	@Override
	public Document open(String url, String name) {
		return doc.open(url, name);
	}

	/** {@inheritDoc} */
	@Override
	public Document open(String url) {
		return doc.open(url);
	}

	/** {@inheritDoc} */
	@Override
	public Document open() {
		return doc.open();
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandEnabled(String commandId) {
		return doc.queryCommandEnabled(commandId);
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandIndeterm(String commandId) {
		return doc.queryCommandIndeterm(commandId);
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandState(String commandId) {
		return doc.queryCommandState(commandId);
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandSupported(String commandId) {
		return doc.queryCommandSupported(commandId);
	}

	/** {@inheritDoc} */
	@Override
	public String queryCommandValue(String commandId) {
		return doc.queryCommandValue(commandId);
	}

	/** {@inheritDoc} */
	@Override
	public void releaseEvents() {
		doc.releaseEvents();

	}

	/** {@inheritDoc} */
	@Override
	public void write(String text) {
		doc.write(text);

	}

	/** {@inheritDoc} */
	@Override
	public void writeln(String text) {
		doc.writeln(text);

	}

	/** {@inheritDoc} */
	@Override
	public XPathExpression createExpression() {

		return doc.createExpression();
	}

	/** {@inheritDoc} */
	@Override
	public XPathNSResolver createNSResolver(Node nodeResolver) {
		return doc.createNSResolver(nodeResolver);
	}

	/** {@inheritDoc} */
	@Override
	public XPathResult evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type,
			Object result) {
		return doc.evaluate(expression, contextNode, resolver, type, result);
	}

	@Override
	public boolean isXml() {
		return doc.isXml();
	}

	@Override
	public void setXml(boolean xml) {
		doc.setXml(xml);
	}
}
