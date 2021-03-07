package org.loboevolution.html.dom;




/**
 * A HTML line break element (&lt;br&gt;). It inherits from HTMLElement.
 */
public interface HTMLBRElement extends HTMLElement {


    /**
     * Sets or retrieves the side on which floating objects are not to be positioned when any IHTMLBlockElement is inserted into the document.
     */
    @Deprecated
    
    String getClear();

    
    void setClear(String clear);

}
