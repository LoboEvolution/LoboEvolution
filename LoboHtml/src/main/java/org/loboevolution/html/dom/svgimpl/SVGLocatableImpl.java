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

import org.loboevolution.html.dom.svg.SVGElement;
import org.loboevolution.html.dom.svg.SVGException;
import org.loboevolution.html.dom.svg.SVGLocatable;
import org.loboevolution.html.dom.svg.SVGMatrix;
import org.loboevolution.html.dom.svg.SVGRect;
import org.loboevolution.html.dom.svg.SVGTransform;
import org.loboevolution.html.dom.svg.SVGTransformList;
import org.loboevolution.html.dom.svg.SVGTransformable;
import org.w3c.dom.Node;

/**
 * <p>SVGLocatableImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGLocatableImpl extends SVGStylableImpl implements SVGLocatable {

	/**
	 * <p>Constructor for SVGLocatableImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGLocatableImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public SVGElement getNearestViewportElement() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public SVGElement getFarthestViewportElement() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public SVGRect getBBox() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix getCTM() {
		SVGMatrix thisMatrix = new SVGMatrixImpl();
		if (this instanceof SVGTransformable) {
			SVGTransformList transofrm = ((SVGTransformable) this).getTransform().getAnimVal();
			if (transofrm != null) {
				SVGTransform consolidate = transofrm.consolidate();
				if (consolidate != null) {
					thisMatrix = new SVGMatrixImpl((SVGMatrixImpl) consolidate.getMatrix());
				}
			}
		}

		Node parent = getParentNode();
		while (parent != null && parent != getViewportElement() && !(parent instanceof SVGTransformable)) {
			parent = parent.getParentNode();
		}

		if (parent instanceof SVGTransformable) {
			SVGMatrix parentMatrix = ((SVGTransformable) parent).getCTM();
			return parentMatrix.multiply(thisMatrix);
		} else {
			return thisMatrix;
		}
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix getScreenCTM() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix getTransformToElement(SVGElement element) throws SVGException {
		// TODO Auto-generated method stub
		return null;
	}

}
