package org.loboevolution.html.dom.svgimpl;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import org.loboevolution.html.dom.svg.SVGMatrix;
import org.loboevolution.html.dom.svg.SVGPoint;

/**
 * <p>SVGPointImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGPointImpl implements SVGPoint {

	private float x;

	private float y;

	/**
	 * <p>Constructor for SVGPointImpl.</p>
	 */
	public SVGPointImpl() {
		x = 0;
		y = 0;
	}

	/**
	 * <p>Constructor for SVGPointImpl.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 */
	public SVGPointImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * <p>Constructor for SVGPointImpl.</p>
	 *
	 * @param point a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 */
	public SVGPointImpl(SVGPoint point) {
		x = point.getX();
		y = point.getY();
	}

	/** {@inheritDoc} */
	@Override
	public float getX() {
		return x;
	}

	/** {@inheritDoc} */
	@Override
	public void setX(float x) {
		this.x = x;
	}

	/** {@inheritDoc} */
	@Override
	public float getY() {
		return y;
	}

	/** {@inheritDoc} */
	@Override
	public void setY(float y) {
		this.y = y;
	}

	/** {@inheritDoc} */
	@Override
	public SVGPoint matrixTransform(SVGMatrix matrix) {
		AffineTransform transform = ((SVGMatrixImpl) matrix).getAffineTransform();
		Point2D srcPoint = new Point2D.Double(x, y);
		Point2D dstPoint = new Point2D.Double();
		transform.transform(srcPoint, dstPoint);
		return new SVGPointImpl((float) dstPoint.getX(), (float) dstPoint.getY());
	}

}
