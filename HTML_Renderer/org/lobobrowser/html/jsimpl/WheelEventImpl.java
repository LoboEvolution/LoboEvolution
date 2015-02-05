package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.EventTarget;
import org.lobobrowser.html.w3c.events.WheelEvent;
import org.w3c.dom.views.AbstractView;

public class WheelEventImpl extends MouseEventImpl implements WheelEvent {
	
	public WheelEventImpl(){}
	
	public WheelEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	public WheelEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	public WheelEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	@Override
	public void initWheelEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, int detailArg,
			int screenXArg, int screenYArg, int clientXArg, int clientYArg,
			short buttonArg, EventTarget relatedTargetArg,
			String modifiersListArg, int deltaXArg, int deltaYArg,
			int deltaZArg, int deltaMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getDeltaX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDeltaY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDeltaZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDeltaMode() {
		// TODO Auto-generated method stub
		return 0;
	}
}
