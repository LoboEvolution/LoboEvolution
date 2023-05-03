/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * $Id: XPathExpressionImpl.java 1225426 2011-12-29 04:13:08Z mrglavas $
 */

package org.loboevolution.html.dom.xpath;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.apache.xpath.XPath;
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.xpath.XPathException;
import org.loboevolution.html.xpath.XPathExpression;

import org.loboevolution.javax.xml.transform.TransformerException;
import java.util.Objects;

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
 * @see org.loboevolution.html.xpath.XPathExpression
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

	/** {@inheritDoc} */
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
			int nodeType = contextNode.getNodeType();
			
			switch (nodeType) {
			case Node.DOCUMENT_NODE:
			case Node.ELEMENT_NODE:
			case Node.ATTRIBUTE_NODE:
			case Node.TEXT_NODE:
			case Node.CDATA_SECTION_NODE:
			case Node.COMMENT_NODE:
			case Node.PROCESSING_INSTRUCTION_NODE:
				break;
			default:
				String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_WRONG_NODETYPE, null);
				throw new UnsupportedOperationException(fmsg);
			}
		}

		if (!XPathResultImpl.isValidType(type)) {
			String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_INVALID_XPATH_TYPE,
					new Object[] {(int) type});
			throw new XPathException(XPathException.TYPE_ERR, fmsg);
		}

		XPathContext xpathSupport = new XPathContext(false);
		XObject xobj = null;

		if (null != m_doc) {
			xpathSupport.getDTMHandleFromNode(m_doc);
		}

		try {
			xobj = m_xpath.execute(xpathSupport, contextNode, null);
		} catch (TransformerException te) {
			throw new XPathException(XPathException.INVALID_EXPRESSION_ERR, te.getMessageAndLocation());
		}

		return new XPathResultImpl(type, xobj, contextNode, m_xpath);
	}
}
