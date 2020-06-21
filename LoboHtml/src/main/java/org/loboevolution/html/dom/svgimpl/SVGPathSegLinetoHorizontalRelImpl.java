package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegLinetoHorizontalRel;

/**
 * <p>SVGPathSegLinetoHorizontalRelImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGPathSegLinetoHorizontalRelImpl implements SVGPathSegLinetoHorizontalRel {

	private float x;

	/**
	 * <p>Constructor for SVGPathSegLinetoHorizontalRelImpl.</p>
	 *
	 * @param x a float.
	 */
	public SVGPathSegLinetoHorizontalRelImpl(float x) {
		this.x = x;
	}

	/** {@inheritDoc} */
	@Override
	public short getPathSegType() {
		return PATHSEG_LINETO_HORIZONTAL_REL;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "h";
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
