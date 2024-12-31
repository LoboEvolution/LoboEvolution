/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.events;

/**
 * Represents a single contact point on a touch-sensitive device<br>
 * The contact point is commonly a finger or stylus and the device may be a touchscreen or trackpad.
 */
public interface Touch {


    /**
     * Returns a unique identifier for this touch object.<br>
     * A given touch point (say, by a finger) will have the same identifier for the duration of its movement around the surface.<br>
     * This lets you ensure that you're tracking the same touch all the time.
     *
     * @return a unique identifier for this touch object
     */
    double getIdentifier();

    /**
     * Returns the element on which the touch point started when it was first placed on the surface, even if the touch point has since moved outside the interactive area of that
     * element or even been removed from the document.
     *
     * @return the element on which the touch point started when it was first placed on the surface
     */
    EventTarget getTarget();

    /**
     * Returns the horizontal position of the touch on the client window of user's screen, excluding any scroll offset.
     *
     * @return the horizontal position of the touch on the client window of user's screen, excluding any scroll offset
     */
    double getClientX();

    /**
     * Returns the vertical position of the touch on the client window of the user's screen, excluding any scroll offset.
     *
     * @return the vertical position of the touch on the client window of the user's screen, excluding any scroll offset
     */
    double getClientY();

    /**
     * Returns the horizontal position of the touch on the user's screen.
     *
     * @return the horizontal position of the touch on the user's screen.
     */
    double getScreenX();

    /**
     * Returns the vertical position of the touch on the user's screen.
     *
     * @return the vertical position of the touch on the user's screen
     */
    double getScreenY();

    /**
     * Returns the horizontal position of the touch on the client window of user's screen, including any scroll offset.
     *
     * @return the horizontal position of the touch on the client window of user's screen, including any scroll offset
     */
    double getPageX();

    /**
     * Returns the horizontal position of the touch on the client window of user's screen, including any scroll offset.
     *
     * @return the horizontal position of the touch on the client window of user's screen, including any scroll offset
     */
    double getPageY();

    /**
     * Returns the X radius of the ellipse that most closely circumscribes the area of contact with the screen.<br>
     * The value is in pixels of the same scale as screenX.
     *
     * @return the X radius of the ellipse that most closely circumscribes the area of contact with the screen
     */
    double getRadiusX();

    /**
     * Returns the Y radius of the ellipse that most closely circumscribes the area of contact with the screen.<br>
     * The value is in pixels of the same scale as screenY.
     *
     * @return the Y radius of the ellipse that most closely circumscribes the area of contact with the screen
     */
    double getRadiusY();

    /**
     * Returns the angle (in degrees) that the ellipse described by radiusX and radiusY must be rotated, clockwise, to most accurately cover the area of contact between the user
     * and the surface.
     *
     * @return the angle (in degrees) that the ellipse described by radiusX and radiusY must be rotated, clockwise, to most accurately cover the area of contact between the user
     * and the surface
     */
    double getRotationAngle();

    /**
     * Returns the amount of pressure being applied to the surface by the user, as a double between 0.0 (no pressure) and 1.0 (maximum pressure).
     *
     * @return the amount of pressure being applied to the surface by the user
     */
    double getForce();

}
