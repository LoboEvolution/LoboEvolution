package org.loboevolution.html.dom;




/**
 * Provides special properties (beyond the regular HTMLElement interface it also has available to it by inheritance) for manipulating &lt;div&gt; elements.
 */
public interface HTMLDivElement extends HTMLElement {


    /**
     * Sets or retrieves how the object is aligned with adjacent text.
     */
    @Deprecated
    
    String getAlign();

    @Deprecated
    
    void setAlign(String align);

}
