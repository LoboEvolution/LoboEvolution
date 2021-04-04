/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegArcRel;

/**
 * <p>SVGPathSegArcRelImpl class.</p>
 *
 *
 *
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
	public SVGPathSegArcRelImpl(float x, float y, float r1, float r2, float angle, boolean largeArcFlag, boolean sweepFlag) {
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
