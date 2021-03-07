package org.loboevolution.html.dom;


import org.loboevolution.html.node.NodeList;

/**
 * The HTML &lt;meter&gt; elements expose the HTMLMeterElement interface, which provides special properties and methods (beyond the HTMLElement object interface they also have available to them by inheritance) for manipulating the layout and presentation of &lt;meter&gt; elements.
 */
public interface HTMLMeterElement extends HTMLElement {
     
    double getHigh();

    
    void setHigh(double high);

    
    NodeList getLabels();

    
    double getLow();

    
    void setLow(double low);

    
    double getMax();

    
    void setMax(double max);

    
    double getMin();

    
    void setMin(double min);

    
    double getOptimum();

    
    void setOptimum(double optimum);

    
    double getValue();

    
    void setValue(double value);

}
