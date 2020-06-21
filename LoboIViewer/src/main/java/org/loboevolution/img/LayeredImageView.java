package org.loboevolution.img;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

/**
 * A component showing an image as well as an arbitrary number of overlays.
 *
 * @author Kazo Csaba
 * @version $Id: $Id
 */
public class LayeredImageView {
	private final ImageComponent theImage;
	private final JLayeredPane layeredPane;

	/**
	 * <p>Constructor for LayeredImageView.</p>
	 *
	 * @param theImage a {@link org.loboevolution.img.ImageComponent} object.
	 */
	public LayeredImageView(ImageComponent theImage) {
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
	public void addOverlay(Overlay overlay, int layer) {
		if (overlay == null)
			throw new NullPointerException();
		OverlayComponent c = new OverlayComponent(overlay, getTheImage());
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
	public void removeOverlay(Overlay overlay) {
		if (overlay == null)
			throw new NullPointerException();
		for (Component c : layeredPane.getComponents()) {
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
