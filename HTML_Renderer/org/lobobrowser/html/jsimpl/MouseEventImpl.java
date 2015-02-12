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
	public void initMouseEvent(String type, boolean canBubble,
			boolean cancelable, AbstractView view, int detail,
			int screenX, int screenY, int clientX, int clientY,
			boolean ctrlKey, boolean altKey, boolean shiftKey,
			boolean metaKey, int button, EventTarget relatedTarget) {

		initMouseEventNS(null, type, canBubble, cancelable, view,
				detail, screenX, screenY, clientX, clientY,
				ctrlKey, altKey, shiftKey, metaKey, button,
				relatedTarget);

	}

	@Override
	public void initMouseEventNS(String namespaceURI, String type, boolean canBubble,
			boolean cancelable, AbstractView view, int detail,
			int screenX, int screenY, int clientX, int clientY,
			boolean ctrlKey, boolean altKey, boolean shiftKey,
			boolean metaKey, int button, EventTarget relatedTarget) {
		
		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);
		setView(view);
		setDetail(detail);
		setScreenX(screenX);
		setScreenY(screenY);
		setClientX(clientX);
		setClientY(clientY);
		setCtrlKey(ctrlKey);
		setAltKey(altKey);
		setShiftKey(shiftKey);
		setMetaKey(metaKey);
		setButton(button);
		setCurrentTarget(relatedTarget);

	}

	@Override
	public HTMLElement getRelatedTarget() {
		return this.getSrcElement();
	}

	@Override
	public boolean getModifierState(String key) {
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
