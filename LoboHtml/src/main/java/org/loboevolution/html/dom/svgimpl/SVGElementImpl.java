package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.domimpl.HTMLAbstractUIElement;
import org.loboevolution.html.dom.svg.SVGAnimatedBoolean;
import org.loboevolution.html.dom.svg.SVGElement;
import org.loboevolution.html.dom.svg.SVGSVGElement;
import org.loboevolution.html.dom.svg.SVGStringList;
import org.w3c.dom.DOMException;

/**
 * <p>SVGElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGElementImpl extends HTMLAbstractUIElement implements SVGElement {

	private SVGSVGElement ownerSvg;

	private SVGStringListImpl requiredFeatures;

	private SVGStringListImpl requiredExtensions;

	private SVGStringListImpl systemLanguage;

	/**
	 * <p>Constructor for SVGElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public SVGSVGElement getOwnerSVGElement() {
		return ownerSvg;
	}

	/** {@inheritDoc} */
	@Override
	public SVGElement getViewportElement() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>getExternalResourcesRequired.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedBoolean} object.
	 */
	public SVGAnimatedBoolean getExternalResourcesRequired() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>Getter for the field requiredFeatures.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGStringList} object.
	 */
	public SVGStringList getRequiredFeatures() {
		return requiredFeatures;
	}

	/**
	 * <p>Getter for the field requiredExtensions.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGStringList} object.
	 */
	public SVGStringList getRequiredExtensions() {
		return requiredExtensions;
	}

	/**
	 * <p>Getter for the field systemLanguage.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGStringList} object.
	 */
	public SVGStringList getSystemLanguage() {
		return systemLanguage;
	}

	/**
	 * <p>hasExtension.</p>
	 *
	 * @param extension a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean hasExtension(String extension) {
		if (extension.equalsIgnoreCase("svg")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * <p>getXMLlang.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getXMLlang() {
		return getAttribute("xml:lang");
	}

	/**
	 * <p>setXMLlang.</p>
	 *
	 * @param xmllang a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public void setXMLlang(String xmllang) throws DOMException {
		setAttribute("xml:lang", xmllang);
	}

	/**
	 * <p>getXMLspace.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getXMLspace() {
		return getAttribute("xml:space");
	}

	/**
	 * <p>setXMLspace.</p>
	 *
	 * @param xmlspace a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public void setXMLspace(String xmlspace) throws DOMException {
		setAttribute("xml:space", xmlspace);
	}

	/** {@inheritDoc} */
	public void setOwnerSVGElement(SVGSVGElement ownerSvg) {
		this.ownerSvg = ownerSvg;
	}
}
