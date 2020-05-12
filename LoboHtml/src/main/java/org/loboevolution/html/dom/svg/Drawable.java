package org.loboevolution.html.dom.svg;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 * <p>Drawable interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface Drawable {

	/**
	 * <p>draw.</p>
	 *
	 * @param graphics a {@link java.awt.Graphics2D} object.
	 */
	void draw(Graphics2D graphics);

	/**
	 * <p>createShape.</p>
	 *
	 * @param transform a {@link java.awt.geom.AffineTransform} object.
	 * @return a {@link java.awt.Shape} object.
	 */
	Shape createShape(AffineTransform transform);

}
