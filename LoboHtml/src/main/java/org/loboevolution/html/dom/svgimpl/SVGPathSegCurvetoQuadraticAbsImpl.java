package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegCurvetoQuadraticAbs;

public class SVGPathSegCurvetoQuadraticAbsImpl implements SVGPathSegCurvetoQuadraticAbs {

	private float x;

	private float y;

	private float x1;

	private float y1;

	public SVGPathSegCurvetoQuadraticAbsImpl(float x, float y, float x1, float y1) {
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
	}

	@Override
	public short getPathSegType() {
		return PATHSEG_CURVETO_QUADRATIC_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "Q";
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
	public float getX1() {
		return x1;
	}

	@Override
	public void setX1(float x1) {
		this.x1 = x1;
	}

	@Override
	public float getY1() {
		return y1;
	}

	@Override
	public void setY1(float y1) {
		this.y1 = y1;
	}

}
