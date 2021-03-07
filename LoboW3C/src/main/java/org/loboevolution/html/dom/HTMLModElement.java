package org.loboevolution.html.dom;


/**
 * Provides special properties (beyond the regular methods and properties available through the HTMLElement interface they also have available to them by inheritance) for manipulating modification elements, that is &lt;del&gt; and &lt;ins&gt;.
 */
public interface HTMLModElement extends HTMLElement {
 

    /**
     * Sets or retrieves reference information about the object.
     */
    
    String getCite();

    
    void setCite(String cite);

    /**
     * Sets or retrieves the date and time of a modification to the object.
     */
    
    String getDateTime();

    
    void setDateTime(String dateTime);

}
