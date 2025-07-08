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

package org.loboevolution.html.js.xml;

import lombok.extern.slf4j.Slf4j;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.nodeimpl.traversal.TreeWalkerImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.events.Event;
import org.loboevolution.html.node.ranges.Range;
import org.loboevolution.js.Location;
import org.loboevolution.js.Window;
import org.loboevolution.traversal.NodeFilter;
import org.loboevolution.traversal.NodeIterator;
import org.loboevolution.traversal.TreeWalker;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.html.xpath.XPathExpression;
import org.loboevolution.html.xpath.XPathNSResolver;
import org.loboevolution.html.xpath.XPathResult;
import org.loboevolution.type.DocumentReadyState;
import org.loboevolution.type.VisibilityState;
import org.mozilla.javascript.Function;
import org.w3c.dom.events.EventException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;

/**
 * XMLDocument class.
 */
@Slf4j
public class XMLDocument implements Document {

	private static Document doc;

	/**
	 * Load XML file
	 *
	 * @param fileName name of file in file system
	 * @return XMLDocument
	 */
	public static XMLDocument load(final String fileName) {
		final XMLDocument ret = new XMLDocument();
		ret.loadFile(fileName);
		return ret;
	}

	/**
	 * Load XML file
	 *
	 * @param fileName name of file in file system
	 * @return XML String
	 */
	public String loadXML(final String fileName) {
		loadFile(fileName);
		return getXML();
	}

	/**
	 * Load XML file
	 * 
	 * @param fileStr name of in file system
	 */
	private synchronized void loadFile(final String fileStr) {
		try {
			final File f = new File(fileStr);
			final XMLDocumentBuilder builder = new XMLDocumentBuilder();
			if (f.isFile()) {
				doc = builder.parse(new InputSourceImpl(Files.newInputStream(f.toPath()), "", StandardCharsets.UTF_8));
			} else {
				final InputStream in = new ByteArrayInputStream(fileStr.getBytes());
				doc = builder.parse(new InputSourceImpl(in, "", StandardCharsets.UTF_8));
			}
		} catch (final SAXException e) {
			log.warn("A DOCTYPE was passed into the XML document");
		} catch (final IOException e) {
			log.error("IOException occurred, XXE may still possible: ", e);
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
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
	public HTMLCollection getElementsByTagName(final String tagname) {
		return doc.getElementsByTagName(tagname);
	}

	/** {@inheritDoc} */
	@Override
	public Element getElementById(final String id) {
		return doc.getElementById(id);
	}

	/** {@inheritDoc} */
	@Override
	public Node appendChild(final Node newChild) {
		return doc.appendChild(newChild);
	}

	/**
	 * <p>prependChild.</p>
	 *
	 * @param newChild a {@link Node} object.
	 * @return a {@link Node} object.
	 */
	@Override
	public Node prependChild(final Node newChild) {
		return doc.prependChild(newChild);
	}


	/**
	 * <p>prepend.</p>
	 *
	 * @param newChild a {@link Node} object.
	 * @return a {@link Node} object.
	 */
	@Override
	public Node prepend(final Node newChild) {
		return doc.prepend(newChild);
	}

	/** {@inheritDoc} */
	@Override
	public Node cloneNode(final boolean deep) {
		return doc.cloneNode(deep);
	}

	/** {@inheritDoc} */
	@Override
	public short compareDocumentPosition(final Node other) {
		return doc.compareDocumentPosition(other);
	}

	@Override
	public boolean isSupported(final String xml, final String s) {
		return false;
	}

	@Override
	public boolean hasAttributes() {
		return doc.hasAttributes();
	}

	@Override
	public Node getFeature(final String name, final String version) {
		return doc.getFeature(name, version);
	}

	@Override
	public Document getDocumentNode() {
		return null;
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
	public Object getUserData(final String key) {
		return doc.getUserData(key);
	}

	@Override
	public Node getPreviousTo(Node node) {
		return doc.getPreviousTo(node);
	}

	@Override
	public Node getNextTo(Node node) {
		return doc.getNextTo(node);
	}

	@Override
	public void forgetRenderState() {
		doc.forgetRenderState();
	}

	@Override
	public void appendChildrenToCollectionImpl(NodeFilter filter, Collection<Node> collection) {
		doc.appendChildrenToCollectionImpl(filter, collection);
	}

	@Override
	public void extractDescendentsArrayImpl(NodeFilter filter, List<Node> al, boolean nestIntoMatchingNodes) {
		doc.extractDescendentsArrayImpl(filter, al, nestIntoMatchingNodes);
	}

	@Override
	public UINode findUINode() {
		return doc.findUINode();
	}

	@Override
	public void visit(NodeVisitor visitor) {
		doc.visit(visitor);
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasChildNodes() {
		return doc.hasChildNodes();
	}

	/** {@inheritDoc} */
	@Override
	public Node insertBefore(final Node newChild, final Node refChild) {
		return doc.insertBefore(newChild, refChild);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDefaultNamespace(final String namespaceURI) {
		return doc.isDefaultNamespace(namespaceURI);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEqualNode(final Node arg) {
		return doc.isEqualNode(arg);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSameNode(final Node other) {
		return doc.isSameNode(other);
	}

	/** {@inheritDoc} */
	@Override
	public String lookupNamespaceURI(final String prefix) {
		return doc.lookupNamespaceURI(prefix);
	}

	/** {@inheritDoc} */
	@Override
	public String lookupPrefix(final String namespaceURI) {
		return doc.lookupPrefix(namespaceURI);
	}

	/** {@inheritDoc} */
	@Override
	public void normalize() {
		doc.normalize();
	}

	/** {@inheritDoc} */
	@Override
	public Node removeChild(final Node oldChild) {
		return doc.removeChild(oldChild);
	}

	/** {@inheritDoc} */
	@Override
	public Node replaceChild(final Node newChild, final Node oldChild) {
		return doc.replaceChild(newChild, oldChild);
	}

	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setNodeValue(final String nodeValue) {
		doc.setNodeValue(nodeValue);
	}

	/** {@inheritDoc} */
	@Override
	public void setPrefix(final String prefix) {
		doc.setPrefix(prefix);
	}

	/** {@inheritDoc} */
	@Override
	public void setTextContent(final String textContent) {
		doc.setTextContent(textContent);
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(final String key, final Object data, final UserDataHandler handler) {
		return doc.setUserData(key, data, handler);
	}

	@Override
	public void setParentImpl(Node parent) {

	}

	@Override
	public void setNamespaceURI(String namespaceURI) {

	}

	/** {@inheritDoc} */
	@Override
	public Node adoptNode(final Node source) {
		return doc.adoptNode(source);
	}

	@Override
	public EntityReference createEntityReference(final String ent1) {
		return doc.createEntityReference(ent1);
	}

	@Override
	public Node renameNode(final Node node, final String namespaceURI, final String qualifiedName) {
		return doc.renameNode(node, namespaceURI, qualifiedName);
	}

	/** {@inheritDoc} */
	@Override
	public Attr createAttribute(final String name) {
		return doc.createAttribute(name);
	}

	/** {@inheritDoc} */
	@Override
	public Attr createAttributeNS(final String namespaceURI, final String qualifiedName) {
		return doc.createAttributeNS(namespaceURI, qualifiedName);
	}

	/** {@inheritDoc} */
	@Override
	public CDATASection createCDATASection(final String data) {
		return doc.createCDATASection(data);
	}

	/** {@inheritDoc} */
	@Override
	public Comment createComment(final String data) {
		return doc.createComment(data);
	}

	/** {@inheritDoc} */
	@Override
	public DocumentFragment createDocumentFragment() {
		return doc.createDocumentFragment();
	}

	/** {@inheritDoc} */
	@Override
	public Element createElement(final String tagName) {
		return doc.createElement(tagName);
	}

	/** {@inheritDoc} */
	@Override
	public Element createElementNS(final String namespaceURI, final String qualifiedName) {
		return doc.createElementNS(namespaceURI, qualifiedName);
	}

	/** {@inheritDoc} */
	@Override
	public ProcessingInstruction createProcessingInstruction(final String target, final String data) {
		return doc.createProcessingInstruction(target, data);
	}

	/** {@inheritDoc} */
	@Override
	public Text createTextNode(final String data) {
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
	public HTMLCollection getElementsByTagNameNS(final String namespaceURI, final String localName) {
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
	public Node importNode(final Node importedNode, final boolean deep) {
		return doc.importNode(importedNode, deep);
	}

	/** {@inheritDoc} */
	@Override
	public void normalizeDocument() {
		doc.normalizeDocument();
	}

	/** {@inheritDoc} */
	@Override
	public void setDocumentURI(final String documentURI) {
		doc.setDocumentURI(documentURI);
	}

	/** {@inheritDoc} */
	@Override
	public void setStrictErrorChecking(final boolean strictErrorChecking) {
		doc.setStrictErrorChecking(strictErrorChecking);
	}

	/** {@inheritDoc} */
	@Override
	public void setXmlStandalone(final boolean xmlStandalone) {
		doc.setXmlStandalone(xmlStandalone);
	}

	/** {@inheritDoc} */
	@Override
	public void setXmlVersion(final String xmlVersion) {
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
	public void setOwnerDocument(final Document document) {
		doc.setOwnerDocument(document);
	}

	@Override
	public void setOwnerDocument(Document value, boolean deep) {

	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(final Node other) {

		return doc.contains(other);
	}

	/** {@inheritDoc} */
	@Override
	public void addEventListener(final String type, final Function listener) {
		doc.addEventListener(type, listener);
	}

	/** {@inheritDoc} */
	@Override
	public void addEventListener(final String type, final Function listener, final boolean useCapture) {
		doc.addEventListener(type, listener, useCapture);
	}

	/** {@inheritDoc} */
	@Override
	public void removeEventListener(final String type, final Function listener) {
		doc.removeEventListener(type, listener);
	}

	/** {@inheritDoc} */
	@Override
	public void removeEventListener(final String type, final Function listener, final boolean useCapture) {
		doc.removeEventListener(type, listener, useCapture);
	}

	/** {@inheritDoc} */
	@Override
	public boolean dispatchEvent(final Node element, final Event evt) {

		return doc.dispatchEvent(element, evt);
	}

	@Override
	public boolean dispatchEvent(final Event evt) throws EventException {
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
	public Element querySelector(final String selectors) {

		return doc.querySelector(selectors);
	}

	/** {@inheritDoc} */
	@Override
	public NodeList querySelectorAll(final String selectors) {

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
	public HTMLAllCollection getall() {
		return doc.getall();
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
	public void setBgColor(final String bgColor) {
		doc.setBgColor(bgColor);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLElement getBody() {

		return doc.getBody();
	}

	/** {@inheritDoc} */
	@Override
	public void setBody(final HTMLElement body) {
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
	public void setCookie(final String cookie) {
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
	public void setDesignMode(final String designMode) {
		doc.setDesignMode(designMode);
	}

	/** {@inheritDoc} */
	@Override
	public String getDir() {

		return doc.getDesignMode();
	}

	/** {@inheritDoc} */
	@Override
	public void setDir(final String dir) {
		doc.setDir(dir);
	}

	/** {@inheritDoc} */
	@Override
	public String getDomain() {

		return doc.getDomain();
	}

	/** {@inheritDoc} */
	@Override
	public void setDomain(final String domain) {
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
	public void setFgColor(final String fgColor) {
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
	public void setLocation(final Location location) {
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
	public void setTitle(final String title) {
		doc.setTitle(title);
	}

	/** {@inheritDoc} */
	@Override
	public VisibilityState getVisibilityState() {

		return doc.getVisibilityState();
	}

	/** {@inheritDoc} */
	@Override
	public Range caretRangeFromPoint(final double x, final double y) {
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
	public Element createElementNS(final String namespace, final String qualifiedName, final String options) {

		return doc.createElementNS(namespace, qualifiedName, options);
	}

	/** {@inheritDoc} */
	@Override
	public Event createEvent(final String eventInterface) {

		return doc.createEvent(eventInterface);
	}

	/** {@inheritDoc} */
	@Override
	public NodeIterator createNodeIterator(final Node root) throws DOMException {
		return doc.createNodeIterator(root);
	}

	/** {@inheritDoc} */
	@Override
	public NodeIterator createNodeIterator(final Node root, final int whatToShow) throws DOMException {
		return doc.createNodeIterator(root, whatToShow);
	}

	/** {@inheritDoc} */
	@Override
	public NodeIterator createNodeIterator(final Node root, final NodeFilter filter) throws DOMException {
		return doc.createNodeIterator(root, filter);
	}

	/** {@inheritDoc} */
	@Override
	public NodeIterator createNodeIterator(final Node root, final int whatToShow, final NodeFilter filter) throws DOMException {
		return doc.createNodeIterator(root, whatToShow, filter);
	}

	/** {@inheritDoc} */
	@Override
	public Range createRange() {
		return doc.createRange();
	}

	/** {@inheritDoc} */
	@Override
	public TreeWalker createTreeWalker(final Node root) throws DOMException {
		return doc.createTreeWalker(root);
	}

	/** {@inheritDoc} */
	@Override
	public TreeWalker createTreeWalker(final Node root, final int whatToShow) throws DOMException {
		return doc.createTreeWalker(root, whatToShow);
	}

	/** {@inheritDoc} */
	@Override
	public TreeWalker createTreeWalker(final Node root, final NodeFilter filter) throws DOMException {
		return doc.createTreeWalker(root, filter);
	}

	@Override
	public TreeWalker createTreeWalker(final Node root, final int whatToShow, final NodeFilter filter) throws DOMException {
		return new TreeWalkerImpl(root, whatToShow, filter);
	}

	/** {@inheritDoc} */
	@Override
	public Element elementFromPoint(final double x, final double y) {
		return doc.elementFromPoint(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public boolean execCommand(final String commandId, final boolean showUI, final String value) {
		return doc.execCommand(commandId, showUI, value);
	}

	/** {@inheritDoc} */
	@Override
	public boolean execCommand(final String commandId, final boolean showUI) {
		return doc.execCommand(commandId, showUI);
	}

	/** {@inheritDoc} */
	@Override
	public boolean execCommand(final String commandId) {
		return doc.execCommand(commandId);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByClassName(final String classNames) {
		return doc.getElementsByClassName(classNames);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByName(final String elementName) {
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
	public Document open(final String url, final String name, final String features, final boolean replace) {
		return doc.open(url, name, features, replace);
	}

	/** {@inheritDoc} */
	@Override
	public Document open(final String url, final String name, final String features) {
		return doc.open(url, name, features);
	}

	/** {@inheritDoc} */
	@Override
	public Document open(final String url, final String name) {
		return doc.open(url, name);
	}

	/** {@inheritDoc} */
	@Override
	public Document open(final String url) {
		return doc.open(url);
	}

	/** {@inheritDoc} */
	@Override
	public Document open() {
		return doc.open();
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandEnabled(final String commandId) {
		return doc.queryCommandEnabled(commandId);
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandIndeterm(final String commandId) {
		return doc.queryCommandIndeterm(commandId);
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandState(final String commandId) {
		return doc.queryCommandState(commandId);
	}

	/** {@inheritDoc} */
	@Override
	public boolean queryCommandSupported(final String commandId) {
		return doc.queryCommandSupported(commandId);
	}

	/** {@inheritDoc} */
	@Override
	public String queryCommandValue(final String commandId) {
		return doc.queryCommandValue(commandId);
	}

	/** {@inheritDoc} */
	@Override
	public void releaseEvents() {
		doc.releaseEvents();

	}

	/** {@inheritDoc} */
	@Override
	public void write(final String text) {
		doc.write(text);

	}

	/** {@inheritDoc} */
	@Override
	public void writeln(final String text) {
		doc.writeln(text);

	}

	/** {@inheritDoc} */
	@Override
	public XPathExpression createExpression() {

		return doc.createExpression();
	}

	/** {@inheritDoc} */
	@Override
	public XPathNSResolver createNSResolver(final Node nodeResolver) {
		return doc.createNSResolver(nodeResolver);
	}

	/** {@inheritDoc} */
	@Override
	public XPathResult evaluate(final String expression, final Node contextNode, final XPathNSResolver resolver, final short type,
                                final Object result) {
		return doc.evaluate(expression, contextNode, resolver, type, result);
	}

	@Override
	public boolean isXml() {
		return doc.isXml();
	}

	@Override
	public void setXml(final boolean xml) {
		doc.setXml(xml);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object XMLDocument]";
	}

	@Override
	public Object getDocumentItem(String name) {
		return null;
	}

	@Override
	public URL getFullURL(String spec) throws Exception {
		return null;
	}

	@Override
	public ModelNode getParentModelNode() {
		return null;
	}

	@Override
	public boolean isEqualOrDescendentOf(ModelNode otherNode) {
		return false;
	}

	@Override
	public Object getRenderState() {
		return null;
	}

	@Override
	public void setDocumentItem(String name, Object value) {

	}

	@Override
	public void warn(String message, Throwable err) {

	}
}
