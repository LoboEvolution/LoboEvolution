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

import org.loboevolution.html.dom.svg.*;
import org.loboevolution.html.node.Node;

import java.awt.geom.AffineTransform;

/**
 * <p>SVGLocatableImpl class.</p>
 */
public abstract class SVGLocatableImpl extends SVGStylableImpl implements SVGLocatable {

	/**
	 * <p>Constructor for SVGLocatableImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGLocatableImpl(final String name) {
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

		AffineTransform screenCTM = getCTM().getAffineTransform();
		SVGSVGElementImpl root = (SVGSVGElementImpl)getOwnerSVGElement();
		SVGSVGElementImpl parentSVGElement = (SVGSVGElementImpl) getOwnerSVGElement();

		if (parentSVGElement != null) {

			AffineTransform viewboxToViewportTransform = parentSVGElement.getViewboxToViewportTransform();
			if (viewboxToViewportTransform != null) {
				screenCTM.preConcatenate(viewboxToViewportTransform);
			}

			screenCTM.preConcatenate(AffineTransform.getTranslateInstance(
					parentSVGElement.getX().getAnimVal().getValue(), parentSVGElement.getY().getAnimVal().getValue()));
		}

		while (parentSVGElement != root) {

			if (parentSVGElement.getParentNode() instanceof SVGLocatable
					&& !(parentSVGElement.getParentNode() instanceof SVGSVGElementImpl)) {
				SVGMatrix ctmMatrix = ((SVGLocatable) parentSVGElement.getParentNode()).getCTM();
				AffineTransform ctm = ctmMatrix.getAffineTransform();
				screenCTM.preConcatenate(ctm);
				parentSVGElement = (SVGSVGElementImpl) ((SVGElement) parentSVGElement.getParentNode())
						.getOwnerSVGElement();

			} else if (parentSVGElement.getParentNode() instanceof SVGSVGElementImpl) {
				parentSVGElement = (SVGSVGElementImpl) parentSVGElement.getParentNode();
			} else {
				parentSVGElement = null;
			}

			if (parentSVGElement != null) {
				AffineTransform viewboxToViewportTransform = parentSVGElement.getViewboxToViewportTransform();

				if (viewboxToViewportTransform != null) {
					screenCTM.preConcatenate(viewboxToViewportTransform);
				}

				screenCTM.preConcatenate(
						AffineTransform.getTranslateInstance(parentSVGElement.getX().getAnimVal().getValue(),
								parentSVGElement.getY().getAnimVal().getValue()));
			}
		}

		SVGMatrix screenCTMMatrix = new SVGMatrixImpl();
		screenCTMMatrix.setA((float) screenCTM.getScaleX());
		screenCTMMatrix.setB((float) screenCTM.getShearY());
		screenCTMMatrix.setC((float) screenCTM.getShearX());
		screenCTMMatrix.setD((float) screenCTM.getScaleY());
		screenCTMMatrix.setE((float) screenCTM.getTranslateX());
		screenCTMMatrix.setF((float) screenCTM.getTranslateY());
		return screenCTMMatrix;
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix getTransformToElement(SVGElement element) throws SVGException {
		// TODO Auto-generated method stub
		return null;
	}

}
