package org.loboevolution.html.node;

import org.loboevolution.jsenum.Mode;

/**
 * A fragment of a document that can contain nodes and parts of text nodes.
 */
public interface Range {
   
    Node getCommonAncestorContainer();

    DocumentFragment cloneContents();

    Range cloneRange();

    void collapse(boolean toStart);

    void collapse();

    int compareBoundaryPoints(Mode how, Range sourceRange);

    /**
     * Returns −1 if the point is before the range, 0 if the point is in the range, and 1 if the point is after the range.
     */
    int comparePoint(Node node, int offset);

    DocumentFragment createContextualFragment(String fragment);

    void deleteContents();

    void detach();

    DocumentFragment extractContents();

    void insertNode(Node node);

    /**
     * Returns whether range intersects node.
     */
    boolean intersectsNode(Node node);

    boolean isPointInRange(Node node, int offset);

    void selectNode(Node node);

    void selectNodeContents(Node node);

    void setEnd(Node node, int offset);

    void setEndAfter(Node node);

    void setEndBefore(Node node);

    void setStart(Node node, int offset);

    void setStartAfter(Node node);

    void setStartBefore(Node node);

    void surroundContents(Node newParent);

}
