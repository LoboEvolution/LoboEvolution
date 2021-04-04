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
