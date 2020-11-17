package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedLengthList;
import org.loboevolution.html.dom.svg.SVGLengthList;

/**
 * <p>SVGAnimatedLengthListImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGAnimatedLengthListImpl implements SVGAnimatedLengthList {

	private final SVGLengthList lenght;

	/**
	 * <p>Constructor for SVGAnimatedLengthListImpl.</p>
	 *
	 * @param lenght a {@link org.loboevolution.html.dom.svg.SVGLengthList} object.
	 */
	public SVGAnimatedLengthListImpl(SVGLengthList lenght) {
		this.lenght = lenght;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLengthList getBaseVal() {
		return lenght;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLengthList getAnimVal() {
		return lenght;
	}
}
