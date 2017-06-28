/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */package org.lobobrowser.html.svgimpl;

import org.lobobrowser.w3c.svg.SVGPathSeg;
import org.lobobrowser.w3c.svg.SVGPathSegArcRel;

public class SVGPathSegArcRelImpl extends SVGPathSegImpl implements SVGPathSegArcRel {

	private static final long serialVersionUID = -6722776032077341870L;

	private float r1;
	private float r2;
	private float angle;
	private boolean sweepFlag;
	private boolean largeArcFlag;

	/**
	 * @param x2
	 * @param y2
	 * @param r12
	 * @param r22
	 * @param angle2
	 * @param largeArcFlag2
	 * @param sweepFlag2
	 */
	public SVGPathSegArcRelImpl(float x2, float y2, float r12, float r22, float angle2, boolean largeArcFlag2,
			boolean sweepFlag2) {
		this.x = x2;
		this.y = y2;
		this.r1 = r12;
		this.r2 = r22;
		this.angle = angle2;
		this.sweepFlag = sweepFlag2;
		this.largeArcFlag = largeArcFlag2;
	}

	@Override
	public short getPathSegType() {
		return SVGPathSeg.PATHSEG_ARC_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "a";
	}

	@Override
	public float getR1() {
		return r1;
	}

	@Override
	public void setR1(float r1) {
		this.r1 = r1;
	}

	@Override
	public float getR2() {
		return r2;
	}

	@Override
	public void setR2(float r2) {
		this.r2 = r2;
	}

	@Override
	public float getAngle() {
		return angle;
	}

	@Override
	public void setAngle(float angle) {
		this.angle = angle;
	}

	@Override
	public boolean getLargeArcFlag() {
		return largeArcFlag;
	}

	@Override
	public void setLargeArcFlag(boolean largeArcFlag) {
		this.largeArcFlag = largeArcFlag;
	}

	@Override
	public boolean getSweepFlag() {
		return sweepFlag;
	}

	@Override
	public void setSweepFlag(boolean sweepFlag) {
		this.sweepFlag = sweepFlag;
	}
}
