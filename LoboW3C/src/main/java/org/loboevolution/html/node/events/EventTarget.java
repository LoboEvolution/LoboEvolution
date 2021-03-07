package org.loboevolution.html.node.events;

import org.loboevolution.html.node.Node;
import org.mozilla.javascript.Function;

public interface EventTarget {

	void addEventListener(String type, final Function listener);
	
	void addEventListener(String type, Function listener, boolean useCapture);
	
	void removeEventListener(String script, Function function);
	
	void removeEventListener(String type, Function listener, boolean useCapture);
	
	boolean dispatchEvent(Node element, Event evt);

}
