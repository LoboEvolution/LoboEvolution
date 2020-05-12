package org.loboevolution.html.js;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;
import org.xml.sax.SAXException;

/**
 * <p>XMLDocument class.</p>
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
	 * @param fileName
	 *            name of file in file system
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
	 * @param fileName
	 *            name of file in file system
	 * @return XML String
	 */
	public String loadXML(String fileName) {
		loadFile(fileName);
		return getXML();
	}

	/**
	 * Load XML file
	 * 
	 * @param fileName
	 *            name of in file system
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
			if (f.isFile()) {
				doc = builder.parse(fileStr);
			} else {
				InputStream is = new ByteArrayInputStream(fileStr.getBytes());
				doc = builder.parse(is);
			}
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			logger.info(FEATURE +  " is not supported");
		} catch (SAXException e) {
			logger.warning("A DOCTYPE was passed into the XML document");
		} catch (IOException e) {
			logger.severe("IOException occurred, XXE may still possible: " + e.getMessage());
		}
	}

	/**
	 * <p>getXML.</p>
	 *
	 * @return XML String
	 */
	public String getXML() {
		return getXmlEncoding();
	}

	/** {@inheritDoc} */
	@Override
	public NodeList getElementsByTagName(String tagname) {
		return doc.getElementsByTagName(tagname);
	}

	/** {@inheritDoc} */
	@Override
	public Element getElementById(String id) {
		return doc.getElementById(id);
	}

	/** {@inheritDoc} */
	@Override
	public Node appendChild(Node newChild) throws DOMException {
		return doc.appendChild(newChild);
	}

	/** {@inheritDoc} */
	@Override
	public Node cloneNode(boolean deep) {
		return doc.cloneNode(deep);
	}

	/** {@inheritDoc} */
	@Override
	public short compareDocumentPosition(Node other) throws DOMException {
		return doc.compareDocumentPosition(other);
	}

	/** {@inheritDoc} */
	@Override
	public NamedNodeMap getAttributes() {
		return doc.getAttributes();
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
	public Object getFeature(String feature, String version) {
		return doc.getFeature(feature, version);
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
	public short getNodeType() {
		return doc.getNodeType();
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() throws DOMException {
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
	public String getTextContent() throws DOMException {
		return doc.getTextContent();
	}

	/** {@inheritDoc} */
	@Override
	public Object getUserData(String key) {
		return doc.getUserData(key);
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasAttributes() {
		return doc.hasAttributes();
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasChildNodes() {
		return doc.hasChildNodes();
	}

	/** {@inheritDoc} */
	@Override
	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
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
	public boolean isSupported(String feature, String version) {
		return doc.isSupported(feature, version);
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
	public Node removeChild(Node oldChild) throws DOMException {
		return doc.removeChild(oldChild);
	}

	/** {@inheritDoc} */
	@Override
	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		return doc.replaceChild(newChild, oldChild);
	}

	/** {@inheritDoc} */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		doc.setNodeValue(nodeValue);
	}

	/** {@inheritDoc} */
	@Override
	public void setPrefix(String prefix) throws DOMException {
		doc.setPrefix(prefix);
	}

	/** {@inheritDoc} */
	@Override
	public void setTextContent(String textContent) throws DOMException {
		doc.setTextContent(textContent);
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return doc.setUserData(key, data, handler);
	}

	/** {@inheritDoc} */
	@Override
	public Node adoptNode(Node source) throws DOMException {
		return doc.adoptNode(source);
	}

	/** {@inheritDoc} */
	@Override
	public Attr createAttribute(String name) throws DOMException {
		return doc.createAttribute(name);
	}

	/** {@inheritDoc} */
	@Override
	public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
		return doc.createAttributeNS(namespaceURI, qualifiedName);
	}

	/** {@inheritDoc} */
	@Override
	public CDATASection createCDATASection(String data) throws DOMException {
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
	public Element createElement(String tagName) throws DOMException {
		return doc.createElement(tagName);
	}

	/** {@inheritDoc} */
	@Override
	public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
		return doc.createElementNS(namespaceURI, qualifiedName);
	}

	/** {@inheritDoc} */
	@Override
	public EntityReference createEntityReference(String name) throws DOMException {
		return doc.createEntityReference(name);
	}

	/** {@inheritDoc} */
	@Override
	public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
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
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
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
	public Node importNode(Node importedNode, boolean deep) throws DOMException {
		return doc.importNode(importedNode, deep);
	}

	/** {@inheritDoc} */
	@Override
	public void normalizeDocument() {
		doc.normalizeDocument();
	}

	/** {@inheritDoc} */
	@Override
	public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
		return doc.renameNode(n, namespaceURI, qualifiedName);
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
	public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
		doc.setXmlStandalone(xmlStandalone);
	}

	/** {@inheritDoc} */
	@Override
	public void setXmlVersion(String xmlVersion) throws DOMException {
		doc.setXmlVersion(xmlVersion);
	}
}
