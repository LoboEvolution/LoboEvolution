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
/*
 * Created on Feb 12, 2006
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLPreElement;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.renderstate.PreRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.HtmlValues;

/**
 * <p>HTMLPreElementImpl class.</p>
 *
 *
 *
 */
public class HTMLPreElementImpl extends HTMLElementImpl implements HTMLPreElement {
	/**
	 * <p>Constructor for HTMLPreElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLPreElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new PreRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public double getWidth() {
		final String widthText = getAttribute("width");
		HTMLDocumentImpl doc =  (HTMLDocumentImpl)this.document;
		return HtmlValues.getPixelSize(widthText, null, doc.getWindow(), 0);
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(double width) {
		setAttribute("width", String.valueOf(width));
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLPreElement]";
	}
}
