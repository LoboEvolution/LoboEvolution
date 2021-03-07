package org.loboevolution.html.dom;


import org.loboevolution.html.node.NodeList;

/**
 * Provides special properties and methods (beyond the regular HTMLElement interface it also has available to it by inheritance) for manipulating the layout and presentation of &lt;progress&gt; elements.
 */
public interface HTMLProgressElement extends HTMLElement {

    
    NodeList getLabels();

    /**
     * Defines the maximum, or "done" value for a progress element.
     */
    
    double getMax();

    
    void setMax(double max);

    /**
     * Returns the quotient of value/max when the value attribute is set (determinate progress bar), or -1 when the value attribute is missing (indeterminate progress bar).
     */
    
    double getPosition();

    /**
     * Sets or gets the current value of a progress element. The value must be a non-negative number between 0 and the max value.
     */
    
    double getValue();

    
    void setValue(double value);

}
