package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedLength;
import org.loboevolution.html.dom.svg.SVGLength;

/**
 * <p>SVGAnimatedLengthImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGAnimatedLengthImpl implements SVGAnimatedLength {

	private final SVGLength baseValue;
	private final SVGLength animValue;

	/**
	 * <p>Constructor for SVGAnimatedLengthImpl.</p>
	 *
	 * @param baseValue a {@link org.loboevolution.html.dom.svg.SVGLength} object.
	 */
	public SVGAnimatedLengthImpl(SVGLength baseValue) {
		this.baseValue = baseValue;
		this.animValue = baseValue;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength getBaseVal() {
		return this.baseValue;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength getAnimVal() {
		return this.animValue;
	}

}
