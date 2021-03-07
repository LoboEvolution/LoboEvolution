package org.loboevolution.html.dom;




/**
 * Implements the document object model (DOM) representation of the font element. The HTML Font Element &lt;font&gt; defines the font size, font face and color of text.
 */
public interface HTMLFontElement extends HTMLElement {


    @Deprecated
    
    String getColor();

    
    void setColor(String color);

    /**
     * Sets or retrieves the current typeface family.
     */
    @Deprecated
    
    String getFace();

    
    void setFace(String face);

    @Deprecated
    
    String getSize();

    
    void setSize(String size);

}
