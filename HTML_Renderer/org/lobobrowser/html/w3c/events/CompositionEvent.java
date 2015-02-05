package org.lobobrowser.html.w3c.events;

import org.w3c.dom.views.AbstractView;

public interface CompositionEvent extends UIEvent {
	// CompositionEvent
	public String getData();

	public void initCompositionEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, String dataArg);
}
