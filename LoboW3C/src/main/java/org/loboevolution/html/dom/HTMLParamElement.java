package org.loboevolution.html.dom;


/**
 * Provides special properties (beyond those of the regular HTMLElement object interface it inherits) for manipulating &lt;param&gt; elements, representing a pair of a key and a value that acts as a parameter for an &lt;object&gt; element.
 */
public interface HTMLParamElement extends HTMLElement {

    /**
     * Sets or retrieves the name of an input parameter for an element.
     */
    
    String getName();

    
    void setName(String name);

    /**
     * Sets or retrieves the content type of the resource designated by the value attribute.
     */
    @Deprecated
    
    String getType();

    
    void setType(String type);

    /**
     * Sets or retrieves the value of an input parameter for an element.
     */
    
    String getValue();

    
    void setValue(String value);

    /**
     * Sets or retrieves the data type of the value attribute.
     */
    @Deprecated
    
    String getValueType();

    
    void setValueType(String valueType);

}
