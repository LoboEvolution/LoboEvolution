package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimationElement;
import org.loboevolution.html.dom.svg.SVGElement;
import org.w3c.dom.DOMException;

/**
 * <p>SVGAnimationElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGAnimationElementImpl extends SVGAnimationImpl implements SVGAnimationElement {

	/**
	 * <p>Constructor for SVGAnimationElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGAnimationElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public SVGElement getTargetElement() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public float getStartTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public float getCurrentTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public float getSimpleDuration() throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}
}
