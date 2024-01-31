/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import java.util.List;

/**
 * An event which takes place in the DOM.
 */
public interface Event {

    /**
     * The current event phase is the capturing phase.
     */
    short CAPTURING_PHASE = 1;

    /**
     * The event is currently being evaluated at the target
     * <code>EventTarget</code>.
     */
    short AT_TARGET = 2;

    /**
     * The current event phase is the bubbling phase.
     */
    short BUBBLING_PHASE = 3;

    /**
     * Returns true or false depending on how event was initialized. True if event
     * goes through its target's ancestors in reverse tree order, and false
     * otherwise.
     *
     * @return a boolean.
     */
    boolean isBubbles();

    /**
     * <p>isCancelBubble.</p>
     *
     * @return a boolean.
     */
    boolean isCancelBubble();

    /**
     * <p>setCancelBubble.</p>
     *
     * @param cancelBubble a boolean.
     */
    void setCancelBubble(boolean cancelBubble);

    /**
     * Returns true or false depending on how event was initialized. Its return
     * value does not always carry meaning, but true can indicate that part of the
     * operation during which event was dispatched, can be canceled by invoking the
     * preventDefault() method.
     *
     * @return a boolean.
     */
    boolean isCancelable();

    /**
     * Returns true or false depending on how event was initialized. True if event
     * invokes listeners past a ShadowRoot node that is the root of its target, and
     * false otherwise.
     *
     * @return a boolean.
     */
    boolean isComposed();

    /**
     * Returns the object whose event listener's callback is currently being
     * invoked.
     *
     * @return a {@link EventTarget} object.
     */
    EventTarget getCurrentTarget();

    /**
     * Returns true if preventDefault() was invoked successfully to indicate
     * cancelation, and false otherwise.
     *
     * @return a boolean.
     */
    boolean isDefaultPrevented();

    /**
     * @return the event's phase, which is one of NONE, CAPTURING_PHASE, AT_TARGET,
     * and BUBBLING_PHASE.
     */
    short getEventPhase();

    /**
     * Returns true if event was dispatched by the user agent, and false otherwise.
     *
     * @return a boolean.
     */
    boolean isIsTrusted();

    /**
     * <p>isReturnValue.</p>
     *
     * @return a boolean.
     */
    boolean isReturnValue();

    /**
     * <p>setReturnValue.</p>
     *
     * @param returnValue a boolean.
     */
    void setReturnValue(boolean returnValue);

    /**
     * <p>getSrcElement.</p>
     *
     * @return a {@link EventTarget} object.
     */
    @Deprecated
    EventTarget getSrcElement();

    /**
     * Returns the object to which event is dispatched (its target).
     *
     * @return a {@link EventTarget} object.
     */
    EventTarget getTarget();

    /**
     * Returns the event's timestamp as the number of milliseconds measured relative
     * to the time origin.
     *
     * @return a double.
     */
    double getTimeStamp();

    /**
     * Returns the type of event, e.g. "click", "hashchange", or "submit".
     *
     * @return a {@link java.lang.String} object.
     */
    String getType();

    /**
     * Returns the invocation target objects of event's path (objects on which
     * listeners will be invoked), except for any nodes in shadow trees of which the
     * shadow root's mode is "closed" that are not reachable from event's
     * currentTarget.
     *
     * @return a {@link java.util.List} object.
     */
    List<EventTarget> composedPath();

    /**
     * <p>initEvent.</p>
     *
     * @param type       a {@link java.lang.String} object.
     * @param bubbles    a boolean.
     * @param cancelable a boolean.
     */
    void initEvent(String type, boolean bubbles, boolean cancelable);

    /**
     * <p>initEvent.</p>
     *
     * @param type    a {@link java.lang.String} object.
     * @param bubbles a boolean.
     */
    void initEvent(String type, boolean bubbles);

    /**
     * <p>initEvent.</p>
     *
     * @param type a {@link java.lang.String} object.
     */
    void initEvent(String type);

    /**
     * If invoked when the cancelable attribute value is true, and while executing a
     * listener for the event with passive set to false, signals to the operation
     * that caused event to be dispatched that it needs to be canceled.
     */
    void preventDefault();

    /**
     * Invoking this method prevents event from reaching any registered event
     * listeners after the current one finishes running and, when dispatched in a
     * tree, also prevents event from reaching any other objects.
     */
    void stopImmediatePropagation();

    /**
     * When dispatched in a tree, invoking this method prevents event from reaching
     * any objects other than the current object.
     */
    void stopPropagation();

    interface EventInit {

        boolean isBubbles();

        void setBubbles(boolean bubbles);

        boolean isCancelable();

        void setCancelable(boolean cancelable);

        boolean isComposed();

        void setComposed(boolean composed);

    }
}
