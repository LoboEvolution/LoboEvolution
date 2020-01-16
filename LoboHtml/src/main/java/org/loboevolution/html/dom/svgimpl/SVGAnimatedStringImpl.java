package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedString;
import org.w3c.dom.DOMException;

public class SVGAnimatedStringImpl implements SVGAnimatedString {

	private String baseVal;

	public SVGAnimatedStringImpl(String baseVal) {
		this.baseVal = baseVal;
	}

	@Override
	public String getBaseVal() {
		return baseVal;
	}

	@Override
	public void setBaseVal(String baseVal) throws DOMException {
		this.baseVal = baseVal;
	}

	@Override
	public String getAnimVal() {
		return baseVal;
	}
}
