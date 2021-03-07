package org.loboevolution.html.dom;



/**
 * Contains descriptive metadata about a document. It inherits all of the properties and methods described in the HTMLElement interface.
 */
public interface HTMLMetaElement extends HTMLElement {
  
    /**
     * Gets or sets meta-information to associate with httpEquiv or name.
     */
    
    String getContent();

    
    void setContent(String content);

    /**
     * Gets or sets information used to bind the value of a content attribute of a meta element to an HTTP response header.
     */
    
    String getHttpEquiv();

    
    void setHttpEquiv(String httpEquiv);

    /**
     * Sets or retrieves the value specified in the content attribute of the meta object.
     */
    
    String getName();

    
    void setName(String name);

    /**
     * Sets or retrieves a scheme to be used in interpreting the value of a property specified for the object.
     */
    @Deprecated
    
    String getScheme();

    
    void setScheme(String scheme);

}
