/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */package org.lobobrowser.html.dom.svgimpl;

import org.lobobrowser.html.dom.svg.SVGPathSeg;
import org.lobobrowser.html.dom.svg.SVGPathSegCurvetoCubicSmoothRel;

public class SVGPathSegCurvetoCubicSmoothRelImpl extends SVGPathSegImpl implements SVGPathSegCurvetoCubicSmoothRel {

	private static final long serialVersionUID = -6722776032077341870L;

	private float x2;
	private float y2;

	/**
	 * @param x3
	 * @param y3
	 * @param x22
	 * @param y22
	 */
	public SVGPathSegCurvetoCubicSmoothRelImpl(float x3, float y3, float x22, float y22) {
		this.x = x3;
		this.y = y3;
		this.x2 = x22;
		this.y2 = y22;
	}

	@Override
	public short getPathSegType() {
		return SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "s";
	}

	@Override
	public float getX2() {
		return x2;
	}

	@Override
	public void setX2(float x2) {
		this.x2 = x2;
	}

	@Override
	public float getY2() {
		return y2;
	}

	@Override
	public void setY2(float y2) {
		this.y2 = y2;
	}
}
