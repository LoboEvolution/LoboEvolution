package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedLengthList;
import org.loboevolution.html.dom.svg.SVGLengthList;

public class SVGAnimatedLengthListImpl implements SVGAnimatedLengthList {

	private SVGLengthList lenght;

	public SVGAnimatedLengthListImpl(SVGLengthList lenght) {
		this.lenght = lenght;
	}

	@Override
	public SVGLengthList getBaseVal() {
		return lenght;
	}

	@Override
	public SVGLengthList getAnimVal() {
		return lenght;
	}
}