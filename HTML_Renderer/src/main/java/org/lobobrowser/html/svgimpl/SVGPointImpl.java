package org.lobobrowser.html.svgimpl;

import org.lobobrowser.w3c.svg.SVGMatrix;
import org.lobobrowser.w3c.svg.SVGPoint;
import org.w3c.dom.DOMException;

public class SVGPointImpl implements SVGPoint {
	
	private float x;
	private float y;

	public SVGPointImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) throws DOMException {
		this.x = x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setY(float y) throws DOMException {
		this.y = y;
	}

	@Override
	public SVGPoint matrixTransform(SVGMatrix matrix) {
		// TODO Auto-generated method stub
		return null;
	}

}
