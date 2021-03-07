package org.loboevolution.html.dom;

/**
 * Provides special properties (beyond the regular HTMLElement object interface it also has available to it by inheritance) for manipulating &lt;source&gt; elements.
 */
public interface HTMLSourceElement extends HTMLElement {

      /**
     * Gets or sets the intended media type of the media source.
     */
    
    String getMedia();

    
    void setMedia(String media);

    
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
     * Gets or sets the MIME type of a media resource.
     */
    
    String getType();

    
    void setType(String type);

}
