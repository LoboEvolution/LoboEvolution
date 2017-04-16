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
 */

package org.lobobrowser.html.svgimpl;

import org.lobobrowser.w3c.svg.SVGPathSeg;
import org.lobobrowser.w3c.svg.SVGPathSegCurvetoCubicSmoothAbs;

/**
 * Draws a cubic Bézier curve from the current point to (x,y). The first
control point is assumed to be the reflection of the second control
point on the previous command relative to the current point. (If there
is no previous command or if the previous command was not an C,
c, S or s, assume the first control point is coincident with the current
point.) (x2,y2) is the second control point (i.e., the control point at
the end of the curve). S (uppercase) indicates that absolute
coordinates will follow; s (lowercase) indicates that relative
coordinates will follow. 
 * 
 */
public class SVGPathSegCurvetoCubicSmoothAbsImpl extends SVGPathSegImpl implements SVGPathSegCurvetoCubicSmoothAbs {

	private static final long serialVersionUID = -6722776032077341870L;
	
	private float x2;
	private float y2;

	/**
	 * @param x
	 * @param y
	 * @param x2
	 * @param y2
	 */
	public SVGPathSegCurvetoCubicSmoothAbsImpl(float x2, float y2, float x, float y) {
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "S";
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
