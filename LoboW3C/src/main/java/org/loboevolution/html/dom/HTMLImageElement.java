package org.loboevolution.html.dom;

import org.loboevolution.jsenum.Decoding;

/**
 * Provides special properties and methods for manipulating &lt;img&gt; elements.
 */
public interface HTMLImageElement extends HTMLElement {
   
    /**
     * Sets or retrieves how the object is aligned with adjacent text.
     */
    @Deprecated
    
    String getAlign();

    @Deprecated
    
    void setAlign(String align);

    /**
     * Sets or retrieves a text alternative to the graphic.
     */
    
    String getAlt();

    
    void setAlt(String alt);

    /**
     * Specifies the properties of a border drawn around an object.
     */
    @Deprecated
    
    String getBorder();

    
    void setBorder(String border);

    /**
     * Retrieves whether the object is fully loaded.
     */
    
    boolean isComplete();

    
    
    String getCrossOrigin();

    
    void setCrossOrigin(String crossOrigin);

    
    String getCurrentSrc();

    
    Decoding getDecoding();

    
    void setDecoding(Decoding decoding);

    /**
     * Sets or retrieves the height of the object.
     */
    
    double getHeight();

    
    void setHeight(double height);

    /**
     * Sets or retrieves the width of the border to draw around the object.
     */
    @Deprecated
    
    double getHspace();

    
    void setHspace(double hspace);

    /**
     * Sets or retrieves whether the image is a server-side image map.
     */
    
    boolean isIsMap();

    
    void setIsMap(boolean isMap);

    /**
     * Sets or retrieves a Uniform Resource Identifier (URI) to a long description of the object.
     */
    @Deprecated
    
    String getLongDesc();

    
    void setLongDesc(String longDesc);

    @Deprecated
    
    String getLowsrc();

    
    void setLowsrc(String lowsrc);

    /**
     * Sets or retrieves the name of the object.
     */
    @Deprecated
    
    String getName();

    
    void setName(String name);

    /**
     * The original height of the image resource before sizing.
     */
    
    double getNaturalHeight();

    /**
     * The original width of the image resource before sizing.
     */
    
    double getNaturalWidth();

    
    String getReferrerPolicy();

    
    void setReferrerPolicy(String referrerPolicy);

    
    String getSizes();

    
    void setSizes(String sizes);

    /**
     * The address or URL of the a media resource that is to be considered.
     */
    
    String getSrc();

    
    void setSrc(String src);

    
    String getSrcset();

    
    void setSrcset(String srcset);

    /**
     * Sets or retrieves the URL, often with a bookmark extension (#name), to use as a client-side image map.
     */
    
    String getUseMap();

    
    void setUseMap(String useMap);

    /**
     * Sets or retrieves the vertical margin for the object.
     */
    @Deprecated
    
    double getVspace();

    
    void setVspace(double vspace);

    /**
     * Sets or retrieves the width of the object.
     */
    
    double getWidth();

    
    void setWidth(double width);

    
    double getX();

    
    double getY();

}
