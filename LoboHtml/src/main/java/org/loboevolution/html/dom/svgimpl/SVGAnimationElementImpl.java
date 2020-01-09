package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimationElement;
import org.loboevolution.html.dom.svg.SVGElement;
import org.w3c.dom.DOMException;

public class SVGAnimationElementImpl extends SVGAnimationImpl implements SVGAnimationElement {

	public SVGAnimationElementImpl(String name) {
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
	public float getCurrentTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getSimpleDuration() throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}
}
