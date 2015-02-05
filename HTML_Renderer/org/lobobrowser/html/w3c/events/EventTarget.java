package org.lobobrowser.html.w3c.events;

import org.lobobrowser.html.jsimpl.EventException;
import org.w3c.dom.DOMException;

public interface EventTarget {
	// EventTarget
	public void addEventListener(String type, EventListener listener,
			boolean useCapture);

	public void removeEventListener(String type, EventListener listener,
			boolean useCapture);

	public boolean dispatchEvent(Event evt) throws EventException, DOMException;
}
