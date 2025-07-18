/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.svg.dom;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.svg.SVGAnimatedBoolean;
import org.loboevolution.svg.SVGElement;
import org.loboevolution.svg.SVGSVGElement;
import org.loboevolution.svg.SVGStringList;


/**
 * <p>SVGElementImpl class.</p> 
 */
public class SVGElementImpl extends SVGElementWrapper implements SVGElement {

	private SVGSVGElement ownerSvg;

	private SVGStringListImpl requiredFeatures;

	private SVGStringListImpl requiredExtensions;

	private SVGStringListImpl systemLanguage;

	/**
	 * <p>Constructor for SVGElementImpl.</p>
	 *
	 * @param element a {@link HTMLElement} object.
	 */
	public SVGElementImpl(final HTMLElement element) {
		super(element);
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
	 * @return a {@link SVGAnimatedBoolean} object.
	 */
	public SVGAnimatedBoolean getExternalResourcesRequired() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>Getter for the field requiredFeatures.</p>
	 *
	 * @return a {@link SVGStringList} object.
	 */
	public SVGStringList getRequiredFeatures() {
		return requiredFeatures;
	}

	/**
	 * <p>Getter for the field requiredExtensions.</p>
	 *
	 * @return a {@link SVGStringList} object.
	 */
	public SVGStringList getRequiredExtensions() {
		return requiredExtensions;
	}

	/**
	 * <p>Getter for the field systemLanguage.</p>
	 *
	 * @return a {@link SVGStringList} object.
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
	public boolean hasExtension(final String extension) {
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
	 * @throws DOMException if any.
	 */
	public void setXMLlang(final String xmllang) {
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
	 * @throws DOMException if any.
	 */
	public void setXMLspace(final String xmlspace) {
		setAttribute("xml:space", xmlspace);
	}

	/** {@inheritDoc} */
	public void setOwnerSVGElement(final SVGSVGElement ownerSvg) {
		this.ownerSvg = ownerSvg;
	}
}
