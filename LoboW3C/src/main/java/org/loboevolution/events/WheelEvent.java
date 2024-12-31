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
 * The WheelEvent interface represents events that occur due to the user moving a mouse wheel or similar input device.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/WheelEvent">WheelEvent - MDN</a>
 * @see <a href="https://w3c.github.io/uievents/#interface-wheelevent"># interface-wheelevent</a>
 */
public interface WheelEvent extends MouseEvent {

    int DOM_DELTA_LINE = 0x01;

    int DOM_DELTA_PAGE = 0x02;

    int DOM_DELTA_PIXEL = 0x00;

    /**
     * The WheelEvent.deltaMode read-only property returns an unsigned long representing the unit of the delta values scroll amount. Permitted values are:
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/WheelEvent/deltaMode">WheelEvent.deltaMode - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-WheelEvent-deltaMode">WheelEvent.deltaMode - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    int deltaMode();

    /**
     * The WheelEvent.deltaX read-only property is a double representing the horizontal scroll amount in the WheelEvent.deltaMode unit.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/WheelEvent/deltaX">WheelEvent.deltaX - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-WheelEvent-deltaX">WheelEvent.deltaX - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    double deltaX();

    /**
     * The WheelEvent.deltaY read-only property is a double representing the vertical scroll amount in the WheelEvent.deltaMode unit.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/WheelEvent/deltaY">WheelEvent.deltaY - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-WheelEvent-deltaY">WheelEvent.deltaY - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    double deltaY();

    /**
     * The WheelEvent.deltaZ read-only property is a double representing the scroll amount along the z-axis, in the WheelEvent.deltaMode unit.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/WheelEvent/deltaZ">WheelEvent.deltaZ - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-WheelEvent-deltaZ">WheelEvent.deltaZ - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    double deltaZ();
}
