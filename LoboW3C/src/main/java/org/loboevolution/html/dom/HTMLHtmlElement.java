package org.loboevolution.html.dom;


/**
 * Serves as the root node for a given HTML document. This object inherits the properties and methods described in the HTMLElement interface.
 */
public interface HTMLHtmlElement extends HTMLElement {

    /**
     * Sets or retrieves the DTD version that governs the current document.
     */
    @Deprecated
    
    String getVersion();

    
    void setVersion(String version);

}
