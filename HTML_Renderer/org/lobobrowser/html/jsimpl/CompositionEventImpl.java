package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.CompositionEvent;
import org.w3c.dom.views.AbstractView;

public class CompositionEventImpl extends UIEventImpl implements CompositionEvent {
	
	private String data;
	
	public CompositionEventImpl(){}
	
	public CompositionEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	public CompositionEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	public CompositionEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}
	
	@Override
	public void initCompositionEvent(String type, boolean cancelBubble,
			boolean cancelable, AbstractView view, String data) {
		setType(type);
		setCanBubble(cancelBubble);
		setCancelable(cancelable);
		setView(view);
		this.data = data;
	}

	@Override
	public String getData() {
		return data;
	}
}
