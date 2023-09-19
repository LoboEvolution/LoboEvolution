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

package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedLength;
import org.loboevolution.html.dom.svg.SVGEllipseElement;
import org.loboevolution.html.dom.svg.SVGMatrix;
import org.loboevolution.html.dom.svg.SVGRect;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;

/**
 * <p>SVGEllipseElementImpl class.</p>
 */
public class SVGEllipseElementImpl extends SVGGraphic implements SVGEllipseElement {

	/**
	 * <p>Constructor for SVGEllipseElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGEllipseElementImpl(final String name) {
		super(name);
	}

	@Override
	public SVGRect getBBox() {
		Shape shape = createShape(null);
		return new SVGRectImpl(shape.getBounds2D());
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getCx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("cx")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getCy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("cy")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getRx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("rx")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getRy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("ry")));
	}

	/** {@inheritDoc} */
	@Override
	public void draw(final Graphics2D graphics) {
		final SVGMatrix ctm = getCTM();
		final Shape shape = createShape(ctm.getAffineTransform());
		animate(this);
		drawable(graphics, shape);
	}

	/** {@inheritDoc} */
	@Override
	public Shape createShape(AffineTransform transform) {
		AffineTransform inverseTransform;
		try {
			inverseTransform = transform.createInverse();
		} catch (NoninvertibleTransformException e) {
			inverseTransform = null;
		}
		float cx = ((SVGLengthImpl) getCx().getAnimVal()).getTransformedLength(inverseTransform);
		float cy = ((SVGLengthImpl) getCy().getAnimVal()).getTransformedLength(inverseTransform);
		float rx = ((SVGLengthImpl) getRx().getAnimVal()).getTransformedLength(inverseTransform);
		float ry = ((SVGLengthImpl) getRy().getAnimVal()).getTransformedLength(inverseTransform);
		Ellipse2D ellipse = new Ellipse2D.Float(cx - rx, cy - ry, 2 * rx, 2 * ry);
		return ellipse;
	}
}
