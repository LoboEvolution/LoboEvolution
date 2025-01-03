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

import java.util.Objects;

/**
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

    /**
     * The xpath object that this expression wraps.
     */
    private final XPath m_xpath;

    /**
     * The document to be searched to parallel the case where the XPathEvaluator
     * is obtained by casting a Document.
     */
    private final Document m_doc;

    /**
     * Constructor for XPathExpressionImpl.
     *
     * @param xpath The wrapped XPath object.
     * @param doc   The document to be searched, to parallel the case where'' the
     *              XPathEvaluator is obtained by casting the document.
     */
    XPathExpressionImpl(final XPath xpath, final Document doc) {
        m_xpath = xpath;
        m_doc = doc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object evaluate(final Node contextNode, final short type, final Object result) throws XPathException, DOMException {

        // If the XPathEvaluator was determined by "casting" the document
        if (m_doc != null) {

            // Check that the context node is owned by the same document
            if (!Objects.equals(contextNode, m_doc) && !contextNode.getOwnerDocument().equals(m_doc)) {
                final String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_WRONG_DOCUMENT, null);
                throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, fmsg);
            }

            // Check that the context node is an acceptable node type
            final int nodeType = contextNode.getNodeType();

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
                    final String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_WRONG_NODETYPE, null);
                    throw new UnsupportedOperationException(fmsg);
            }
        }

        if (!XPathResultImpl.isValidType(type)) {
            final String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_INVALID_XPATH_TYPE,
                    new Object[]{(int) type});
            throw new XPathException(XPathException.TYPE_ERR, fmsg);
        }

        final XPathContext xpathSupport = new XPathContext(false);
        XObject xobj;

        if (null != m_doc) {
            xpathSupport.getDTMHandleFromNode(m_doc);
        }

        try {
            xobj = m_xpath.execute(xpathSupport, contextNode, null);
        } catch (final Exception te) {
            throw new XPathException(XPathException.INVALID_EXPRESSION_ERR, te.getLocalizedMessage());
        }

        return new XPathResultImpl(type, xobj, contextNode, m_xpath);
    }
}
