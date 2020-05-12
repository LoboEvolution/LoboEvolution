package org.loboevolution.html.dom.svgimpl;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

import org.loboevolution.html.dom.svg.SVGException;
import org.loboevolution.html.dom.svg.SVGMatrix;
import org.w3c.dom.DOMException;

/**
 * <p>SVGMatrixImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
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
	public SVGMatrixImpl(SVGMatrixImpl matrix) {
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
	public SVGMatrixImpl(float a, float b, float c, float d, float e, float f) {
		transform = new AffineTransform(a, b, c, d, e, f);
	}

	/** {@inheritDoc} */
	@Override
	public float getA() {
		return (float) transform.getScaleX();
	}

	/** {@inheritDoc} */
	@Override
	public void setA(float a) throws DOMException {
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
	public void setB(float b) throws DOMException {
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
	public void setC(float c) throws DOMException {
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
	public void setD(float d) throws DOMException {
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
	public void setE(float e) throws DOMException {
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
	public void setF(float f) throws DOMException {
		final float a = getA();
		final float b = getB();
		final float c = getC();
		final float d = getD();
		final float e = getE();
		transform = new AffineTransform(a, b, c, d, e, f);
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix multiply(SVGMatrix secondMatrix) {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.concatenate(((SVGMatrixImpl) secondMatrix).transform);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix inverse() throws SVGException {
		AffineTransform inverse;
		try {
			inverse = this.transform.createInverse();
		} catch (NoninvertibleTransformException e) {
			return null;
		}

		SVGMatrixImpl result = new SVGMatrixImpl();
		result.transform = new AffineTransform(inverse);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix translate(float x, float y) {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.translate(x, y);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix scale(float scaleFactor) {
		return scaleNonUniform(scaleFactor, scaleFactor);
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix scaleNonUniform(float scaleFactorX, float scaleFactorY) {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.scale(scaleFactorX, scaleFactorY);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix rotate(float angle) {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.rotate(Math.toRadians(angle));
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix rotateFromVector(float x, float y) throws SVGException {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		double angle = Math.atan(y / x);
		result.transform.rotate(angle);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix flipX() {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.concatenate(new AffineTransform(-1, 0, 0, 1, 0, 0));
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix flipY() {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.concatenate(new AffineTransform(1, 0, 0, -1, 0, 0));
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix skewX(float angle) {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.concatenate(new AffineTransform(1, 0, Math.tan(Math.toRadians(angle)), 1, 0, 0));
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix skewY(float angle) {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
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
