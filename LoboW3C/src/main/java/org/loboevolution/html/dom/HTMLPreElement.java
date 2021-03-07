package org.loboevolution.html.dom;

/**
 * Exposes specific properties and methods (beyond those of the HTMLElement interface it also has available to it by inheritance) for manipulating a block of preformatted text (&lt;pre&gt;).
 */
public interface HTMLPreElement extends HTMLElement {

     /**
     * Sets or gets a value that you can use to implement your own width functionality for the object.
     */
    @Deprecated
    
    double getWidth();

    
    void setWidth(double width);

}
