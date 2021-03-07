package org.loboevolution.html.dom;




/**
 * Provides special properties and methods (beyond those of the regular object HTMLElement interface it also has available to it by inheritance) for manipulating the layout and presentation of map elements.
 */
public interface HTMLMapElement extends HTMLElement {
 

    /**
     * Retrieves a collection of the area objects defined for the given map object.
     */
    
    HTMLCollection getAreas();

    /**
     * Sets or retrieves the name of the object.
     */
    
    String getName();

    
    void setName(String name);

}
