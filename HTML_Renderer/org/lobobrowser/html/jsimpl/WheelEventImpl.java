package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.EventTarget;
import org.lobobrowser.html.w3c.events.WheelEvent;
import org.w3c.dom.views.AbstractView;


/**
 * The Class WheelEventImpl.
 */
public class WheelEventImpl extends MouseEventImpl implements WheelEvent {

	/** The delta x. */
	private int deltaX;
	
	/** The delta y. */
	private int deltaY;
	
	/** The delta z. */
	private int deltaZ;
	
	/** The delta mode. */
	private int deltaMode;

	/**
	 * Instantiates a new wheel event impl.
	 */
	public WheelEventImpl() {
	}

	/**
	 * Instantiates a new wheel event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 */
	public WheelEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	/**
	 * Instantiates a new wheel event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 * @param mouseEvent the mouse event
	 * @param leafX the leaf x
	 * @param leafY the leaf y
	 */
	public WheelEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	/**
	 * Instantiates a new wheel event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 * @param keyEvent the key event
	 */
	public WheelEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.WheelEvent#initWheelEvent(java.lang.String, boolean, boolean, org.w3c.dom.views.AbstractView, int, int, int, int, int, short, org.lobobrowser.html.w3c.events.EventTarget, java.lang.String, int, int, int, int)
	 */
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

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.WheelEvent#getDeltaX()
	 */
	@Override
	public int getDeltaX() {
		return deltaX;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.WheelEvent#getDeltaY()
	 */
	@Override
	public int getDeltaY() {
		return deltaY;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.WheelEvent#getDeltaZ()
	 */
	@Override
	public int getDeltaZ() {
		return deltaZ;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.WheelEvent#getDeltaMode()
	 */
	@Override
	public int getDeltaMode() {
		return deltaMode;
	}
}
