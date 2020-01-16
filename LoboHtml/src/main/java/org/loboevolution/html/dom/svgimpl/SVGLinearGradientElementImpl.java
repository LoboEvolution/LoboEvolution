package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedLength;
import org.loboevolution.html.dom.svg.SVGLinearGradientElement;

public class SVGLinearGradientElementImpl extends SVGGradientElementImpl implements SVGLinearGradientElement {

	public SVGLinearGradientElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedLength getX1() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("x1")));
	}

	@Override
	public SVGAnimatedLength getY1() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("y1")));
	}

	@Override
	public SVGAnimatedLength getX2() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("x2")));
	}

	@Override
	public SVGAnimatedLength getY2() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("y2")));
	}

}
