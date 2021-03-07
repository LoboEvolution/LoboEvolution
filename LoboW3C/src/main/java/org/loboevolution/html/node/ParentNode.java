package org.loboevolution.html.node;

import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Node;

public interface ParentNode extends Node {

    int getChildElementCount();

    /**
     * Returns the child elements.
     */
    HTMLCollection getChildren();

    /**
     * Returns the first child that is an element, and null otherwise.
     */
    Element getFirstElementChild();

    /**
     * Returns the last child that is an element, and null otherwise.
     */
    Element getLastElementChild();

    /**
     * Returns the first element that is a descendant of node that matches selectors.
     */
    Element querySelector(String selectors);

    /**
     * Returns all element descendants of node that match selectors.
     */
    NodeList querySelectorAll(String selectors);
}

