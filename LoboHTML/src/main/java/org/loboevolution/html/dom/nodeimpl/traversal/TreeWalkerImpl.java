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

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.traversal.NodeFilter;
import org.loboevolution.html.node.traversal.TreeWalker;


/**
 * <p>TreeWalkerImpl class.</p>
 */
@Getter
@Setter
public class TreeWalkerImpl implements TreeWalker {

    private Node root;

    private Node currentNode;

    private Document document;

    private NodeFilter filter;

    private int whatToShow;

    public TreeWalkerImpl(Node root, int whatToShow, NodeFilter filter) {
        this.currentNode = root;
        this.root = root;
        this.whatToShow = whatToShow;
        this.filter = filter;
    }

    @Override
    public Node firstChild() {
        if (currentNode == null) return null;

        Node node = currentNode.getFirstChild();
        if (node !=null) {
            currentNode = node;
        }
        return node;
    }

    @Override
    public Node lastChild() {
        if (currentNode == null) return null;

        Node node = currentNode.getLastChild();
        if (node !=null) {
            currentNode = node;
        }
        return node;
    }

    @Override
    public Node nextNode() {
        if (currentNode == null)
            return null;

        Node result = currentNode.getFirstChild();

        if (result != null) {
            currentNode = result;
            return result;
        }

        result = currentNode.getNextSibling();

        if (result != null) {
            currentNode = result;
            return result;
        }

        Node parent = currentNode.getParentNode();
        while (parent != null) {
            result = parent.getNextSibling();
            if (result != null) {
                currentNode = result;
                return result;
            } else {
                parent = parent.getParentNode();
            }
        }
        return null;
    }

    @Override
    public Node nextSibling() {
        if (currentNode == null) return null;

        Node node = currentNode.getNextSibling();
        if (node !=null) {
            currentNode = node;
        }
        return node;
    }

    @Override
    public Node parentNode() {
        if (currentNode == null) return null;

        Node node = currentNode.getParentNode();
        if (node !=null) {
            currentNode = node;
        }
        return node;
    }

    @Override
    public Node previousNode() {
        Node result;

        if (currentNode == null)
            return null;

        result = currentNode.getPreviousSibling();
        if (result == null) {
            result = currentNode.getParentNode();
            if (result != null) {
                currentNode = result;
                return currentNode;
            }
            return null;
        } else {

            Node lastChild = result.getLastChild();
            Node prev = lastChild;
            while (lastChild != null) {
                prev = lastChild;
                lastChild = prev.getLastChild();
            }

            lastChild = prev;
            if (lastChild != null) {
                currentNode = lastChild;
                return currentNode;
            }
            currentNode = result;
            return currentNode;
        }
    }

    @Override
    public Node previousSibling() {
        if (currentNode == null) return null;

        Node node = currentNode.getPreviousSibling();
        if (node !=null) {
            currentNode = node;
        }
        return node;
    }
}