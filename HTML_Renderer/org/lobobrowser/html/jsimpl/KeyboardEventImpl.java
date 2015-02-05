package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.KeyboardEvent;
import org.w3c.dom.views.AbstractView;

public class KeyboardEventImpl extends UIEventImpl implements KeyboardEvent {
	
	public KeyboardEventImpl(){}
	
	public KeyboardEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	public KeyboardEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	public KeyboardEventImpl(String type, HTMLElement srcElement,
			KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	@Override
	public void initKeyboardEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, String keyArg,
			int locationArg, String modifiersListArg, boolean repeat) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLocation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getMetaKey() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getRepeat() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getModifierState(String keyArg) {
		// TODO Auto-generated method stub
		return false;
	}

}
