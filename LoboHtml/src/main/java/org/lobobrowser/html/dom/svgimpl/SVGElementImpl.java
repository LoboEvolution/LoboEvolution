/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.dom.svgimpl;

import org.lobobrowser.html.dom.svg.SVGElement;
import org.lobobrowser.html.dom.svg.SVGSVGElement;
import org.lobobrowser.html.dom.svg.SVGSymbolElement;
import org.lobobrowser.html.domimpl.HTMLAbstractUIElement;
import org.w3c.dom.Node;

public class SVGElementImpl extends HTMLAbstractUIElement implements SVGElement {

	private SVGDocumentImpl ownerDoc = null;

	public SVGElementImpl(String name) {
		super(name);
	}

	public SVGDocumentImpl getOwnerDoc() {
		return ownerDoc;
	}

	public void setOwnerDoc(SVGDocumentImpl v) {
		this.ownerDoc = v;
	}

	@Override
	public SVGSVGElement getOwnerSVGElement() {
		if (getParentNode() == ownerDoc) {
			return null;
		}
		Node parent = getParentNode();
		while (parent != null && !(parent instanceof SVGSVGElement)) {
			parent = parent.getParentNode();
		}

		if (parent instanceof SVGGElementImpl) {
			SVGGElementImpl a = (SVGGElementImpl) parent;
			return a.getSvg();
		}

		return (SVGSVGElement) parent;
	}

	@Override
	public SVGElement getViewportElement() {
		if (getParentNode() == ownerDoc) {
			return null;
		}
		Node parent = getParentNode();
		while (parent != null && !(parent instanceof SVGSVGElement || parent instanceof SVGSymbolElement)) {
			parent = parent.getParentNode();
		}
		return (SVGElement) parent;
	}
}
