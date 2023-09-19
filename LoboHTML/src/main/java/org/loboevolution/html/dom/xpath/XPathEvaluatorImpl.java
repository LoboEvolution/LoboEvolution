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
/*
 * $Id: XPathEvaluatorImpl.java 1225443 2011-12-29 05:44:18Z mrglavas $
 */

package org.loboevolution.html.dom.xpath;

import org.htmlunit.cssparser.dom.DOMException;
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
