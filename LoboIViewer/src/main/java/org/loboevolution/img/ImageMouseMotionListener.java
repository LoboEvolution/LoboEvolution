package org.loboevolution.img;

import java.util.EventListener;

/**
 * Interface for receiving mouse motion events on an image.
 *
 * @author Kazo Csaba
 * @version $Id: $Id
 */
public interface ImageMouseMotionListener extends EventListener {
	/**
	 * Invoked when the mouse has entered a pixel of an image.
	 *
	 * @param e
	 *            the event object containing attributes of the event
	 */
	public void mouseMoved(ImageMouseEvent e);

	/**
	 * Invoked when the mouse has entered the area of an image.
	 *
	 * @param e
	 *            the event object containing attributes of the event
	 */
	public void mouseEntered(ImageMouseEvent e);

	/**
	 * Invoked when the mouse has left the area of an image.
	 *
	 * @param e
	 *            the event object containing attributes of the event
	 */
	public void mouseExited(ImageMouseEvent e);

	/**
	 * Invoked when the mouse is moved while a button is down. Note that the
	 * coordinates for the event can be outside the image bounds.
	 *
	 * @param e
	 *            the event object containing attributes of the event
	 */
	public void mouseDragged(ImageMouseEvent e);
}
