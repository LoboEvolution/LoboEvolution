package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.EventTarget;
import org.lobobrowser.html.w3c.events.FocusEvent;
import org.w3c.dom.views.AbstractView;

public class FocusEventImpl extends UIEventImpl implements FocusEvent {

	private EventTarget relatedTarget;

	public FocusEventImpl() {
	}

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
	public void initFocusEvent(String type, boolean canBubble,
			boolean cancelable, AbstractView view, int detail,
			EventTarget relatedTarget) {
		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);
		setView(view);
		setDetail(detail);
		this.relatedTarget = relatedTarget;

	}

	@Override
	public EventTarget getRelatedTarget() {
		return relatedTarget;
	}

}
