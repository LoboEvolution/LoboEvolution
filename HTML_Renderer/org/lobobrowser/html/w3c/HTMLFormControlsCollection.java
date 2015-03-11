package org.lobobrowser.html.w3c;

import org.w3c.dom.Node;


/**
 * The Interface HTMLFormControlsCollection.
 */
public interface HTMLFormControlsCollection extends HTMLCollection {
	// HTMLFormControlsCollection
	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLCollection#namedItem(java.lang.String)
	 */
	public Node namedItem(String name);
}
