package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.DOMException;

/**
 * <p>SVGAnimatedEnumerationImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGAnimatedEnumerationImpl implements SVGAnimatedEnumeration {

	private short baseVal;

	/**
	 * <p>Constructor for SVGAnimatedEnumerationImpl.</p>
	 *
	 * @param baseVal a short.
	 */
	public SVGAnimatedEnumerationImpl(short baseVal) {
		this.baseVal = baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public short getBaseVal() {
		return baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public void setBaseVal(short baseVal) throws DOMException {
		this.baseVal = baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public short getAnimVal() {
		return baseVal;
	}

}
