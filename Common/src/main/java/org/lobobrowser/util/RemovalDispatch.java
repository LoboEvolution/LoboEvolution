package org.lobobrowser.util;

import java.util.EventListener;
import java.util.EventObject;

public class RemovalDispatch extends EventDispatch2 {
	protected void dispatchEvent(EventListener listener, EventObject event) {
		((RemovalListener) listener).removed((RemovalEvent) event);
	}
}