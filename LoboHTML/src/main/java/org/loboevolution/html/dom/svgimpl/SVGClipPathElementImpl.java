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
import org.loboevolution.html.node.Node;

import java.awt.*;
import java.awt.geom.*;

/**
 * <p>SVGClipPathElementImpl class.</p>
 */
public class SVGClipPathElementImpl extends SVGGraphic implements SVGClipPathElement {
	
	
	/**
	 * <p>Constructor for SVGClipPathElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGClipPathElementImpl(final String name) {
		super(name);
	}

	@Override
	public SVGRect getBBox() {
		final Shape clipShape = getClippingShape(this.getOwnerSVGElement());
		final Rectangle2D bounds = clipShape.getBounds2D();
		return new SVGRectImpl(bounds);
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedEnumeration getClipPathUnits() {
		return new SVGAnimatedEnumerationImpl(SVG_UNIT_TYPE_USERSPACEONUSE);
	}
	
	/**
	 * <p>getClippingShape.</p>
	 *
	 * @param clippedElement a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 * @return a {@link java.awt.Shape} object.
	 */
	protected Shape getClippingShape(final SVGElement clippedElement) {
		Area clipArea = null;
		final AffineTransform clipTransform = new AffineTransform();
		if (hasChildNodes()) {
			final NodeListImpl children = (NodeListImpl) getChildNodes();
			for (final Node child : children) {
				if (child instanceof SVGUseElementImpl) {
					final String href = ((SVGUseElementImpl) child).getHref().getAnimVal();
					if (href.length() > 0) {
						final int index = href.indexOf('#');
						if (index != -1) {
							final String id = href.substring(index + 1).trim();
							final Element ref = (Element) child(id);
							if (ref != null) {
								final Shape childShape = ((Drawable) ref).createShape(clipTransform);

								if (childShape != null) {
									final AffineTransform childAffineTransform = clipTransform;
									if (ref instanceof SVGTransformable) {
										final SVGTransformListImpl childTransform = ( SVGTransformListImpl) ((SVGTransformable) ref).getTransform().getAnimVal();
										if (childTransform != null) {
											childAffineTransform.concatenate(childTransform.getAffineTransform());
										}
									}

									final GeneralPath path = new GeneralPath(childShape);
									path.transform(childAffineTransform);
									final String clipRule = ((SVGStylableImpl) child).getClipRule();
									final SVGClipPathElementImpl clipPath = ((SVGStylableImpl) child).getClippingPath();

									if (clipRule.equals("evenodd")) {
										path.setWindingRule(Path2D.WIND_EVEN_ODD);
									} else {
										path.setWindingRule(Path2D.WIND_NON_ZERO);
									}

									final Area childClipArea = new Area(path);
									if (clipPath != null) {
										final Shape clipShape = clipPath.getClippingShape(clippedElement);
										if (clipShape != null) {
											childClipArea.intersect(new Area(clipShape));
										}
									}

									if (clipArea == null) {
										clipArea = childClipArea;
									} else {
										clipArea.add(childClipArea);
									}
								}
							}
						}
					}
				} else if (child instanceof Drawable) {
					final Shape childShape = ((Drawable) child).createShape(clipTransform);
					if (childShape != null) {
						if (child instanceof SVGTransformable) {
							final SVGAnimatedTransformListImpl childTransform = ( SVGAnimatedTransformListImpl) ((SVGTransformable) child)
									.getTransform();
							if (childTransform != null) {
								clipTransform.concatenate(
										((SVGTransformListImpl) childTransform.getAnimVal()).getAffineTransform());
							}
						}

						final GeneralPath path = new GeneralPath(childShape);
						path.transform(clipTransform);

						final String clipRule = ((SVGStylableImpl) child).getClipRule();
						final SVGClipPathElementImpl clipPath = ((SVGStylableImpl) child).getClippingPath();

						if (clipRule.equals("evenodd")) {
							path.setWindingRule(Path2D.WIND_EVEN_ODD);
						} else {
							path.setWindingRule(Path2D.WIND_NON_ZERO);
						}

						final Area childClipArea = new Area(path);

						// see if child has a clipPath property
						if (clipPath != null) {
							final Shape clipShape = clipPath.getClippingShape(clippedElement);
							if (clipShape != null) {
								childClipArea.intersect(new Area(clipShape));
							}
						}

						if (clipArea == null) {
							clipArea = childClipArea;
						} else {
							clipArea.add(childClipArea);
						}
					}

				}
			}
		}

		final SVGClipPathElementImpl clipPath = getClippingPath();
		getClipRule();

		if (clipPath != null) {
			final Shape clipShape = clipPath.getClippingShape(clippedElement);
			if (clipShape != null) {
				if (clipArea == null) {
					clipArea = new Area(clipShape);
				} else {
					clipArea.intersect(new Area(clipShape));
				}
			}
		}

		if (clipArea != null) {
			Shape clipShape = clipArea;
			if (getClipPathUnits().getAnimVal() == SVG_UNIT_TYPE_OBJECTBOUNDINGBOX) {
				SVGRectImpl bbox = null;
				if (clippedElement instanceof SVGTransformable) {
					bbox = ( SVGRectImpl) ((SVGTransformable) clippedElement).getBBox();
				} else if (clippedElement instanceof SVGSVGElementImpl) {
					bbox = ( SVGRectImpl) ((SVGSVGElementImpl) clippedElement).getBBox();
				}
				if (bbox != null) {
					final AffineTransform clipTrans = AffineTransform.getTranslateInstance(bbox.getX(), bbox.getY());
					clipTrans.scale(bbox.getWidth(), bbox.getHeight());
					clipShape = clipTrans.createTransformedShape(clipShape);
					return clipShape;
				}
			}
		}
		return clipArea;
	}	
}
