package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.DOMException;

public class SVGAnimatedEnumerationImpl implements SVGAnimatedEnumeration {

	private short baseVal;

	public SVGAnimatedEnumerationImpl(short baseVal) {
		this.baseVal = baseVal;
	}

	@Override
	public short getBaseVal() {
		return baseVal;
	}

	@Override
	public void setBaseVal(short baseVal) throws DOMException {
		this.baseVal = baseVal;
	}

	@Override
	public short getAnimVal() {
		return baseVal;
	}

}
