package org.loboevolution.html.dom.svgimpl;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.svg.Drawable;
import org.loboevolution.html.dom.svg.SVGAnimatedEnumeration;
import org.loboevolution.html.dom.svg.SVGClipPathElement;
import org.loboevolution.html.dom.svg.SVGElement;
import org.loboevolution.html.dom.svg.SVGTransformable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>SVGClipPathElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGClipPathElementImpl extends SVGGraphic implements SVGClipPathElement {
	
	
	/**
	 * <p>Constructor for SVGClipPathElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGClipPathElementImpl(String name) {
		super(name);
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
	protected Shape getClippingShape(SVGElement clippedElement) {
		Area clipArea = null;
		AffineTransform clipTransform = new AffineTransform();
		if (hasChildNodes()) {
			NodeList children = getChildNodes();
			for (Node child : Nodes.iterable(children)) {
				if (child instanceof SVGUseElementImpl) {
					String href = ((SVGUseElementImpl) child).getHref().getAnimVal();
					if (href.length() > 0) {
						int index = href.indexOf('#');
						if (index != -1) {
							String id = href.substring(index + 1).trim();
							Element ref = (Element) child(id);
							if (ref != null) {
								Shape childShape = ((Drawable) ref).createShape(clipTransform);

								if (childShape != null) {
									AffineTransform childAffineTransform = clipTransform;
									if (ref instanceof SVGTransformable) {
										SVGTransformListImpl childTransform = (SVGTransformListImpl) ((SVGTransformable) ref).getTransform().getAnimVal();
										if (childTransform != null) {
											childAffineTransform.concatenate(childTransform.getAffineTransform());
										}
									}

									GeneralPath path = new GeneralPath(childShape);
									path.transform(childAffineTransform);
									String clipRule = ((SVGStylableImpl) child).getClipRule();
									SVGClipPathElementImpl clipPath = ((SVGStylableImpl) child).getClippingPath();

									if (clipRule.equals("evenodd")) {
										path.setWindingRule(Path2D.WIND_EVEN_ODD);
									} else {
										path.setWindingRule(Path2D.WIND_NON_ZERO);
									}

									Area childClipArea = new Area(path);
									if (clipPath != null) {
										Shape clipShape = clipPath.getClippingShape(clippedElement);
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
					Shape childShape = ((Drawable) child).createShape(clipTransform);
					if (childShape != null) {
						AffineTransform childAffineTransform = clipTransform;
						if (child instanceof SVGTransformable) {
							SVGAnimatedTransformListImpl childTransform = (SVGAnimatedTransformListImpl) ((SVGTransformable) child).getTransform();
							if (childTransform != null) {
								childAffineTransform.concatenate(
										((SVGTransformListImpl) childTransform.getAnimVal()).getAffineTransform());
							}
						}

						GeneralPath path = new GeneralPath(childShape);
						path.transform(childAffineTransform);

						String clipRule = ((SVGStylableImpl) child).getClipRule();
						SVGClipPathElementImpl clipPath = ((SVGStylableImpl) child).getClippingPath();

						if (clipRule.equals("evenodd")) {
							path.setWindingRule(Path2D.WIND_EVEN_ODD);
						} else {
							path.setWindingRule(Path2D.WIND_NON_ZERO);
						}

						Area childClipArea = new Area(path);

						// see if child has a clipPath property
						if (clipPath != null) {
							Shape clipShape = clipPath.getClippingShape(clippedElement);
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

		SVGClipPathElementImpl clipPath = getClippingPath();
		getClipRule();

		if (clipPath != null) {
			Shape clipShape = clipPath.getClippingShape(clippedElement);
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
					bbox = (SVGRectImpl) ((SVGTransformable) clippedElement).getBBox();
				} else if (clippedElement instanceof SVGSVGElementImpl) {
					bbox = (SVGRectImpl) ((SVGSVGElementImpl) clippedElement).getBBox();
				}
				if (bbox != null) {
					AffineTransform clipTrans = AffineTransform.getTranslateInstance(bbox.getX(), bbox.getY());
					clipTrans.scale(bbox.getWidth(), bbox.getHeight());
					clipShape = clipTrans.createTransformedShape(clipShape);
					return clipShape;
				}
			}
		}
		return clipArea;
	}	
}
