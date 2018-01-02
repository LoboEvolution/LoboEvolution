/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
import org.lobobrowser.w3c.svg.SVGPathSegCurvetoQuadraticRel;

public class SVGPathSegCurvetoQuadraticRelImpl extends SVGPathSegImpl implements SVGPathSegCurvetoQuadraticRel {

	private static final long serialVersionUID = -6722776032077341870L;

	private float x1;
	private float y1;

	/**
	 * @param x2
	 * @param y2
	 * @param x12
	 * @param y12
	 */
	public SVGPathSegCurvetoQuadraticRelImpl(float x2, float y2, float x12, float y12) {
		this.x = x2;
		this.y = y2;
		this.x1 = x12;
		this.y1 = y12;
	}

	@Override
	public short getPathSegType() {
		return SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "q";
	}

	@Override
	public float getX1() {
		return x1;
	}

	@Override
	public void setX1(float x1) {
		this.x1 = x1;
	}

	@Override
	public float getY1() {
		return y1;
	}

	@Override
	public void setY1(float y1) {
		this.y1 = y1;
	}
}
