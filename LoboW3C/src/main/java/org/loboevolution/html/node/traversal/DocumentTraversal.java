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
