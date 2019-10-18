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
 */
package org.loboevolution.html.dom.svg;

import org.w3c.dom.DOMException;

public interface SVGPathSegCurvetoCubicAbs extends SVGPathSeg {
	@Override
	public float getX();

	@Override
	public void setX(float x) throws DOMException;

	@Override
	public float getY();

	@Override
	public void setY(float y) throws DOMException;

	public float getX1();

	public void setX1(float x1) throws DOMException;

	public float getY1();

	public void setY1(float y1) throws DOMException;

	public float getX2();

	public void setX2(float x2) throws DOMException;

	public float getY2();

	public void setY2(float y2) throws DOMException;
}
