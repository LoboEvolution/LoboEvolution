/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
