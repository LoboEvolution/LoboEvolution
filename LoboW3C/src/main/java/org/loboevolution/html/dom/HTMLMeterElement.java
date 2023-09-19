/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom;


import org.loboevolution.html.node.NodeList;

/**
 * The HTML &lt;meter&gt; elements expose the HTMLMeterElement interface, which provides special properties and methods (beyond the HTMLElement object interface they also have available to them by inheritance) for manipulating the layout and presentation of &lt;meter&gt; elements.
 *
 *
 *
 */
public interface HTMLMeterElement extends HTMLElement {
     
    /**
     * <p>getHigh.</p>
     *
     * @return a double.
     */
    double getHigh();

    
    /**
     * <p>setHigh.</p>
     *
     * @param high a double.
     */
    void setHigh(double high);

    
    /**
     * <p>getLabels.</p>
     *
     * @return a {@link org.loboevolution.html.node.NodeList} object.
     */
    NodeList getLabels();

    
    /**
     * <p>getLow.</p>
     *
     * @return a double.
     */
    double getLow();

    
    /**
     * <p>setLow.</p>
     *
     * @param low a double.
     */
    void setLow(double low);

    
    /**
     * <p>getMax.</p>
     *
     * @return a double.
     */
    double getMax();

    
    /**
     * <p>setMax.</p>
     *
     * @param max a double.
     */
    void setMax(double max);

    
    /**
     * <p>getMin.</p>
     *
     * @return a double.
     */
    double getMin();

    
    /**
     * <p>setMin.</p>
     *
     * @param min a double.
     */
    void setMin(double min);

    
    /**
     * <p>getOptimum.</p>
     *
     * @return a double.
     */
    double getOptimum();

    
    /**
     * <p>setOptimum.</p>
     *
     * @param optimum a double.
     */
    void setOptimum(double optimum);

    
    /**
     * <p>getValue.</p>
     *
     * @return a double.
     */
    double getValue();

    
    /**
     * <p>setValue.</p>
     *
     * @param value a double.
     */
    void setValue(double value);

}
