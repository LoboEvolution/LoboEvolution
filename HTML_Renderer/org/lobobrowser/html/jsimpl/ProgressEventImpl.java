package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.ProgressEvent;


/**
 * The Class ProgressEventImpl.
 */
public class ProgressEventImpl extends EventImpl implements ProgressEvent {
	
	/** The loaded. */
	private int loaded;
	
	/** The total. */
	private int total;

	/**
	 * Instantiates a new progress event impl.
	 */
	public ProgressEventImpl(){}
	
	/**
	 * Instantiates a new progress event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 */
	public ProgressEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	/**
	 * Instantiates a new progress event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 * @param mouseEvent the mouse event
	 * @param leafX the leaf x
	 * @param leafY the leaf y
	 */
	public ProgressEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	/**
	 * Instantiates a new progress event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 * @param keyEvent the key event
	 */
	public ProgressEventImpl(String type, HTMLElement srcElement,
			KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.ProgressEvent#initProgressEvent(java.lang.String, boolean, boolean, boolean, int, int)
	 */
	@Override
	public void initProgressEvent(String type, boolean canBubble,
			boolean cancelable, boolean lengthComputable, int loaded,
			int total) {

		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);		
		total = (lengthComputable)? total: -1;
		loaded = (loaded >= 0)? loaded: 0;

	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.ProgressEvent#getLengthComputable()
	 */
	@Override
	public boolean getLengthComputable() {
		return (total >= 0);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.ProgressEvent#getLoaded()
	 */
	@Override
	public int getLoaded() {
		return loaded;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.ProgressEvent#getTotal()
	 */
	@Override
	public int getTotal() {
		return (total >= 0)? total: 0;
	}
}