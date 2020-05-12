package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGRect;
import org.w3c.dom.DOMException;

/**
 * <p>SVGRectImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGRectImpl implements SVGRect {

	private float x;

	private float y;

	private float width;

	private float height;

	/**
	 * <p>Constructor for SVGRectImpl.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param width a float.
	 * @param height a float.
	 */
	public SVGRectImpl(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/** {@inheritDoc} */
	@Override
	public float getX() {
		return x;
	}

	/** {@inheritDoc} */
	@Override
	public void setX(float x) throws DOMException {
		this.x = x;
	}

	/** {@inheritDoc} */
	@Override
	public float getY() {
		return y;
	}

	/** {@inheritDoc} */
	@Override
	public void setY(float y) throws DOMException {
		this.y = y;
	}

	/** {@inheritDoc} */
	@Override
	public float getWidth() {
		return width;
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(float width) throws DOMException {
		this.width = width;
	}

	/** {@inheritDoc} */
	@Override
	public float getHeight() {
		return height;
	}

	/** {@inheritDoc} */
	@Override
	public void setHeight(float height) throws DOMException {
		this.height = height;
	}

}
