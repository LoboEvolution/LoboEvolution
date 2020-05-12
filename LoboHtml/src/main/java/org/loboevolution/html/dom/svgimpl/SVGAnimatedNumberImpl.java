package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedNumber;
import org.w3c.dom.DOMException;

/**
 * <p>SVGAnimatedNumberImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGAnimatedNumberImpl implements SVGAnimatedNumber {

	private SVGNumberImpl svgNumber;

	/**
	 * <p>Constructor for SVGAnimatedNumberImpl.</p>
	 *
	 * @param svgNumber a {@link org.loboevolution.html.dom.svgimpl.SVGNumberImpl} object.
	 */
	public SVGAnimatedNumberImpl(SVGNumberImpl svgNumber) {
		this.svgNumber = svgNumber;
	}

	/** {@inheritDoc} */
	@Override
	public float getBaseVal() {
		return svgNumber.getValue();
	}

	/** {@inheritDoc} */
	@Override
	public void setBaseVal(float baseVal) throws DOMException {
		svgNumber.setValue(baseVal);

	}

	/** {@inheritDoc} */
	@Override
	public float getAnimVal() {
		return svgNumber.getValue();
	}
}
