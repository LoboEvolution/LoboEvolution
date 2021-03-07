package org.loboevolution.html.dom;







/**
 * &lt;option&gt; elements and inherits all classes and methods of the HTMLElement interface.
 */
public interface HTMLOptionElement extends HTMLElement {

    /**
     * Sets or retrieves the status of an option.
     */
    
    boolean isDefaultSelected();

    
    void setDefaultSelected(boolean defaultSelected);

    
    boolean isDisabled();

    
    void setDisabled(boolean disabled);

    /**
     * Retrieves a reference to the form that the object is embedded in.
     */
    
    
    HTMLFormElement getForm();

    /**
     * Sets or retrieves the ordinal position of an option in a list box.
     */
    
    int getIndex();

    /**
     * Sets or retrieves a value that you can use to implement your own label functionality for the object.
     */
    
    String getLabel();

    
    void setLabel(String label);

    /**
     * Sets or retrieves whether the option in the list box is the default item.
     */
    
    boolean isSelected();

    
    void setSelected(boolean selected);

    /**
     * Sets or retrieves the text string specified by the option tag.
     */
    
    String getText();

    
    void setText(String text);

    /**
     * Sets or retrieves the value which is returned to the server when the form control is submitted.
     */
    
    String getValue();

    
    void setValue(String value);

}
