package org.loboevolution.html.dom;




/**
 * Contains the base URI for a document. This object inherits all of the properties and methods as described in the HTMLElement interface.
 */
public interface HTMLBaseElement extends HTMLElement {


    /**
     * Gets or sets the baseline URL on which relative links are based.
     */
    
    String getHref();

    
    void setHref(String href);

    /**
     * Sets or retrieves the window or frame at which to target content.
     */
    
    String getTarget();

    
    void setTarget(String target);

}
