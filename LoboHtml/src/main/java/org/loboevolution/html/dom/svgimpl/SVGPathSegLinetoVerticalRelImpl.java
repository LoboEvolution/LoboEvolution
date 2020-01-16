package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegLinetoVerticalRel;
import org.w3c.dom.DOMException;

public class SVGPathSegLinetoVerticalRelImpl implements SVGPathSegLinetoVerticalRel {

	private float y;

	public SVGPathSegLinetoVerticalRelImpl(float y) {
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		return PATHSEG_LINETO_VERTICAL_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "v";
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
