package org.loboevolution.html.node;

import org.loboevolution.html.dom.NodeFilter;

/**
 * The nodes of a document subtree and a position within them.
 */
public interface TreeWalker {

	Node getCurrentNode();

	void setCurrentNode(Node currentNode);

	NodeFilter getFilter();

	Node getRoot();

	double getWhatToShow();

	Node firstChild();

	Node lastChild();

	Node nextNode();

	Node nextSibling();

	Node parentNode();

	Node previousNode();

	Node previousSibling();

}
