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

/**
 * <p>SVGTransform interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGTransform {
	// Transform Types
	/** Constant SVG_TRANSFORM_UNKNOWN=0 */
	static final short SVG_TRANSFORM_UNKNOWN = 0;
	/** Constant SVG_TRANSFORM_MATRIX=1 */
	static final short SVG_TRANSFORM_MATRIX = 1;
	/** Constant SVG_TRANSFORM_TRANSLATE=2 */
	static final short SVG_TRANSFORM_TRANSLATE = 2;
	/** Constant SVG_TRANSFORM_SCALE=3 */
	static final short SVG_TRANSFORM_SCALE = 3;
	/** Constant SVG_TRANSFORM_ROTATE=4 */
	static final short SVG_TRANSFORM_ROTATE = 4;
	/** Constant SVG_TRANSFORM_SKEWX=5 */
	static final short SVG_TRANSFORM_SKEWX = 5;
	/** Constant SVG_TRANSFORM_SKEWY=6 */
	static final short SVG_TRANSFORM_SKEWY = 6;

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
