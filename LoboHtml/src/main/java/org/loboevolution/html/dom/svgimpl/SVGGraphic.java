package org.loboevolution.html.dom.svgimpl;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.svg.SVGAnimateElement;
import org.loboevolution.html.dom.svg.SVGAnimatedBoolean;
import org.loboevolution.html.dom.svg.SVGExternalResourcesRequired;
import org.loboevolution.html.dom.svg.SVGLangSpace;
import org.loboevolution.html.dom.svg.SVGStringList;
import org.loboevolution.html.dom.svg.SVGTests;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SVGGraphic extends SVGTransformableImpl implements SVGLangSpace, SVGTests, SVGExternalResourcesRequired  {
	
	private SVGStringListImpl requiredFeatures;
	
	private SVGStringListImpl requiredExtensions;
	
	private SVGStringListImpl systemLanguage;

	public SVGGraphic(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedBoolean getExternalResourcesRequired() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGStringList getRequiredFeatures() {
		return requiredFeatures;
	}

	@Override
	public SVGStringList getRequiredExtensions() {
		return requiredExtensions;
	}

	@Override
	public SVGStringList getSystemLanguage() {
		return systemLanguage;
	}

	@Override
	public boolean hasExtension(String extension) {
		if (extension.equalsIgnoreCase("svg")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getXMLlang() {
		return getAttribute("xml:lang");
	}

	@Override
	public void setXMLlang(String xmllang) throws DOMException {
		setAttribute("xml:lang", xmllang);
		
	}

	@Override
	public String getXMLspace() {
		return getAttribute("xml:space");
	}

	@Override
	public void setXMLspace(String xmlspace) throws DOMException {
		setAttribute("xml:space", xmlspace);
	}
	
	protected void drawable(Graphics2D graphics, Shape shape) {
		final Paint fillPaint = getFillPaint(shape);
		final Paint strokePaint = getStrokelPaint(shape);
		final BasicStroke stroke = getStroke();
		SVGClipPathElementImpl clipPath = getClippingPath();
		graphics.setStroke(stroke);

		if (clipPath != null) {
			Shape clipShape = clipPath.getClippingShape(this);
			if (clipShape != null) {
				graphics.setClip(clipShape);
			}
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
	
	protected void animate(SVGElementImpl elem) {
		NodeList childNodes = elem.getChildNodes();
		for (Node child : Nodes.iterable(childNodes)) {
			if (child instanceof SVGAnimateElement) {
				SVGAnimateElementImpl svga = (SVGAnimateElementImpl)child;
				new SVGAnimateImpl(elem, svga);
			}
		}
	}	
}