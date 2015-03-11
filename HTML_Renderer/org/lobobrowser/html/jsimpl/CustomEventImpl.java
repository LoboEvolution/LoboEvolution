package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.CustomEvent;


/**
 * The Class CustomEventImpl.
 */
public class CustomEventImpl extends EventImpl implements CustomEvent {
	
	/**
	 * Instantiates a new custom event impl.
	 */
	public CustomEventImpl(){}
	
	/**
	 * Instantiates a new custom event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 */
	public CustomEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	/**
	 * Instantiates a new custom event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 * @param mouseEvent the mouse event
	 * @param leafX the leaf x
	 * @param leafY the leaf y
	 */
	public CustomEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	/**
	 * Instantiates a new custom event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 * @param keyEvent the key event
	 */
	public CustomEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}
	
	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.CustomEvent#initCustomEvent(java.lang.String, boolean, boolean, int)
	 */
	@Override
	public void initCustomEvent(String type, boolean canBubble,
			boolean cancelable, int detail) {
		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);
		setDetail(detail);
	}
}
