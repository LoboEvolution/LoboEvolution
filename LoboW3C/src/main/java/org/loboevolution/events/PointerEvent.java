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
 * The PointerEvent interface represents the state of a DOM event produced by a pointer such as the geometry of the contact point, the device type that generated the event, the amount of pressure that was applied on the contact surface, etc.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/PointerEvent">PointerEvent - MDN</a>
 * @see <a href="https://w3c.github.io/pointerevents/#pointerevent-interface"># pointerevent-interface</a>
 */
public interface PointerEvent extends MouseEvent {

    /**
     * The height read-only property of the PointerEvent interface represents the height of the pointer's contact geometry, along the y-axis (in CSS pixels). Depending on the source of the pointer device (for example a finger), for a given pointer, each event may produce a different value.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/PointerEvent/height">PointerEvent.height - MDN</a>
     * @see <a href="https://www.w3.org/TR/pointerevents2/#dom-pointerevent-height">height - Pointer Events &ndash; Level 2</a>
     * @see <a href="https://www.w3.org/TR/pointerevents1/#widl-PointerEvent-height">height - Pointer Events</a>
     */
    Double getHeight();

    /**
     * The isPrimary read-only property of the PointerEvent interface indicates whether or not the pointer device that created the event is the primary pointer. It returns true if the pointer that caused the event to be fired is the primary device and returns false otherwise.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/PointerEvent/isPrimary">PointerEvent.isPrimary - MDN</a>
     * @see <a href="https://www.w3.org/TR/pointerevents2/#dom-pointerevent-isprimary">isPrimary - Pointer Events &ndash; Level 2</a>
     * @see <a href="https://www.w3.org/TR/pointerevents1/#widl-PointerEvent-isPrimary">isPrimary - Pointer Events</a>
     */
    Boolean getIsPrimary();

    /**
     * The pointerId read-only property of the PointerEvent interface is an identifier assigned to a given pointer event. The identifier is unique, being different from the identifiers of all other active pointer events. Since the value may be randomly generated, it is not guaranteed to convey any particular meaning.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/PointerEvent/pointerId">PointerEvent.pointerId - MDN</a>
     * @see <a href="https://www.w3.org/TR/pointerevents2/#dom-pointerevent-pointerid">pointerId - Pointer Events &ndash; Level 2</a>
     * @see <a href="https://www.w3.org/TR/pointerevents1/#widl-PointerEventInit-pointerId">pointerId - Pointer Events</a>
     */
    Double getPointerId();

    /**
     * The pointerType read-only property of the PointerEvent interface indicates the device type (mouse, pen, or touch) that caused a given pointer event.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/PointerEvent/pointerType">PointerEvent.pointerType - MDN</a>
     * @see <a href="https://www.w3.org/TR/pointerevents1/#widl-PointerEventInit-pointerType">pointerType - Pointer Events</a>
     * @see <a href="https://www.w3.org/TR/pointerevents2/#dom-pointerevent-pointertype">pointerType - Pointer Events &ndash; Level 2</a>
     * @see <a href="https://drafts.csswg.org/cssom-view/#extensions-to-the-mouseevent-interface">MouseEvent - CSS Object Model (CSSOM) View Module</a>
     */
    String getPointerType();

    /**
     * The pressure read-only property of the PointerEvent interface indicates the normalized pressure of the pointer input.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/PointerEvent/pressure">PointerEvent.pressure - MDN</a>
     * @see <a href="https://www.w3.org/TR/pointerevents2/#dom-pointerevent-pressure">pressure - Pointer Events &ndash; Level 2</a>
     * @see <a href="https://www.w3.org/TR/pointerevents1/#widl-PointerEventInit-pressure">pressure - Pointer Events</a>
     */
    Double getPressure();

    /**
     * The tangentialPressure read-only property of the PointerEvent interface represents the normalized tangential pressure of the pointer input (also known as barrel pressure or cylinder stress).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/PointerEvent/tangentialPressure">PointerEvent.tangentialPressure - MDN</a>
     * @see <a href="https://www.w3.org/TR/pointerevents2/#dom-pointerevent-tangentialpressure">tangentialPressure - Pointer Events &ndash; Level 2</a>
     */
    float getTangentialPressure();

    /**
     * The tiltX read-only property of the PointerEvent interface is the angle (in degrees) between the Y-Z plane of the pointer and the screen. This property is typically only useful for a pen/stylus pointer type.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/PointerEvent/tiltX">PointerEvent.tiltX - MDN</a>
     * @see <a href="https://www.w3.org/TR/pointerevents2/#dom-pointerevent-tiltx">tiltX - Pointer Events &ndash; Level 2</a>
     * @see <a href="https://www.w3.org/TR/pointerevents1/#widl-PointerEventInit-tiltX">tiltX - Pointer Events</a>
     */
    Integer getTiltX();

    /**
     * The tiltY read-only property of the PointerEvent interface is the angle (in degrees) between the X-Z plane of the pointer and the screen. This property is typically only useful for a pen/stylus pointer type.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/PointerEvent/tiltY">PointerEvent.tiltY - MDN</a>
     * @see <a href="https://www.w3.org/TR/pointerevents2/#dom-pointerevent-tilty">tiltY - Pointer Events &ndash; Level 2</a>
     * @see <a href="https://www.w3.org/TR/pointerevents1/#widl-PointerEventInit-tiltY">tiltY - Pointer Events</a>
     */
    Integer getTiltY();

    /**
     * The twist read-only property of the PointerEvent interface represents the clockwise rotation of the pointer (e.g., pen stylus) around its major axis, in degrees.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/PointerEvent/twist">PointerEvent.twist - MDN</a>
     * @see <a href="https://www.w3.org/TR/pointerevents2/#dom-pointerevent-twist">twist - Pointer Events &ndash; Level 2</a>
     */
    int getTwist();

    /**
     * The width read-only property of the PointerEvent interface represents the width of the pointer's contact geometry along the x-axis, measured in CSS pixels. Depending on the source of the pointer device (such as a finger), for a given pointer, each event may produce a different value.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/PointerEvent/width">PointerEvent.width - MDN</a>
     * @see <a href="https://www.w3.org/TR/pointerevents2/#dom-pointerevent-width">width - Pointer Events &ndash; Level 2</a>
     * @see <a href="https://www.w3.org/TR/pointerevents1/#widl-PointerEvent-width">width - Pointer Events</a>
     */
    Double getWidth();

    Double getAltitudeAngle();

    Double getAzimuthAngle();

}
