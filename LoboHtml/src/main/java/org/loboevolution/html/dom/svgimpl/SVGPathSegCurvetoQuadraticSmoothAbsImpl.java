package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegCurvetoQuadraticSmoothAbs;

public class SVGPathSegCurvetoQuadraticSmoothAbsImpl implements SVGPathSegCurvetoQuadraticSmoothAbs {

	private float x;

	private float y;

	public SVGPathSegCurvetoQuadraticSmoothAbsImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		return PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "T";
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

}
