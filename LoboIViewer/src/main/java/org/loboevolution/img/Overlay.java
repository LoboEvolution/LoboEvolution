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
 * @author Kazo Csaba
 * @version $Id: $Id
 */
public abstract class Overlay {
	private final List<OverlayComponent> overlayComponents = new ArrayList<OverlayComponent>(1);

	/**
	 * <p>addOverlayComponent.</p>
	 *
	 * @param c a {@link org.loboevolution.img.OverlayComponent} object.
	 */
	public final void addOverlayComponent(OverlayComponent c) {
		overlayComponents.add(c);
	}

	/**
	 * <p>removeOverlayComponent.</p>
	 *
	 * @param c a {@link org.loboevolution.img.OverlayComponent} object.
	 */
	public final void removeOverlayComponent(OverlayComponent c) {
		overlayComponents.remove(c);
	}

	/**
	 * Causes the overlay to be repainted.
	 */
	public void repaint() {
		for (OverlayComponent overlayComponent : overlayComponents)
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
