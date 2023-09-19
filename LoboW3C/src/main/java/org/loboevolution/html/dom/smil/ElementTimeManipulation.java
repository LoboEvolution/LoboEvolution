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

package org.loboevolution.html.dom.smil;

import org.htmlunit.cssparser.dom.DOMException;

/**
 * This interface support use-cases commonly associated with animation.
 * "accelerate" and "decelerate" are float values in the timing draft and
 * percentage values even in this draft if both of them represent a percentage.
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
     * @throws DOMException if any.
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
     * @throws DOMException if any.
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
     * @throws DOMException if any.
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
     * @throws DOMException if any.
     */
    void setAutoReverse(boolean autoReverse) throws DOMException;

}
