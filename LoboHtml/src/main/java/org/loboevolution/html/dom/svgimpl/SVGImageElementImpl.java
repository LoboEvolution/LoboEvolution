package org.loboevolution.html.dom.svgimpl;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import org.loboevolution.html.dom.svg.SVGAnimatedLength;
import org.loboevolution.html.dom.svg.SVGAnimatedPreserveAspectRatio;
import org.loboevolution.html.dom.svg.SVGAnimatedString;
import org.loboevolution.html.dom.svg.SVGImageElement;
import org.loboevolution.net.HttpNetwork;

public class SVGImageElementImpl extends SVGGraphic implements SVGImageElement {

	public SVGImageElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedString getHref() {
		return new SVGAnimatedStringImpl(getAttribute("href"));
	}

	@Override
	public SVGAnimatedLength getX() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("x")));
	}

	@Override
	public SVGAnimatedLength getY() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("y")));
	}

	@Override
	public SVGAnimatedLength getWidth() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("width")));
	}

	@Override
	public SVGAnimatedLength getHeight() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("height")));
	}

	@Override
	public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics2D graphics) {
		Image image = HttpNetwork.getImage(getHref().getBaseVal(), null);
		int realWidth = image.getWidth(null);
		int realHeight = image.getHeight(null);

		float drawWidth = getWidth().getAnimVal().getValue();
		float drawHeight = getHeight().getAnimVal().getValue();

		AffineTransform at = new AffineTransform();
		at.translate(getX().getAnimVal().getValue(), getY().getAnimVal().getValue());
		at.scale(drawWidth / realWidth, drawHeight / realHeight);
		graphics.drawImage(image, at, null);
	}

	@Override
	public Shape createShape(AffineTransform transform) {
		return null;
	}
}