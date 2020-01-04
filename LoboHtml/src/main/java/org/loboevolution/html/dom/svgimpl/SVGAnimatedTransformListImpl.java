package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedTransformList;
import org.loboevolution.html.dom.svg.SVGTransformList;

public class SVGAnimatedTransformListImpl implements SVGAnimatedTransformList {
	
	private SVGTransformListImpl baseVal;
	
	public SVGAnimatedTransformListImpl(SVGTransformListImpl baseVal) {
		this.baseVal = baseVal;
	}

	@Override
	public SVGTransformList getBaseVal() {
		return baseVal;
	}

	@Override
	public SVGTransformList getAnimVal() {
		return baseVal;
	}
}
