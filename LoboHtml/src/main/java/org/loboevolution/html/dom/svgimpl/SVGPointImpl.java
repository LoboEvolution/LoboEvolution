package org.loboevolution.html.dom.svgimpl;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import org.loboevolution.html.dom.svg.SVGMatrix;
import org.loboevolution.html.dom.svg.SVGPoint;

public class SVGPointImpl implements SVGPoint {

	private float x;

	private float y;

	public SVGPointImpl() {
		x = 0;
		y = 0;
	}

	public SVGPointImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public SVGPointImpl(SVGPoint point) {
		x = point.getX();
		y = point.getY();
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public SVGPoint matrixTransform(SVGMatrix matrix) {
		AffineTransform transform = ((SVGMatrixImpl) matrix).getAffineTransform();
		Point2D srcPoint = new Point2D.Double(x, y);
		Point2D dstPoint = new Point2D.Double();
		transform.transform(srcPoint, dstPoint);
		return new SVGPointImpl((float) dstPoint.getX(), (float) dstPoint.getY());
	}

}
