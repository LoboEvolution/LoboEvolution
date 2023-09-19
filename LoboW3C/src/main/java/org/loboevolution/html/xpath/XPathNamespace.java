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

import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

/**
 * The XPathNamespace interface is returned by
 * XPathResult interfaces to represent the XPath namespace node
 * type that DOM lacks. There is no public constructor for this node type.
 * Attempts to place it into a hierarchy or a NamedNodeMap result in a
 * DOMException with the code HIERARCHY_REQUEST_ERR
 * . This node is read only, so methods or setting of attributes that would
 * mutate the node result in a DOMException with the code
 * NO_MODIFICATION_ALLOWED_ERR.
 * <p>The core specification describes attributes of the Node
 * interface that are different for different node node types but does not
 * describe XPATH_NAMESPACE_NODE, so here is a description of
 * those attributes for this node type. All attributes of Node
 * not described in this section have a null or
 * false value.
 * <p>ownerDocument matches the ownerDocument of the
 * ownerElement even if the element is later adopted.
 * <p>prefix is the prefix of the namespace represented by the
 * node.
 * <p>nodeName is the same as prefix.
 * <p>nodeType is equal to XPATH_NAMESPACE_NODE.
 * <p>namespaceURI is the namespace URI of the namespace
 * represented by the node.
 * <p>adoptNode, cloneNode, and
 * importNode fail on this node type by raising a
 * DOMException with the code NOT_SUPPORTED_ERR.In
 * future versions of the XPath specification, the definition of a namespace
 * node may be changed incomatibly, in which case incompatible changes to
 * field values may be required to implement versions beyond XPath 1.0.
 * <p>See also the <a href=http://www.w3.org/2002/08/WD-DOM-Level-3-XPath-20020820>Document Object Model (DOM) Level 3 XPath Specification</a>.
 */
public interface XPathNamespace extends Node {

    /**
     * The node is a Namespace.
     */
     short XPATH_NAMESPACE_NODE = 13;

    /**
     * The Element on which the namespace was in scope when it
     * was requested. This does not change on a returned namespace node even
     * if the document changes such that the namespace goes out of scope on
     * that element and this node is no longer found there by XPath.
     *
     * @return a {@link org.loboevolution.html.node.Node} object.
     */
    Node getOwnerElement();

}
