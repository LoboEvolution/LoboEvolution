package org.loboevolution.html.dom;


import org.loboevolution.html.node.DOMTokenList;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;

/**
 * Provides properties and methods (beyond those inherited from HTMLElement) for manipulating the layout and presentation of &lt;output&gt; elements.
 */
public interface HTMLOutputElement extends HTMLElement {

    
    String getDefaultValue();

    
    void setDefaultValue(String defaultValue);

    
    
    HTMLFormElement getForm();

    
    DOMTokenList getHtmlFor();

    
    NodeList getLabels();

    
    String getName();

    
    void setName(String name);

    
    String getType();

    
    String getValidationMessage();

    
    ValidityState getValidity();

    
    String getValue();

    
    void setValue(String value);

    
    boolean isWillValidate();

    boolean checkValidity();

    boolean reportValidity();

    void setCustomValidity(String error);

}
