package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedTransformList;
import org.loboevolution.html.dom.svg.SVGTransformList;

/**
 * <p>SVGAnimatedTransformListImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGAnimatedTransformListImpl implements SVGAnimatedTransformList {
	
	private final SVGTransformListImpl baseVal;
	
	/**
	 * <p>Constructor for SVGAnimatedTransformListImpl.</p>
	 *
	 * @param baseVal a {@link org.loboevolution.html.dom.svgimpl.SVGTransformListImpl} object.
	 */
	public SVGAnimatedTransformListImpl(SVGTransformListImpl baseVal) {
		this.baseVal = baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransformList getBaseVal() {
		return baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransformList getAnimVal() {
		return baseVal;
	}
}
