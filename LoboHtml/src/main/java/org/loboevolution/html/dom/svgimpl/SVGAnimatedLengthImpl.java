package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedLength;
import org.loboevolution.html.dom.svg.SVGLength;

public class SVGAnimatedLengthImpl implements SVGAnimatedLength {

	private SVGLength baseValue;
	private SVGLength animValue;

	public SVGAnimatedLengthImpl(SVGLength baseValue) {
		this.baseValue = baseValue;
		this.animValue = baseValue;
	}

	@Override
	public SVGLength getBaseVal() {
		return this.baseValue;
	}

	@Override
	public SVGLength getAnimVal() {
		return this.animValue;
	}

}
