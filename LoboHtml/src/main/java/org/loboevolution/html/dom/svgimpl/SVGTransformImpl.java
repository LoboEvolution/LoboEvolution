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

package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGMatrix;
import org.loboevolution.html.dom.svg.SVGTransform;

/**
 * <p>SVGTransformImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGTransformImpl implements SVGTransform {

	private short transformType;
	private SVGMatrix matrix;

	/**
	 * <p>Constructor for SVGTransformImpl.</p>
	 */
	public SVGTransformImpl() {
		this.transformType = SVGTransform.SVG_TRANSFORM_UNKNOWN;
	}

	/**
	 * <p>Constructor for SVGTransformImpl.</p>
	 *
	 * @param transformType a short.
	 */
	public SVGTransformImpl(short transformType) {
		this.transformType = transformType;
	}

	/** {@inheritDoc} */
	@Override
	public short getType() {
		return transformType;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix getMatrix() {
		return matrix;
	}

	/** {@inheritDoc} */
	@Override
	public float getAngle() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void setMatrix(SVGMatrix matrix) {
		this.matrix = matrix;

	}

	/** {@inheritDoc} */
	@Override
	public void setTranslate(float tx, float ty) {
		this.transformType = SVGTransform.SVG_TRANSFORM_TRANSLATE;
		this.matrix = new SVGMatrixImpl(1, 0, 0, 1, tx, ty);

	}

	/** {@inheritDoc} */
	@Override
	public void setScale(float sx, float sy) {
		this.transformType = SVGTransform.SVG_TRANSFORM_SCALE;
		this.matrix = new SVGMatrixImpl(sx, 0.0f, 0.0f, sy, 0.0f, 0.0f);
	}

	/** {@inheritDoc} */
	@Override
	public void setRotate(float angle, float cx, float cy) {
		this.transformType = SVGTransform.SVG_TRANSFORM_ROTATE;
		this.matrix = new SVGMatrixImpl(angle, cx, cy, 0.0f, 0.0f, 0.0f);
	}

	/** {@inheritDoc} */
	@Override
	public void setSkewX(float angle) {
		this.transformType = SVGTransform.SVG_TRANSFORM_SKEWX;
		this.matrix = new SVGMatrixImpl(1.0f, 0.0f, (float) Math.tan(angle), 1.0f, 0.0f, 0.0f);

	}

	/** {@inheritDoc} */
	@Override
	public void setSkewY(float angle) {
		this.transformType = SVGTransform.SVG_TRANSFORM_SKEWY;
		this.matrix = new SVGMatrixImpl(1.0f, (float) Math.tan(angle), 0.0f, 1.0f, 0.0f, 0.0f);

	}
}
