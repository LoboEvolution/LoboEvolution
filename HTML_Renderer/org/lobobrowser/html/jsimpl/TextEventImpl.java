package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.TextEvent;
import org.w3c.dom.views.AbstractView;


/**
 * The Class TextEventImpl.
 */
public class TextEventImpl extends UIEventImpl implements TextEvent {

	/** The data. */
	private String data;

	/**
	 * Instantiates a new text event impl.
	 */
	public TextEventImpl() {
	}

	/**
	 * Instantiates a new text event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 */
	public TextEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	/**
	 * Instantiates a new text event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 * @param mouseEvent the mouse event
	 * @param leafX the leaf x
	 * @param leafY the leaf y
	 */
	public TextEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	/**
	 * Instantiates a new text event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 * @param keyEvent the key event
	 */
	public TextEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.TextEvent#initTextEvent(java.lang.String, boolean, boolean, org.w3c.dom.views.AbstractView, java.lang.String)
	 */
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

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.TextEvent#getData()
	 */
	@Override
	public String getData() {
		return data;
	}
}
