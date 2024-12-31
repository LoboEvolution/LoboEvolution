/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * An overlay is a layer on top of an image. It can be used to add annotations,
 * arbitrary shapes to an image. Drawing is implemented in the
 * paint method.
 * <p>
 * The following is an implementation of a simple example overlay that draws a
 * cross over the image:
 *
 * <pre>
 *
 * class XPainter extends Overlay {
 * 	public void paint(Graphics2D g, BufferedImage image, AffineTransform transform) {
 * 		g.setColor(Color.RED);
 * 		double[] bounds = { 0, 0, image.getWidth(), 0, image.getWidth(), image.getHeight(), 0, image.getHeight() };
 * 		transform.transform(bounds, 0, bounds, 0, 4);
 * 		g.drawLine((int) bounds[0], (int) bounds[1], (int) bounds[4], (int) bounds[5]);
 * 		g.drawLine((int) bounds[2], (int) bounds[3], (int) bounds[6], (int) bounds[7]);
 * 	}
 * }
 * </pre>
 *
 * It can be added to a viewer by calling
 * viewer.addOverlay(new XPainter(), 10).
 *
 * Author Kazo Csaba
 *
 */
public abstract class Overlay {
	private final List<OverlayComponent> overlayComponents = new ArrayList<>(1);

	/**
	 * <p>addOverlayComponent.</p>
	 *
	 * @param c a {@link org.loboevolution.img.OverlayComponent} object.
	 */
	public final void addOverlayComponent(final OverlayComponent c) {
		overlayComponents.add(c);
	}

	/**
	 * <p>removeOverlayComponent.</p>
	 *
	 * @param c a {@link org.loboevolution.img.OverlayComponent} object.
	 */
	public final void removeOverlayComponent(final OverlayComponent c) {
		overlayComponents.remove(c);
	}

	/**
	 * Causes the overlay to be repainted.
	 */
	public void repaint() {
		for (final OverlayComponent overlayComponent : overlayComponents)
			overlayComponent.repaint();
	}

	/**
	 * Called to paint the contents of this overlay. The graphics context to paint
	 * on is a copy for this overlay and can be freely modified.
	 * <p>
	 * The method receives the currently displayed image. The image is never
	 * null - if there is currently no image being displayed in the
	 * image viewer, then the paint method is not called.
	 * <p>
	 * This method also receives the transformation that is applied to the image
	 * before it is displayed. This transformation is most commonly the
	 * concatenation of a uniform scale and a translation. The original image bounds
	 * (0, 0) - (image.getWidth(), image.getHeight()) are mapped using this
	 * transformation to get the final display bounds. The overlay should not rely
	 * on whether painting outside these final bounds will be visible or not.
	 *
	 * @param g
	 *            the graphics context to draw onto
	 * @param image
	 *            the current image
	 * @param transform
	 *            the transformation applied to the image before displaying
	 */
	public abstract void paint(Graphics2D g, BufferedImage image, AffineTransform transform);
}
