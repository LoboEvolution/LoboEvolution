package org.loboevolution.img;

import java.util.EventListener;

/**
 * Interface for receiving mouse click events on an image.
 *
 * @author csaba
 * @version $Id: $Id
 */
public interface ImageMouseClickListener extends EventListener {
	/**
	 * Invoked when a mouse button has been clicked over a pixel of an image.
	 *
	 * @param e
	 *            the event object containing attributes of the event
	 */
    void mouseClicked(ImageMouseEvent e);
}
