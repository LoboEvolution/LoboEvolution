package org.lobobrowser.html.w3c.events;

import org.w3c.dom.views.AbstractView;

public interface UIEvent extends Event {
	// UIEvent
	public AbstractView getView();

	public int getDetail();

	public void initUIEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, int detailArg);
}
