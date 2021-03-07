package org.loboevolution.html.dom;




/**
 * Provides special properties and methods (beyond the regular HTMLElement interface it also has available to it by inheritance) for manipulating quoting elements, like &lt;blockquote&gt; and &lt;q&gt;, but not the &lt;cite&gt; element.
 */
public interface HTMLQuoteElement extends HTMLElement {

    /**
     * Sets or retrieves reference information about the object.
     */
    
    String getCite();

    
    void setCite(String cite);

}
