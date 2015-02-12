package org.lobobrowser.html.w3c.events;

import org.lobobrowser.html.w3c.HTMLElement;
import org.w3c.dom.views.AbstractView;

public interface MouseEvent extends UIEvent {
	// MouseEvent
	public int getScreenX();

	public int getScreenY();

	public int getClientX();

	public int getClientY();

	public boolean getCtrlKey();

	public boolean getShiftKey();

	public boolean getAltKey();

	public boolean getMetaKey();

	public HTMLElement getRelatedTarget();

	public void initMouseEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, int detailArg,
			int screenXArg, int screenYArg, int clientXArg, int clientYArg,
			boolean ctrlKeyArg, boolean altKeyArg, boolean shiftKeyArg,
			boolean metaKeyArg, int buttonArg, EventTarget relatedTargetArg);

	public void initMouseEventNS(String namespaceURIArg, String typeArg,
			boolean canBubbleArg, boolean cancelableArg, AbstractView viewArg,
			int detailArg, int screenXArg, int screenYArg, int clientXArg,
			int clientYArg, boolean ctrlKeyArg, boolean altKeyArg,
			boolean shiftKeyArg, boolean metaKeyArg, int buttonArg,
			EventTarget relatedTargetArg);

	public boolean getModifierState(String keyArg);

	// MouseEvent-43
	public int getPageX();

	public int getPageY();

	public int getX();

	public int getY();

	public int getOffsetX();

	public int getOffsetY();
}
