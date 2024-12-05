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
 * The TransitionEvent interface represents events providing information related to transitions.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/TransitionEvent">TransitionEvent - MDN</a>
 * @see <a href="https://drafts.csswg.org/css-transitions/#interface-transitionevent"># interface-transitionevent</a>
 */
public interface TransitionEvent extends Event {
    /**
     * The TransitionEvent.elapsedTime read-only property is a float giving the amount of time the animation has been running, in seconds, when this event fired. This value is not affected by the transition-delay property.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/TransitionEvent/elapsedTime">TransitionEvent.elapsedTime - MDN</a>
     * @see <a href="https://drafts.csswg.org/css-transitions/#Events-TransitionEvent-elapsedTime">TransitionEvent.elapsedTime - CSS Transitions</a>
     */
    Double getElapsedTime();

    String getPropertyName();

    /**
     * The TransitionEvent.pseudoElement read-only property is a DOMString, starting with '::', containing the name of the pseudo-element the animation runs on. If the transition doesn't run on a pseudo-element but on the element, an empty string: ''.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/TransitionEvent/pseudoElement">TransitionEvent.pseudoElement - MDN</a>
     * @see <a href="https://drafts.csswg.org/css-transitions/#Events-TransitionEvent-pseudoElement">TransitionEvent.pseudoElement - CSS Transitions</a>
     */
    String getPseudoElement();
}
