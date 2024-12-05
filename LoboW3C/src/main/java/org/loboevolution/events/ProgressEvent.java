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
 * The ProgressEvent interface represents events measuring progress of an underlying process,
 * like an HTTP request (for an XMLHttpRequest, or the loading of the underlying resource
 * of an &lt;img&gt;, &lt;audio&gt;, &lt;video&gt;, &lt;style&gt; or &lt;link&gt;).
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/ProgressEvent">ProgressEvent - MDN</a>
 * @see <a href="https://xhr.spec.whatwg.org/#interface-progressevent"># interface-progressevent</a>
 */
public interface ProgressEvent extends Event {

    /**
     * The ProgressEvent.lengthComputable read-only property is a Boolean flag indicating if the resource concerned by the ProgressEvent has a length that can be calculated.
     * If not, the ProgressEvent.total property has no significant value.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/ProgressEvent/lengthComputable">ProgressEvent.lengthComputable - MDN</a>
     * @see <a href="https://xhr.spec.whatwg.org/#dom-progressevent-lengthcomputable">ProgressEvent.lengthComputable - XMLHttpRequest</a>
     */
    boolean getLengthComputable();

    /**
     * The ProgressEvent.loaded read-only property is an integer representing the amount of work already performed by the underlying process.
     * The ratio of work done can be calculated with the property and ProgressEvent.total. When downloading a resource using HTTP,
     * this only represent the part of the content itself, not headers and other overhead.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/ProgressEvent/loaded">ProgressEvent.loaded - MDN</a>
     * @see <a href="https://xhr.spec.whatwg.org/#dom-progressevent-loaded">ProgressEvent.loaded - XMLHttpRequest</a>
     */
    Double getLoaded();

    /**
     * The ProgressEvent.total read-only property is an unsigned 64-bit integer value indicating the total size of the data being processed or transmitted.
     * In the case of an HTTP transmission, this is the size of the body of the message (the Content-Length), and does not include headers and other overhead.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/ProgressEvent/total">ProgressEvent.total - MDN</a>
     * @see <a href="https://xhr.spec.whatwg.org/#dom-progressevent-total">ProgressEvent.lengthComputable - XMLHttpRequest</a>
     */
    Double getTotal();
}
