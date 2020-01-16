package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedRect;
import org.loboevolution.html.dom.svg.SVGRect;
import org.w3c.dom.DOMException;

public class SVGAnimatedRectImpl implements SVGAnimatedRect {

	private SVGRect baseVal;

	public SVGAnimatedRectImpl(SVGRect baseVal) {
		this.baseVal = baseVal;
	}

	@Override
	public SVGRect getBaseVal() {
		return baseVal;
	}

	void setBaseVal(SVGRect baseVal) throws DOMException {
		this.baseVal = baseVal;
	}

	@Override
	public SVGRect getAnimVal() {
		return baseVal;
	}

}
