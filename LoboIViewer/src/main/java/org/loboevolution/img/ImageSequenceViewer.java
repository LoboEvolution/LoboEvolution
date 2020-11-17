package org.loboevolution.img;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
 * @author Kaz Csaba
 * @version $Id: $Id
 */
public class ImageSequenceViewer {

	private final ImageViewer imageViewer;
	private int number, position;
	private JButton forwardButton, backwardButton;
	private JLabel locationLabel;

	private final JPanel panel = new JPanel(new BorderLayout()) {
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
	public ImageSequenceViewer(int number) {
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
	public ImageSequenceViewer(int number, int startPos) {
		imageViewer = new ImageViewer();
		createAndShowGUI(number, startPos);
	}

	private void createAndShowGUI(int number, int startPos) {
		if (number <= 0 || startPos < 0 || startPos >= number)
			throw new IllegalArgumentException();
		
		this.number = number;
		this.position = startPos;
		panel.add(imageViewer.getComponent(), BorderLayout.CENTER);
		forwardButton = new JButton(">");
		backwardButton = new JButton("<");
		JPanel locationPanel = new JPanel(new FlowLayout());
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
	public void setPosition(int pos) {
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
	 * @return a int.
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
	protected void updateLocationDefinition(int pos) {
		locationLabel.setText(String.format("%d/%d", pos + 1, number));
	}
}
