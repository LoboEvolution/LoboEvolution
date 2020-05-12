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
