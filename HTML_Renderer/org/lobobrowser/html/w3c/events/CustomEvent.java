package org.lobobrowser.html.w3c.events;

public interface CustomEvent extends Event {
	// CustomEvent
	public int getDetail();

	public void initCustomEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, int detailArg);
}
