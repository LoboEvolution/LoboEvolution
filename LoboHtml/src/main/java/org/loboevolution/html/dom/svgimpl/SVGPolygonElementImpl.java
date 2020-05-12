package org.loboevolution.html.dom.svgimpl;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.loboevolution.html.dom.svg.SVGMatrix;
import org.loboevolution.html.dom.svg.SVGPoint;
import org.loboevolution.html.dom.svg.SVGPointList;
import org.loboevolution.html.dom.svg.SVGPolygonElement;

/**
 * <p>SVGPolygonElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGPolygonElementImpl extends SVGGraphic implements SVGPolygonElement {

	/**
	 * <p>Constructor for SVGPolygonElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGPolygonElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPointList getPoints() {
		return constructPointList(getAttribute("points"));
	}

	/** {@inheritDoc} */
	@Override
	public SVGPointList getAnimatedPoints() {
		return constructPointList(getAttribute("points"));
	}

	/** {@inheritDoc} */
	@Override
	public void draw(Graphics2D graphics) {
		final SVGMatrix ctm = getCTM();
		final Shape shape = createShape(ctm.getAffineTransform());
		animate(this);
		drawable(graphics, shape);
	}

	/** {@inheritDoc} */
	@Override
	public Shape createShape(AffineTransform transform) {
		GeneralPath path = new GeneralPath();
		SVGPointList points = getAnimatedPoints();
		int numPoints = points.getNumberOfItems();
		for (int i = 0; i < numPoints; i++) {
			SVGPoint point = points.getItem(i);
			float x = point.getX();
			float y = point.getY();
			if (i == 0) {
				path.moveTo(x, y);
			} else {
				path.lineTo(x, y);
			}
		}
		path.closePath();
		return path;
	}

	private SVGPointList constructPointList(String pointString) {
		SVGPointListImpl points = new SVGPointListImpl();
		StringTokenizer st = new StringTokenizer(pointString, " ,", false);
		while (st.hasMoreTokens()) {
			try {
				float x = Float.parseFloat(st.nextToken());
				float y = Float.parseFloat(st.nextToken());
				SVGPoint point = new SVGPointImpl(x, y);
				points.appendItem(point);
			} catch (NoSuchElementException e) {
			}
		}
		return points;
	}
}
