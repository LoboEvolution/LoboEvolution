package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.domimpl.HTMLAbstractUIElement;
import org.loboevolution.html.dom.svg.SVGElement;
import org.loboevolution.html.dom.svg.SVGSVGElement;

public class SVGElementImpl extends HTMLAbstractUIElement implements SVGElement {
	
	private SVGSVGElement ownerSvg;

	public SVGElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGSVGElement getOwnerSVGElement() {
		return ownerSvg;
	}

	@Override
	public SVGElement getViewportElement() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setOwnerSVGElement(SVGSVGElement ownerSvg) {
		this.ownerSvg = ownerSvg;
	}
}
