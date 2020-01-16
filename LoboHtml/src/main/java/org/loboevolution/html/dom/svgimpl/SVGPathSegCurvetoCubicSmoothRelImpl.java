package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegCurvetoCubicSmoothRel;

public class SVGPathSegCurvetoCubicSmoothRelImpl implements SVGPathSegCurvetoCubicSmoothRel {

	private float x;

	private float y;

	private float x2;

	private float y2;

	public SVGPathSegCurvetoCubicSmoothRelImpl(float x, float y, float x2, float y2) {
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public short getPathSegType() {
		return PATHSEG_CURVETO_CUBIC_SMOOTH_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "s";
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
	public float getX2() {
		return x2;
	}

	@Override
	public void setX2(float x2) {
		this.x2 = x2;
	}

	@Override
	public float getY2() {
		return y2;
	}

	@Override
	public void setY2(float y2) {
		this.y2 = y2;
	}

}
