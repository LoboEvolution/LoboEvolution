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
 * The AnimationEvent interface represents events providing information related to animations.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AnimationEvent">AnimationEvent - MDN</a>
 * @see <a href="https://drafts.csswg.org/css-animations/#interface-animationevent"># interface-animationevent</a>
 */
public interface AnimationEvent extends Event {


    /**
     * The AnimationEvent.animationName read-only property is a DOMString containing the value of the animation-name CSS property associated with the transition.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AnimationEvent/animationName">AnimationEvent.animationName - MDN</a>
     * @see <a href="https://drafts.csswg.org/css-animations/#dom-animationevent-animationname">AnimationEvent.animationName - CSS Animations</a>
     */
    String getAnimationName();

    /**
     * The AnimationEvent.elapsedTime read-only property is a float giving the amount of time the animation has been running, in seconds, when this event fired, excluding any time the animation was paused. For an animationstart event, elapsedTime is 0.0 unless there was a negative value for animation-delay, in which case the event will be fired with elapsedTime containing (-1 * delay).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AnimationEvent/elapsedTime">AnimationEvent.elapsedTime - MDN</a>
     * @see <a href="https://drafts.csswg.org/css-animations/#dom-animationevent-elapsedtime">AnimationEvent.elapsedTime - CSS Animations</a>
     */
    double getElapsedTime();

    /**
     * The AnimationEvent.pseudoElement read-only property is a DOMString, starting with '::', containing the name of the pseudo-element the animation runs on. If the animation doesn't run on a pseudo-element but on the element, an empty string: ''.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AnimationEvent/pseudoElement">AnimationEvent.pseudoElement - MDN</a>
     * @see <a href="https://drafts.csswg.org/css-animations/#dom-animationevent-pseudoelement">AnimationEvent.pseudoElement - CSS Animations</a>
     */
    String getPseudoElement();
}
