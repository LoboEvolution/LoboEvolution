package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedLength;
import org.loboevolution.html.dom.svg.SVGRadialGradientElement;

public class SVGRadialGradientElementImpl extends SVGGradientElementImpl implements SVGRadialGradientElement {

	public SVGRadialGradientElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedLength getCx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("cx")));
	}

	@Override
	public SVGAnimatedLength getCy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("cy")));
	}

	@Override
	public SVGAnimatedLength getR() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("r")));
	}

	@Override
	public SVGAnimatedLength getFx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("fx")));
	}

	@Override
	public SVGAnimatedLength getFy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("fy")));
	}
}
