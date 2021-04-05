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

package org.loboevolution.html.dom.smil;

import org.loboevolution.html.dom.nodeimpl.DOMException;

/**
 * This interface support use-cases commonly associated with animation.
 * "accelerate" and "decelerate" are float values in the timing draft and
 * percentage values even in this draft if both of them represent a percentage.
 *
 *
 *
 */
public interface ElementTimeManipulation {
    /**
     * Defines the playback speed of element time. The value is specified as a
     * multiple of normal (parent time container) play speed. Legal values are
     * signed floating point values. Zero values are not allowed. The default is
     * 1.0 (no modification of speed).
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a float.
     */
    float getSpeed();

    /**
     * <p>setSpeed.</p>
     *
     * @param speed a float.
     * @throws org.w3c.dom.DOMException if any.
     * @throws org.loboevolution.html.dom.nodeimpl.DOMException if any.
     */
    void setSpeed(float speed) throws DOMException;

    /**
     * The percentage value of the simple acceleration of time for the element.
     * Allowed values are from 0 to 100 . Default
     * value is 0 (no acceleration). <br>
     * The sum of the values for accelerate and decelerate must not exceed 100.
     * If it does, the deceleration value will be reduced to make the sum legal.
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a float.
     */
    float getAccelerate();

    /**
     * <p>setAccelerate.</p>
     *
     * @param accelerate a float.
     * @throws org.w3c.dom.DOMException if any.
     * @throws org.loboevolution.html.dom.nodeimpl.DOMException if any.
     */
    void setAccelerate(float accelerate) throws DOMException;

    /**
     * The percentage value of the simple decelerate of time for the element.
     * Allowed values are from 0 to 100 . Default
     * value is 0 (no deceleration). <br>
     * The sum of the values for accelerate and decelerate must not exceed 100.
     * If it does, the deceleration value will be reduced to make the sum legal.
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a float.
     */
    float getDecelerate();

    /**
     * <p>setDecelerate.</p>
     *
     * @param decelerate a float.
     * @throws org.w3c.dom.DOMException if any.
     * @throws org.loboevolution.html.dom.nodeimpl.DOMException if any.
     */
    void setDecelerate(float decelerate) throws DOMException;

    /**
     * The autoReverse attribute controls the "play forwards then backwards"
     * functionality. Default value is false .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a boolean.
     */
    boolean getAutoReverse();

    /**
     * <p>setAutoReverse.</p>
     *
     * @param autoReverse a boolean.
     * @throws org.w3c.dom.DOMException if any.
     * @throws org.loboevolution.html.dom.nodeimpl.DOMException if any.
     */
    void setAutoReverse(boolean autoReverse) throws DOMException;

}
