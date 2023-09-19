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

package org.loboevolution.html.node.traversal;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.node.Node;

/**
 * <p>NodeFilter interface.</p>
 */
public interface DocumentTraversal {

     /**
     * Creates a NodeIterator object that you can use to traverse filtered lists of
     * nodes or elements in a document.
     *
     * @param root a {@link org.loboevolution.html.node.Node} object.
     */
    NodeIterator createNodeIterator(Node root) throws DOMException;

    /**
     * Creates a NodeIterator object that you can use to traverse filtered lists of
     * nodes or elements in a document.
     *
     * @param root a {@link org.loboevolution.html.node.Node} object.
     * @param whatToShow a {@link java.lang.Integer} object.
     */
    NodeIterator createNodeIterator(Node root,
                                    int whatToShow) throws DOMException;

    /**
     * Creates a NodeIterator object that you can use to traverse filtered lists of
     * nodes or elements in a document.
     *
     * @param root a {@link org.loboevolution.html.node.Node} object.
     * @param filter a {@link org.loboevolution.html.node.traversal.NodeFilter} object.
     */
    NodeIterator createNodeIterator(Node root,
                                    NodeFilter filter) throws DOMException;


    /**
     * Creates a NodeIterator object that you can use to traverse filtered lists of
     * nodes or elements in a document.
     *
     * @param root a {@link org.loboevolution.html.node.Node} object.
     * @param whatToShow a {@link java.lang.Integer} object.
     * @param filter a {@link org.loboevolution.html.node.traversal.NodeFilter} object.
     */
    NodeIterator createNodeIterator(Node root,
                                    int whatToShow,
                                    NodeFilter filter) throws DOMException;

    /**
     * <p>createTreeWalker.</p>
     *
     * @param root  a {@link org.loboevolution.html.node.Node} object.
     */
    TreeWalker createTreeWalker(Node root) throws DOMException;

    /**
     * <p>createTreeWalker.</p>
     *
     * @param root a {@link org.loboevolution.html.node.Node} object.
     * @param whatToShow a {@link java.lang.Integer} object.
     */
    TreeWalker createTreeWalker(Node root,
                                int whatToShow) throws DOMException;


    /**
     * <p>createTreeWalker.</p>
     *
     * @param root  a {@link org.loboevolution.html.node.Node} object.
     * @param filter a {@link org.loboevolution.html.node.traversal.NodeFilter} object.
     */
    TreeWalker createTreeWalker(Node root,
                                NodeFilter filter) throws DOMException;

    /**
     * <p>createTreeWalker.</p>
     *
     * @param root                     a {@link org.loboevolution.html.node.Node} object.
     * @param whatToShow               a {@link java.lang.Integer} object.
     * @param filter                   a {@link org.loboevolution.html.node.traversal.NodeFilter} object.
     */
    TreeWalker createTreeWalker(Node root,
                                int whatToShow,
                                NodeFilter filter) throws DOMException;
}
