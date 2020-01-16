package org.loboevolution.html.dom.svgimpl;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;

import org.loboevolution.html.dom.svg.SVGAnimatedLength;
import org.loboevolution.html.dom.svg.SVGEllipseElement;
import org.loboevolution.html.dom.svg.SVGMatrix;

public class SVGEllipseElementImpl extends SVGGraphic implements SVGEllipseElement {

	public SVGEllipseElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedLength getCx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("cx")));
	}

	@Override
	public SVGAnimatedLength getCy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("cy")));
	}

	@Override
	public SVGAnimatedLength getRx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("rx")));
	}

	@Override
	public SVGAnimatedLength getRy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("ry")));
	}

	@Override
	public void draw(Graphics2D graphics) {
		final SVGMatrix ctm = getCTM();
		final Shape shape = createShape(ctm.getAffineTransform());
		animate(this);
		drawable(graphics, shape);
	}

	@Override
	public Shape createShape(AffineTransform transform) {
		AffineTransform inverseTransform;
		try {
			inverseTransform = transform.createInverse();
		} catch (NoninvertibleTransformException e) {
			inverseTransform = null;
		}
		float cx = ((SVGLengthImpl) getCx().getAnimVal()).getTransformedLength(inverseTransform);
		float cy = ((SVGLengthImpl) getCy().getAnimVal()).getTransformedLength(inverseTransform);
		float rx = ((SVGLengthImpl) getRx().getAnimVal()).getTransformedLength(inverseTransform);
		float ry = ((SVGLengthImpl) getRy().getAnimVal()).getTransformedLength(inverseTransform);
		Ellipse2D ellipse = new Ellipse2D.Float(cx - rx, cy - ry, 2 * rx, 2 * ry);
		return ellipse;
	}
}
