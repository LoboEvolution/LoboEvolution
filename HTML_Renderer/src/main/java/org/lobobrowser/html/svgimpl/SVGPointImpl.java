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

import org.lobobrowser.w3c.svg.SVGMatrix;
import org.lobobrowser.w3c.svg.SVGPoint;
import org.w3c.dom.DOMException;

public class SVGPointImpl implements SVGPoint {
	
	private float x;
	private float y;

	public SVGPointImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) throws DOMException {
		this.x = x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setY(float y) throws DOMException {
		this.y = y;
	}

	@Override
	public SVGPoint matrixTransform(SVGMatrix matrix) {
		// TODO Auto-generated method stub
		return null;
	}

}
