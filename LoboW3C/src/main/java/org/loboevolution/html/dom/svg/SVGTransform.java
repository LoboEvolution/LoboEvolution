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

/**
 * <p>SVGTransform interface.</p>
 *
 *
 *
 */
public interface SVGTransform {
	// Transform Types
	/** Constant SVG_TRANSFORM_UNKNOWN=0 */
    short SVG_TRANSFORM_UNKNOWN = 0;
	/** Constant SVG_TRANSFORM_MATRIX=1 */
    short SVG_TRANSFORM_MATRIX = 1;
	/** Constant SVG_TRANSFORM_TRANSLATE=2 */
    short SVG_TRANSFORM_TRANSLATE = 2;
	/** Constant SVG_TRANSFORM_SCALE=3 */
    short SVG_TRANSFORM_SCALE = 3;
	/** Constant SVG_TRANSFORM_ROTATE=4 */
    short SVG_TRANSFORM_ROTATE = 4;
	/** Constant SVG_TRANSFORM_SKEWX=5 */
    short SVG_TRANSFORM_SKEWX = 5;
	/** Constant SVG_TRANSFORM_SKEWY=6 */
    short SVG_TRANSFORM_SKEWY = 6;

	/**
	 * <p>getType.</p>
	 *
	 * @return a short.
	 */
	short getType();

	/**
	 * <p>getMatrix.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix getMatrix();

	/**
	 * <p>getAngle.</p>
	 *
	 * @return a float.
	 */
	float getAngle();

	/**
	 * <p>setMatrix.</p>
	 *
	 * @param matrix a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	void setMatrix(SVGMatrix matrix);

	/**
	 * <p>setTranslate.</p>
	 *
	 * @param tx a float.
	 * @param ty a float.
	 */
	void setTranslate(float tx, float ty);

	/**
	 * <p>setScale.</p>
	 *
	 * @param sx a float.
	 * @param sy a float.
	 */
	void setScale(float sx, float sy);

	/**
	 * <p>setRotate.</p>
	 *
	 * @param angle a float.
	 * @param cx a float.
	 * @param cy a float.
	 */
	void setRotate(float angle, float cx, float cy);

	/**
	 * <p>setSkewX.</p>
	 *
	 * @param angle a float.
	 */
	void setSkewX(float angle);

	/**
	 * <p>setSkewY.</p>
	 *
	 * @param angle a float.
	 */
	void setSkewY(float angle);
}
