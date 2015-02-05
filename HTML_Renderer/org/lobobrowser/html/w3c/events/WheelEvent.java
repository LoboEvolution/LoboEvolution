package org.lobobrowser.html.w3c.events;

import org.w3c.dom.views.AbstractView;

public interface WheelEvent extends MouseEvent {
	// WheelEvent
	public static final int DOM_DELTA_PIXEL = 0x00;
	public static final int DOM_DELTA_LINE = 0x01;
	public static final int DOM_DELTA_PAGE = 0x02;

	public int getDeltaX();

	public int getDeltaY();

	public int getDeltaZ();

	public int getDeltaMode();

	public void initWheelEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, int detailArg,
			int screenXArg, int screenYArg, int clientXArg, int clientYArg,
			short buttonArg, EventTarget relatedTargetArg,
			String modifiersListArg, int deltaXArg, int deltaYArg,
			int deltaZArg, int deltaMode);
}
