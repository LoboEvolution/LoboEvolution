package org.loboevolution.img;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * An overlay for marking pixels of the image. A circle will be drawn around the
 * selected pixels; the size of the circle is set independently of the current
 * zoom, but it may be enlarged in order to contain the pixel entirely.
 * <p>
 * The default size is 5 pixels and the default color is red.
 * <p>
 * This object stores the list of pixels; this list can be edited with the
 * {@link #setPoint(Point)}, {@link #setPoints(Iterable)},
 * {@link #addPoint(Point)}, and {@link #clear()} functions. All of these
 * mutator functions cause the overlay to be repainted.
 *
 * @author Kazo Csaba
 * @version $Id: $Id
 */
public final class PixelMarkerOverlay extends Overlay {

	private final Set<Point> points = new HashSet<>();
	private Color color = Color.RED;
	private int size = 5;

	/**
	 * Sets the size of the circle. The actual width and height of the circle will
	 * be {@code 2*newSize+1}.
	 *
	 * @param newSize
	 *            the new size of the marker circles
	 * @throws java.lang.IllegalArgumentException
	 *             if {@code newSize} is negative
	 */
	public void setSize(int newSize) {
		if (newSize < 0)
			throw new IllegalArgumentException("Negative size");
		size = newSize;
		repaint();
	}

	/**
	 * Sets the color of the marker circles.
	 *
	 * @param color
	 *            the new color
	 * @throws java.lang.NullPointerException
	 *             if {@code color} is {@code null}
	 */
	public void setColor(Color color) {
		if (color == null)
			throw new NullPointerException();
		this.color = color;
		repaint();
	}

	/**
	 * Adds a point to the list of pixels marked by this overlay.
	 *
	 * @param p
	 *            a new point
	 * @throws java.lang.NullPointerException
	 *             if {@code p} is {@code null}
	 */
	public void addPoint(Point p) {
		if (p == null)
			throw new NullPointerException();
		points.add(new Point(p));
		repaint();
	}

	/**
	 * Sets the argument as the only point marked by this overlay.
	 *
	 * @param p
	 *            the point to mark; if {@code null}, then no points will be
	 *            selected
	 */
	public void setPoint(Point p) {
		points.clear();
		if (p != null) {
			points.add(new Point(p));
		}
		repaint();
	}

	/**
	 * Sets the marked pixels.
	 *
	 * @param points
	 *            an iterable of all the pixels that should be selected
	 * @throws java.lang.NullPointerException
	 *             if {@code points} or any individual point is {@code null}
	 */
	public void setPoints(Iterable<Point> points) {
		if (points == null)
			throw new NullPointerException();
		this.points.clear();
		for (Point p : points) {
			this.points.add(new Point(p));
		}
		repaint();
	}

	/**
	 * Removes all pixels from the overlay, clearing the selection.
	 */
	public void clear() {
		if (!points.isEmpty()) {
			points.clear();
			repaint();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void paint(Graphics2D g, BufferedImage image, AffineTransform transform) {
		g.setColor(color);
		Point2D p2d = new Point2D.Double();
		int currentSize = Math.max(size, (int) Math.ceil(transform.getScaleX() / Math.sqrt(2)));

		for (Point p : points) {
			p2d.setLocation(p.x + .5, p.y + .5);
			transform.transform(p2d, p2d);
			g.drawOval((int) p2d.getX() - currentSize, (int) p2d.getY() - currentSize, 2 * currentSize + 1,
					2 * currentSize + 1);
		}
	}
}
