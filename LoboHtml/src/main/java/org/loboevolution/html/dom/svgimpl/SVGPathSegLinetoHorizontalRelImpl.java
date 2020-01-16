package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegLinetoHorizontalRel;
import org.w3c.dom.DOMException;

public class SVGPathSegLinetoHorizontalRelImpl implements SVGPathSegLinetoHorizontalRel {

	private float x;

	public SVGPathSegLinetoHorizontalRelImpl(float x) {
		this.x = x;
	}

	@Override
	public short getPathSegType() {
		return PATHSEG_LINETO_HORIZONTAL_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "h";
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
