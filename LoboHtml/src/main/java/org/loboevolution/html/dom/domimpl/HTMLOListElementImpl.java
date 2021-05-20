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

import org.loboevolution.html.dom.HTMLOListElement;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.renderstate.ListRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.HtmlValues;

/**
 * <p>HTMLOListElementImpl class.</p>
 */
public class HTMLOListElementImpl extends HTMLElementImpl implements HTMLOListElement {
	/**
	 * <p>Constructor for HTMLOListElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLOListElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new ListRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public boolean getCompact() {
		final String compactText = getAttribute("compact");
		return "compact".equalsIgnoreCase(compactText);
	}

	/** {@inheritDoc} */
	@Override
	public int getStart() {
		final String startText = getAttribute("start");
		HTMLDocumentImpl doc =  (HTMLDocumentImpl)this.document;
		return HtmlValues.getPixelSize(startText, null, doc.getWindow(), 1);
	}

	/** {@inheritDoc} */
	@Override
	public String getType() {
		return getAttribute("type");
	}

	@Override
	public boolean isCompact() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setCompact(boolean compact) {
		setAttribute("compact", compact ? "compact" : null);
	}

	@Override
	public boolean isReversed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setReversed(boolean reversed) {
	// TODO Auto-generated method stub
	}

	/** {@inheritDoc} */
	@Override
	public void setStart(int start) {
		setAttribute("start", String.valueOf(start));
	}

	/** {@inheritDoc} */
	@Override
	public void setType(String type) {
		setAttribute("type", type);
	}


	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLOListElement]";
	}
}
