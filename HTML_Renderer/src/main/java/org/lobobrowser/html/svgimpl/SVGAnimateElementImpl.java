package org.lobobrowser.html.svgimpl;

import org.lobobrowser.w3c.svg.SVGAnimateElement;
import org.lobobrowser.w3c.svg.SVGElement;
import org.w3c.dom.DOMException;

public class SVGAnimateElementImpl extends SVGSVGElementImpl implements SVGAnimateElement {

	public SVGAnimateElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGElement getTargetElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getStartTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getSimpleDuration() throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean beginElement() throws DOMException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean beginElementAt(float arg0) throws DOMException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean endElement() throws DOMException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean endElementAt(float arg0) throws DOMException {
		// TODO Auto-generated method stub
		return false;
	}

}
