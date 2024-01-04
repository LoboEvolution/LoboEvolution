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

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

/**
 * A component showing an image as well as an arbitrary number of overlays.
 *
 * Author Kazo Csaba
 *
 */
public class LayeredImageView {
	private final ImageComponent theImage;
	private final JLayeredPane layeredPane;

	/**
	 * <p>Constructor for LayeredImageView.</p>
	 *
	 * @param theImage a {@link org.loboevolution.img.ImageComponent} object.
	 */
	public LayeredImageView(final ImageComponent theImage) {
		this.theImage = theImage;
		layeredPane = new ScrollableLayeredPane(this);
		layeredPane.setLayout(new OverlayLayout(this));
		layeredPane.add(theImage, Integer.valueOf(0));
		layeredPane.setOpaque(true);
	}

	/**
	 * Returns the component for this layered view.
	 *
	 * @return the Swing component for this view
	 */
	public JComponent getComponent() {
		return layeredPane;
	}

	/**
	 * Adds an overlay as the specified layer.
	 *
	 * @param overlay
	 *            the overlay to add
	 * @param layer
	 *            the layer to add the overlay to; higher layers are on top of lower
	 *            layers; the image resides in layer 0
	 */
	public void addOverlay(final Overlay overlay, final int layer) {
		if (overlay == null)
			throw new NullPointerException();
		final OverlayComponent c = new OverlayComponent(overlay, getTheImage());
		overlay.addOverlayComponent(c);
		layeredPane.add(c, Integer.valueOf(layer));
		layeredPane.revalidate();
		layeredPane.repaint();
	}

	/**
	 * Removes an overlay from the image viewer.
	 *
	 * @param overlay
	 *            the overlay to remove
	 * @throws java.lang.IllegalArgumentException
	 *             if the overlay is not in the image viewer
	 */
	public void removeOverlay(final Overlay overlay) {
		if (overlay == null)
			throw new NullPointerException();
		for (final Component c : layeredPane.getComponents()) {
			if (c instanceof OverlayComponent && ((OverlayComponent) c).getOverlay() == overlay) {
				overlay.removeOverlayComponent((OverlayComponent) c);
				layeredPane.remove(c);
				layeredPane.revalidate();
				layeredPane.repaint();
				return;
			}
		}
		throw new IllegalArgumentException("Overlay not part of this viewer");
	}

	/**
	 * <p>Getter for the field theImage.</p>
	 *
	 * @return the theImage
	 */
	public ImageComponent getTheImage() {
		return theImage;
	}
}
