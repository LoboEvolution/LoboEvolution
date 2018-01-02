/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.lobobrowser.xpath;

import java.io.StringWriter;
import java.util.Properties;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.xpath.function.XPathFunctionResolverImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * <p>
 * Various utilities for working with XPath. This class uses a static XPath
 * object for most of it's work. This XPath object is preconfigured to use the
 * extended XPath functions of the org.lobobrowser.http package.
 * </p>
 *
 * @author rbair
 */
public class XPathUtils {
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(XPathUtils.class);
	/** The Constant xpath. */
	private static final XPath xpath;
	/** The Constant functionResolver. */
	private static final XPathFunctionResolverImpl functionResolver;

	static {
		xpath = XPathFactory.newInstance().newXPath();
		functionResolver = new XPathFunctionResolverImpl();
		xpath.setNamespaceContext(functionResolver);
		xpath.setXPathFunctionResolver(functionResolver);
	}

	/**
	 * Instantiates a new x path utils.
	 */
	private XPathUtils() {
	}

	/**
	 * Returns a {@link SimpleNodeList} containing all the nodes that match the
	 * given expression when executed on the given node (as opposed to the dom
	 * as a whole).
	 *
	 * @param expression
	 *            an XPath expression
	 * @param node
	 *            the contextual node
	 * @return SimpleNodeList containing the results from the expression. This
	 *         will never be null, but may contain no results.
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 */
	/*
	 * public synchronized static NodeList getElements(String expression, Node
	 * node) throws XPathExpressionException {NodeList nodes = new
	 * DOMNodeListImpl((Collection)xpath.evaluate(expression, node,
	 * XPathConstants.NODESET)); return nodes;}
	 */
	/**
	 * Returns a Node matching the given expression. If more than one node
	 * matches, the return value is undefined.
	 *
	 * @param expression
	 *            an XPath expression
	 * @param node
	 *            the contextual node
	 * @return Node. May be null.
	 * @throws XPathExpressionException
	 *             if the expression does not parse
	 */
	public synchronized static Node getElement(String expression, Node node) throws XPathExpressionException {
		Node n = (Node) xpath.evaluate(expression, node, XPathConstants.NODE);
		return n;
	}

	/**
	 * Returns the text content of the Node matching the given expression. If
	 * more than one node matches, the return value is undefined.
	 *
	 * @param expression
	 *            an XPath expression
	 * @param node
	 *            the contextual node
	 * @return text content of the selected Node. May be null.
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 */
	public synchronized static String getString(String expression, Node node) throws XPathExpressionException {
		Node n = (Node) xpath.evaluate(expression, node, XPathConstants.NODE);
		return n == null ? null : n.getTextContent();
	}

	/**
	 * Gets the string.
	 *
	 * @param expression
	 *            the expression
	 * @param node
	 *            the node
	 * @param namespace
	 *            the namespace
	 * @param namespacePrefix
	 *            the namespace prefix
	 * @return the string
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 */
	public synchronized static String getString(String expression, Node node, String namespace, String namespacePrefix)
			throws XPathExpressionException {
		functionResolver.addNamespaceMapping(namespacePrefix, namespace);
		Node n = (Node) xpath.evaluate(expression, node, XPathConstants.NODE);
		functionResolver.removeNamespaceMapping(namespacePrefix);
		return n == null ? null : n.getTextContent();
	}

	/**
	 * Returns a {@link SimpleNodeList} containing all the nodes that match the
	 * given expression when executed on the given node (as opposed to the dom
	 * as a whole).
	 *
	 * @param expression
	 *            an XPath expression
	 * @param node
	 *            the contextual node
	 * @return SimpleNodeList containing the results from the expression. This
	 *         will never be null, but may contain no results.
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 */
	/*
	 * public synchronized static NodeList getElements(XPathExpression
	 * expression, Node node) throws XPathExpressionException {NodeList nodes =
	 * new DOMNodeListImpl((Collection)expression.evaluate(node,
	 * XPathConstants.NODESET)); return nodes;}
	 */
	/**
	 * Returns a Node matching the given expression. If more than one node
	 * matches, the return value is undefined.
	 *
	 * @param expression
	 *            an XPath expression
	 * @param node
	 *            the contextual node
	 * @return Node. May be null.
	 * @throws XPathExpressionException
	 *             if the expression does not parse
	 */
	public synchronized static Node getElement(XPathExpression expression, Node node) throws XPathExpressionException {
		Node n = (Node) expression.evaluate(node, XPathConstants.NODE);
		return n;
	}

	/**
	 * Returns the text content of the Node matching the given expression. If
	 * more than one node matches, the return value is undefined.
	 *
	 * @param expression
	 *            an XPath expression
	 * @param node
	 *            the contextual node
	 * @return text content of the selected Node. May be null.
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 */
	public synchronized static String getString(XPathExpression expression, Node node) throws XPathExpressionException {
		Node n = (Node) expression.evaluate(node, XPathConstants.NODE);
		return n == null ? null : n.getTextContent();
	}

	/**
	 * Compiles the given expression, using the internal XPath instance of this
	 * class and returns the XPathExpression.
	 *
	 * @param expression
	 *            The XPath expression to compile
	 * @return the compiled XPathExpression
	 * @throws XPathExpressionException
	 *             for a malformed XPath expression
	 */
	public synchronized static XPathExpression compile(String expression) throws XPathExpressionException {
		return xpath.compile(expression);
	}

	/**
	 * Exports this DOM as a String.
	 *
	 * @param dom
	 *            the dom
	 * @return the string
	 */
	public static String toXML(Document dom) {
		return toXML(dom, null);
	}

	/**
	 * To xml.
	 *
	 * @param dom
	 *            the dom
	 * @param outputProperties
	 *            the output properties
	 * @return the string
	 */
	public static String toXML(Document dom, Properties outputProperties) {
		try {
			DOMSource source = new DOMSource(dom);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			Transformer tx = TransformerFactory.newInstance().newTransformer();
			if (outputProperties != null) {
				tx.setOutputProperties(outputProperties);
			}
			tx.transform(source, result);
			String s = writer.toString();
			writer.close();
			return s;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
