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

public interface SVGTransform {
	// Transform Types
	static final short SVG_TRANSFORM_UNKNOWN = 0;
	static final short SVG_TRANSFORM_MATRIX = 1;
	static final short SVG_TRANSFORM_TRANSLATE = 2;
	static final short SVG_TRANSFORM_SCALE = 3;
	static final short SVG_TRANSFORM_ROTATE = 4;
	static final short SVG_TRANSFORM_SKEWX = 5;
	static final short SVG_TRANSFORM_SKEWY = 6;

	short getType();

	SVGMatrix getMatrix();

	float getAngle();

	void setMatrix(SVGMatrix matrix);

	void setTranslate(float tx, float ty);

	void setScale(float sx, float sy);

	void setRotate(float angle, float cx, float cy);

	void setSkewX(float angle);

	void setSkewY(float angle);
}
