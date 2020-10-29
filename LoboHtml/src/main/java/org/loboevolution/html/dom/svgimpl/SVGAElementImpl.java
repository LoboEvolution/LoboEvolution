package org.loboevolution.html.dom.svgimpl;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.svg.SVGAElement;
import org.loboevolution.html.dom.svg.SVGAnimatedString;
import org.loboevolution.html.dom.svg.SVGAnimatedTransformList;
import org.loboevolution.html.dom.svg.SVGImageElement;
import org.loboevolution.html.dom.svg.SVGMatrix;
import org.loboevolution.html.dom.svg.SVGRect;
import org.loboevolution.html.dom.svg.SVGSVGElement;
import org.loboevolution.html.dom.svg.SVGTransformable;
import org.loboevolution.html.dom.svg.SVGUseElement;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>SVGAElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGAElementImpl extends SVGGraphic implements SVGAElement {

	/**
	 * <p>Constructor for SVGAElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGAElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedString getHref() {
		return new SVGAnimatedStringImpl(getAttribute("href"));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedString getTarget() {
		return new SVGAnimatedStringImpl(getAttribute("target"));
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
		GeneralPath path = new GeneralPath();
		if (hasChildNodes()) {
			NodeList children = getChildNodes();
			for (Node child : Nodes.iterable(children)) {
				Shape childShape = null;
				if (child instanceof SVGGElementImpl) {
					childShape = ((SVGGElementImpl) child).createShape(transform);
				} else if (child instanceof SVGAElementImpl) {
					childShape = ((SVGAElementImpl) child).createShape(transform);
				} else if (child instanceof SVGImageElementImpl) {
					SVGRect bbox = ((SVGImageElement) child).getBBox();
					childShape = new Rectangle2D.Float(bbox.getX(), bbox.getY(), bbox.getWidth(), bbox.getHeight());
				} else if (child instanceof SVGUseElementImpl) {
					SVGRect bbox = ((SVGUseElement) child).getBBox();
					childShape = new Rectangle2D.Float(bbox.getX(), bbox.getY(), bbox.getWidth(), bbox.getHeight());
				} else if (child instanceof SVGSVGElementImpl) {
					SVGSVGElement svg = (SVGSVGElement) child;
					AffineTransform ctm = getCTM().getAffineTransform();
					AffineTransform inverseTransform;
					try {
						inverseTransform = ctm.createInverse();
					} catch (NoninvertibleTransformException e) {
						inverseTransform = null;
					}
					float x = ((SVGLengthImpl) svg.getX()).getTransformedLength(inverseTransform);
					float y = ((SVGLengthImpl) svg.getY()).getTransformedLength(inverseTransform);
					float width = ((SVGLengthImpl) svg.getWidth()).getTransformedLength(inverseTransform);
					float height = ((SVGLengthImpl) svg.getHeight()).getTransformedLength(inverseTransform);
					childShape = new Rectangle2D.Float(x, y, width, height);
				}
				if (child instanceof SVGTransformable) {
					SVGAnimatedTransformList childTransformList = ((SVGTransformable) child).getTransform();
					AffineTransform childTransform = ((SVGTransformListImpl) childTransformList.getAnimVal()).getAffineTransform();
					childShape = childTransform.createTransformedShape(childShape);
				}
				if (childShape != null) {
					path.append(childShape, false);
				}
			}
		}
		return path;
	}
}
