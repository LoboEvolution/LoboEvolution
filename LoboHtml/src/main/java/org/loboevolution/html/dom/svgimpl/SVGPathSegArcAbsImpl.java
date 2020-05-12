package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegArcAbs;

/**
 * <p>SVGPathSegArcAbsImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGPathSegArcAbsImpl implements SVGPathSegArcAbs {

	private float x;

	private float y;

	private float r1;

	private float r2;

	private float angle;

	private boolean largeArcFlag;

	private boolean sweepFlag;

	/**
	 * <p>Constructor for SVGPathSegArcAbsImpl.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param r1 a float.
	 * @param r2 a float.
	 * @param angle a float.
	 * @param largeArcFlag a boolean.
	 * @param sweepFlag a boolean.
	 */
	public SVGPathSegArcAbsImpl(float x, float y, float r1, float r2, float angle, boolean largeArcFlag, boolean sweepFlag) {
		this.x = x;
		this.y = y;
		this.r1 = r1;
		this.r2 = r2;
		this.angle = angle;
		this.largeArcFlag = largeArcFlag;
		this.sweepFlag = sweepFlag;
	}

	/** {@inheritDoc} */
	@Override
	public short getPathSegType() {
		return PATHSEG_ARC_ABS;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "A";
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
	public float getR1() {
		return r1;
	}

	/** {@inheritDoc} */
	@Override
	public void setR1(float r1) {
		this.r1 = r1;
	}

	/** {@inheritDoc} */
	@Override
	public float getR2() {
		return r2;
	}

	/** {@inheritDoc} */
	@Override
	public void setR2(float r2) {
		this.r2 = r2;
	}

	/** {@inheritDoc} */
	@Override
	public float getAngle() {
		return angle;
	}

	/** {@inheritDoc} */
	@Override
	public void setAngle(float angle) {
		this.angle = angle;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getLargeArcFlag() {
		return largeArcFlag;
	}

	/** {@inheritDoc} */
	@Override
	public void setLargeArcFlag(boolean largeArcFlag) {
		this.largeArcFlag = largeArcFlag;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getSweepFlag() {
		return sweepFlag;
	}

	/** {@inheritDoc} */
	@Override
	public void setSweepFlag(boolean sweepFlag) {
		this.sweepFlag = sweepFlag;
	}
}
