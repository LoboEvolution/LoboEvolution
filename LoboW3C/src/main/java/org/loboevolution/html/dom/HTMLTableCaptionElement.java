package org.loboevolution.html.dom;



/**
 * Special properties (beyond the regular HTMLElement interface it also has available to it by inheritance) for manipulating table caption elements.
 */
public interface HTMLTableCaptionElement extends HTMLElement {

    /**
     * Sets or retrieves the alignment of the caption or legend.
     */
    @Deprecated
    
    String getAlign();

    
    void setAlign(String align);

}
