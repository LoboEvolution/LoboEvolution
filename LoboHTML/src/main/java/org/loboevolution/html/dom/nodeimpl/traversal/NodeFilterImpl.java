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

package org.loboevolution.html.dom.nodeimpl.traversal;

import lombok.AllArgsConstructor;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.traversal.NodeFilter;

/**
 * <p>NodeFilterImpl class.</p>
 */
@AllArgsConstructor
public class NodeFilterImpl implements NodeFilter {

    /**
     * Show all <code>Nodes</code>.
     */
    public short getSHOW_ALL() {
        return SHOW_ALL;
    }

    /**
     * Show <code>Element</code> nodes.
     */
    public short getSHOW_ELEMENT() {
        return SHOW_ELEMENT;
    }

    /**
     * Show <code>Attr</code> nodes. This is meaningful only when creating an
     * iterator or tree-walker with an attribute node as its
     * <code>root</code>; in this case, it means that the attribute node
     * will appear in the first position of the iteration or traversal.
     * Since attributes are never children of other nodes, they do not
     * appear when traversing over the main document tree.
     */
    public short getSHOW_ATTRIBUTE() {
        return SHOW_ATTRIBUTE;
    }

    /**
     * Show <code>Text</code> nodes.
     */
    public short getSHOW_TEXT() {
        return SHOW_TEXT;
    }

    /**
     * Show <code>CDATASection</code> nodes.
     */
    public short getSHOW_CDATA_SECTION() {
        return SHOW_CDATA_SECTION;
    }

    /**
     * Show <code>EntityReference</code> nodes. Note that if Entity References
     * have been fully expanded while the tree was being constructed, these
     * nodes will not appear and this mask has no effect.
     */
    public short getSHOW_ENTITY_REFERENCE() {
        return SHOW_ENTITY_REFERENCE;
    }

    /**
     * Show <code>Entity</code> nodes. This is meaningful only when creating
     * an iterator or tree-walker with an<code> Entity</code> node as its
     * <code>root</code>; in this case, it means that the <code>Entity</code>
     * node will appear in the first position of the traversal. Since
     * entities are not part of the document tree, they do not appear when
     * traversing over the main document tree.
     */
    public short getSHOW_ENTITY() {
        return SHOW_ENTITY;
    }

    /**
     * Show <code>ProcessingInstruction</code> nodes.
     */
    public short getSHOW_PROCESSING_INSTRUCTION() {
        return SHOW_PROCESSING_INSTRUCTION;
    }

    /**
     * Show <code>Comment</code> nodes.
     */
    public short getSHOW_COMMENT() {
        return SHOW_COMMENT;
    }

    /**
     * Show <code>Document</code> nodes. (Of course, as with Attributes
     * and such, this is meaningful only when the iteration root is the
     * Document itself, since Document has no parent.)
     */
    public short getSHOW_DOCUMENT() {
        return SHOW_DOCUMENT;
    }

    /**
     * Show <code>DocumentType</code> nodes.
     */
    public short getSHOW_DOCUMENT_TYPE() {
        return SHOW_DOCUMENT_TYPE;
    }

    /**
     * Show <code>DocumentFragment</code> nodes. (Of course, as with
     * Attributes and such, this is meaningful only when the iteration
     * root is the Document itself, since DocumentFragment has no parent.)
     */
    public short getSHOW_DOCUMENT_FRAGMENT() {
        return SHOW_DOCUMENT_FRAGMENT;
    }

    /**
     * Show <code>Notation</code> nodes. This is meaningful only when creating
     * an iterator or tree-walker with a <code>Notation</code> node as its
     * <code>root</code>; in this case, it means that the
     * <code>Notation</code> node will appear in the first position of the
     * traversal. Since notations are not part of the document tree, they do
     * not appear when traversing over the main document tree.
     */
    public short getSHOW_NOTATION() {
        return SHOW_NOTATION;
    }

    /**
     * Accept the node.
     */
    public short getFILTER_ACCEPT() {
        return FILTER_ACCEPT;
    }

    /**
     * Reject the node. Same behavior as FILTER_SKIP. (In the DOM these
     * differ when applied to a TreeWalker but have the same result when
     * applied to a NodeIterator).
     */
    public short getFILTER_REJECT() {
        return FILTER_REJECT;
    }

    /**
     * Skip this single node.
     */
    public short getFILTER_SKIP() {
        return FILTER_SKIP;
    }

    @Override
    public short acceptNode(Node node) {
        return 0;
    }
}