package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.CustomEvent;

public class CustomEventImpl extends EventImpl implements CustomEvent {
	
	public CustomEventImpl(){}
	
	public CustomEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	public CustomEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	public CustomEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}
	
	@Override
	public void initCustomEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, int detailArg) {
		setType(typeArg);
		setCancelBubble(canBubbleArg);
		setCancelable(cancelableArg);
		setDetail(detailArg);
	}
}
