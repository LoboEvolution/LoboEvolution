package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.TextEvent;
import org.w3c.dom.views.AbstractView;

public class TextEventImpl extends UIEventImpl implements TextEvent {
	
	public TextEventImpl(){}
	
	public TextEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	public TextEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	public TextEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	@Override
	public void initTextEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, String dataArg,
			int inputMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInputMode() {
		// TODO Auto-generated method stub
		return 0;
	}

}
