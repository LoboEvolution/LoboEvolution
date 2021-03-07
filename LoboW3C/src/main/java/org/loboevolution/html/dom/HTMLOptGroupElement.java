package org.loboevolution.html.dom;


/**
 * Provides special properties and methods (beyond the regular HTMLElement object interface they also have available to them by inheritance) for manipulating the layout and presentation of &lt;optgroup&gt; elements.
 */
public interface HTMLOptGroupElement extends HTMLElement {

    boolean isDisabled();

    void setDisabled(boolean disabled);

    /**
     * Retrieves a reference to the form that the object is embedded in.
     */
    
      HTMLFormElement getForm();

    /**
     * Sets or retrieves a value that you can use to implement your own label functionality for the object.
     */
    
    String getLabel();

    
    void setLabel(String label);

}
