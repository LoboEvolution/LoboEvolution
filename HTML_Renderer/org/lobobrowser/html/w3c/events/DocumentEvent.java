package org.lobobrowser.html.w3c.events;

import org.w3c.dom.DOMException;


/**
 * The Interface DocumentEvent.
 */
public interface DocumentEvent {
	// DocumentEvent
	/**
	 * Creates the event.
	 *
	 * @param eventInterface the event interface
	 * @return the event
	 * @throws DOMException the DOM exception
	 */
	public Event createEvent(String eventInterface) throws DOMException;
}
