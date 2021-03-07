package org.loboevolution.html.dom;




/**
 * Exposes specific properties and methods (beyond those defined by regular HTMLElement interface it also has available to it by inheritance) for manipulating list elements.
 */
public interface HTMLLIElement extends HTMLElement {
   

    @Deprecated
    
    String getType();

    
    void setType(String type);

    /**
     * Sets or retrieves the value of a list item.
     */
    
    int getValue();

    
    void setValue(int value);

}
