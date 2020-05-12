package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedString;
import org.w3c.dom.DOMException;

/**
 * <p>SVGAnimatedStringImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGAnimatedStringImpl implements SVGAnimatedString {

	private String baseVal;

	/**
	 * <p>Constructor for SVGAnimatedStringImpl.</p>
	 *
	 * @param baseVal a {@link java.lang.String} object.
	 */
	public SVGAnimatedStringImpl(String baseVal) {
		this.baseVal = baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public String getBaseVal() {
		return baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public void setBaseVal(String baseVal) throws DOMException {
		this.baseVal = baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public String getAnimVal() {
		return baseVal;
	}
}
