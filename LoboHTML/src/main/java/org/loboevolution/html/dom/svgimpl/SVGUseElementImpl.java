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

import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.dom.svg.*;
import org.loboevolution.html.node.Element;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

/**
 * <p>SVGUseElementImpl class.</p>
 */
public class SVGUseElementImpl extends SVGGraphic implements SVGUseElement {
	
	private Graphics2D graphics;

	/**
	 * <p>Constructor for SVGUseElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGUseElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedString getHref() {
		String href = this.getAttribute("xlink:href");
		if (href == null) {
			href = this.getAttribute("href");
		}
		return new SVGAnimatedStringImpl(href);
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
	public SVGElementInstance getInstanceRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public SVGElementInstance getAnimatedInstanceRoot() {
		// TODO Auto-generated method stub
		return null;
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
	public void draw(final Graphics2D graphics) {
		this.graphics = graphics;
		final SVGMatrix ctm = getCTM();
		createShape(ctm.getAffineTransform());
	}

	/** {@inheritDoc} */
	@Override
	public Shape createShape(AffineTransform transform) {
		String href = getHref().getBaseVal();
		if (href.toLowerCase().indexOf("#") != -1) {
			int hashIndex = href.indexOf('#');
			if (hashIndex != -1) {
				String idElement = href.substring(hashIndex + 1);
				Element elementById = (Element) child(idElement);
				if (elementById instanceof SVGSymbolElementImpl) {
					SVGSymbolElementImpl symbol = (SVGSymbolElementImpl) elementById;
					NodeListImpl children = (NodeListImpl) symbol.getChildNodes();
					children.forEach(child -> {
						if (child instanceof Drawable) {
							Drawable drawable = (Drawable) child;
							drawable.draw(graphics);
						}
					});
				} else if (elementById instanceof Drawable) {
					Drawable drawable = (Drawable) elementById;
					drawable.draw(graphics);
				}
			}
		}
		return null;
	}
}
