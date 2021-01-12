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
 * $Id: XPathExpressionImpl.java 1225426 2011-12-29 04:13:08Z mrglavas $
 */

package org.loboevolution.html.dom.xpath;

import java.util.Objects;

import javax.xml.transform.TransformerException;

import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.res.XPATHErrorResources;
import org.apache.xpath.res.XPATHMessages;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.xpath.XPathException;
import org.w3c.dom.xpath.XPathExpression;
import org.w3c.dom.xpath.XPathNamespace;

/**
 *
 * The class provides an implementation of XPathExpression according to the DOM
 * L3 XPath Specification, Working Group Note 26 February 2004.
 *
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2004/NOTE-DOM-Level-3-XPath-20040226'>Document
 * Object Model (DOM) Level 3 XPath Specification</a>.
 * </p>
 *
 * <p>
 * The XPathExpression interface represents a parsed and resolved
 * XPath expression.
 * </p>
 *
 * @see org.w3c.dom.xpath.XPathExpression
 * 
 * @author utente
 * @version $Id: $Id
 */
public class XPathExpressionImpl implements XPathExpression {

	/** The xpath object that this expression wraps. */
	private final XPath m_xpath;

	/**
	 * The document to be searched to parallel the case where the XPathEvaluator
	 * is obtained by casting a Document.
	 */
	private final Document m_doc;

	/**
	 * Constructor for XPathExpressionImpl.
	 *
	 * @param xpath
	 *            The wrapped XPath object.
	 * @param doc
	 *            The document to be searched, to parallel the case where'' the
	 *            XPathEvaluator is obtained by casting the document.
	 */
	XPathExpressionImpl(XPath xpath, Document doc) {
		m_xpath = xpath;
		m_doc = doc;
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 * This method provides an implementation XPathResult.evaluate according to
	 * the DOM L3 XPath Specification, Working Group Note 26 February 2004.
	 *
	 * <p>
	 * See also the <a href=
	 * 'http://www.w3.org/TR/2004/NOTE-DOM-Level-3-XPath-20040226'>Document
	 * Object Model (DOM) Level 3 XPath Specification</a>.
	 * </p>
	 *
	 * <p>
	 * Evaluates this XPath expression and returns a result.
	 * </p>
	 * @exception XPathException
	 *                TYPE_ERR: Raised if the result cannot be converted to
	 *                return the specified type.
	 * @exception DOMException
	 *                WRONG_DOCUMENT_ERR: The Node is from a document that is
	 *                not supported by the XPathEvaluator that created this
	 *                XPathExpression. <br>
	 *                NOT_SUPPORTED_ERR: The Node is not a type permitted as an
	 *                XPath context node.
	 * @see org.w3c.dom.xpath.XPathExpression#evaluate(Node, short, Object)
	 * 
	 */
	@Override
	public Object evaluate(Node contextNode, short type, Object result) throws XPathException, DOMException {

		// If the XPathEvaluator was determined by "casting" the document
		if (m_doc != null) {

			// Check that the context node is owned by the same document
			if (!Objects.equals(contextNode, m_doc) && !contextNode.getOwnerDocument().equals(m_doc)) {
				String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_WRONG_DOCUMENT, null);
				throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, fmsg);
			}

			// Check that the context node is an acceptable node type
			short nodeType = contextNode.getNodeType();
			
			switch (nodeType) {
			case Node.DOCUMENT_NODE:
			case Node.ELEMENT_NODE:
			case Node.ATTRIBUTE_NODE:
			case Node.TEXT_NODE:
			case Node.CDATA_SECTION_NODE:
			case Node.COMMENT_NODE:
			case Node.PROCESSING_INSTRUCTION_NODE:
			case XPathNamespace.XPATH_NAMESPACE_NODE:
				break;
			default:
				String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_WRONG_NODETYPE, null);
				throw new DOMException(DOMException.NOT_SUPPORTED_ERR, fmsg);
			}
		}

		//
		// If the type is not a supported type, throw an exception and be
		// done with it!
		if (!XPathResultImpl.isValidType(type)) {
			String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_INVALID_XPATH_TYPE,
					new Object[] {(int) type});
			throw new XPathException(XPathException.TYPE_ERR, fmsg);
		}

		// Create an XPathContext that doesn't support pushing and popping of
		// variable resolution scopes. Sufficient for simple XPath 1.0
		// expressions.
		// Cache xpath context?
		XPathContext xpathSupport = new XPathContext(false);

		// if m_document is not null, build the DTM from the document
		if (null != m_doc) {
			xpathSupport.getDTMHandleFromNode(m_doc);
		}

		XObject xobj = null;
		try {
			xobj = m_xpath.execute(xpathSupport, contextNode, null);
		} catch (TransformerException te) {
			// What should we do here?
			throw new XPathException(XPathException.INVALID_EXPRESSION_ERR, te.getMessageAndLocation());
		}

		// Create a new XPathResult object
		// Reuse result object passed in?
		// The constructor will check the compatibility of type and xobj and
		// throw an exception if they are not compatible.
		return new XPathResultImpl(type, xobj, contextNode, m_xpath);
	}

}
