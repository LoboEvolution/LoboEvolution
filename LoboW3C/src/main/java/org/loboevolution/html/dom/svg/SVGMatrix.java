/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.svg;

import org.htmlunit.cssparser.dom.DOMException;

import java.awt.geom.AffineTransform;



/**
 * <p>SVGMatrix interface.</p>
 *
 *
 *
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
	 * @throws DOMException if any.
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
	 * @throws DOMException if any.
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
	 * @throws DOMException if any.
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
	 * @throws DOMException if any.
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
	 * @throws DOMException if any.
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
	 * @throws DOMException if any.
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
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
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
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
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
