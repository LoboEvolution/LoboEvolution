package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegClosePath;

public class SVGPathSegClosePathImpl implements SVGPathSegClosePath {

	@Override
	public short getPathSegType() {
		return PATHSEG_CLOSEPATH;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "z";
	}
}
