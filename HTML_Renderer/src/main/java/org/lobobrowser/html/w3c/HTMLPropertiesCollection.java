package org.lobobrowser.html.w3c;

import org.w3c.dom.DOMStringList;
import org.w3c.dom.Node;

public interface HTMLPropertiesCollection extends HTMLCollection {
	// HTMLPropertiesCollection
	public Node namedItem(String name);

	public DOMStringList getNames();
}
