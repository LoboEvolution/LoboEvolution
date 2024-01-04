/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGException;
import org.loboevolution.html.dom.svg.SVGMatrix;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;


/**
 * <p>SVGMatrixImpl class.</p>
 *
 *
 *
 */
public class SVGMatrixImpl implements SVGMatrix {
	
	private AffineTransform transform;

	/**
	 * <p>Constructor for SVGMatrixImpl.</p>
	 */
	public SVGMatrixImpl() {
		transform = new AffineTransform();
	}

	/**
	 * <p>Constructor for SVGMatrixImpl.</p>
	 *
	 * @param matrix a {@link org.loboevolution.html.dom.svgimpl.SVGMatrixImpl} object.
	 */
	public SVGMatrixImpl(final SVGMatrixImpl matrix) {
		transform = new AffineTransform(matrix.transform);
	}

	/**
	 * <p>Constructor for SVGMatrixImpl.</p>
	 *
	 * @param a a float.
	 * @param b a float.
	 * @param c a float.
	 * @param d a float.
	 * @param e a float.
	 * @param f a float.
	 */
	public SVGMatrixImpl(final float a, final float b, final float c, final float d, final float e, final float f) {
		transform = new AffineTransform(a, b, c, d, e, f);
	}

	/** {@inheritDoc} */
	@Override
	public float getA() {
		return (float) transform.getScaleX();
	}

	/** {@inheritDoc} */
	@Override
	public void setA(final float a) {
		final float b = getB();
		final float c = getC();
		final float d = getD();
		final float e = getE();
		final float f = getF();
		transform = new AffineTransform(a, b, c, d, e, f);

	}

	/** {@inheritDoc} */
	@Override
	public float getB() {
		return (float) transform.getShearY();
	}

	/** {@inheritDoc} */
	@Override
	public void setB(final float b) {
		final float a = getA();
		final float c = getC();
		final float d = getD();
		final float e = getE();
		final float f = getF();
		transform = new AffineTransform(a, b, c, d, e, f);

	}

	/** {@inheritDoc} */
	@Override
	public float getC() {
		return (float) transform.getShearX();
	}

	/** {@inheritDoc} */
	@Override
	public void setC(final float c) {
		final float a = getA();
		final float b = getB();
		final float d = getD();
		final float e = getE();
		final float f = getF();
		transform = new AffineTransform(a, b, c, d, e, f);

	}

	/** {@inheritDoc} */
	@Override
	public float getD() {
		return (float) transform.getScaleY();
	}

	/** {@inheritDoc} */
	@Override
	public void setD(final float d) {
		final float a = getA();
		final float b = getB();
		final float c = getC();
		final float e = getE();
		final float f = getF();
		transform = new AffineTransform(a, b, c, d, e, f);

	}

	/** {@inheritDoc} */
	@Override
	public float getE() {
		return (float) transform.getTranslateX();
	}

	/** {@inheritDoc} */
	@Override
	public void setE(final float e) {
		final float a = getA();
		final float b = getB();
		final float c = getC();
		final float d = getD();
		final float f = getF();
		transform = new AffineTransform(a, b, c, d, e, f);

	}

	/** {@inheritDoc} */
	@Override
	public float getF() {
		return (float) transform.getTranslateY();
	}

	/** {@inheritDoc} */
	@Override
	public void setF(final float f) {
		final float a = getA();
		final float b = getB();
		final float c = getC();
		final float d = getD();
		final float e = getE();
		transform = new AffineTransform(a, b, c, d, e, f);
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix multiply(final SVGMatrix secondMatrix) {
		final SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.concatenate(((SVGMatrixImpl) secondMatrix).transform);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix inverse() throws SVGException {
		final AffineTransform inverse;
		try {
			inverse = this.transform.createInverse();
		} catch (final NoninvertibleTransformException e) {
			return null;
		}

		final SVGMatrixImpl result = new SVGMatrixImpl();
		result.transform = new AffineTransform(inverse);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix translate(final float x, final float y) {
		final SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.translate(x, y);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix scale(final float scaleFactor) {
		return scaleNonUniform(scaleFactor, scaleFactor);
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix scaleNonUniform(final float scaleFactorX, final float scaleFactorY) {
		final SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.scale(scaleFactorX, scaleFactorY);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix rotate(final float angle) {
		final SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.rotate(Math.toRadians(angle));
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix rotateFromVector(final float x, final float y) throws SVGException {
		final SVGMatrixImpl result = new SVGMatrixImpl(this);
		final double angle = Math.atan(y / x);
		result.transform.rotate(angle);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix flipX() {
		final SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.concatenate(new AffineTransform(-1, 0, 0, 1, 0, 0));
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix flipY() {
		final SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.concatenate(new AffineTransform(1, 0, 0, -1, 0, 0));
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix skewX(final float angle) {
		final SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.concatenate(new AffineTransform(1, 0, Math.tan(Math.toRadians(angle)), 1, 0, 0));
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix skewY(final float angle) {
		final SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.concatenate(new AffineTransform(1, Math.tan(Math.toRadians(angle)), 0, 1, 0, 0));
		return result;
	}
	
	/**
	 * <p>getAffineTransform.</p>
	 *
	 * @return a {@link java.awt.geom.AffineTransform} object.
	 */
	public AffineTransform getAffineTransform() {
		return transform;
	}

}
