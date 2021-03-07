package org.loboevolution.html.dom;



/**
 * Provides special properties (beyond those of the regular HTMLElement object interface it inherits) for manipulating <p> elements.
 */
public interface HTMLParagraphElement extends HTMLElement {

    /**
     * Sets or retrieves how the object is aligned with adjacent text.
     */
    @Deprecated
    
    String getAlign();

    @Deprecated
    
    void setAlign(String align);

}
