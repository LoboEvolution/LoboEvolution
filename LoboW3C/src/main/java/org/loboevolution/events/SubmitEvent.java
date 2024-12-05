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

import org.loboevolution.html.dom.HTMLElement;

/**
 * The SubmitEvent interface defines the object used to represent an HTML form's submit event. This event is fired at the &lt;form&gt; when the form's submit action is invoked.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/SubmitEvent">SubmitEvent - MDN</a>
 * @see <a href="https://html.spec.whatwg.org/multipage/form-control-infrastructure.html#the-submitevent-interface"># the-submitevent-interface</a>
 */
public interface SubmitEvent extends Event {

    /**
     * The read-only submitter property found on the SubmitEvent interface specifies the submit button
     * or other element that was invoked to cause the form to be submitted.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/SubmitEvent/submitter">SubmitEvent.submitter - MDN</a>
     * @see <a href="https://html.spec.whatwg.org/multipage/form-control-infrastructure.html#dom-submitevent-submitter">SubmitEvent.submitter - HTML Living Standard</a>
     */
    HTMLElement getSubmitter();
}
