package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.EventTarget;
import org.lobobrowser.html.w3c.events.FocusEvent;
import org.w3c.dom.views.AbstractView;


/**
 * The Class FocusEventImpl.
 */
public class FocusEventImpl extends UIEventImpl implements FocusEvent {

	/** The related target. */
	private EventTarget relatedTarget;

	/**
	 * Instantiates a new focus event impl.
	 */
	public FocusEventImpl() {
	}

	/**
	 * Instantiates a new focus event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 */
	public FocusEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	/**
	 * Instantiates a new focus event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 * @param mouseEvent the mouse event
	 * @param leafX the leaf x
	 * @param leafY the leaf y
	 */
	public FocusEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	/**
	 * Instantiates a new focus event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 * @param keyEvent the key event
	 */
	public FocusEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.FocusEvent#initFocusEvent(java.lang.String, boolean, boolean, org.w3c.dom.views.AbstractView, int, org.lobobrowser.html.w3c.events.EventTarget)
	 */
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

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.FocusEvent#getRelatedTarget()
	 */
	@Override
	public EventTarget getRelatedTarget() {
		return relatedTarget;
	}

}
