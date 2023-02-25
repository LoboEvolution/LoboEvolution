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
 * $Id: XPathEvaluatorImpl.java 1225443 2011-12-29 05:44:18Z mrglavas $
 */

package org.loboevolution.html.dom.xpath;

import com.gargoylesoftware.css.dom.DOMException;
import org.loboevolution.apache.xml.utils.PrefixResolver;
import org.loboevolution.apache.xpath.XPath;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.xpath.XPathEvaluator;
import org.loboevolution.html.xpath.XPathException;
import org.loboevolution.html.xpath.XPathExpression;
import org.loboevolution.html.xpath.XPathNSResolver;

import org.loboevolution.javax.xml.transform.TransformerException;

/**
 * <p>XPathEvaluatorImpl class.</p>
 */
public final class XPathEvaluatorImpl implements XPathEvaluator {
	
	/**
	 * The document to be searched to parallel the case where the XPathEvaluator
	 * is obtained by casting a Document.
	 */
	private final Document m_doc;

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

	/** {@inheritDoc} */
	@Override
	public XPathExpression createExpression(String expression, XPathNSResolver resolver)
			throws XPathException, DOMException {
		try {
			XPath xpath = new XPath(expression,
					null == resolver ? new PrefixResolverImpl() : (PrefixResolver) resolver, XPath.SELECT);
			return new XPathExpressionImpl(xpath, m_doc);
		} catch (TransformerException e) {
			throw new XPathException(XPathException.INVALID_EXPRESSION_ERR, e.getMessageAndLocation());
		}
	}

	/** {@inheritDoc} */
	@Override
	public XPathNSResolver createNSResolver(Node nodeResolver) {
		return new XPathNSResolverImpl(nodeResolver.getNodeType() == Node.DOCUMENT_NODE
				? ((Document) nodeResolver).getDocumentElement() : nodeResolver);
	}

	/** {@inheritDoc} */
	@Override
	public Object evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type, Object result)
			throws XPathException, DOMException {
		XPathExpression xpathExpression = createExpression(expression, resolver);
		return xpathExpression.evaluate(contextNode, type, result);
	}
}
