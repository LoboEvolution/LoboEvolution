/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.node.events;

import java.util.List;

import org.loboevolution.jsenum.EventPhase;

/**
 * An event which takes place in the DOM.
 *
 *
 *
 */
public interface Event {

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
	 * @return a {@link org.loboevolution.html.node.events.EventTarget} object.
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
	 * Returns the event's phase, which is one of NONE, CAPTURING_PHASE, AT_TARGET,
	 * and BUBBLING_PHASE.
	 *
	 * @return a {@link org.loboevolution.jsenum.EventPhase} object.
	 */
	EventPhase getEventPhase();

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
	 * @return a {@link org.loboevolution.html.node.events.EventTarget} object.
	 */
	@Deprecated
	EventTarget getSrcElement();

	/**
	 * Returns the object to which event is dispatched (its target).
	 *
	 * @return a {@link org.loboevolution.html.node.events.EventTarget} object.
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
	 * @param type a {@link java.lang.String} object.
	 * @param bubbles a boolean.
	 * @param cancelable a boolean.
	 */
	void initEvent(String type, boolean bubbles, boolean cancelable);

	/**
	 * <p>initEvent.</p>
	 *
	 * @param type a {@link java.lang.String} object.
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
