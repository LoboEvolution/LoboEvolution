/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
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
 * @author utente
 * @version $Id: $Id
 */
public class PaintManager {
	private BufferedImage cachedImage = null;
	private boolean cachedImageChanged = false;
	private AffineTransform cachedTransform;
	private ImageComponent ic;
	
	/**
	 * <p>Constructor for PaintManager.</p>
	 *
	 * @param ic a {@link org.loboevolution.img.ImageComponent} object.
	 */
	public PaintManager(ImageComponent ic) {
		this.ic = ic; 
	}

	private void doPaint(Graphics2D gg, AffineTransform imageTransform) {
		gg.setColor(ic.getBackground());
		gg.fillRect(0, 0, ic.getWidth(), ic.getHeight());

		gg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		if (ic.isPixelatedZoom() && imageTransform.getScaleX() >= 1)
			gg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		else
			gg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, ic.getInterpolationType());

		gg.drawImage(ic.getImage(), imageTransform, ic);
	}

	private void ensureCachedValid(AffineTransform imageTransform) {
		boolean cacheValid;

		// create the image if necessary; if the existing one is sufficiently large, use
		// it
		if (cachedImage == null || cachedImage.getWidth() < ic.getWidth() || cachedImage.getHeight() < ic.getHeight()) {
			cachedImage = ic.getGraphicsConfiguration().createCompatibleImage(ic.getWidth(), ic.getHeight());
			cacheValid = false;
		} else {
			cacheValid = cachedTransform.equals(imageTransform) && !cachedImageChanged;
		}

		if (!cacheValid) {
			Graphics2D gg = cachedImage.createGraphics();
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
	public void paintComponent(Graphics g) {
		if (ic.getImage() == null) {
			Graphics2D gg = (Graphics2D) g.create();
			gg.setColor(ic.getBackground());
			gg.fillRect(0, 0, ic.getWidth(), ic.getHeight());
			gg.dispose();
			return;
		}

		AffineTransform imageTransform = ic.getImageTransform();

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
			Graphics2D gg = (Graphics2D) g.create();
			doPaint(gg, imageTransform);
			gg.dispose();
		}

	}
}
