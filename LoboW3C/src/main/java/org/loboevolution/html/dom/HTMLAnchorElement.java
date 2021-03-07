package org.loboevolution.html.dom;


import org.loboevolution.html.node.DOMTokenList;

/**
 * Hyperlink elements and provides special properties and methods (beyond those of the regular HTMLElement object interface that they inherit from) for manipulating the layout and presentation of such elements.
 */
public interface HTMLAnchorElement extends HTMLElement, HTMLHyperlinkElementUtils {


    /**
     * Sets or retrieves the character set used to encode the object.
     */
    @Deprecated
    
    String getCharset();

    @Deprecated
    
    void setCharset(String charset);

    /**
     * Sets or retrieves the coordinates of the object.
     */
    @Deprecated
    
    String getCoords();

    @Deprecated
    
    void setCoords(String coords);

    
    String getDownload();

    
    void setDownload(String download);

    /**
     * Sets or retrieves the language code of the object.
     */
    
    String getHreflang();

    
    void setHreflang(String hreflang);

    /**
     * Sets or retrieves the shape of the object.
     */
    @Deprecated
    
    String getName();

    @Deprecated
    
    void setName(String name);

    
    String getPing();

    
    void setPing(String ping);

    
    String getReferrerPolicy();

    
    void setReferrerPolicy(String referrerPolicy);

    /**
     * Sets or retrieves the relationship between the object and the destination of the link.
     */
    
    String getRel();

    
    void setRel(String rel);

    
    DOMTokenList getRelList();

    /**
     * Sets or retrieves the relationship between the object and the destination of the link.
     */
    @Deprecated
    
    String getRev();

    @Deprecated
    
    void setRev(String rev);

    /**
     * Sets or retrieves the shape of the object.
     */
    @Deprecated
    
    String getShape();

    @Deprecated
    
    void setShape(String shape);

    /**
     * Sets or retrieves the window or frame at which to target content.
     */
    
    String getTarget();

    
    void setTarget(String target);

    /**
     * Retrieves or sets the text of the object as a string.
     */
    
    String getText();

    
    void setText(String text);

    
    String getType();

    
    void setType(String type);

}
