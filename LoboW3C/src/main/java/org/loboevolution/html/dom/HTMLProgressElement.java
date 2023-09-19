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
 * Provides special properties and methods (beyond the regular HTMLElement interface it also has available to it by inheritance) for manipulating the layout and presentation of &lt;progress&gt; elements.
 *
 *
 *
 */
public interface HTMLProgressElement extends HTMLElement {

    
    /**
     * <p>getLabels.</p>
     *
     * @return a {@link org.loboevolution.html.node.NodeList} object.
     */
    NodeList getLabels();

    /**
     * Defines the maximum, or "done" value for a progress element.
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
     * Returns the quotient of value/max when the value attribute is set (determinate progress bar), or -1 when the value attribute is missing (indeterminate progress bar).
     *
     * @return a double.
     */
    double getPosition();

    /**
     * Sets or gets the current value of a progress element. The value must be a non-negative number between 0 and the max value.
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
