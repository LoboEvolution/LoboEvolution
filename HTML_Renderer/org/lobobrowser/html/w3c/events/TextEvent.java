package org.lobobrowser.html.w3c.events;

import org.w3c.dom.views.AbstractView;

public interface TextEvent extends UIEvent {
	
	public String getData();

	public void initTextEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, String dataArg);
}
