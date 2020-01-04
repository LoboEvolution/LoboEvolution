package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegLinetoHorizontalAbs;

public class SVGPathSegLinetoHorizontalAbsImpl implements SVGPathSegLinetoHorizontalAbs {

	private float x;

	public SVGPathSegLinetoHorizontalAbsImpl(float x) {
		this.x = x;
	}

	@Override
	public short getPathSegType() {
		return PATHSEG_LINETO_HORIZONTAL_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "H";
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}
}
