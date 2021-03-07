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

package org.loboevolution.html.js;

import java.io.File;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.CDATASection;
import org.loboevolution.html.node.Comment;
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLHeadElement;
import org.loboevolution.html.dom.HTMLScriptElement;

import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeIterator;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.NodeType;
import org.loboevolution.html.node.ParentNode;
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
import org.loboevolution.jsenum.DocumentReadyState;
import org.loboevolution.jsenum.VisibilityState;
import org.mozilla.javascript.Function;
import org.w3c.dom.UserDataHandler;

/**
 * <p>
 * XMLDocument class.
 * </p>
 *
 * @author utente
 * @version $Id: $Id
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

		String FEATURE = "";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
			factory.setFeature(FEATURE, true);

			FEATURE = "http://xml.org/sax/features/external-general-entities";
			factory.setFeature(FEATURE, false);

			FEATURE = "http://xml.org/sax/features/external-parameter-entities";
			factory.setFeature(FEATURE, false);

			FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
			factory.setFeature(FEATURE, false);

			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			factory.setXIncludeAware(false);
			factory.setExpandEntityReferences(false);

			DocumentBuilder builder = factory.newDocumentBuilder();
			File f = new File(fileStr);
	        // TODO Broken with new interfaces
			/*if (f.isFile()) {
				doc = builder.parse(fileStr);
			} else {
				InputStream is = new ByteArrayInputStream(fileStr.getBytes());
				doc = builder.parse(is);
			}*/
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			logger.info(FEATURE + " is not supported");
		} /*catch (SAXException e) {
			logger.warning("A DOCTYPE was passed into the XML document");
		} catch (IOException e) {
			logger.severe("IOException occurred, XXE may still possible: " + e.getMessage());
		}*/
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
	public NodeType getNodeType() {
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
	public ParentNode getParentNode() {
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

	@Override
	public boolean isIsConnected() {

		return doc.isIsConnected();
	}

	@Override
	public HTMLElement getParentElement() {

		return doc.getParentElement();
	}

	@Override
	public Node getRootNode() {

		return doc.getRootNode();
	}

	@Override
	public Node cloneNode() {

		return doc.cloneNode();
	}

	@Override
	public boolean contains(Node other) {

		return doc.contains(other);
	}

	@Override
	public void addEventListener(String type, Function listener) {
		doc.addEventListener(type, listener);
	}

	@Override
	public void addEventListener(String type, Function listener, boolean useCapture) {
		doc.addEventListener(type, listener, useCapture);
	}

	@Override
	public void removeEventListener(String script, Function function) {
		doc.removeEventListener(script, function);
	}

	@Override
	public void removeEventListener(String type, Function listener, boolean useCapture) {
		doc.removeEventListener(type, listener, useCapture);
	}

	@Override
	public boolean dispatchEvent(Node element, Event evt) {

		return doc.dispatchEvent(element, evt);
	}

	@Override
	public int getChildElementCount() {

		return doc.getChildElementCount();
	}

	@Override
	public HTMLCollection getChildren() {

		return doc.getChildren();
	}

	@Override
	public Element getFirstElementChild() {

		return doc.getFirstElementChild();
	}

	@Override
	public Element getLastElementChild() {

		return doc.getLastElementChild();
	}

	@Override
	public Element querySelector(String selectors) {

		return doc.querySelector(selectors);
	}

	@Override
	public NodeList querySelectorAll(String selectors) {

		return doc.querySelectorAll(selectors);
	}

	@Override
	public String getURL() {

		return doc.getURL();
	}

	@Override
	public Element getActiveElement() {

		return doc.getActiveElement();
	}

	@Override
	public String getAlinkColor() {

		return doc.getAlinkColor();
	}

	@Override
	public void setAlinkColor(String alinkColor) {
		doc.setAlinkColor(alinkColor);
	}

	@Override
	public HTMLCollection getAll() {

		return doc.getAll();
	}

	@Override
	public HTMLCollection getAnchors() {

		return doc.getAnchors();
	}

	@Override
	public HTMLCollection getApplets() {

		return doc.getApplets();
	}

	@Override
	public HTMLCollection getCommands() {

		return doc.getCommands();
	}

	@Override
	public String getBgColor() {

		return doc.getBgColor();
	}

	@Override
	public void setBgColor(String bgColor) {
		doc.setBgColor(bgColor);
	}

	@Override
	public HTMLElement getBody() {

		return doc.getBody();
	}

	@Override
	public void setBody(HTMLElement body) {
		doc.setBody(body);
	}

	@Override
	public String getCharacterSet() {

		return doc.getCharacterSet();
	}

	@Override
	public String getCharset() {

		return doc.getCharset();
	}

	@Override
	public String getCompatMode() {

		return doc.getCompatMode();
	}

	@Override
	public String getContentType() {

		return doc.getContentType();
	}

	@Override
	public String getCookie() {

		return doc.getCookie();
	}

	@Override
	public void setCookie(String cookie) {
		doc.setCookie(cookie);
	}

	@Override
	public HTMLScriptElement getCurrentScript() {

		return doc.getCurrentScript();
	}

	@Override
	public Window getDefaultView() {
		return doc.getDefaultView();
	}

	@Override
	public String getDesignMode() {

		return doc.getDesignMode();
	}

	@Override
	public void setDesignMode(String designMode) {
		doc.setDesignMode(designMode);
	}

	@Override
	public String getDir() {

		return doc.getDesignMode();
	}

	@Override
	public void setDir(String dir) {
		doc.setDir(dir);
	}

	@Override
	public String getDomain() {

		return doc.getDomain();
	}

	@Override
	public void setDomain(String domain) {
		doc.setDomain(domain);
	}

	@Override
	public HTMLCollection getEmbeds() {

		return doc.getEmbeds();
	}

	@Override
	public String getFgColor() {

		return doc.getFgColor();
	}

	@Override
	public void setFgColor(String fgColor) {
		doc.setFgColor(fgColor);
	}

	@Override
	public HTMLCollection getForms() {

		return doc.getForms();
	}

	@Override
	public boolean isFullscreen() {

		return doc.isFullscreen();
	}

	@Override
	public boolean isFullscreenEnabled() {

		return doc.isFullscreenEnabled();
	}

	@Override
	public HTMLHeadElement getHead() {

		return doc.getHead();
	}

	@Override
	public boolean isHidden() {

		return doc.isHidden();
	}

	@Override
	public HTMLCollection getImages() {

		return doc.getImages();
	}

	@Override
	public String getLastModified() {

		return doc.getLastModified();
	}

	@Override
	public HTMLCollection getLinks() {

		return doc.getLinks();
	}

	@Override
	public Location getLocation() {

		return doc.getLocation();
	}

	@Override
	public void setLocation(Location location) {
		doc.setLocation(location);
	}

	@Override
	public String getOrigin() {

		return doc.getOrigin();
	}

	@Override
	public HTMLCollection getPlugins() {

		return doc.getPlugins();
	}

	@Override
	public DocumentReadyState getReadyState() {

		return doc.getReadyState();
	}

	@Override
	public String getReferrer() {

		return doc.getReferrer();
	}

	@Override
	public HTMLCollection getScripts() {

		return doc.getScripts();
	}

	@Override
	public Element getScrollingElement() {

		return doc.getScrollingElement();
	}

	@Override
	public String getTitle() {

		return doc.getTitle();
	}

	@Override
	public void setTitle(String title) {
		doc.setTitle(title);
	}

	@Override
	public VisibilityState getVisibilityState() {

		return doc.getVisibilityState();
	}

	@Override
	public void captureEvents() {
		doc.captureEvents();
	}

	@Override
	public Range caretRangeFromPoint(double x, double y) {

		return doc.caretRangeFromPoint(x, y);
	}

	@Override
	public void clear() {
		doc.clear();

	}

	@Override
	public void close() {
		doc.close();

	}

	@Override
	public Element createElementNS(String namespace, String qualifiedName, String options) {

		return doc.createElementNS(namespace, qualifiedName, options);
	}

	@Override
	public Event createEvent(String eventInterface) {

		return doc.createEvent(eventInterface);
	}

	@Override
	public NodeIterator createNodeIterator(Node root) {

		return doc.createNodeIterator(root);
	}

	@Override
	public Range createRange() {

		return doc.createRange();
	}

	@Override
	public TreeWalker createTreeWalker(Node root) {

		return doc.createTreeWalker(root);
	}

	@Override
	public Element elementFromPoint(double x, double y) {

		return doc.elementFromPoint(x, y);
	}

	@Override
	public boolean execCommand(String commandId, boolean showUI, String value) {

		return doc.execCommand(commandId, showUI, value);
	}

	@Override
	public boolean execCommand(String commandId, boolean showUI) {

		return doc.execCommand(commandId, showUI);
	}

	@Override
	public boolean execCommand(String commandId) {

		return doc.execCommand(commandId);
	}

	@Override
	public HTMLCollection getElementsByClassName(String classNames) {

		return doc.getElementsByClassName(classNames);
	}

	@Override
	public HTMLCollection getElementsByName(String elementName) {

		return doc.getElementsByName(elementName);
	}

	@Override
	public Selection getSelection() {

		return doc.getSelection();
	}

	@Override
	public boolean hasFocus() {

		return doc.hasFocus();
	}

	@Override
	public Document open(String url, String name, String features, boolean replace) {

		return doc.open(url, name, features, replace);
	}

	@Override
	public Document open(String url, String name, String features) {

		return doc.open(url, name, features);
	}

	@Override
	public Document open(String url, String name) {

		return doc.open(url, name);
	}

	@Override
	public Document open(String url) {

		return doc.open(url);
	}

	@Override
	public Document open() {

		return doc.open();
	}

	@Override
	public boolean queryCommandEnabled(String commandId) {

		return doc.queryCommandEnabled(commandId);
	}

	@Override
	public boolean queryCommandIndeterm(String commandId) {

		return doc.queryCommandIndeterm(commandId);
	}

	@Override
	public boolean queryCommandState(String commandId) {

		return doc.queryCommandState(commandId);
	}

	@Override
	public boolean queryCommandSupported(String commandId) {

		return doc.queryCommandSupported(commandId);
	}

	@Override
	public String queryCommandValue(String commandId) {

		return doc.queryCommandValue(commandId);
	}

	@Override
	public void releaseEvents() {
		doc.releaseEvents();

	}

	@Override
	public void write(String text) {
		doc.write(text);

	}

	@Override
	public void writeln(String text) {
		doc.writeln(text);

	}

	@Override
	public XPathExpression createExpression() {

		return doc.createExpression();
	}

	@Override
	public XPathNSResolver createNSResolver(Node nodeResolver) {
		return doc.createNSResolver(nodeResolver);
	}

	@Override
	public XPathResult evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type,
			Object result) {
		return doc.evaluate(expression, contextNode, resolver, type, result);
	}
}
