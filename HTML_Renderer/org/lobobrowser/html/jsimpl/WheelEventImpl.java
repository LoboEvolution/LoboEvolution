package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.EventTarget;
import org.lobobrowser.html.w3c.events.WheelEvent;
import org.w3c.dom.views.AbstractView;

public class WheelEventImpl extends MouseEventImpl implements WheelEvent {

	private int deltaX;
	private int deltaY;
	private int deltaZ;
	private int deltaMode;

	public WheelEventImpl() {
	}

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
	public void initWheelEvent(String type, boolean canBubble,
			boolean cancelable, AbstractView view, int detail, int screenX,
			int screenY, int clientX, int clientY, short button,
			EventTarget relatedTarget, String modifiersList, int deltaX,
			int deltaY, int deltaZ, int deltaMode) {
		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);
		setView(view);
		setDetail(detail);
		setScreenX(screenX);
		setScreenY(screenY);
		setClientX(clientX);
		setClientY(clientY);
		setButton(button);
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.deltaZ = deltaZ;
		this.deltaMode = deltaMode;

	}

	@Override
	public int getDeltaX() {
		return deltaX;
	}

	@Override
	public int getDeltaY() {
		return deltaY;
	}

	@Override
	public int getDeltaZ() {
		return deltaZ;
	}

	@Override
	public int getDeltaMode() {
		return deltaMode;
	}
}
