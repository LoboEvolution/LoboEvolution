/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.dom.svg;

import java.awt.geom.AffineTransform;



/**
 * <p>SVGMatrix interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGMatrix {

	/**
	 * <p>getA.</p>
	 *
	 * @return a float.
	 */
	float getA();

	/**
	 * <p>setA.</p>
	 *
	 * @param a a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setA(float a);

	/**
	 * <p>getB.</p>
	 *
	 * @return a float.
	 */
	float getB();

	/**
	 * <p>setB.</p>
	 *
	 * @param b a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setB(float b);

	/**
	 * <p>getC.</p>
	 *
	 * @return a float.
	 */
	float getC();

	/**
	 * <p>setC.</p>
	 *
	 * @param c a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setC(float c);

	/**
	 * <p>getD.</p>
	 *
	 * @return a float.
	 */
	float getD();

	/**
	 * <p>setD.</p>
	 *
	 * @param d a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setD(float d);

	/**
	 * <p>getE.</p>
	 *
	 * @return a float.
	 */
	float getE();

	/**
	 * <p>setE.</p>
	 *
	 * @param e a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setE(float e);

	/**
	 * <p>getF.</p>
	 *
	 * @return a float.
	 */
	float getF();

	/**
	 * <p>setF.</p>
	 *
	 * @param f a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setF(float f);

	/**
	 * <p>multiply.</p>
	 *
	 * @param secondMatrix a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix multiply(SVGMatrix secondMatrix);

	/**
	 * <p>inverse.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGMatrix inverse() throws SVGException;

	/**
	 * <p>translate.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix translate(float x, float y);

	/**
	 * <p>scale.</p>
	 *
	 * @param scaleFactor a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix scale(float scaleFactor);

	/**
	 * <p>scaleNonUniform.</p>
	 *
	 * @param scaleFactorX a float.
	 * @param scaleFactorY a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix scaleNonUniform(float scaleFactorX, float scaleFactorY);

	/**
	 * <p>rotate.</p>
	 *
	 * @param angle a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix rotate(float angle);

	/**
	 * <p>rotateFromVector.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGMatrix rotateFromVector(float x, float y) throws SVGException;

	/**
	 * <p>flipX.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix flipX();

	/**
	 * <p>flipY.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix flipY();

	/**
	 * <p>skewX.</p>
	 *
	 * @param angle a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix skewX(float angle);

	/**
	 * <p>skewY.</p>
	 *
	 * @param angle a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix skewY(float angle);

	/**
	 * <p>getAffineTransform.</p>
	 *
	 * @return a {@link java.awt.geom.AffineTransform} object.
	 */
	AffineTransform getAffineTransform();
}
