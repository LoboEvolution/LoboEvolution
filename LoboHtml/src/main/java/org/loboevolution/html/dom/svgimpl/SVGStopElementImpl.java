package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedNumber;
import org.loboevolution.html.dom.svg.SVGStopElement;

/**
 * <p>SVGStopElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGStopElementImpl extends SVGGraphic implements SVGStopElement {

	/**
	 * <p>Constructor for SVGStopElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGStopElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
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
