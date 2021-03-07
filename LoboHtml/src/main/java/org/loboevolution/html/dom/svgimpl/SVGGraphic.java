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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.dom.svg.SVGAnimateElement;
import org.loboevolution.html.dom.svg.SVGAnimateTransformElement;
import org.loboevolution.html.dom.svg.SVGAnimatedTransformList;
import org.loboevolution.html.dom.svg.SVGExternalResourcesRequired;
import org.loboevolution.html.dom.svg.SVGLangSpace;
import org.loboevolution.html.dom.svg.SVGTests;
import org.loboevolution.html.dom.svg.SVGTransform;
import org.loboevolution.html.dom.svg.SVGTransformList;

import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

/**
 * <p>SVGGraphic class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGGraphic extends SVGTransformableImpl implements SVGLangSpace, SVGTests, SVGExternalResourcesRequired {

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
