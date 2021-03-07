package org.loboevolution.html.dom;


import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

public interface HTMLCollection {

     /**
     * Retrieves a select object or an object from an options collection.
     */
    
    Element namedItem(String name);
    
    int getLength();
    
    Node item(int index);

}
