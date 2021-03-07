package org.loboevolution.html.dom;


import org.loboevolution.html.node.DOMTokenList;

/**
 * Provides special properties and methods (beyond those of the regular object HTMLElement interface it also has available to it by inheritance) for manipulating the layout and presentation of &lt;area&gt; elements.
 */
public interface HTMLAreaElement extends HTMLElement, HTMLHyperlinkElementUtils {


    /**
     * Sets or retrieves a text alternative to the graphic.
     */
    
    String getAlt();

    
    void setAlt(String alt);

    /**
     * Sets or retrieves the coordinates of the object.
     */
    
    String getCoords();

    
    void setCoords(String coords);

    
    String getDownload();

    
    void setDownload(String download);

    /**
     * Sets or gets whether clicks in this region cause action.
     */
    @Deprecated
    
    boolean isNoHref();

    
    void setNoHref(boolean noHref);

    
    String getPing();

    
    void setPing(String ping);

    
    String getReferrerPolicy();

    
    void setReferrerPolicy(String referrerPolicy);

    
    String getRel();

    
    void setRel(String rel);

    
    DOMTokenList getRelList();

    /**
     * Sets or retrieves the shape of the object.
     */
    
    String getShape();

    
    void setShape(String shape);

    /**
     * Sets or retrieves the window or frame at which to target content.
     */
    
    String getTarget();

    
    void setTarget(String target);

}
