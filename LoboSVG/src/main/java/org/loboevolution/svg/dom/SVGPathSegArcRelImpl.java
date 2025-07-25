/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.svg.dom;

import org.loboevolution.svg.SVGPathSegArcRel;

/**
 * <p>SVGPathSegArcRelImpl class.</p> 
 */
public class SVGPathSegArcRelImpl implements SVGPathSegArcRel {
	
	private float x;
	
	private float y;
	
	private float r1;
	
	private float r2;
	
	private float angle;
	
	private boolean largeArcFlag;
	
	private boolean sweepFlag;

	/**
	 * <p>Constructor for SVGPathSegArcRelImpl.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param r1 a float.
	 * @param r2 a float.
	 * @param angle a float.
	 * @param largeArcFlag a boolean.
	 * @param sweepFlag a boolean.
	 */
	public SVGPathSegArcRelImpl(final float x, final float y, final float r1, final float r2, final float angle, final boolean largeArcFlag, final boolean sweepFlag) {
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
		return PATHSEG_ARC_REL;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "a";
	}

	/** {@inheritDoc} */
	@Override
	public float getX() {
		return x;
	}

	/** {@inheritDoc} */
	@Override
	public void setX(final float x) {
		this.x = x;
	}

	/** {@inheritDoc} */
	@Override
	public float getY() {
		return y;
	}

	/** {@inheritDoc} */
	@Override
	public void setY(final float y) {
		this.y = y;
	}

	/** {@inheritDoc} */
	@Override
	public float getR1() {
		return r1;
	}

	/** {@inheritDoc} */
	@Override
	public void setR1(final float r1) {
		this.r1 = r1;
	}

	/** {@inheritDoc} */
	@Override
	public float getR2() {
		return r2;
	}

	/** {@inheritDoc} */
	@Override
	public void setR2(final float r2) {
		this.r2 = r2;
	}

	/** {@inheritDoc} */
	@Override
	public float getAngle() {
		return angle;
	}

	/** {@inheritDoc} */
	@Override
	public void setAngle(final float angle) {
		this.angle = angle;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getLargeArcFlag() {
		return largeArcFlag;
	}

	/** {@inheritDoc} */
	@Override
	public void setLargeArcFlag(final boolean largeArcFlag) {
		this.largeArcFlag = largeArcFlag;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getSweepFlag() {
		return sweepFlag;
	}

	/** {@inheritDoc} */
	@Override
	public void setSweepFlag(final boolean sweepFlag) {
		this.sweepFlag = sweepFlag;
	}
}
