package org.loboevolution.html.dom;

/**
 * Provides special properties (beyond the regular HTMLElement interface it also has available to it by inheritance) for manipulating &lt;basefont&gt; elements.
 */
public interface HTMLBaseFontElement extends HTMLElement {

    /**
     * Sets or retrieves the current typeface family.
     */
    @Deprecated
    
    String getFace();

    
    void setFace(String face);

    /**
     * Sets or retrieves the font size of the object.
     */
    @Deprecated
    
    double getSize();

    
    void setSize(double size);

}
