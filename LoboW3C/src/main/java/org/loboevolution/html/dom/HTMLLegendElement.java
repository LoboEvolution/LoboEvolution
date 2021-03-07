package org.loboevolution.html.dom;



/**
 * The HTMLLegendElement is an interface allowing to access properties of the &lt;legend&gt; elements. It inherits properties and methods from the HTMLElement interface.
 */
public interface HTMLLegendElement extends HTMLElement {
  

    @Deprecated
    
    String getAlign();

    
    void setAlign(String align);

    /**
     * Retrieves a reference to the form that the object is embedded in.
     */
    
    
    HTMLFormElement getForm();

}
