package org.lobobrowser.util;

import java.util.EventListener;
import java.util.EventObject;


/**
 * The Class RemovalDispatch.
 */
public class RemovalDispatch extends EventDispatch2 {
	
	/* (non-Javadoc)
	 * @see org.lobobrowser.util.EventDispatch2#dispatchEvent(java.util.EventListener, java.util.EventObject)
	 */
	protected void dispatchEvent(EventListener listener, EventObject event) {
		((RemovalListener) listener).removed((RemovalEvent) event);
	}
}