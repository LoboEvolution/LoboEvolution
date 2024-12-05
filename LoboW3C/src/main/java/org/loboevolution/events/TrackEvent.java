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

import javax.sound.midi.Track;

/**
 * The TrackEvent interface, which is part of the HTML DOM specification, is used for events which represent changes to a set of available tracks on an HTML media element; these events are addtrack and removetrack.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/TrackEvent">TrackEvent - MDN</a>
 * @see <a href="https://html.spec.whatwg.org/multipage/media.html#the-trackevent-interface"># the-trackevent-interface</a>
 */
public interface TrackEvent extends Event {

    /**
     * The read-only track property of the TrackEvent interface specifies the media track object to which the event applies.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/TrackEvent/track">TrackEvent.track - MDN</a>
     * @see <a href="https://html.spec.whatwg.org/multipage/media.html#dom-trackevent-track">TrackEvent.track - HTML Living Standard</a>
     * @see <a href="https://www.w3.org/TR/html52/embedded-content-0.html#dom-trackevent-track">TrackEvent.track - HTML5</a>
     */
    Track getTack();
}
