/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Helper class that manages the actual painting.
 *
 *
 *
 */
public class PaintManager {
	private BufferedImage cachedImage = null;
	private boolean cachedImageChanged = false;
	private AffineTransform cachedTransform;
	private final ImageComponent ic;
	
	/**
	 * <p>Constructor for PaintManager.</p>
	 *
	 * @param ic a {@link org.loboevolution.img.ImageComponent} object.
	 */
	public PaintManager(final ImageComponent ic) {
		this.ic = ic; 
	}

	private void doPaint(final Graphics2D gg, final AffineTransform imageTransform) {
		gg.setColor(ic.getBackground());
		gg.fillRect(0, 0, ic.getWidth(), ic.getHeight());

		gg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		if (ic.isPixelatedZoom() && imageTransform.getScaleX() >= 1)
			gg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		else
			gg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, ic.getInterpolationType());

		gg.drawImage(ic.getImage(), imageTransform, ic);
	}

	private void ensureCachedValid(final AffineTransform imageTransform) {
		final boolean cacheValid;

		// create the image if necessary; if the existing one is sufficiently large, use
		// it
		if (cachedImage == null || cachedImage.getWidth() < ic.getWidth() || cachedImage.getHeight() < ic.getHeight()) {
			cachedImage = ic.getGraphicsConfiguration().createCompatibleImage(ic.getWidth(), ic.getHeight());
			cacheValid = false;
		} else {
			cacheValid = cachedTransform.equals(imageTransform) && !cachedImageChanged;
		}

		if (!cacheValid) {
			final Graphics2D gg = cachedImage.createGraphics();
			doPaint(gg, imageTransform);
			gg.dispose();
			cachedImageChanged = false;
			cachedTransform = new AffineTransform(imageTransform);
		}
	}

	/**
	 * Called when a property which affects how the component is painted changes.
	 * This invalidates the cache and causes it to be redrawn upon the next paint
	 * request.
	 */
	public void notifyChanged() {
		cachedImageChanged = true;
	}

	/**
	 * <p>paintComponent.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 */
	public void paintComponent(final Graphics g) {
		if (ic.getImage() == null) {
			final Graphics2D gg = (Graphics2D) g.create();
			gg.setColor(ic.getBackground());
			gg.fillRect(0, 0, ic.getWidth(), ic.getHeight());
			gg.dispose();
			return;
		}

		final AffineTransform imageTransform = ic.getImageTransform();

		if (imageTransform.getScaleX() < 1
				&& ic.getInterpolationType() != RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR) {
			/*
			 * We're shrinking the image; instead of letting the Graphics object do it every
			 * time, we do it and cache the result.
			 */
			ensureCachedValid(imageTransform);
			g.drawImage(cachedImage, 0, 0, ic);
		} else {
			// draw the image directly
			final Graphics2D gg = (Graphics2D) g.create();
			doPaint(gg, imageTransform);
			gg.dispose();
		}

	}
}
