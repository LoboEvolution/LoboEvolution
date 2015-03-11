package org.lobobrowser.html.w3c.events;


/**
 * The listener interface for receiving event events.
 * The class that is interested in processing a event
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addEventListener</code> method. When
 * the event event occurs, that object's appropriate
 * method is invoked.
 */
public interface EventListener {
	// EventListener
	/**
	 * Handle event.
	 *
	 * @param evt the evt
	 */
	public void handleEvent(Event evt);
}
