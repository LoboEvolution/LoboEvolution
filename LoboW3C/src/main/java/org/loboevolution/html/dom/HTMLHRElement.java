package org.loboevolution.html.dom;




/**
 * Provides special properties (beyond those of the HTMLElement interface it also has available to it by inheritance) for manipulating &lt;hr&gt; elements.
 */
public interface HTMLHRElement extends HTMLElement {


    /**
     * Sets or retrieves how the object is aligned with adjacent text.
     */
    @Deprecated
    
    String getAlign();

    
    void setAlign(String align);

    @Deprecated
    
    String getColor();

    
    void setColor(String color);

    /**
     * Sets or retrieves whether the horizontal rule is drawn with 3-D shading.
     */
    @Deprecated
    
    boolean isNoShade();

    
    void setNoShade(boolean noShade);

    @Deprecated
    
    String getSize();

    
    void setSize(String size);

    /**
     * Sets or retrieves the width of the object.
     */
    @Deprecated
    
    String getWidth();

    
    void setWidth(String width);

}
