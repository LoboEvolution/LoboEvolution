package org.lobobrowser.html.w3c.events;

import org.w3c.dom.views.AbstractView;

public interface FocusEvent extends UIEvent {
	// FocusEvent
	public EventTarget getRelatedTarget();

	public void initFocusEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, int detailArg,
			EventTarget relatedTargetArg);
}
