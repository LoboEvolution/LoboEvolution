package org.loboevolution.html.dom;


/**
 * Provides special properties (beyond the HTMLElement interface it also has available to it inheritance) for manipulating single or grouped table column elements.
 */
public interface HTMLTableColElement extends HTMLElement {
 
    /**
     * Sets or retrieves the alignment of the object relative to the display or table.
     */
    @Deprecated
    
    String getAlign();

    
    void setAlign(String align);

    @Deprecated
    
    String getCh();

    
    void setCh(String ch);

    @Deprecated
    
    String getChOff();

    
    void setChOff(String chOff);

    /**
     * Sets or retrieves the number of columns in the group.
     */
    
    int getSpan();

    
    void setSpan(int span);

    @Deprecated
    
    String getVAlign();

    
    void setVAlign(String vAlign);

    /**
     * Sets or retrieves the width of the object.
     */
    @Deprecated
    
    String getWidth();

    
    void setWidth(String width);

}
