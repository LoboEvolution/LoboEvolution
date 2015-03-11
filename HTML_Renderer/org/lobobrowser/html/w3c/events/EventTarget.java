package org.lobobrowser.html.w3c.events;

import org.lobobrowser.html.jsimpl.EventException;
import org.w3c.dom.DOMException;


/**
 * The Interface EventTarget.
 */
public interface EventTarget {
	// EventTarget
	/**
	 * Adds the event listener.
	 *
	 * @param type the type
	 * @param listener the listener
	 * @param useCapture the use capture
	 */
	public void addEventListener(String type, EventListener listener,
			boolean useCapture);

	/**
	 * Removes the event listener.
	 *
	 * @param type the type
	 * @param listener the listener
	 * @param useCapture the use capture
	 */
	public void removeEventListener(String type, EventListener listener,
			boolean useCapture);

	/**
	 * Dispatch event.
	 *
	 * @param evt the evt
	 * @return true, if successful
	 * @throws EventException the event exception
	 * @throws DOMException the DOM exception
	 */
	public boolean dispatchEvent(Event evt) throws EventException, DOMException;
}
