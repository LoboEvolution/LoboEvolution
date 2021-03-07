package org.loboevolution.html.node;

public interface NonDocumentTypeChildNode {
    /**
     * Returns the first following sibling that is an element, and null otherwise.
     */
    Element getNextElementSibling();

    /**
     * Returns the first preceding sibling that is an element, and null otherwise.
     */
    Element getPreviousElementSibling();

}
