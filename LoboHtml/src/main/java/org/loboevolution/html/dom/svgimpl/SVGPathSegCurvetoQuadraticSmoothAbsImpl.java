package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegCurvetoQuadraticSmoothAbs;

/**
 * <p>SVGPathSegCurvetoQuadraticSmoothAbsImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGPathSegCurvetoQuadraticSmoothAbsImpl implements SVGPathSegCurvetoQuadraticSmoothAbs {

	private float x;

	private float y;

	/**
	 * <p>Constructor for SVGPathSegCurvetoQuadraticSmoothAbsImpl.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 */
	public SVGPathSegCurvetoQuadraticSmoothAbsImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/** {@inheritDoc} */
	@Override
	public short getPathSegType() {
		return PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "T";
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

}
