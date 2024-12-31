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
 * The InputEvent interface represents an event notifying the user of editable content changes.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/InputEvent">InputEvent - MDN</a>
 * @see <a href="https://w3c.github.io/input-events/#interface-InputEvent"># interface-InputEvent</a>
 * @see <a href="https://w3c.github.io/uievents/#interface-inputevent"># interface-inputevent</a>
 */
public interface InputEvent extends UIEvent {
    /**
     * The data read-only property of the InputEvent interface returns a DOMString with the inserted characters. This may be an empty string if the change doesn't insert text (such as when deleting characters, for example).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/InputEvent/data">InputEvent.data - MDN</a>
     * @see <a href="https://w3c.github.io/input-events/#dfn-data">data - Input Events Level 2</a>
     */
    String getData();

    /**
     * The inputType read-only property of the InputEvent interface returns the type of change made to editible content. Possible changes include for example inserting, deleting, and formatting text.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/InputEvent/inputType">InputEvent.inputType - MDN</a>
     * @see <a href="https://w3c.github.io/uievents/#dom-inputevent-inputtype">inputType - UI Events</a>
     */
    String getInputType();

    /**
     * The InputEvent.isComposing read-only property returns a Boolean value indicating if the event is fired after compositionstart and before compositionend.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/InputEvent/isComposing">InputEvent.isComposing - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-InputEvent-isComposing">InputEvent.isComposing - Document Object Model (DOM) Level 3 Events Specification</a>
     */

    Boolean getIsComposing();
}
