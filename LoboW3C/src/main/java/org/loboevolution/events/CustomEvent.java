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
 * The CustomEvent interface represents events initialized by an application for any purpose.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CustomEvent">CustomEvent - MDN</a>
 * @see <a href="https://dom.spec.whatwg.org/#interface-customevent"># interface-customevent</a>
 */
public interface CustomEvent extends Event {

    /**
     * The detail readonly property of the CustomEvent interface returns any data passed when initializing the event.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CustomEvent/detail">CustomEvent.detail - MDN</a>
     * @see <a href="https://dom.spec.whatwg.org/#dom-customevent-detail">detail - DOM</a>
     */
    Object getDetail();

    /**
     * The CustomEvent.initCustomEvent() method initializes a CustomEvent object. If the event has already been dispatched, this method does nothing.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CustomEvent/initCustomEvent">CustomEvent.initCustomEvent - MDN</a>
     * @see <a href="https://dom.spec.whatwg.org/#dom-customevent-initcustomevent">CustomEvent - DOM</a>
     */
    void initCustomEvent(String type, Boolean bubbles, boolean cancelable,
                         Object detail);

    /**
     * The CustomEvent.initCustomEvent() method initializes a CustomEvent object. If the event has already been dispatched, this method does nothing.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CustomEvent/initCustomEvent">CustomEvent.initCustomEvent - MDN</a>
     * @see <a href="https://dom.spec.whatwg.org/#dom-customevent-initcustomevent">CustomEvent - DOM</a>
     */
    void initCustomEvent(String type, Boolean bubbles, boolean cancelable);

    /**
     * The CustomEvent.initCustomEvent() method initializes a CustomEvent object. If the event has already been dispatched, this method does nothing.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CustomEvent/initCustomEvent">CustomEvent.initCustomEvent - MDN</a>
     * @see <a href="https://dom.spec.whatwg.org/#dom-customevent-initcustomevent">CustomEvent - DOM</a>
     */
    void initCustomEvent(String type, Boolean bubbles);

    /**
     * The CustomEvent.initCustomEvent() method initializes a CustomEvent object. If the event has already been dispatched, this method does nothing.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CustomEvent/initCustomEvent">CustomEvent.initCustomEvent - MDN</a>
     * @see <a href="https://dom.spec.whatwg.org/#dom-customevent-initcustomevent">CustomEvent - DOM</a>
     */
    void initCustomEvent(String type);
}
