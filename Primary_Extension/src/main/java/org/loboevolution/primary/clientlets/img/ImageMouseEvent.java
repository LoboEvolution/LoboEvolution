package org.loboevolution.primary.clientlets.img;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.EventObject;

/**
 * An event indicating that a mouse action occured over an image.
 * 
 * @author Kazó Csaba
 */
public class ImageMouseEvent extends EventObject {
	private BufferedImage image;
	private int x, y;
	private MouseEvent orig;

	public ImageMouseEvent(Object source, BufferedImage image, int x, int y, MouseEvent orig) {
		super(source);
		this.image = image;
		this.x = x;
		this.y = y;
		this.orig = orig;
	}

	/**
	 * Returns the image on which the event occured.
	 * 
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Returns the x coordinate of the pixel related to the event.
	 * 
	 * @return the x coordinate of the pixel related to the event
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y coordinate of the pixel related to the event.
	 * 
	 * @return the y coordinate of the pixel related to the event
	 */
	public int getY() {
		return y;
	}

	/**
	 * Returns the mouse event that caused this image mouse event. This can
	 * occasionally be <code>null</code>, for example for the exit event fired when
	 * the image is set to <code>null</code>.
	 * 
	 * @return the original event
	 */
	public MouseEvent getOriginalEvent() {
		return orig;
	}

}
