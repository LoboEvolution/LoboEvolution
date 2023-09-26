/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.img;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.EventObject;

/**
 * An event indicating that a mouse action occured over an image.
 *
 * Author Kazo Csaba
 *
 */
public class ImageMouseEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private final transient BufferedImage image;
	private final int x;
    private final int y;
	private final MouseEvent orig;

	/**
	 * <p>Constructor for ImageMouseEvent.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param image a {@link java.awt.image.BufferedImage} object.
	 * @param x a int.
	 * @param y a int.
	 * @param orig a {@link java.awt.event.MouseEvent} object.
	 */
	public ImageMouseEvent(final Object source, final BufferedImage image, final int x, final int y, final MouseEvent orig) {
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
	 * occasionally be null, for example for the exit event fired when
	 * the image is set to null.
	 *
	 * @return the original event
	 */
	public MouseEvent getOriginalEvent() {
		return orig;
	}

}
