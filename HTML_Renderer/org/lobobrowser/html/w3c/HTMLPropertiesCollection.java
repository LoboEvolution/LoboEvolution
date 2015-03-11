package org.lobobrowser.html.w3c;

import org.w3c.dom.DOMStringList;
import org.w3c.dom.Node;


/**
 * The Interface HTMLPropertiesCollection.
 */
public interface HTMLPropertiesCollection extends HTMLCollection {
	// HTMLPropertiesCollection
	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLCollection#namedItem(java.lang.String)
	 */
	public Node namedItem(String name);

	/**
	 * Gets the names.
	 *
	 * @return the names
	 */
	public DOMStringList getNames();
}
