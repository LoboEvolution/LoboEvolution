package org.loboevolution.html.dom;




/**
 * Provides special properties (beyond those of the regular HTMLElement interface it also has available to it by inheritance) for manipulating definition list (&lt;dl&gt;) elements.
 */
public interface HTMLDListElement extends HTMLElement {


    @Deprecated
    
    boolean isCompact();

    
    void setCompact(boolean compact);

}
