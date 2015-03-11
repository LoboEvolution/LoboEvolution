package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.CompositionEvent;
import org.w3c.dom.views.AbstractView;


/**
 * The Class CompositionEventImpl.
 */
public class CompositionEventImpl extends UIEventImpl implements CompositionEvent {
	
	/** The data. */
	private String data;
	
	/**
	 * Instantiates a new composition event impl.
	 */
	public CompositionEventImpl(){}
	
	/**
	 * Instantiates a new composition event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 */
	public CompositionEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	/**
	 * Instantiates a new composition event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 * @param mouseEvent the mouse event
	 * @param leafX the leaf x
	 * @param leafY the leaf y
	 */
	public CompositionEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	/**
	 * Instantiates a new composition event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 * @param keyEvent the key event
	 */
	public CompositionEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}
	
	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.CompositionEvent#initCompositionEvent(java.lang.String, boolean, boolean, org.w3c.dom.views.AbstractView, java.lang.String)
	 */
	@Override
	public void initCompositionEvent(String type, boolean cancelBubble,
			boolean cancelable, AbstractView view, String data) {
		setType(type);
		setCanBubble(cancelBubble);
		setCancelable(cancelable);
		setView(view);
		this.data = data;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.CompositionEvent#getData()
	 */
	@Override
	public String getData() {
		return data;
	}
}
