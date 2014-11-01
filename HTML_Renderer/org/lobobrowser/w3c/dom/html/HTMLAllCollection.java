
package org.lobobrowser.w3c.dom.html;

import org.lobobrowser.w3c.dom.html.HTMLCollection;
import org.w3c.dom.Node;

public interface HTMLAllCollection extends HTMLCollection
{
    
    public Node namedItem(String name);
    public HTMLAllCollection tags(String tagName);
}
