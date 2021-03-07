package org.loboevolution.html.node.events;

import java.util.List;

import org.loboevolution.jsenum.EventPhase;

/**
 * An event which takes place in the DOM.
 */
public interface Event {

	/**
	 * Returns true or false depending on how event was initialized. True if event
	 * goes through its target's ancestors in reverse tree order, and false
	 * otherwise.
	 */

	boolean isBubbles();

	boolean isCancelBubble();

	void setCancelBubble(boolean cancelBubble);

	/**
	 * Returns true or false depending on how event was initialized. Its return
	 * value does not always carry meaning, but true can indicate that part of the
	 * operation during which event was dispatched, can be canceled by invoking the
	 * preventDefault() method.
	 */

	boolean isCancelable();

	/**
	 * Returns true or false depending on how event was initialized. True if event
	 * invokes listeners past a ShadowRoot node that is the root of its target, and
	 * false otherwise.
	 */

	boolean isComposed();

	/**
	 * Returns the object whose event listener's callback is currently being
	 * invoked.
	 */

	
	EventTarget getCurrentTarget();

	/**
	 * Returns true if preventDefault() was invoked successfully to indicate
	 * cancelation, and false otherwise.
	 */

	boolean isDefaultPrevented();

	/**
	 * Returns the event's phase, which is one of NONE, CAPTURING_PHASE, AT_TARGET,
	 * and BUBBLING_PHASE.
	 */

	EventPhase getEventPhase();

	/**
	 * Returns true if event was dispatched by the user agent, and false otherwise.
	 */

	boolean isIsTrusted();

	boolean isReturnValue();

	void setReturnValue(boolean returnValue);

	@Deprecated

	
	EventTarget getSrcElement();

	/**
	 * Returns the object to which event is dispatched (its target).
	 */

	
	EventTarget getTarget();

	/**
	 * Returns the event's timestamp as the number of milliseconds measured relative
	 * to the time origin.
	 */

	double getTimeStamp();

	/**
	 * Returns the type of event, e.g. "click", "hashchange", or "submit".
	 */

	String getType();

	/**
	 * Returns the invocation target objects of event's path (objects on which
	 * listeners will be invoked), except for any nodes in shadow trees of which the
	 * shadow root's mode is "closed" that are not reachable from event's
	 * currentTarget.
	 */
	List<EventTarget> composedPath();

	void initEvent(String type, boolean bubbles, boolean cancelable);

	void initEvent(String type, boolean bubbles);

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
