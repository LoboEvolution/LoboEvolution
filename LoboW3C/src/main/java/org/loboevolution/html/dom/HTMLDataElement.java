package org.loboevolution.html.dom;




/**
 * Provides special properties (beyond the regular HTMLElement interface it also has available to it by inheritance) for manipulating &lt;data&gt; elements.
 */
public interface HTMLDataElement extends HTMLElement {


    
    String getValue();

    
    void setValue(String value);

}
