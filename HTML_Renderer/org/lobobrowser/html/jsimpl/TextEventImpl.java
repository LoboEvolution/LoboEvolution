package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.TextEvent;
import org.w3c.dom.views.AbstractView;

public class TextEventImpl extends UIEventImpl implements TextEvent {

	private String data;

	public TextEventImpl() {
	}

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
	public void initTextEvent(String type, boolean canBubble,
			boolean cancelable, AbstractView view, String data) {
		
		if (data == null)
			throw new NullPointerException();
		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);
		setView(view);
		this.data = data;
	}

	@Override
	public String getData() {
		return data;
	}
}
