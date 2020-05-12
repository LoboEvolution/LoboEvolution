package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegLinetoHorizontalAbs;

/**
 * <p>SVGPathSegLinetoHorizontalAbsImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGPathSegLinetoHorizontalAbsImpl implements SVGPathSegLinetoHorizontalAbs {

	private float x;

	/**
	 * <p>Constructor for SVGPathSegLinetoHorizontalAbsImpl.</p>
	 *
	 * @param x a float.
	 */
	public SVGPathSegLinetoHorizontalAbsImpl(float x) {
		this.x = x;
	}

	/** {@inheritDoc} */
	@Override
	public short getPathSegType() {
		return PATHSEG_LINETO_HORIZONTAL_ABS;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "H";
	}

	/** {@inheritDoc} */
	@Override
	public float getX() {
		return x;
	}

	/** {@inheritDoc} */
	@Override
	public void setX(float x) {
		this.x = x;
	}
}
