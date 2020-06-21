package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegLinetoVerticalRel;

/**
 * <p>SVGPathSegLinetoVerticalRelImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGPathSegLinetoVerticalRelImpl implements SVGPathSegLinetoVerticalRel {

	private float y;

	/**
	 * <p>Constructor for SVGPathSegLinetoVerticalRelImpl.</p>
	 *
	 * @param y a float.
	 */
	public SVGPathSegLinetoVerticalRelImpl(float y) {
		this.y = y;
	}

	/** {@inheritDoc} */
	@Override
	public short getPathSegType() {
		return PATHSEG_LINETO_VERTICAL_REL;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "v";
	}

	/** {@inheritDoc} */
	@Override
	public float getY() {
		return y;
	}

	/** {@inheritDoc} */
	@Override
	public void setY(float y) {
		this.y = y;
	}
}
