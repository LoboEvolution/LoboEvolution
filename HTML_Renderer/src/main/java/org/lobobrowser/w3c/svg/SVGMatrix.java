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
package org.lobobrowser.w3c.svg;

import org.w3c.dom.DOMException;

public interface SVGMatrix {
	public float getA();

	public void setA(float a) throws DOMException;

	public float getB();

	public void setB(float b) throws DOMException;

	public float getC();

	public void setC(float c) throws DOMException;

	public float getD();

	public void setD(float d) throws DOMException;

	public float getE();

	public void setE(float e) throws DOMException;

	public float getF();

	public void setF(float f) throws DOMException;

	public SVGMatrix multiply(SVGMatrix secondMatrix);

	public SVGMatrix inverse() throws SVGException;

	public SVGMatrix translate(float x, float y);

	public SVGMatrix scale(float scaleFactor);

	public SVGMatrix scaleNonUniform(float scaleFactorX, float scaleFactorY);

	public SVGMatrix rotate(float angle);

	public SVGMatrix rotateFromVector(float x, float y) throws SVGException;

	public SVGMatrix flipX();

	public SVGMatrix flipY();

	public SVGMatrix skewX(float angle);

	public SVGMatrix skewY(float angle);
}
