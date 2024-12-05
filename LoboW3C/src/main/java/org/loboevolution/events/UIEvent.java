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

import org.loboevolution.js.Window;

/**
 * The UIEvent interface represents simple user interface events.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/UIEvent">UIEvent - MDN</a>
 * @see <a href="https://w3c.github.io/uievents/#idl-uievent"># idl-uievent</a>
 */
public interface UIEvent extends Event {

    /**
     * The UIEvent.detail read-only property, when non-zero, provides the current (or next, depending on the event) click count.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/UIEvent/detail">UIEvent.detail - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-UIEvent-detail">UIEvent.detail - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-UIEvent-detail">UIEvent.detail - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    Double getDetail();

    /**
     * The UIEvent.view read-only property returns the WindowProxy object from which the event was generated. In browsers, this is the Window object the event happened in.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/UIEvent/view">UIEvent.view - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#interface-UIEvent">UIEvent - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-UIEvent">UIEvent - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    Window getView();

    Double getWhich();

    /**
     * The UIEvent.initUIEvent() method initializes a UI event once it's been created.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/UIEvent/initUIEvent">UIEvent.initUIEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-UIEvent-initUIEvent">UIEvent.initUIEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-UIEvent">UIEvent.initUIEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initUIEvent(String typeArg, Boolean bubblesArg, boolean cancelableArg,
                     Window viewArg, Double detailArg);

    /**
     * The UIEvent.initUIEvent() method initializes a UI event once it's been created.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/UIEvent/initUIEvent">UIEvent.initUIEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-UIEvent-initUIEvent">UIEvent.initUIEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-UIEvent">UIEvent.initUIEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initUIEvent(String typeArg, Boolean bubblesArg, boolean cancelableArg,
                     Window viewArg);

    /**
     * The UIEvent.initUIEvent() method initializes a UI event once it's been created.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/UIEvent/initUIEvent">UIEvent.initUIEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-UIEvent-initUIEvent">UIEvent.initUIEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-UIEvent">UIEvent.initUIEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initUIEvent(String typeArg, Boolean bubblesArg,
                     boolean cancelableArg);

    /**
     * The UIEvent.initUIEvent() method initializes a UI event once it's been created.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/UIEvent/initUIEvent">UIEvent.initUIEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-UIEvent-initUIEvent">UIEvent.initUIEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-UIEvent">UIEvent.initUIEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initUIEvent(String typeArg, Boolean bubblesArg);

    /**
     * The UIEvent.initUIEvent() method initializes a UI event once it's been created.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/UIEvent/initUIEvent">UIEvent.initUIEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-UIEvent-initUIEvent">UIEvent.initUIEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-UIEvent">UIEvent.initUIEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initUIEvent(String typeArg);
}
