package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegCurvetoCubicSmoothAbs;

/**
 * <p>SVGPathSegCurvetoCubicSmoothAbsImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGPathSegCurvetoCubicSmoothAbsImpl implements SVGPathSegCurvetoCubicSmoothAbs {

	private float x;
	
	private float y;
	
	private float x2;
	
	private float y2;

	/**
	 * <p>Constructor for SVGPathSegCurvetoCubicSmoothAbsImpl.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x2 a float.
	 * @param y2 a float.
	 */
	public SVGPathSegCurvetoCubicSmoothAbsImpl(float x, float y, float x2, float y2) {
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
	}

	/** {@inheritDoc} */
	@Override
	public short getPathSegType() {
		return PATHSEG_CURVETO_CUBIC_SMOOTH_ABS;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "S";
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

	/** {@inheritDoc} */
	@Override
	public float getX2() {
		return x2;
	}

	/** {@inheritDoc} */
	@Override
	public void setX2(float x2) {
		this.x2 = x2;
	}

	/** {@inheritDoc} */
	@Override
	public float getY2() {
		return y2;
	}

	/** {@inheritDoc} */
	@Override
	public void setY2(float y2) {
		this.y2 = y2;
	}
}
