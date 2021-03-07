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
/*
 * $Id: XPathEvaluatorImpl.java 1225443 2011-12-29 05:44:18Z mrglavas $
 */

package org.loboevolution.html.dom.xpath;

import javax.xml.transform.TransformerException;

import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.XPath;
import org.apache.xpath.domapi.XPathStylesheetDOM3Exception;
import org.loboevolution.html.dom.nodeimpl.DOMException;
import org.loboevolution.html.node.Code;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeType;
import org.loboevolution.html.xpath.XPathEvaluator;
import org.loboevolution.html.xpath.XPathException;
import org.loboevolution.html.xpath.XPathExpression;
import org.loboevolution.html.xpath.XPathNSResolver;

/**
 *
 * The class provides an implementation of XPathEvaluator according to the DOM
 * L3 XPath Specification, Working Group Note 26 February 2004.
 *
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2004/NOTE-DOM-Level-3-XPath-20040226'>Document
 * Object Model (DOM) Level 3 XPath Specification</a>.
 * </p>
 *
 * <p>
 * The evaluation of XPath expressions is provided by
 * XPathEvaluator, which will provide evaluation of XPath 1.0
 * expressions with no specialized extension functions or variables. It is
 * expected that the XPathEvaluator interface will be implemented
 * on the same object which implements the Document interface in an
 * implementation which supports the XPath DOM module.
 * XPathEvaluator implementations may be available from other
 * sources that may provide support for special extension functions or variables
 * which are not defined in this specification.
 * </p>
 *
 * @see org.loboevolution.html.xpath.XPathEvaluator
 *
 * @author utente
 * @version $Id: $Id
 */
public final class XPathEvaluatorImpl implements XPathEvaluator {
	
	/**
	 * The document to be searched to parallel the case where the XPathEvaluator
	 * is obtained by casting a Document.
	 */
	private final Document m_doc;

	/**
	 * This prefix resolver is created whenever null is passed to the evaluate
	 * method. Its purpose is to satisfy the DOM L3 XPath API requirement that
	 * if a null prefix resolver is used, an exception should only be thrown
	 * when an attempt is made to resolve a prefix.
	 */
	private static class DummyPrefixResolver implements PrefixResolver {

		/**
		 * Constructor for DummyPrefixResolver.
		 */
		DummyPrefixResolver() {
		}

		/**
		 * @exception DOMException
		 *                NAMESPACE_ERR: Always throws this exceptionn
		 *
		 * @see org.apache.xml.utils.PrefixResolver#getNamespaceForPrefix(String)
		 */
		@Override
		public String getNamespaceForPrefix(String prefix) {
			return getNamespaceForPrefix(prefix, null);
		}

		/**
		 * @see org.apache.xml.utils.PrefixResolver#handlesNullPrefixes()
		 */
		@Override
		public boolean handlesNullPrefixes() {
			return false;
		}

		/**
		 * @see org.apache.xml.utils.PrefixResolver#getBaseIdentifier()
		 */
		@Override
		public String getBaseIdentifier() {
			return null;
		}

		@Override
		public String getNamespaceForPrefix(String prefix, org.w3c.dom.Node context) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	/**
	 * Constructor for XPathEvaluatorImpl.
	 *
	 * @param doc
	 *            The document to be searched, to parallel the case where'' the
	 *            XPathEvaluator is obtained by casting the document.
	 */
	public XPathEvaluatorImpl(Document doc) {
		m_doc = doc;
	}

	/**
	 * Constructor in the case that the XPath expression can be evaluated
	 * without needing an XML document at all.
	 */
	public XPathEvaluatorImpl() {
		m_doc = null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Creates a parsed XPath expression with resolved namespaces. This is
	 * useful when an expression will be reused in an application since it makes
	 * it possible to compile the expression string into a more efficient
	 * internal form and preresolve all namespace prefixes which occur within
	 * the expression.
	 * @exception XPathException
	 *                INVALID_EXPRESSION_ERR: Raised if the expression is not
	 *                legal according to the rules of the
	 *                XPathEvaluatori
	 * @exception DOMException
	 *                NAMESPACE_ERR: Raised if the expression contains namespace
	 *                prefixes which cannot be resolved by the specified
	 *                XPathNSResolver.
	 * @see org.loboevolution.html.xpath.XPathEvaluator#createExpression(String,
	 *      XPathNSResolver)
	 */
	@Override
	public XPathExpression createExpression(String expression, XPathNSResolver resolver)
			throws XPathException, DOMException {

		try {

			// If the resolver is null, create a dummy prefix resolver
			XPath xpath = new XPath(expression, null,
					null == resolver ? new DummyPrefixResolver() : (PrefixResolver) resolver, XPath.SELECT);

			return new XPathExpressionImpl(xpath, m_doc);

		} catch (TransformerException e) {
			// Need to pass back exception code DOMException.NAMESPACE_ERR also.
			// Error found in DOM Level 3 XPath Test Suite.
			if (e instanceof XPathStylesheetDOM3Exception) {
				throw new DOMException(Code.NAMESPACE_ERR, e.getMessageAndLocation());
			} else {
				throw new XPathException(XPathException.INVALID_EXPRESSION_ERR, e.getMessageAndLocation());
			}

		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Adapts any DOM node to resolve namespaces so that an XPath expression can
	 * be easily evaluated relative to the context of the node where it appeared
	 * within the document. This adapter works like the DOM Level 3 method
	 * lookupNamespaceURI on nodes in resolving the namespaceURI
	 * from a given prefix using the current information available in the node's
	 * hierarchy at the time lookupNamespaceURI is called, also correctly
	 * resolving the implicit xml prefix.
	 * @see org.loboevolution.html.xpath.XPathEvaluator#createNSResolver(Node)
	 */
	@Override
	public XPathNSResolver createNSResolver(Node nodeResolver) {
		// TODO Broken with new interfaces
		/*return new XPathNSResolverImpl(nodeResolver.getNodeType() == NodeType.DOCUMENT_NODE
				? ((Document) nodeResolver).getDocumentElement() : nodeResolver);*/
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Evaluates an XPath expression string and returns a result of the
	 * specified type if possible.
	 * @exception XPathException
	 *                INVALID_EXPRESSION_ERR: Raised if the expression is not
	 *                legal according to the rules of the
	 *                XPathEvaluatori <br>
	 *                TYPE_ERR: Raised if the result cannot be converted to
	 *                return the specified type.
	 * @exception DOMException
	 *                NAMESPACE_ERR: Raised if the expression contains namespace
	 *                prefixes which cannot be resolved by the specified
	 *                XPathNSResolver. <br>
	 *                WRONG_DOCUMENT_ERR: The Node is from a document that is
	 *                not supported by this XPathEvaluator. <br>
	 *                NOT_SUPPORTED_ERR: The Node is not a type permitted as an
	 *                XPath context node.
	 * @see org.loboevolution.html.xpath.XPathEvaluator#evaluate(String, Node, XPathNSResolver, short, Object)
	 */
	@Override
	public Object evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type, Object result)
			throws XPathException, DOMException {

		XPathExpression xpathExpression = createExpression(expression, resolver);

		return xpathExpression.evaluate(contextNode, type, result);
	}

}
