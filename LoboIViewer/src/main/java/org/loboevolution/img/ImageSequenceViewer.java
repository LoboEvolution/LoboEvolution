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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.Serial;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A component for displaying a series of images. Supports paging through GUI as
 * well as setting the current position via function {@link #setPosition(int)}.
 * When the position is changed, the {@link #positionChanged()} method is
 * called. Subclasses should override this method to update the image according
 * to the new position.
 *
 * Author Kaz Csaba
 *
 */
public class ImageSequenceViewer {

	private final ImageViewer imageViewer;
	private int number, position;
	private JButton forwardButton, backwardButton;
	private JLabel locationLabel;

	private final JPanel panel = new JPanel(new BorderLayout()) {
		@Serial
		private static final long serialVersionUID = 1L;

		/**
		 * Overridden to call .
		 */
		@Override
		public void addNotify() {
			super.addNotify();
			positionChanged();
		}
	};

	/**
	 * Creates a new sequence viewer that can display the specified number of
	 * images.
	 *
	 * @param number
	 *            the number of images
	 * @throws java.lang.IllegalArgumentException
	 *             if the number is negative
	 */
	public ImageSequenceViewer(final int number) {
		this(number, 0);
	}

	/**
	 * Creates a new sequence viewer that can display the specified number of
	 * images.
	 *
	 * @param number
	 *            the number of images
	 * @param startPos
	 *            the initial position of the viewer
	 * @throws java.lang.IllegalArgumentException
	 *             if the number is negative or the starting position is not valid
	 */
	public ImageSequenceViewer(final int number, final int startPos) {
		imageViewer = new ImageViewer();
		createAndShowGUI(number, startPos);
	}

	private void createAndShowGUI(final int number, final int startPos) {
		if (number <= 0 || startPos < 0 || startPos >= number)
			throw new IllegalArgumentException();
		
		this.number = number;
		this.position = startPos;
		panel.add(imageViewer.getComponent(), BorderLayout.CENTER);
		forwardButton = new JButton(">");
		backwardButton = new JButton("<");
		final JPanel locationPanel = new JPanel(new FlowLayout());
		locationPanel.add(backwardButton);
		locationPanel.add(createLocationDefinition());
		locationPanel.add(forwardButton);

		forwardButton.addActionListener(e -> setPosition(position + 1));
		backwardButton.addActionListener(e -> setPosition(position - 1));
		panel.add(locationPanel, BorderLayout.NORTH);
		setPosition(position);
	}

	/**
	 * Returns the Swing component for this sequence viewer.
	 *
	 * @return the component
	 */
	public JComponent getComponent() {
		return panel;
	}

	/**
	 * Called when the current position of the viewer has changed. The default
	 * implementation does nothing. Subclasses should override this method to update
	 * the image.
	 * <p>
	 * This method is not called from the constructor, but it is called before the
	 * viewer is made visible, so subclasses can safely use this method to set the
	 * initial image.
	 */
	protected void positionChanged() {
	}

	/**
	 * <p>Getter for the field imageViewer.</p>
	 *
	 * @return a {@link org.loboevolution.img.ImageViewer} object.
	 */
	public ImageViewer getImageViewer() {
		return imageViewer;
	}

	/**
	 * Sets the position of the viewer.
	 *
	 * @param pos
	 *            the new position of the viewer
	 * @throws java.lang.IllegalArgumentException
	 *             if the position is not valid
	 */
	public void setPosition(final int pos) {
		if (pos < 0 || pos >= number)
			throw new IllegalArgumentException("Position " + pos + " out of range");
		position = pos;
		updateLocationDefinition(position);
		forwardButton.setEnabled(position < number - 1);
		backwardButton.setEnabled(position > 0);
		if (panel.getParent() != null)
			positionChanged();
	}

	/**
	 * Returns the current position of this image sequence shower.
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Creates and returns the component that displays the current position to the
	 * user. The default implementation creates a JLabel.
	 *
	 * @return the location component
	 */
	protected JComponent createLocationDefinition() {
		locationLabel = new JLabel();
		return locationLabel;
	}

	/**
	 * Called when the current position changes to update the location component.
	 *
	 * @param pos
	 *            the current position
	 */
	protected void updateLocationDefinition(final int pos) {
		locationLabel.setText(String.format("%d/%d", pos + 1, number));
	}
}
