/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.html.xpath;

import org.loboevolution.html.node.Node;
import org.w3c.dom.DOMException;
import org.w3c.dom.xpath.XPathException;

/**
 * The XPathExpression interface represents a parsed and resolved
 * XPath expression.
 * <p>See also the <a href='http://www.w3.org/2002/08/WD-DOM-Level-3-XPath-20020820'>Document Object Model (DOM) Level 3 XPath Specification</a>.
 *
 *
 *
 */
public interface XPathExpression {
    /**
     * Evaluates this XPath expression and returns a result.
     *
     * @param contextNode The context is context node for the
     *   evaluation of this XPath expression.If the XPathEvaluator was
     *   obtained by casting the Document then this must be
     *   owned by the same document and must be a Document,
     *   Element, Attribute, Text,
     *   CDATASection, Comment,
     *   ProcessingInstruction, or XPathNamespace
     *   node.If the context node is a Text or a
     *   CDATASection, then the context is interpreted as the
     *   whole logical text node as seen by XPath, unless the node is empty
     *   in which case it may not serve as the XPath context.
     * @param type If a specific type is specified, then the
     *   result will be coerced to return the specified type relying on
     *   XPath conversions and fail if the desired coercion is not possible.
     *   This must be one of the type codes of XPathResult.
     * @param result The result specifies a specific result
     *   object which may be reused and returned by this method. If this is
     *   specified as nullor the implementation does not reuse
     *   the specified result, a new result object will be constructed and
     *   returned.For XPath 1.0 results, this object will be of type
     *   XPathResult.
     * @return The result of the evaluation of the XPath expression.For XPath
     *   1.0 results, this object will be of type XPathResult.
     * @exception XPathException
     *   TYPE_ERR: Raised if the result cannot be converted to return the
     *   specified type.
     * @exception DOMException
     *   WRONG_DOCUMENT_ERR: The Node is from a document that is not supported
     *   by the XPathEvaluator that created this XPathExpression
     *   .
     *   <br>NOT_SUPPORTED_ERR: The Node is not a type permitted as an XPath
     *   context node or the request type is not permitted by this
     *   XPathExpression.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     * @throws org.w3c.dom.DOMException if any.
     * @throws org.w3c.dom.DOMException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     * @throws org.w3c.dom.DOMException if any.
     * @throws org.w3c.dom.DOMException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     * @throws org.w3c.dom.DOMException if any.
     */
    public Object evaluate(Node contextNode,
                           short type,
                           Object result)
                           throws XPathException, DOMException;

}
