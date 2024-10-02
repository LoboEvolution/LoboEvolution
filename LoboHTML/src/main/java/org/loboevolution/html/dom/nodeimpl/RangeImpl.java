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

package org.loboevolution.html.dom.nodeimpl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.ranges.Range;
import org.loboevolution.html.node.ranges.RangeException;
import org.loboevolution.html.parser.XHtmlParser;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.js.geom.DOMRect;
import org.loboevolution.js.geom.DOMRectList;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>RangeImpl class.</p>
 */
@NoArgsConstructor
@Slf4j
public class RangeImpl extends AbstractScriptableDelegate implements Range {


    final int EXTRACT_CONTENTS = 1;
    final int CLONE_CONTENTS = 2;
    final int DELETE_CONTENTS = 3;
    private Document document;
    private Node selectNode;
    private Node endContainer;
    private Node startContainer;
    private boolean detach;
    private int startOffset;
    private int endOffset;

    public RangeImpl(final Document doc) {
        this.document = doc;
        this.startContainer = doc;
        this.endContainer = doc;
        this.startOffset = 0;
        this.endOffset = 0;
        this.detach = false;
    }

    @Override
    public Node getStartContainer() throws DOMException {
        if (!detach) return startContainer;
        throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
    }

    @Override
    public int getStartOffset() throws DOMException {
        if (!detach) return startOffset;
        throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
    }

    @Override
    public Node getEndContainer() throws DOMException {
        if (!detach) return endContainer;
        throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
    }

    @Override
    public int getEndOffset() throws DOMException {
        if (!detach) return endOffset;
        throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
    }

    @Override
    public boolean getCollapsed() throws DOMException {
        return startContainer == endContainer && startOffset == endOffset;
    }

    @Override
    public DocumentFragment createContextualFragment(String newHtml) {
        final HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
        final XHtmlParser parser = new XHtmlParser(document.getUserAgentContext(), document, false);
        DocumentFragmentImpl frag = new DocumentFragmentImpl();
        try (final Reader reader = new StringReader(newHtml)) {
            parser.parse(reader, frag);
        } catch (final Exception thrown) {
            log.warn("Error setting inner HTML", thrown);
        }
        return frag;
    }

    @Override
    public Node getCommonAncestorContainer() {
        List<Node> startV = new ArrayList<>();
        Node node;
        for (node = startContainer; node != null; node = node.getParentNode()) {
            startV.add(node);
        }

        List<Node> endV = new ArrayList<>();
        for (node = endContainer; node != null; node = node.getParentNode()) {
            endV.add(node);
        }

        int s = startV.size() - 1;
        int e = endV.size() - 1;
        Node result = null;
        while (s >= 0 && e >= 0) {
            if (startV.get(s) == endV.get(e)) {
                result = startV.get(s);
            } else {
                break;
            }
            --s;
            --e;
        }
        return result;
    }

    @Override
    public void setStart(Node refNode, int offset) throws RangeException, DOMException {
        if (detach) {
            throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
        }
        if (!isLegalContainer(refNode)) {
            throw new RangeException(
                    RangeException.INVALID_NODE_TYPE_ERR, "Invalid NodeType");
        }
        if (document != refNode.getOwnerDocument() && document != refNode) {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
        }

        checkIndex(refNode, offset);

        startContainer = refNode;
        startOffset = offset;

        if (getCommonAncestorContainer() == null
                || (startContainer == endContainer && endOffset < startOffset)) {
            collapse(true);
        }
    }

    @Override
    public void setEnd(Node refNode, int offset) throws RangeException, DOMException {
        if (detach) {
            throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
        }
        if (!isLegalContainer(refNode)) {
            throw new RangeException(
                    RangeException.INVALID_NODE_TYPE_ERR, "Invalid NodeType");
        }
        if (document != refNode.getOwnerDocument() && document != refNode) {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
        }

        checkIndex(refNode, offset);

        endContainer = refNode;
        endOffset = offset;

        if (getCommonAncestorContainer() == null
                || (startContainer == endContainer && endOffset < startOffset)) {
            collapse(false);
        }
    }

    @Override
    public void setStartBefore(Node refNode) throws RangeException {
        if (detach) {
            throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
        }
        if (!hasLegalRootContainer(refNode) ||
                !isLegalContainedNode(refNode)) {
            throw new RangeException(
                    RangeException.INVALID_NODE_TYPE_ERR, "Invalid NodeType");
        }
        if (document != refNode.getOwnerDocument() && document != refNode) {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
        }

        startContainer = refNode.getParentNode();
        int i = 0;
        for (Node n = refNode; n != null; n = n.getPreviousSibling()) {
            i++;
        }
        startOffset = i - 1;

        if (getCommonAncestorContainer() == null
                || (startContainer == endContainer && endOffset < startOffset)) {
            collapse(true);
        }
    }

    @Override
    public void setStartAfter(Node refNode) throws RangeException {
        if (detach) {
            throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
        }
        if (!hasLegalRootContainer(refNode) ||
                !isLegalContainedNode(refNode)) {
            throw new RangeException(
                    RangeException.INVALID_NODE_TYPE_ERR, "Invalid NodeType");
        }
        if (document != refNode.getOwnerDocument() && document != refNode) {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
        }

        startContainer = refNode.getParentNode();
        int i = 0;
        for (Node n = refNode; n != null; n = n.getPreviousSibling()) {
            i++;
        }
        startOffset = i;

        if (getCommonAncestorContainer() == null
                || (startContainer == endContainer && endOffset < startOffset)) {
            collapse(true);
        }
    }

    @Override
    public void setEndBefore(Node refNode) throws RangeException {
        if (detach) {
            throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
        }
        if (!hasLegalRootContainer(refNode) ||
                !isLegalContainedNode(refNode)) {
            throw new RangeException(
                    RangeException.INVALID_NODE_TYPE_ERR, "Invalid NodeType");
        }
        if (document != refNode.getOwnerDocument() && document != refNode) {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
        }
        endContainer = refNode.getParentNode();
        int i = 0;
        for (Node n = refNode; n != null; n = n.getPreviousSibling()) {
            i++;
        }
        endOffset = i - 1;

        if (getCommonAncestorContainer() == null
                || (startContainer == endContainer && endOffset < startOffset)) {
            collapse(false);
        }
    }

    @Override
    public void setEndAfter(Node refNode) throws RangeException {
        if (detach) {
            throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
        }
        if (!hasLegalRootContainer(refNode) ||
                !isLegalContainedNode(refNode)) {
            throw new RangeException(
                    RangeException.INVALID_NODE_TYPE_ERR, "Invalid NodeType");
        }
        if (document != refNode.getOwnerDocument() && document != refNode) {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
        }

        endContainer = refNode.getParentNode();
        int i = 0;
        for (Node n = refNode; n != null; n = n.getPreviousSibling()) {
            i++;
        }
        endOffset = i;

        if (getCommonAncestorContainer() == null
                || (startContainer == endContainer && endOffset < startOffset)) {
            collapse(false);
        }
    }

    @Override
    public void collapse(boolean toStart) throws DOMException {

        if (detach) {
            throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
        }

        if (toStart) {
            endContainer = startContainer;
            endOffset = startOffset;
        } else {
            startContainer = endContainer;
            startOffset = endOffset;
        }
    }

    @Override
    public void selectNode(Node refNode) throws RangeException {
        if (detach) {
            throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
        }
        if (!isLegalContainer(refNode.getParentNode()) ||
                !isLegalContainedNode(refNode)) {
            throw new RangeException(
                    RangeException.INVALID_NODE_TYPE_ERR, "Invalid NodeType");
        }
        if (document != refNode.getOwnerDocument() && document != refNode) {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
        }

        Node parent = refNode.getParentNode();
        if (parent != null) {
            startContainer = parent;
            endContainer = parent;
            int i = 0;
            for (Node n = refNode; n != null; n = n.getPreviousSibling()) {
                i++;
            }
            startOffset = i - 1;
            endOffset = startOffset + 1;
        }
        selectNode = refNode;
    }

    @Override
    public void selectNodeContents(Node refNode) throws RangeException {
        if (detach) {
            throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
        }
        if (!isLegalContainer(refNode)) {
            throw new RangeException(
                    RangeException.INVALID_NODE_TYPE_ERR, "Invalid NodeType");
        }
        if (document != refNode.getOwnerDocument() && document != refNode) {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
        }
        startContainer = refNode;
        endContainer = refNode;
        Node first = refNode.getFirstChild();
        startOffset = 0;
        if (first == null) {
            endOffset = 0;
        } else {
            int i = 0;
            for (Node n = first; n != null; n = n.getNextSibling()) {
                i++;
            }
            endOffset = i;
        }
    }

    @Override
    public int getCompareBoundaryPoints(int how, Range sourceRange) throws DOMException {
        if (detach) {
            throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
        }
        // WRONG_DOCUMENT_ERR: Raised if the two Ranges are not in the same Document or DocumentFragment.
        if ((document != sourceRange.getStartContainer().getOwnerDocument()
                && document != sourceRange.getStartContainer()
                && sourceRange.getStartContainer() != null)
                || (document != sourceRange.getEndContainer().getOwnerDocument()
                && document != sourceRange.getEndContainer()
                && sourceRange.getStartContainer() != null)) {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
        }

        Node endPointA;
        Node endPointB;
        int offsetA;
        int offsetB;

        switch (how) {
            case START_TO_START:
                endPointA = sourceRange.getStartContainer();
                endPointB = startContainer;
                offsetA = sourceRange.getStartOffset();
                offsetB = startOffset;
                break;

            case START_TO_END:
                endPointA = sourceRange.getStartContainer();
                endPointB = endContainer;
                offsetA = sourceRange.getStartOffset();
                offsetB = endOffset;
                break;

            case END_TO_START:
                endPointA = sourceRange.getEndContainer();
                endPointB = startContainer;
                offsetA = sourceRange.getEndOffset();
                offsetB = startOffset;
                break;

            default:
                endPointA = sourceRange.getEndContainer();
                endPointB = endContainer;
                offsetA = sourceRange.getEndOffset();
                offsetB = endOffset;
                break;
        }

        if (endPointA == endPointB) {
            return Integer.compare(offsetB, offsetA);
        }

        for (Node c = endPointB, p = c.getParentNode();
             p != null;
             c = p, p = p.getParentNode()) {
            if (p == endPointA) {
                int index = indexOf(c, endPointA);
                if (offsetA <= index) return 1;
                return -1;
            }
        }

        for (Node c = endPointA, p = c.getParentNode();
             p != null;
             c = p, p = p.getParentNode()) {
            if (p == endPointB) {
                int index = indexOf(c, endPointB);
                if (index < offsetB) return 1;
                return -1;
            }
        }

        int depthDiff = 0;
        for (Node n = endPointA; n != null; n = n.getParentNode())
            depthDiff++;
        for (Node n = endPointB; n != null; n = n.getParentNode())
            depthDiff--;
        while (depthDiff > 0) {
            endPointA = endPointA.getParentNode();
            depthDiff--;
        }
        while (depthDiff < 0) {
            endPointB = endPointB.getParentNode();
            depthDiff++;
        }
        for (Node pA = endPointA.getParentNode(),
             pB = endPointB.getParentNode();
             pA != pB;
             pA = pA.getParentNode(), pB = pB.getParentNode()) {
            endPointA = pA;
            endPointB = pB;
        }
        for (Node n = endPointA.getNextSibling();
             n != null;
             n = n.getNextSibling()) {
            if (n == endPointB) {
                return 1;
            }
        }
        return -1;
    }

    @Override
    public void deleteContents() throws DOMException {
        traverseContents(DELETE_CONTENTS);
    }

    @Override
    public DocumentFragment extractContents() throws DOMException {
        return traverseContents(EXTRACT_CONTENTS);
    }

    @Override
    public DocumentFragment cloneContents() throws DOMException {
        return traverseContents(CLONE_CONTENTS);
    }

    @Override
    public void insertNode(Node newNode) throws DOMException, RangeException {

    }

    @Override
    public void surroundContents(Node newParent) throws DOMException, RangeException {

    }

    @Override
    public Range cloneRange() throws DOMException {
        return null;
    }

    @Override
    public void detach() throws DOMException {

    }

    @Override
    public DOMRectList getClientRects() {
        if (selectNode instanceof HTMLElement) {
            return ((HTMLElement) selectNode).getClientRects();
        }
        return null;
    }

    @Override
    public DOMRect getBoundingClientRect() {
        if (selectNode instanceof HTMLElement) {
            return ((HTMLElement) selectNode).getBoundingClientRect();
        }
        return null;
    }

    @Override
    public String toString() {
        if (detach) {
            throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
        }

        Node node = startContainer;
        Node stopNode = endContainer;
        StringBuilder sb = new StringBuilder();
        if (startContainer.getNodeType() == Node.TEXT_NODE
                || startContainer.getNodeType() == Node.CDATA_SECTION_NODE) {
            if (startContainer == endContainer) {
                sb.append(startContainer.getNodeValue(), startOffset, endOffset);
                return sb.toString();
            }
            sb.append(startContainer.getNodeValue().substring(startOffset));
            node = nextNode(node, true); //endContainer!=startContainer

        } else {  //startContainer is not a TextNode
            node = node.getFirstChild();
            if (startOffset > 0) { //find a first node within a range, specified by startOffset
                int counter = 0;
                while (counter < startOffset && node != null) {
                    node = node.getNextSibling();
                    counter++;
                }
            }
            if (node == null) {
                node = nextNode(startContainer, false);
            }
        }
        if (endContainer.getNodeType() != Node.TEXT_NODE &&
                endContainer.getNodeType() != Node.CDATA_SECTION_NODE) {
            int i = endOffset;
            stopNode = endContainer.getFirstChild();
            while (i > 0 && stopNode != null) {
                --i;
                stopNode = stopNode.getNextSibling();
            }
            if (stopNode == null)
                stopNode = nextNode(endContainer, false);
        }
        while (node != stopNode) {  //look into all kids of the Range
            if (node == null) break;
            if (node.getNodeType() == Node.TEXT_NODE
                    || node.getNodeType() == Node.CDATA_SECTION_NODE) {
                sb.append(node.getNodeValue());
            }

            node = nextNode(node, true);
        }

        if (endContainer.getNodeType() == Node.TEXT_NODE
                || endContainer.getNodeType() == Node.CDATA_SECTION_NODE) {
            sb.append(endContainer.getNodeValue(), 0, endOffset);
        }
        return sb.toString();
    }

    public short getEND_TO_START() {
        return END_TO_START;
    }

    public short getEND_TO_END() {
        return END_TO_END;
    }

    public short getSTART_TO_END() {
        return START_TO_END;
    }

    public short getSTART_TO_START() {
        return START_TO_START;
    }


    private boolean isLegalContainedNode(Node node) {
        if (node == null)
            return false;
        return switch (node.getNodeType()) {
            case Node.DOCUMENT_NODE, Node.DOCUMENT_FRAGMENT_NODE, Node.ATTRIBUTE_NODE, Node.ENTITY_NODE,
                 Node.NOTATION_NODE -> false;
            default -> true;
        };
    }

    private boolean isLegalContainer(Node node) {
        if (node == null)
            return false;

        while (node != null) {
            switch (node.getNodeType()) {
                case Node.ENTITY_NODE:
                case Node.NOTATION_NODE:
                case Node.DOCUMENT_TYPE_NODE:
                    return false;
            }
            node = node.getParentNode();
        }

        return true;
    }

    private boolean hasLegalRootContainer(Node node) {
        if (node == null)
            return false;

        Node rootContainer = getRootContainer(node);
        return switch (rootContainer.getNodeType()) {
            case Node.ATTRIBUTE_NODE, Node.DOCUMENT_NODE, Node.DOCUMENT_FRAGMENT_NODE -> true;
            default -> false;
        };
    }

    private Node getRootContainer(Node node) {
        if (node == null)
            return null;

        while (node.getParentNode() != null)
            node = node.getParentNode();
        return node;
    }

    private void checkIndex(Node refNode, int offset) throws DOMException {
        if (offset < 0) {
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "Invalid Size");
        }

        int type = refNode.getNodeType();
        if (type == Node.TEXT_NODE
                || type == Node.CDATA_SECTION_NODE
                || type == Node.COMMENT_NODE
                || type == Node.PROCESSING_INSTRUCTION_NODE) {
            if (offset > refNode.getNodeValue().length()) {
                throw new DOMException(DOMException.INDEX_SIZE_ERR, "Invalid Size");
            }
        } else {
            if (offset > refNode.getChildNodes().getLength()) {
                throw new DOMException(DOMException.INDEX_SIZE_ERR, "Invalid Size");
            }
        }
    }

    private Node nextNode(Node node, boolean visitChildren) {

        if (node == null) return null;

        Node result;
        if (visitChildren) {
            result = node.getFirstChild();
            if (result != null) {
                return result;
            }
        }

        result = node.getNextSibling();
        if (result != null) {
            return result;
        }


        Node parent = node.getParentNode();
        while (parent != null
                && parent != document
        ) {
            result = parent.getNextSibling();
            if (result != null) {
                return result;
            } else {
                parent = parent.getParentNode();
            }

        }
        return null;
    }

    private int indexOf(Node child, Node parent) {
        if (child.getParentNode() != parent) return -1;
        int i = 0;
        for (Node node = parent.getFirstChild(); node != child; node = node.getNextSibling()) {
            i++;
        }
        return i;
    }

    private DocumentFragment traverseContents(int how) throws DOMException {

        if (startContainer == null || endContainer == null) {
            return null; // REVIST: Throw exception?
        }

        if (detach) {
            throw new DOMException(DOMException.INVALID_STATE_ERR, "Invalid State");
        }

        if (startContainer == endContainer)
            return traverseSameContainer(how);

        int endContainerDepth = 0;
        for (Node c = endContainer, p = c.getParentNode();
             p != null;
             c = p, p = p.getParentNode()) {
            if (p == startContainer)
                return traverseCommonStartContainer(c, how);
            ++endContainerDepth;
        }

        int startContainerDepth = 0;
        for (Node c = startContainer, p = c.getParentNode();
             p != null;
             c = p, p = p.getParentNode()) {
            if (p == endContainer)
                return traverseCommonEndContainer(c, how);
            ++startContainerDepth;
        }

        int depthDiff = startContainerDepth - endContainerDepth;

        Node startNode = startContainer;
        while (depthDiff > 0) {
            startNode = startNode.getParentNode();
            depthDiff--;
        }

        Node endNode = endContainer;
        while (depthDiff < 0) {
            endNode = endNode.getParentNode();
            depthDiff++;
        }

        for (Node sp = startNode.getParentNode(), ep = endNode.getParentNode();
             sp != ep;
             sp = sp.getParentNode(), ep = ep.getParentNode()) {
            startNode = sp;
            endNode = ep;
        }
        return traverseCommonAncestors(startNode, endNode, how);
    }

    private DocumentFragment traverseSameContainer(int how) {
        DocumentFragment frag = null;
        if (how != DELETE_CONTENTS) {
            frag = document.createDocumentFragment();
        }

        if (startOffset == endOffset) {
            return frag;
        }

        // Text, CDATASection, Comment and ProcessingInstruction nodes need special case handling
        final int nodeType = startContainer.getNodeType();
        if (nodeType == Node.TEXT_NODE ||
                nodeType == Node.CDATA_SECTION_NODE ||
                nodeType == Node.COMMENT_NODE ||
                nodeType == Node.PROCESSING_INSTRUCTION_NODE) {
            // get the substring
            String s = startContainer.getNodeValue();
            String sub = s.substring(startOffset, endOffset);

            // set the original text node to its new value
            if (how != CLONE_CONTENTS) {
                ((CharacterDataImpl) startContainer).deleteData(startOffset,
                        endOffset - startOffset);
                // Nothing is partially selected, so collapse to start point
                collapse(true);
            }
            if (how == DELETE_CONTENTS) {
                return null;
            }
            if (nodeType == Node.TEXT_NODE) {
                frag.appendChild(document.createTextNode(sub));
            } else if (nodeType == Node.CDATA_SECTION_NODE) {
                frag.appendChild(document.createCDATASection(sub));
            } else if (nodeType == Node.COMMENT_NODE) {
                frag.appendChild(document.createComment(sub));
            } else { // nodeType == Node.PROCESSING_INSTRUCTION_NODE
                frag.appendChild(document.createProcessingInstruction(startContainer.getNodeName(), sub));
            }
            return frag;
        }

        // Copy nodes between the start/end offsets.
        Node n = getSelectedNode(startContainer, startOffset);
        int cnt = endOffset - startOffset;
        while (cnt > 0) {
            Node sibling = n.getNextSibling();
            Node xferNode = traverseFullySelected(n, how);
            if (frag != null)
                frag.appendChild(xferNode);
            --cnt;
            n = sibling;
        }

        // Nothing is partially selected, so collapse to start point
        if (how != CLONE_CONTENTS) {
            collapse(true);
        }
        return frag;
    }

    private DocumentFragment traverseCommonStartContainer(Node endAncestor, int how) {
        DocumentFragment frag = null;
        if (how != DELETE_CONTENTS)
            frag = document.createDocumentFragment();
        Node n = traverseRightBoundary(endAncestor, how);
        if (frag != null)
            frag.appendChild(n);

        int endIdx = indexOf(endAncestor, startContainer);
        int cnt = endIdx - startOffset;
        if (cnt <= 0) {
            // Collapse to just before the endAncestor, which
            // is partially selected.
            if (how != CLONE_CONTENTS) {
                setEndBefore(endAncestor);
                collapse(false);
            }
            return frag;
        }

        n = endAncestor.getPreviousSibling();
        while (cnt > 0) {
            Node sibling = n.getPreviousSibling();
            Node xferNode = traverseFullySelected(n, how);
            if (frag != null)
                frag.insertBefore(xferNode, frag.getFirstChild());
            --cnt;
            n = sibling;
        }
        // Collapse to just before the endAncestor, which
        // is partially selected.
        if (how != CLONE_CONTENTS) {
            setEndBefore(endAncestor);
            collapse(false);
        }
        return frag;
    }

    private DocumentFragment traverseCommonEndContainer(Node startAncestor, int how) {
        DocumentFragment frag = null;
        if (how != DELETE_CONTENTS)
            frag = document.createDocumentFragment();
        Node n = traverseLeftBoundary(startAncestor, how);
        if (frag != null)
            frag.appendChild(n);
        int startIdx = indexOf(startAncestor, endContainer);
        ++startIdx;  // Because we already traversed it....

        int cnt = endOffset - startIdx;
        n = startAncestor.getNextSibling();
        while (cnt > 0) {
            Node sibling = n.getNextSibling();
            Node xferNode = traverseFullySelected(n, how);
            if (frag != null)
                frag.appendChild(xferNode);
            --cnt;
            n = sibling;
        }

        if (how != CLONE_CONTENTS) {
            setStartAfter(startAncestor);
            collapse(true);
        }

        return frag;
    }

    private DocumentFragment traverseCommonAncestors(Node startAncestor, Node endAncestor, int how) {
        DocumentFragment frag = null;
        if (how != DELETE_CONTENTS)
            frag = document.createDocumentFragment();

        Node n = traverseLeftBoundary(startAncestor, how);
        if (frag != null)
            frag.appendChild(n);

        Node commonParent = startAncestor.getParentNode();
        int startOffset = indexOf(startAncestor, commonParent);
        int endOffset = indexOf(endAncestor, commonParent);
        ++startOffset;

        int cnt = endOffset - startOffset;
        Node sibling = startAncestor.getNextSibling();

        while (cnt > 0) {
            Node nextSibling = sibling.getNextSibling();
            n = traverseFullySelected(sibling, how);
            if (frag != null)
                frag.appendChild(n);
            sibling = nextSibling;
            --cnt;
        }

        n = traverseRightBoundary(endAncestor, how);
        if (frag != null)
            frag.appendChild(n);

        if (how != CLONE_CONTENTS) {
            setStartAfter(startAncestor);
            collapse(true);
        }
        return frag;
    }

    private Node traverseRightBoundary(Node root, int how) {
        Node next = getSelectedNode(endContainer, endOffset - 1);
        boolean isFullySelected = (next != endContainer);

        if (next == root)
            return traverseNode(next, isFullySelected, false, how);

        Node parent = next.getParentNode();
        Node clonedParent = traverseNode(parent, false, false, how);

        while (parent != null) {
            while (next != null) {
                Node prevSibling = next.getPreviousSibling();
                Node clonedChild =
                        traverseNode(next, isFullySelected, false, how);
                if (how != DELETE_CONTENTS) {
                    clonedParent.insertBefore(
                            clonedChild,
                            clonedParent.getFirstChild()
                    );
                }
                isFullySelected = true;
                next = prevSibling;
            }
            if (parent == root)
                return clonedParent;

            next = parent.getPreviousSibling();
            parent = parent.getParentNode();
            Node clonedGrandParent = traverseNode(parent, false, false, how);
            if (how != DELETE_CONTENTS)
                clonedGrandParent.appendChild(clonedParent);
            clonedParent = clonedGrandParent;

        }

        // should never occur
        return null;
    }

    private Node traverseLeftBoundary(Node root, int how) {
        Node next = getSelectedNode(getStartContainer(), getStartOffset());
        boolean isFullySelected = (next != getStartContainer());

        if (next == root)
            return traverseNode(next, isFullySelected, true, how);

        Node parent = next.getParentNode();
        Node clonedParent = traverseNode(parent, false, true, how);

        while (parent != null) {
            while (next != null) {
                Node nextSibling = next.getNextSibling();
                Node clonedChild =
                        traverseNode(next, isFullySelected, true, how);
                if (how != DELETE_CONTENTS)
                    clonedParent.appendChild(clonedChild);
                isFullySelected = true;
                next = nextSibling;
            }
            if (parent == root)
                return clonedParent;

            next = parent.getNextSibling();
            parent = parent.getParentNode();
            Node clonedGrandParent = traverseNode(parent, false, true, how);
            if (how != DELETE_CONTENTS)
                clonedGrandParent.appendChild(clonedParent);
            clonedParent = clonedGrandParent;

        }

        return null;

    }

    private Node traverseNode(Node n, boolean isFullySelected, boolean isLeft, int how) {
        if (isFullySelected) {
            return traverseFullySelected(n, how);
        }
        final int nodeType = n.getNodeType();
        if (nodeType == Node.TEXT_NODE ||
                nodeType == Node.CDATA_SECTION_NODE ||
                nodeType == Node.COMMENT_NODE ||
                nodeType == Node.PROCESSING_INSTRUCTION_NODE) {
            return traverseCharacterDataNode(n, isLeft, how);
        }
        return traversePartiallySelected(n, how);
    }

    private Node traverseFullySelected(Node n, int how) {
        return switch (how) {
            case CLONE_CONTENTS -> n.cloneNode(true);
            case EXTRACT_CONTENTS -> {
                if (n.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
                    throw new DOMException(
                            DOMException.HIERARCHY_REQUEST_ERR,
                            "HIERARCHY_REQUEST_ERR");
                }
                yield n;
            }
            case DELETE_CONTENTS -> {
                n.getParentNode().removeChild(n);
                yield null;
            }
            default -> null;
        };
    }

    private Node traversePartiallySelected(Node n, int how) {
        return switch (how) {
            case CLONE_CONTENTS, EXTRACT_CONTENTS -> n.cloneNode(false);
            default -> null;
        };
    }

    private Node traverseCharacterDataNode(Node n, boolean isLeft, int how) {
        String txtValue = n.getNodeValue();
        String newNodeValue;
        String oldNodeValue;

        if (isLeft) {
            int offset = getStartOffset();
            newNodeValue = txtValue.substring(offset);
            oldNodeValue = txtValue.substring(0, offset);
        } else {
            int offset = getEndOffset();
            newNodeValue = txtValue.substring(0, offset);
            oldNodeValue = txtValue.substring(offset);
        }

        if (how != CLONE_CONTENTS)
            n.setNodeValue(oldNodeValue);
        if (how == DELETE_CONTENTS)
            return null;
        Node newNode = n.cloneNode(false);
        newNode.setNodeValue(newNodeValue);
        return newNode;
    }

    private Node getSelectedNode(Node container, int offset) {
        if (container.getNodeType() == Node.TEXT_NODE)
            return container;

        if (offset < 0)
            return container;

        Node child = container.getFirstChild();
        while (child != null && offset > 0) {
            --offset;
            child = child.getNextSibling();
        }
        if (child != null)
            return child;
        return container;
    }
}