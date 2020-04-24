/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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

import java.awt.geom.AffineTransform;

import org.w3c.dom.DOMException;

public interface SVGMatrix {

	float getA();

	void setA(float a) throws DOMException;

	float getB();

	void setB(float b) throws DOMException;

	float getC();

	void setC(float c) throws DOMException;

	float getD();

	void setD(float d) throws DOMException;

	float getE();

	void setE(float e) throws DOMException;

	float getF();

	void setF(float f) throws DOMException;

	SVGMatrix multiply(SVGMatrix secondMatrix);

	SVGMatrix inverse() throws SVGException;

	SVGMatrix translate(float x, float y);

	SVGMatrix scale(float scaleFactor);

	SVGMatrix scaleNonUniform(float scaleFactorX, float scaleFactorY);

	SVGMatrix rotate(float angle);

	SVGMatrix rotateFromVector(float x, float y) throws SVGException;

	SVGMatrix flipX();

	SVGMatrix flipY();

	SVGMatrix skewX(float angle);

	SVGMatrix skewY(float angle);

	AffineTransform getAffineTransform();
}
