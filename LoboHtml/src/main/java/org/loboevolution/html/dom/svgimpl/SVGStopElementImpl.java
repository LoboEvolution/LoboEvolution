package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedNumber;
import org.loboevolution.html.dom.svg.SVGStopElement;

public class SVGStopElementImpl extends SVGGraphic implements SVGStopElement {

	public SVGStopElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedNumber getOffset() {
		final String value = this.getAttribute("offset");
		float number = 0;
		int index = value.indexOf('%');
		if (index != -1) {
			number = Float.parseFloat(value.substring(0, index)) / 100;
		} else {
			number = Float.parseFloat(value);
		}

		if (number < 0) {
			number = 0;
		} else if (number > 1) {
			number = 1;
		}
		return new SVGAnimatedNumberImpl(new SVGNumberImpl(number));
	}
}
