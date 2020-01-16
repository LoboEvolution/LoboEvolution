package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegLinetoAbs;

public class SVGPathSegLinetoAbsImpl implements SVGPathSegLinetoAbs {

	private float x;

	private float y;

	public SVGPathSegLinetoAbsImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		return PATHSEG_LINETO_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "L";
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
