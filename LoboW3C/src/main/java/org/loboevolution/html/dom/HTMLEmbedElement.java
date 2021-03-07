package org.loboevolution.html.dom;

import org.loboevolution.html.node.Document;

/**
 * Provides special properties (beyond the regular HTMLElement interface it also has available to it by inheritance) for manipulating &lt;embed&gt; elements.
 */
public interface HTMLEmbedElement extends HTMLElement {


    @Deprecated
    
    String getAlign();

    
    void setAlign(String align);

    /**
     * Sets or retrieves the height of the object.
     */
    
    String getHeight();

    
    void setHeight(String height);

    /**
     * Sets or retrieves the name of the object.
     */
    @Deprecated
    
    String getName();

    
    void setName(String name);

    /**
     * Sets or retrieves a URL to be loaded by the object.
     */
    
    String getSrc();

    
    void setSrc(String src);

    
    String getType();

    
    void setType(String type);

    /**
     * Sets or retrieves the width of the object.
     */
    
    String getWidth();

    
    void setWidth(String width);

    
    Document getSVGDocument();

}
