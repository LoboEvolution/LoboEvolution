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
package org.loboevolution.w3c.svg;

public interface SVGTransform {
	// Transform Types
	public static final short SVG_TRANSFORM_UNKNOWN = 0;
	public static final short SVG_TRANSFORM_MATRIX = 1;
	public static final short SVG_TRANSFORM_TRANSLATE = 2;
	public static final short SVG_TRANSFORM_SCALE = 3;
	public static final short SVG_TRANSFORM_ROTATE = 4;
	public static final short SVG_TRANSFORM_SKEWX = 5;
	public static final short SVG_TRANSFORM_SKEWY = 6;

	public short getType();

	public SVGMatrix getMatrix();

	public float getAngle();

	public void setMatrix(SVGMatrix matrix);

	public void setTranslate(float tx, float ty);

	public void setScale(float sx, float sy);

	public void setRotate(float angle, float cx, float cy);

	public void setSkewX(float angle);

	public void setSkewY(float angle);
}
