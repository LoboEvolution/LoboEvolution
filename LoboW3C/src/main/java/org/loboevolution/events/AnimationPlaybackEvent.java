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
 * The AnimationPlaybackEvent interface of the Web Animations API represents animation events.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AnimationPlaybackEvent">AnimationPlaybackEvent - MDN</a>
 * @see <a href="https://drafts.csswg.org/web-animations-1/#the-animationplaybackevent-interface"># the-animationplaybackevent-interface</a>
 */
public interface AnimationPlaybackEvent extends Event {


    /**
     * The currentTime read-only property of the AnimationPlaybackEvent interface represents the current time of the animation that generated the event at the moment the event is queued. This will be unresolved if the animation was idle at the time the event was generated.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AnimationPlaybackEvent/currentTime">AnimationPlaybackEvent.currentTime - MDN</a>
     * @see <a href="https://drafts.csswg.org/web-animations-1/#dom-animationplaybackevent-currenttime">AnimationPlaybackEvent.currentTime - Web Animations</a>
     */
    Double currentTime();

    /**
     * The timelineTime read-only property of the AnimationPlaybackEvent interface represents the time value of the animation's timeline at the moment the event is queued. This will be unresolved if the animation was not associated with a timeline at the time the event was generated or if the associated timeline was inactive.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AnimationPlaybackEvent/timelineTime">AnimationPlaybackEvent.timelineTime - MDN</a>
     * @see <a href="https://drafts.csswg.org/web-animations-1/#dom-animationplaybackevent-timelinetime">AnimationPlaybackEvent.timelineTime - Web Animations</a>
     */
    Double timelineTime();
}
