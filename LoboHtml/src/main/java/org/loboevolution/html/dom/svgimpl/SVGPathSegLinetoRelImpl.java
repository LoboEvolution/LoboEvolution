package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegLinetoRel;
import org.w3c.dom.DOMException;

public class SVGPathSegLinetoRelImpl implements SVGPathSegLinetoRel {

	private float x;

	private float y;

	public SVGPathSegLinetoRelImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		return PATHSEG_LINETO_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "l";
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
