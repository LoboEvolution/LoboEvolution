package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.domimpl.HTMLAbstractUIElement;
import org.loboevolution.html.dom.svg.SVGAnimatedBoolean;
import org.loboevolution.html.dom.svg.SVGElement;
import org.loboevolution.html.dom.svg.SVGSVGElement;
import org.loboevolution.html.dom.svg.SVGStringList;
import org.w3c.dom.DOMException;

public class SVGElementImpl extends HTMLAbstractUIElement implements SVGElement {

	private SVGSVGElement ownerSvg;

	private SVGStringListImpl requiredFeatures;

	private SVGStringListImpl requiredExtensions;

	private SVGStringListImpl systemLanguage;

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

	public SVGAnimatedBoolean getExternalResourcesRequired() {
		// TODO Auto-generated method stub
		return null;
	}

	public SVGStringList getRequiredFeatures() {
		return requiredFeatures;
	}

	public SVGStringList getRequiredExtensions() {
		return requiredExtensions;
	}

	public SVGStringList getSystemLanguage() {
		return systemLanguage;
	}

	public boolean hasExtension(String extension) {
		if (extension.equalsIgnoreCase("svg")) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getXMLlang() {
		return getAttribute("xml:lang");
	}

	public void setXMLlang(String xmllang) throws DOMException {
		setAttribute("xml:lang", xmllang);
	}

	public String getXMLspace() {
		return getAttribute("xml:space");
	}

	public void setXMLspace(String xmlspace) throws DOMException {
		setAttribute("xml:space", xmlspace);
	}

	public void setOwnerSVGElement(SVGSVGElement ownerSvg) {
		this.ownerSvg = ownerSvg;
	}
}
