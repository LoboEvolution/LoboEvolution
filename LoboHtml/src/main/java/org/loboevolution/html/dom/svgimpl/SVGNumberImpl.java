package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGNumber;
import org.w3c.dom.DOMException;

/**
 * <p>SVGNumberImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGNumberImpl implements SVGNumber {
	
	private float value;

	/**
	 * <p>Constructor for SVGNumberImpl.</p>
	 */
	public SVGNumberImpl() {
		this.value= 0;
	}
	
	/**
	 * <p>Constructor for SVGNumberImpl.</p>
	 *
	 * @param value a float.
	 */
	public SVGNumberImpl(float value) {
		this.value = value;
	}
	/** {@inheritDoc} */
	@Override
	public float getValue() {
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(float value) throws DOMException {
		this.value = value;
	}
}
