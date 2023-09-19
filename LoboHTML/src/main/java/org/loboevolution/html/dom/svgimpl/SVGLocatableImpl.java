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
