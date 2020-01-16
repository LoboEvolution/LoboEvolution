package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedNumber;
import org.w3c.dom.DOMException;

public class SVGAnimatedNumberImpl implements SVGAnimatedNumber {

	private SVGNumberImpl svgNumber;

	public SVGAnimatedNumberImpl(SVGNumberImpl svgNumber) {
		this.svgNumber = svgNumber;
	}

	@Override
	public float getBaseVal() {
		return svgNumber.getValue();
	}

	@Override
	public void setBaseVal(float baseVal) throws DOMException {
		svgNumber.setValue(baseVal);

	}

	@Override
	public float getAnimVal() {
		return svgNumber.getValue();
	}
}