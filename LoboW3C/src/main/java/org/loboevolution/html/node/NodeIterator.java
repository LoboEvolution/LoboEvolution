package org.loboevolution.html.node;

import org.loboevolution.html.dom.NodeFilter;

/**
 * An iterator over the members of a list of the nodes in a subtree of the DOM.
 * The nodes will be returned in document order.
 */
public interface NodeIterator {

	NodeFilter getFilter();

	boolean isPointerBeforeReferenceNode();

	Node getReferenceNode();

	Node getRoot();

	int getWhatToShow();

	void detach();

	Node nextNode();

	Node previousNode();

}
