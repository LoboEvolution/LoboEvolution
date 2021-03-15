package org.loboevolution.html.dom;

import org.loboevolution.html.node.js.WindowEventHandlers;

/**
 * Provides special properties (beyond those of the regular HTMLElement interface they also inherit) for manipulating &lt;frameset&gt; elements.
 */
public interface HTMLFrameSetElement extends HTMLElement, WindowEventHandlers {


    /**
     * Sets or retrieves the frame widths of the object.
     */
    @Deprecated
    
    String getCols();

    
    void setCols(String cols);

    /**
     * Sets or retrieves the frame heights of the object.
     */
    @Deprecated
    
    String getRows();

    
    void setRows(String rows);

}
