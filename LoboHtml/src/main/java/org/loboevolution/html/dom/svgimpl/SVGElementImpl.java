/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.svg.SVGAnimatedBoolean;
import org.loboevolution.html.dom.svg.SVGElement;
import org.loboevolution.html.dom.svg.SVGSVGElement;
import org.loboevolution.html.dom.svg.SVGStringList;


/**
 * <p>SVGElementImpl class.</p>
 *
 *
 *
 */
public class SVGElementImpl extends HTMLElementImpl implements SVGElement {

	private SVGSVGElement ownerSvg;

	private SVGStringListImpl requiredFeatures;

	private SVGStringListImpl requiredExtensions;

	private SVGStringListImpl systemLanguage;

	/**
	 * <p>Constructor for SVGElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGElementImpl(final String name) {
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
		return extension.equalsIgnoreCase("svg");
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
	public void setXMLlang(String xmllang) {
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
	public void setXMLspace(String xmlspace) {
		setAttribute("xml:space", xmlspace);
	}

	/** {@inheritDoc} */
	public void setOwnerSVGElement(SVGSVGElement ownerSvg) {
		this.ownerSvg = ownerSvg;
	}
}
