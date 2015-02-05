package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.EventTarget;
import org.lobobrowser.html.w3c.events.FocusEvent;
import org.w3c.dom.views.AbstractView;

public class FocusEventImpl extends UIEventImpl implements FocusEvent {
	
	public FocusEventImpl(){}
	
	public FocusEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	public FocusEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	public FocusEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	@Override
	public void initFocusEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, int detailArg,
			EventTarget relatedTargetArg) {
		// TODO Auto-generated method stub

	}

	@Override
	public EventTarget getRelatedTarget() {
		// TODO Auto-generated method stub
		return null;
	}

}
