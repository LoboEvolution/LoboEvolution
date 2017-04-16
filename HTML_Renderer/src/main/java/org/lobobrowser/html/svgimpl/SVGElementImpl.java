package org.lobobrowser.html.svgimpl;

import org.lobobrowser.html.domimpl.HTMLAbstractUIElement;
import org.lobobrowser.w3c.svg.SVGElement;
import org.lobobrowser.w3c.svg.SVGSVGElement;
import org.w3c.dom.DOMException;

public class SVGElementImpl extends HTMLAbstractUIElement implements SVGElement {

	public SVGElementImpl(String name) {
		super(name);
	}

	@Override
	public String getXMLbase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setXMLbase(String xmlbase) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SVGSVGElement getOwnerSVGElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGElement getViewportElement() {
		// TODO Auto-generated method stub
		return null;
	}
}
