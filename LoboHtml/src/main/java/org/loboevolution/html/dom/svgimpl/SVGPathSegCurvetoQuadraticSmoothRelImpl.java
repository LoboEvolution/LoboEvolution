package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegCurvetoQuadraticSmoothRel;

public class SVGPathSegCurvetoQuadraticSmoothRelImpl implements SVGPathSegCurvetoQuadraticSmoothRel {
	
	private float x;

	private float y;

	public SVGPathSegCurvetoQuadraticSmoothRelImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		return PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL;
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
