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

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.dom.svg.Drawable;
import org.loboevolution.html.dom.svg.SVGAnimatedLength;
import org.loboevolution.html.dom.svg.SVGAnimatedString;
import org.loboevolution.html.dom.svg.SVGElementInstance;
import org.loboevolution.html.dom.svg.SVGMatrix;
import org.loboevolution.html.dom.svg.SVGUseElement;
import org.loboevolution.html.node.Element;

/**
 * <p>SVGUseElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGUseElementImpl extends SVGGraphic implements SVGUseElement {
	
	private Graphics2D graphics;

	/**
	 * <p>Constructor for SVGUseElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGUseElementImpl(String name) {
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

	/** {@inheritDoc} */
	@Override
	public void draw(Graphics2D graphics) {
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
