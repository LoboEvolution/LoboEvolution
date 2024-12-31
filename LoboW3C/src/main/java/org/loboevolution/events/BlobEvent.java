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

import org.loboevolution.html.dom.Blob;

/**
 * The BlobEvent interface represents events associated with a Blob. These blobs are typically, but not necessarily, associated with media content.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/BlobEvent">BlobEvent - MDN</a>
 * @see <a href="https://w3c.github.io/mediacapture-record/#blobevent-section"># blobevent-section</a>
 */
public interface BlobEvent extends Event {

    /**
     * The BlobEvent.data read-only property represents a Blob associated with the event.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/BlobEvent/data">BlobEvent.data - MDN</a>
     * @see <a href="https://w3c.github.io/mediacapture-record/#dom-blobevent-data">BlobEvent.data - MediaStream Recording</a>
     */
    Blob getData();

    /**
     * The timecode readonlyinline property of the BlobEvent interface a DOMHighResTimeStamp indicating the difference between the timestamp of the first chunk in data, and the timestamp of the first chunk in the first BlobEvent produced by this recorder.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/BlobEvent/timecode">BlobEvent.timecode - MDN</a>
     * @see <a href="https://w3c.github.io/mediacapture-record/#dom-blobevent-timecode">timecode - MediaStream Recording</a>
     */
    Double getTimecode();
}
