/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
 * The FocusEvent interface represents focus-related events, including focus, blur, focusin, and focusout.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/FocusEvent">FocusEvent - MDN</a>
 * @see <a href="https://w3c.github.io/uievents/#interface-focusevent"># interface-focusevent</a>
 */
public interface FocusEvent extends UIEvent {

    /**
     * The FocusEvent.relatedTarget read-only property is the secondary target, depending on the type of event:
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/FocusEvent/relatedTarget">FocusEvent.relatedTarget - MDN</a>
     * @see <a href="https://w3c.github.io/uievents/#idl-focusevent">FocusEvent.relatedTarget - UI Events</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-FocusEvent-relatedTarget">FocusEvent.relatedTarget - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    EventTarget getRelatedTarget();
}
