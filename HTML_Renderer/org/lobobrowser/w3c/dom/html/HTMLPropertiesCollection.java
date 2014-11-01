package org.lobobrowser.w3c.dom.html;

import org.w3c.dom.DOMStringList;
import org.w3c.dom.Node;

public interface HTMLPropertiesCollection extends HTMLCollection
{
    // HTMLPropertiesCollection
    public Node namedItem(String name);
    public DOMStringList getNames();
}
