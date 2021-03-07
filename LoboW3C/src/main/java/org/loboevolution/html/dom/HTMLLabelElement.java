package org.loboevolution.html.dom;







/**
 * Gives access to properties specific to &lt;label&gt; elements. It inherits methods and properties from the base HTMLElement interface.
 */
public interface HTMLLabelElement extends HTMLElement {
  
    
    
    HTMLElement getControl();

    /**
     * Retrieves a reference to the form that the object is embedded in.
     */
    
    
    HTMLFormElement getForm();

    /**
     * Sets or retrieves the object to which the given label object is assigned.
     */
    
    String getHtmlFor();

    
    void setHtmlFor(String htmlFor);

}
