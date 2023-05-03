package org.loboevolution.html.dom.nodeimpl.traversal;

import org.htmlunit.cssparser.dom.DOMException;
import lombok.Getter;
import lombok.Setter;
import org.loboevolution.html.node.AbstractList;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.traversal.NodeFilter;
import org.loboevolution.html.node.traversal.NodeIterator;

/**
 * <p>TreeWalkerImpl class.</p>
 */
@Getter
@Setter
public class NodeIteratorImpl extends AbstractList<Node> implements NodeIterator {

    private Node root;

    private Node currentNode;

    private Document document;

    private NodeFilter filter;

    private boolean fForward = true;

    private boolean fDetach = false;

    private int whatToShow;

    public NodeIteratorImpl(Node root, int whatToShow, NodeFilter filter) {
        this.root = root;
        this.whatToShow = whatToShow;
        this.filter = filter;
    }

    @Override
    public void detach() {
        fDetach = true;
    }

    @Override
    public Node nextNode() {

        if (fDetach) {
            throw new DOMException(
                    DOMException.INVALID_STATE_ERR, "");
        }

        if (root == null) return null;
        Node nextNode = currentNode;
        boolean accepted = false;

        while (!accepted) {
            if (!fForward && nextNode != null) {
                nextNode = currentNode;
            } else {
                if (nextNode != null
                        && nextNode.getNodeType() == Node.ENTITY_REFERENCE_NODE) {
                    nextNode = nextNode(nextNode, false);
                } else {
                    nextNode = nextNode(nextNode, true);
                }
            }

            fForward = true;
            if (nextNode == null) return null;
            accepted = acceptNode(nextNode);
            if (accepted) {
                currentNode = nextNode;
                return currentNode;
            }

        }
        return null;

    }

    @Override
    public Node previousNode() {
        if(fDetach) {
            throw new DOMException(
                    DOMException.INVALID_STATE_ERR, "");
        }

        if (root == null || currentNode == null) return null;

        Node previousNode = currentNode;
        boolean accepted = false;

        while (!accepted) {

            if (fForward && previousNode != null) {
                //repeat last node.
                previousNode = currentNode;
            } else {
                previousNode = previousNode(previousNode);
            }

            fForward = false;
            if (previousNode == null) return null;
            accepted = acceptNode(previousNode);
            if (accepted) {
                currentNode = previousNode;
                return currentNode;
            }
        }
        return null;
    }

    private Node nextNode(Node node, boolean visitChildren) {

        if (node == null) return root;

        Node result;
        if (visitChildren) {
            if (node.hasChildNodes()) {
                result = node.getFirstChild();
                return result;
            }
        }

        if (node == root) {
            return null;
        }

        result = node.getNextSibling();
        if (result != null) return result;


        Node parent = node.getParentNode();
        while (parent != null && parent != root) {
            result = parent.getNextSibling();
            if (result != null) {
                return result;
            } else {
                parent = parent.getParentNode();
            }

        }
        return null;
    }

    private Node previousNode(Node node) {
        Node result;
        if (node == root) return null;

        result = node.getPreviousSibling();
        if (result == null) {
            result = node.getParentNode();
            return result;
        }

        if (result.hasChildNodes() &&
                !(result.getNodeType() == Node.ENTITY_REFERENCE_NODE)) {
            while (result.hasChildNodes()) {
                result = result.getLastChild();
            }
        }

        return result;
    }

    boolean acceptNode(Node node) {

        if (filter == null) {
            return ( whatToShow & (1 << node.getNodeType()-1)) != 0 ;
        } else {
            return ((whatToShow & (1 << node.getNodeType()-1)) != 0 )
                    && filter.acceptNode(node) == NodeFilter.FILTER_ACCEPT;
        }
    }
}
