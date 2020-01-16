package org.loboevolution.html.dom.svg;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public interface Drawable {

	void draw(Graphics2D graphics);

	Shape createShape(AffineTransform transform);

}
