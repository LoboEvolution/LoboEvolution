package org.loboevolution.html.node.events;

/**
 * PopStateEvent is an event handler for the popstate event on the window.
 */
public interface PopStateEvent extends Event {

	Object getState();

	interface PopStateEventInit extends EventInit {

		Object getState();

		void setState(Object state);

	}
}
