package org.loboevolution.html.dom;



/**
 * A &lt;style&gt; element. It inherits properties and methods from its parent, HTMLElement, and from LinkStyle.
 */
public interface HTMLStyleElement extends HTMLElement {


    /**
     * Sets or retrieves the media type.
     */
    
    String getMedia();

    
    void setMedia(String media);

    /**
     * Retrieves the CSS language in which the style sheet is written.
     */
    @Deprecated
    
    String getType();

    
    void setType(String type);


	void setDisabled(boolean disabled);


	boolean isDisabled();

}
