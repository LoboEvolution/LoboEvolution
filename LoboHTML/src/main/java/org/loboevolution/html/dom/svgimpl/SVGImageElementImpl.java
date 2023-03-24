/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.svg.*;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.info.TimingInfo;
import org.loboevolution.net.HttpNetwork;

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
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGImageElementImpl(final String name) {
		super(name);
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
		TimingInfo info = new TimingInfo();
		Image image = HttpNetwork.getImage(this, info, false);
		int realWidth = image.getWidth(null);
		int realHeight = image.getHeight(null);

		float drawWidth = getWidth().getAnimVal().getValue();
		float drawHeight = getHeight().getAnimVal().getValue();

		AffineTransform at = new AffineTransform();
		at.translate(getX().getAnimVal().getValue(), getY().getAnimVal().getValue());
		at.scale(drawWidth / realWidth, drawHeight / realHeight);
		graphics.drawImage(image, at, null);

		final HtmlRendererContext htmlRendererContext = this.getHtmlRendererContext();
		final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
		htmlPanel.getBrowserPanel().getTimingList.add(info);
	}

	/** {@inheritDoc} */
	@Override
	public Shape createShape(AffineTransform transform) {
		return null;
	}
}
