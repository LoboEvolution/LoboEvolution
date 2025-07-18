/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.svg.dom;

import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.info.TimingInfo;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.svg.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

/**
 * <p>SVGImageElementImpl class.</p>
 */
public class SVGImageElementImpl extends SVGGraphic implements SVGImageElement {

	/**
	 * <p>Constructor for SVGImageElementImpl.</p>
	 *
	 * @param element a {@link HTMLElement} object.
	 */
	public SVGImageElementImpl(final HTMLElement element) {
		super(element);
	}

	@Override
	public SVGRect getBBox() {

		AffineTransform ctm = getCTM().getAffineTransform();
		AffineTransform inverseTransform;
		try {
			inverseTransform = ctm.createInverse();
		} catch (NoninvertibleTransformException e) {
			inverseTransform = null;
		}
		float x = ((SVGLengthImpl) getX().getAnimVal()).getTransformedLength(inverseTransform);
		float y = ((SVGLengthImpl) getY().getAnimVal()).getTransformedLength(inverseTransform);
		float width = ((SVGLengthImpl) getWidth().getAnimVal()).getTransformedLength(inverseTransform);
		float height = ((SVGLengthImpl) getHeight().getAnimVal()).getTransformedLength(inverseTransform);

		return new SVGRectImpl(x, y, width, height);
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedString getHref() {
		return new SVGAnimatedStringImpl(getAttribute("href"));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getX() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("x")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getY() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("y")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getWidth() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("width")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getHeight() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("height")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void draw(final Graphics2D graphics) {
		final TimingInfo info = new TimingInfo();
		final Image image = HttpNetwork.getImage(this, info, false);
		final int realWidth = image.getWidth(null);
		final int realHeight = image.getHeight(null);

		final float drawWidth = getWidth().getAnimVal().getValue();
		final float drawHeight = getHeight().getAnimVal().getValue();

		final AffineTransform at = new AffineTransform();
		at.translate(getX().getAnimVal().getValue(), getY().getAnimVal().getValue());
		at.scale(drawWidth / realWidth, drawHeight / realHeight);
		graphics.drawImage(image, at, null);
	}

	/** {@inheritDoc} */
	@Override
	public Shape createShape(final AffineTransform transform) {
		return null;
	}
}
