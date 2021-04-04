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

/**
 *  The evaluation of XPath expressions is provided by
 * XPathEvaluator. In a DOM implementation which supports the
 * XPath 3.0 feature, as described above, the XPathEvaluator
 * interface will be implemented on the same object which implements the
 * Document interface permitting it to be obtained by the usual
 * binding-specific method such as casting or by using the DOM Level 3
 * getInterface method. In this case the implementation obtained from the
 * Document supports the XPath DOM module and is compatible with the XPath
 * 1.0 specification.
 * <p>Evaluation of expressions with specialized extension functions or
 * variables may not work in all implementations and is, therefore, not
 * portable. XPathEvaluator implementations may be available
 * from other sources that could provide specific support for specialized
 * extension functions or variables as would be defined by other
 * specifications.
 * <p>See also the <a href='http://www.w3.org/2002/08/WD-DOM-Level-3-XPath-20020820'>Document Object Model (DOM) Level 3 XPath Specification</a>.
 *
 *
 *
 */
public interface XPathEvaluator {
    /**
     * Creates a parsed XPath expression with resolved namespaces. This is
     * useful when an expression will be reused in an application since it
     * makes it possible to compile the expression string into a more
     * efficient internal form and preresolve all namespace prefixes which
     * occur within the expression.
     *
     * @param expression The XPath expression string to be parsed.
     * @param resolver The resolver permits translation of
     *   prefixes within the XPath expression into appropriate namespace URIs
     *   . If this is specified as null, any namespace prefix
     *   within the expression will result in DOMException
     *   being thrown with the code NAMESPACE_ERR.
     * @return The compiled form of the XPath expression.
     * @exception XPathException
     *   INVALID_EXPRESSION_ERR: Raised if the expression is not legal
     *   according to the rules of the XPathEvaluatori
     * @throws org.loboevolution.html.xpath.XPathException if any.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     */
    public XPathExpression createExpression(String expression,
                                            XPathNSResolver resolver)
                                            throws XPathException;

    /**
     * Adapts any DOM node to resolve namespaces so that an XPath expression
     * can be easily evaluated relative to the context of the node where it
     * appeared within the document. This adapter works like the DOM Level 3
     * method lookupNamespaceURI on nodes in resolving the
     * namespaceURI from a given prefix using the current information
     * available in the node's hierarchy at the time lookupNamespaceURI is
     * called. also correctly resolving the implicit xml prefix.
     *
     * @param nodeResolver The node to be used as a context for namespace
     *   resolution.
     * @return XPathNSResolver which resolves namespaces with
     *   respect to the definitions in scope for a specified node.
     */
    public XPathNSResolver createNSResolver(Node nodeResolver);

    /**
     * Evaluates an XPath expression string and returns a result of the
     * specified type if possible.
     *
     * @param expression The XPath expression string to be parsed and
     *   evaluated.
     * @param contextNode The context is context node for the
     *   evaluation of this XPath expression. If the XPathEvaluator was
     *   obtained by casting the Document then this must be
     *   owned by the same document and must be a Document,
     *   Element, Attribute, Text,
     *   CDATASection, Comment,
     *   ProcessingInstruction, or XPathNamespace
     *   node. If the context node is a Text or a
     *   CDATASection, then the context is interpreted as the
     *   whole logical text node as seen by XPath, unless the node is empty
     *   in which case it may not serve as the XPath context.
     * @param resolver The resolver permits translation of
     *   prefixes within the XPath expression into appropriate namespace URIs
     *   . If this is specified as null, any namespace prefix
     *   within the expression will result in DOMException
     *   being thrown with the code NAMESPACE_ERR.
     * @param type If a specific type is specified, then the
     *   result will be returned as the corresponding type.For XPath 1.0
     *   results, this must be one of the codes of the
     *   XPathResult interface.
     * @param result The result specifies a specific result
     *   object which may be reused and returned by this method. If this is
     *   specified as nullor the implementation does not reuse
     *   the specified result, a new result object will be constructed and
     *   returned.For XPath 1.0 results, this object will be of type
     *   XPathResult.
     * @return The result of the evaluation of the XPath expression.For XPath
     *   1.0 results, this object will be of type XPathResult.
     * @exception XPathException
     *   INVALID_EXPRESSION_ERR: Raised if the expression is not legal
     *   according to the rules of the XPathEvaluatori
     *   <br>TYPE_ERR: Raised if the result cannot be converted to return the
     *   specified type.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     */
    public Object evaluate(String expression,
                           Node contextNode,
                           XPathNSResolver resolver,
                           short type,
                           Object result)
                           throws XPathException;

}
