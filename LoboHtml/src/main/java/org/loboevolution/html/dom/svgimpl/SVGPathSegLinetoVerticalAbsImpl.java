package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegLinetoVerticalAbs;

/**
 * <p>SVGPathSegLinetoVerticalAbsImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGPathSegLinetoVerticalAbsImpl implements SVGPathSegLinetoVerticalAbs {

	private float y;

	/**
	 * <p>Constructor for SVGPathSegLinetoVerticalAbsImpl.</p>
	 *
	 * @param y a float.
	 */
	public SVGPathSegLinetoVerticalAbsImpl(float y) {
		this.y = y;
	}

	/** {@inheritDoc} */
	@Override
	public short getPathSegType() {
		return PATHSEG_LINETO_VERTICAL_ABS;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "V";
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
