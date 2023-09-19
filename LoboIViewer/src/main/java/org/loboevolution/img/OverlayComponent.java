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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * The JComponent implementing an overlay.
 *
 * Author Kazo Csaba
 *
 */
public class OverlayComponent extends JComponent {
	
	private static final long serialVersionUID = 1L;
	private transient Overlay overlay;
	private transient ImageComponent theImage;

	/**
	 * <p>Constructor for OverlayComponent.</p>
	 *
	 * @param overlay a {@link org.loboevolution.img.Overlay} object.
	 * @param image a {@link org.loboevolution.img.ImageComponent} object.
	 */
	public OverlayComponent(Overlay overlay, ImageComponent image) {
		this.overlay = overlay;
		this.theImage = image;
	}

	/** {@inheritDoc} */
	@Override
	protected void paintComponent(Graphics g) {
		BufferedImage image = theImage.getImage();
		if (image != null) {
			Graphics2D gg = (Graphics2D) g.create();
			overlay.paint(gg, image, theImage.getImageTransform());
			gg.dispose();
		}
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * <p>Getter for the field overlay.</p>
	 *
	 * @return the overlay
	 */
	public Overlay getOverlay() {
		return overlay;
	}

	/**
	 * <p>Setter for the field overlay.</p>
	 *
	 * @param overlay the overlay to set
	 */
	public void setOverlay(Overlay overlay) {
		this.overlay = overlay;
	}

	/**
	 * <p>Getter for the field theImage.</p>
	 *
	 * @return the theImage
	 */
	public ImageComponent getTheImage() {
		return theImage;
	}

	/**
	 * <p>Setter for the field theImage.</p>
	 *
	 * @param theImage the theImage to set
	 */
	public void setTheImage(ImageComponent theImage) {
		this.theImage = theImage;
	}
}
