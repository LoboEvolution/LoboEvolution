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

package org.loboevolution.html.xpath;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.node.Node;

/**
 * The XPathExpression interface represents a parsed and resolved
 * XPath expression.
 * <p>See also the <a href=http://www.w3.org/2002/08/WD-DOM-Level-3-XPath-20020820>Document Object Model (DOM) Level 3 XPath Specification</a>.
 */
public interface XPathExpression {
    /**
     * Evaluates this XPath expression and returns a result.
     *
     * @param contextNode The context is context node for the
     *                    evaluation of this XPath expression.If the XPathEvaluator was
     *                    obtained by casting the Document then this must be
     *                    owned by the same document and must be a Document,
     *                    Element, Attribute, Text,
     *                    CDATASection, Comment,
     *                    ProcessingInstruction, or XPathNamespace
     *                    node.If the context node is a Text or a
     *                    CDATASection, then the context is interpreted as the
     *                    whole logical text node as seen by XPath, unless the node is empty
     *                    in which case it may not serve as the XPath context.
     * @param type        If a specific type is specified, then the
     *                    result will be coerced to return the specified type relying on
     *                    XPath conversions and fail if the desired coercion is not possible.
     *                    This must be one of the type codes of XPathResult.
     * @param result      The result specifies a specific result
     *                    object which may be reused and returned by this method. If this is
     *                    specified as nullor the implementation does not reuse
     *                    the specified result, a new result object will be constructed and
     *                    returned.For XPath 1.0 results, this object will be of type
     *                    XPathResult.
     * @return The result of the evaluation of the XPath expression.For XPath
     * 1.0 results, this object will be of type XPathResult.
     * @throws XPathException                              TYPE_ERR: Raised if the result cannot be converted to return the
     *                                                     specified type.
     * @throws DOMException                                WRONG_DOCUMENT_ERR: The Node is from a document that is not supported
     *                                                     by the XPathEvaluator that created this XPathExpression
     *                                                     .
     *                                                     <br>NOT_SUPPORTED_ERR: The Node is not a type permitted as an XPath
     *                                                     context node or the request type is not permitted by this
     *                                                     XPathExpression.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     * @throws DOMException   if any.
     * @throws DOMException   if any.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     * @throws DOMException   if any.
     * @throws DOMException   if any.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     * @throws DOMException   if any.
     */
    Object evaluate(Node contextNode,
                    short type,
                    Object result)
            throws XPathException, DOMException;

}
