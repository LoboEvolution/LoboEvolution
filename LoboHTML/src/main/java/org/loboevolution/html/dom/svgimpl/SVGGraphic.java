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

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * <p>SVGGraphic class.</p>
 */
public abstract class SVGGraphic extends SVGTransformableImpl implements SVGLangSpace, SVGTests, SVGExternalResourcesRequired {

	/**
	 * <p>Constructor for SVGGraphic.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGGraphic(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public String getXMLlang() {
		return getAttribute("xml:lang");
	}

	/** {@inheritDoc} */
	@Override
	public void setXMLlang(String xmllang) {
		setAttribute("xml:lang", xmllang);

	}

	/** {@inheritDoc} */
	@Override
	public String getXMLspace() {
		return getAttribute("xml:space");
	}

	/** {@inheritDoc} */
	@Override
	public void setXMLspace(String xmlspace) {
		setAttribute("xml:space", xmlspace);
	}

	/**
	 * <p>drawable.</p>
	 *
	 * @param graphics a {@link java.awt.Graphics2D} object.
	 * @param shape a {@link java.awt.Shape} object.
	 */
	protected void drawable(Graphics2D graphics, Shape shape) {
		final Paint fillPaint = getFillPaint(shape);
		final Paint strokePaint = getStrokelPaint(shape);
		final BasicStroke stroke = getStroke();
		SVGClipPathElementImpl clipPath = getClippingPath();
		SVGAnimatedTransformList animateTransformList = getTransform();
		graphics.setStroke(stroke);

		if (clipPath != null) {
			Shape clipShape = clipPath.getClippingShape(this);
			if (clipShape != null) {
				graphics.setClip(clipShape);
			}
		}

		if (animateTransformList != null) {
			SVGTransformList transformList = animateTransformList.getBaseVal();
			if (transformList != null) {
				transform(graphics, transformList);
			}
		}

		if (fillPaint == null && strokePaint == null) {
			graphics.setPaint(Color.BLACK);
			graphics.fill(shape);
		}

		if (fillPaint != null) {
			graphics.setPaint(fillPaint);
			graphics.fill(shape);
		}

		if (strokePaint != null) {
			graphics.setPaint(strokePaint);
			graphics.draw(shape);
		}
	}

	private void transform(Graphics2D graphics, SVGTransformList transformList) {
		int numPoints = transformList.getNumberOfItems();
		for (int i = 0; i < numPoints; i++) {
			SVGTransform point = transformList.getItem(i);
			SVGMatrixImpl mtrx = (SVGMatrixImpl) point.getMatrix();
			AffineTransform affine = new AffineTransform();
			switch (point.getType()) {
			case SVGTransform.SVG_TRANSFORM_MATRIX:
                case SVGTransform.SVG_TRANSFORM_SKEWY:
                case SVGTransform.SVG_TRANSFORM_SKEWX:
                    affine.concatenate(new AffineTransform(mtrx.getA(), mtrx.getB(), mtrx.getC(), mtrx.getD(), mtrx.getE(), mtrx.getF()));
				break;
			case SVGTransform.SVG_TRANSFORM_TRANSLATE:
				affine.translate(mtrx.getE(), mtrx.getF());
				break;
			case SVGTransform.SVG_TRANSFORM_SCALE:
				affine.scale(mtrx.getA(), mtrx.getD());
				break;
			case SVGTransform.SVG_TRANSFORM_ROTATE:
				affine.rotate(Math.toRadians(mtrx.getA()), mtrx.getB(), mtrx.getC());
				break;
                default:
				break;
			}
			graphics.transform(affine);
		}
		
	}

	/**
	 * <p>animate.</p>
	 *
	 * @param elem a {@link org.loboevolution.html.dom.svgimpl.SVGElementImpl} object.
	 */
	protected void animate(SVGElementImpl elem) {
		NodeListImpl children = (NodeListImpl)elem.getChildNodes();
		children.forEach(child -> {
			if (child instanceof SVGAnimateElement || child instanceof SVGAnimateTransformElement) {
				SVGAnimateElementImpl svga = (SVGAnimateElementImpl) child;				
				SVGAnimateImpl animate = new SVGAnimateImpl(elem, svga);
				svga.setAnimate(animate);
			}
		});
	}
}
