package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.EventTarget;
import org.lobobrowser.html.w3c.events.MouseEvent;
import org.w3c.dom.views.AbstractView;

public class MouseEventImpl extends UIEventImpl implements MouseEvent {

	public MouseEventImpl() {
	}

	public MouseEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	public MouseEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	public MouseEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	@Override
	public void initMouseEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, int detailArg,
			int screenXArg, int screenYArg, int clientXArg, int clientYArg,
			boolean ctrlKeyArg, boolean altKeyArg, boolean shiftKeyArg,
			boolean metaKeyArg, int buttonArg, EventTarget relatedTargetArg) {
		
		setType(typeArg);
		setCancelBubble(canBubbleArg);
		setCancelable(cancelableArg);
		setView(viewArg);
		setDetail(detailArg);
		setScreenX(screenXArg);
		setScreenY(screenYArg);
		setClientX(clientXArg);
		setClientY(clientYArg);
		setCtrlKey(ctrlKeyArg);
		setAltKey(altKeyArg);
		setShiftKey(shiftKeyArg);
		setMetaKey(metaKeyArg);
		setButton(buttonArg);
		setCurrentTarget(relatedTargetArg);

	}

	@Override
	public boolean getMetaKey() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EventTarget getRelatedTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getModifierState(String keyArg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPageX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPageY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOffsetX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOffsetY() {
		// TODO Auto-generated method stub
		return 0;
	}
}
