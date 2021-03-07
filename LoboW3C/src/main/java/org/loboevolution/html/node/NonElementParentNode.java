package org.loboevolution.html.node;

public interface NonElementParentNode {
	
	/**
     * Returns the first element within node's descendants whose ID is elementId.
     */
    Element getElementById(String elementId);

}
