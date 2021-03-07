package org.loboevolution.html.dom;

import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.js.WindowProxy;

public interface HTMLFrameElement extends HTMLElement {


    /**
     * Retrieves the document object of the page or frame.
     */
    @Deprecated
    
    
    Document getContentDocument();

    /**
     * Retrieves the object of the specified.
     */
    @Deprecated
    
    
    WindowProxy getContentWindow();

    /**
     * Sets or retrieves whether to display a border for the frame.
     */
    @Deprecated
    
    String getFrameBorder();

    
    void setFrameBorder(String frameBorder);

    /**
     * Sets or retrieves a URI to a long description of the object.
     */
    @Deprecated
    
    String getLongDesc();

    
    void setLongDesc(String longDesc);

    /**
     * Sets or retrieves the top and bottom margin heights before displaying the text in a frame.
     */
    @Deprecated
    
    String getMarginHeight();

    
    void setMarginHeight(String marginHeight);

    /**
     * Sets or retrieves the left and right margin widths before displaying the text in a frame.
     */
    @Deprecated
    
    String getMarginWidth();

    
    void setMarginWidth(String marginWidth);

    /**
     * Sets or retrieves the frame name.
     */
    @Deprecated
    
    String getName();

    
    void setName(String name);

    /**
     * Sets or retrieves whether the user can resize the frame.
     */
    @Deprecated
    
    boolean isNoResize();

    
    void setNoResize(boolean noResize);

    /**
     * Sets or retrieves whether the frame can be scrolled.
     */
    @Deprecated
    
    String getScrolling();

    
    void setScrolling(String scrolling);

    /**
     * Sets or retrieves a URL to be loaded by the object.
     */
    @Deprecated
    
    String getSrc();

    
    void setSrc(String src);

}
