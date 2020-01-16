package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegMovetoRel;

public class SVGPathSegMovetoRelImpl implements SVGPathSegMovetoRel {

	private float x;

	private float y;

	public SVGPathSegMovetoRelImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		return PATHSEG_MOVETO_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "m";
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
