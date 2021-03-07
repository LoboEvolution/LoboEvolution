package org.loboevolution.html.dom;


/**
 * Provides special properties (beyond the regular HTMLElement interface it also has available to it by inheritance) for manipulating &lt;time&gt; elements.
 */
public interface HTMLTimeElement extends HTMLElement {
   
    String getDateTime();

    
    void setDateTime(String dateTime);

}
