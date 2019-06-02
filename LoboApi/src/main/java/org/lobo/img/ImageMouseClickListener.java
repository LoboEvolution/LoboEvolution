package org.lobo.img;

import java.util.EventListener;

/**
 * Interface for receiving mouse click events on an image.
 * 
 * @author csaba
 */
public interface ImageMouseClickListener extends EventListener {
	/**
	 * Invoked when a mouse button has been clicked over a pixel of an image.
	 * 
	 * @param e
	 *            the event object containing attributes of the event
	 */
	public void mouseClicked(ImageMouseEvent e);
}
