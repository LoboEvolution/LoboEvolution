package org.lobobrowser.html.w3c;

import org.lobobrowser.html.w3c.HTMLCollection;
import org.w3c.dom.Node;

public interface HTMLAllCollection extends HTMLCollection {

	public Node namedItem(String name);

	public HTMLAllCollection tags(String tagName);
}
