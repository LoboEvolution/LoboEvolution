package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedRect;
import org.loboevolution.html.dom.svg.SVGRect;
import org.w3c.dom.DOMException;

/**
 * <p>SVGAnimatedRectImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGAnimatedRectImpl implements SVGAnimatedRect {

	private SVGRect baseVal;

	/**
	 * <p>Constructor for SVGAnimatedRectImpl.</p>
	 *
	 * @param baseVal a {@link org.loboevolution.html.dom.svg.SVGRect} object.
	 */
	public SVGAnimatedRectImpl(SVGRect baseVal) {
		this.baseVal = baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public SVGRect getBaseVal() {
		return baseVal;
	}

	void setBaseVal(SVGRect baseVal) throws DOMException {
		this.baseVal = baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public SVGRect getAnimVal() {
		return baseVal;
	}

}
