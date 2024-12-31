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

import java.awt.Color;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

import java.util.Objects;

/**
 * A status bar implementation that display information based on a pixel of the
 * image. Call the {@link #setPixel(int, int)} function to specify which pixel
 * it should display. The current implementation shows the position and color of
 * the pixel; override
 * {@link #updateLabel(java.awt.image.BufferedImage, int, int, int)} to
 * customize this information.
 *
 * Author Kazo Csaba
 *
 */
public class PixelInfoStatusBar extends StatusBar {

	private final JPanel statusBar;
	/** The label displaying the info text. */
	protected final JLabel label;

	private final Insets statusBarInsets;

	private PixelModel model;

	private final PropertyChangeListener propertyChangeListener = evt -> update();

	private final ChangeListener modelListener = e -> update();

	/**
	 * Creates a new instance.
	 */
	public PixelInfoStatusBar() {
		statusBar = new JPanel();
		statusBar.setBorder(BorderFactory.createEtchedBorder());
		label = new JLabel("n/a");
		statusBar.add(label);
		statusBarInsets = statusBar.getInsets();

		setModel(new PixelModel());
	}

	/**
	 * Sets the model dictating the pixel shown by this status bar.
	 *
	 * @param newModel
	 *            the new model
	 * @throws java.lang.NullPointerException in case of error
	 *             if {@code newModel} is {@code null}
	 */
	public final void setModel(final PixelModel newModel) {
		if (newModel == null)
			throw new NullPointerException();

		if (!Objects.equals(model, newModel)) {
			if (model != null)
				model.removeChangeListener(modelListener);
			model = newModel;
			model.addChangeListener(modelListener);
			update();
		}
	}

	/**
	 * Returns the model storing the pixel shown by this status bar.
	 *
	 * @return the current model; never {@code null}
	 */
	public PixelModel getModel() {
		return model;
	}

	/** {@inheritDoc} */
	@Override
	public final JComponent getComponent() {
		return statusBar;
	}

	/**
	 * Sets the pixel this status bar is to display information on. The values may
	 * be arbitrary; you can use negative values to indicate that there is nothing
	 * to display.
	 *
	 * @param x
	 *            the x coordinate of the pixel
	 * @param y
	 *            the y coordinate of the pixel
	 */
	public void setPixel(final int x, final int y) {
		model.set(x, y);
	}

	/**
	 * Updates the info label. This function is called when either the selected
	 * pixel or the image shown in the viewer is changed. You can call this method
	 * to indicate that the message should be updated for some different reason.
	 */
	protected final void update() {
		final BufferedImage image = getImageViewer() == null ? null : getImageViewer().getImage();
		if (image == null || model.isInvalid() || model.getX() >= image.getWidth() || model.getY() >= image.getHeight())
			updateLabelNoData();
		else
			updateLabel(image, model.getX(), model.getY(),
					statusBar.getWidth() - statusBarInsets.left - statusBarInsets.right);
	}

	/**
	 * This function updates the contents of the {@link #label} to indicate that no
	 * data can be shown. By default, this function is called by {@code update} when
	 * the image is {@code null} or the pixel to be shown is not within bounds.
	 */
	protected void updateLabelNoData() {
		label.setText("n/a");
	}

	/**
	 * This function updates the contents of the {@link #label}. It is called when
	 * the highlighted pixel or the image changes, and only when the image is not
	 * {@code null} and the pixel is within the bounds of the image. If either of
	 * these conditions aren't true, the {@code update} function calls
	 * {@link #updateLabelNoData()} instead. Override it to provide a custom
	 * message.
	 *
	 * @param image
	 *            the current image displayed in the viewer
	 * @param x
	 *            the x coordinate of the pixel that should be displayed
	 * @param y
	 *            the y coordinate of the pixel that should be displayed
	 * @param availableWidth
	 *            the maximum label width that can be displayed; you can use this
	 *            parameter to specify a shorter message if there is not enough room
	 */
	protected void updateLabel(final BufferedImage image, final int x, final int y, final int availableWidth) {
		if (image.getRaster().getNumBands() == 1) {
			label.setText(String.format("%d, %d; intensity %d", x, y, image.getRaster().getSample(x, y, 0)));
		} else if (image.getRaster().getNumBands() == 4) {
			final int rgb = image.getRGB(x, y);
			final Color c = new Color(rgb, true);
			label.setText(String.format("%d, %d; color %d,%d,%d, alpha %d", x, y, c.getRed(), c.getGreen(), c.getBlue(),
					c.getAlpha()));
		} else {
			final int rgb = image.getRGB(x, y);
			final Color c = new Color(rgb);
			label.setText(String.format("%d, %d; color %d,%d,%d", x, y, c.getRed(), c.getGreen(), c.getBlue()));
		}

		if (availableWidth < label.getPreferredSize().width) {
			// not enough space, shorter version is required
			if (image.getRaster().getNumBands() == 1) {
				label.setText(String.format("%d, %d; %d", x, y, image.getRaster().getSample(x, y, 0)));
			} else if (image.getRaster().getNumBands() == 4) {
				final int rgb = image.getRGB(x, y);
				final Color c = new Color(rgb, true);
				label.setText(String.format("%d, %d; (%d,%d,%d,%d)", x, y, c.getRed(), c.getGreen(), c.getBlue(),
						c.getAlpha()));
			} else {
				final int rgb = image.getRGB(x, y);
				final Color c = new Color(rgb);
				label.setText(String.format("%d, %d; (%d,%d,%d)", x, y, c.getRed(), c.getGreen(), c.getBlue()));
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void register(final ImageViewer viewer) {
		viewer.addPropertyChangeListener("image", propertyChangeListener);
		update();
	}

	/** {@inheritDoc} */
	@Override
	protected void unregister(final ImageViewer viewer) {
		viewer.removePropertyChangeListener("image", propertyChangeListener);
	}

}
