package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegLinetoVerticalAbs;

public class SVGPathSegLinetoVerticalAbsImpl implements SVGPathSegLinetoVerticalAbs {

	private float y;

	public SVGPathSegLinetoVerticalAbsImpl(float y) {
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		return PATHSEG_LINETO_VERTICAL_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "V";
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
