package org.loboevolution.html.dom.svgimpl;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

import org.loboevolution.html.dom.svg.SVGException;
import org.loboevolution.html.dom.svg.SVGMatrix;
import org.w3c.dom.DOMException;

public class SVGMatrixImpl implements SVGMatrix {
	
	private AffineTransform transform;

	public SVGMatrixImpl() {
		transform = new AffineTransform();
	}

	public SVGMatrixImpl(SVGMatrixImpl matrix) {
		transform = new AffineTransform(matrix.transform);
	}

	@Override
	public float getA() {
		return (float) transform.getScaleX();
	}

	@Override
	public void setA(float a) throws DOMException {
		final float b = getB();
		final float c = getC();
		final float d = getD();
		final float e = getE();
		final float f = getF();
		transform = new AffineTransform(a, b, c, d, e, f);

	}

	@Override
	public float getB() {
		return (float) transform.getShearY();
	}

	@Override
	public void setB(float b) throws DOMException {
		final float a = getA();
		final float c = getC();
		final float d = getD();
		final float e = getE();
		final float f = getF();
		transform = new AffineTransform(a, b, c, d, e, f);

	}

	@Override
	public float getC() {
		return (float) transform.getShearX();
	}

	@Override
	public void setC(float c) throws DOMException {
		final float a = getA();
		final float b = getB();
		final float d = getD();
		final float e = getE();
		final float f = getF();
		transform = new AffineTransform(a, b, c, d, e, f);

	}

	@Override
	public float getD() {
		return (float) transform.getScaleY();
	}

	@Override
	public void setD(float d) throws DOMException {
		final float a = getA();
		final float b = getB();
		final float c = getC();
		final float e = getE();
		final float f = getF();
		transform = new AffineTransform(a, b, c, d, e, f);

	}

	@Override
	public float getE() {
		return (float) transform.getTranslateX();
	}

	@Override
	public void setE(float e) throws DOMException {
		final float a = getA();
		final float b = getB();
		final float c = getC();
		final float d = getD();
		final float f = getF();
		transform = new AffineTransform(a, b, c, d, e, f);

	}

	@Override
	public float getF() {
		return (float) transform.getTranslateY();
	}

	@Override
	public void setF(float f) throws DOMException {
		final float a = getA();
		final float b = getB();
		final float c = getC();
		final float d = getD();
		final float e = getE();
		transform = new AffineTransform(a, b, c, d, e, f);
	}

	@Override
	public SVGMatrix multiply(SVGMatrix secondMatrix) {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.concatenate(((SVGMatrixImpl) secondMatrix).transform);
		return result;
	}

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

	@Override
	public SVGMatrix translate(float x, float y) {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.translate(x, y);
		return result;
	}

	@Override
	public SVGMatrix scale(float scaleFactor) {
		return scaleNonUniform(scaleFactor, scaleFactor);
	}

	@Override
	public SVGMatrix scaleNonUniform(float scaleFactorX, float scaleFactorY) {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.scale(scaleFactorX, scaleFactorY);
		return result;
	}

	@Override
	public SVGMatrix rotate(float angle) {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.rotate(Math.toRadians(angle));
		return result;
	}

	@Override
	public SVGMatrix rotateFromVector(float x, float y) throws SVGException {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		double angle = Math.atan(y / x);
		result.transform.rotate(angle);
		return result;
	}

	@Override
	public SVGMatrix flipX() {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.concatenate(new AffineTransform(-1, 0, 0, 1, 0, 0));
		return result;
	}

	@Override
	public SVGMatrix flipY() {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.concatenate(new AffineTransform(1, 0, 0, -1, 0, 0));
		return result;
	}

	@Override
	public SVGMatrix skewX(float angle) {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.concatenate(new AffineTransform(1, 0, Math.tan(Math.toRadians(angle)), 1, 0, 0));
		return result;
	}

	@Override
	public SVGMatrix skewY(float angle) {
		SVGMatrixImpl result = new SVGMatrixImpl(this);
		result.transform.concatenate(new AffineTransform(1, Math.tan(Math.toRadians(angle)), 0, 1, 0, 0));
		return result;
	}
	
	public AffineTransform getAffineTransform() {
		return transform;
	}

}
