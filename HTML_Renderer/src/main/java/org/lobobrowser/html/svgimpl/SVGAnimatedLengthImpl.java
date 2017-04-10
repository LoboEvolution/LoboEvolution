package org.lobobrowser.html.svgimpl;

import org.lobobrowser.w3c.svg.SVGAnimatedLength;
import org.lobobrowser.w3c.svg.SVGLength;

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
