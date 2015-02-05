package org.lobobrowser.html.w3c.events;

import org.w3c.dom.DOMException;

public interface DocumentEvent {
	// DocumentEvent
	public Event createEvent(String eventInterface) throws DOMException;
}
