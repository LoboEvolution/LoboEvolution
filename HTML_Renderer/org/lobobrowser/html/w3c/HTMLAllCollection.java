package org.lobobrowser.html.w3c;

import org.w3c.dom.Node;


/**
 * The Interface HTMLAllCollection.
 */
public interface HTMLAllCollection extends HTMLCollection {

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLCollection#namedItem(java.lang.String)
	 */
	public Node namedItem(String name);

	/**
	 * Tags.
	 *
	 * @param tagName the tag name
	 * @return the HTML all collection
	 */
	public HTMLAllCollection tags(String tagName);
}
