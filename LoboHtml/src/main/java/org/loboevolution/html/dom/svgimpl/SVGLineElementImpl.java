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
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;

import org.loboevolution.html.dom.svg.SVGAnimatedLength;
import org.loboevolution.html.dom.svg.SVGLineElement;
import org.loboevolution.html.dom.svg.SVGMatrix;

/**
 * <p>SVGLineElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGLineElementImpl extends SVGGraphic implements SVGLineElement {

	/**
	 * <p>Constructor for SVGLineElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGLineElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getX1() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("x1")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getY1() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("y1")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getX2() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("x2")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getY2() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("y2")));
	}

	/** {@inheritDoc} */
	@Override
	public void draw(Graphics2D graphics) {
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
		GeneralPath path = new GeneralPath();
		float x1 = ((SVGLengthImpl) getX1().getAnimVal()).getTransformedLength(inverseTransform);
		float y1 = ((SVGLengthImpl) getY1().getAnimVal()).getTransformedLength(inverseTransform);
		float x2 = ((SVGLengthImpl) getX2().getAnimVal()).getTransformedLength(inverseTransform);
		float y2 = ((SVGLengthImpl) getY2().getAnimVal()).getTransformedLength(inverseTransform);
		path.moveTo(x1, y1);
		path.lineTo(x2, y2);
		return path;
	}
}
