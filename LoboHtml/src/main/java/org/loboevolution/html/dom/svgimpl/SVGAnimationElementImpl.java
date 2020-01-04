package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedBoolean;
import org.loboevolution.html.dom.svg.SVGAnimationElement;
import org.loboevolution.html.dom.svg.SVGElement;
import org.loboevolution.html.dom.svg.SVGStringList;
import org.w3c.dom.DOMException;

public class SVGAnimationElementImpl extends SVGAnimationImpl implements SVGAnimationElement {

	public SVGAnimationElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGStringList getRequiredFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGStringList getRequiredExtensions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGStringList getSystemLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasExtension(String extension) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SVGAnimatedBoolean getExternalResourcesRequired() {
		// TODO Auto-generated method stub
		return null;
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
