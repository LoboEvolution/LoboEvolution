/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

package org.loboevolution.html.dom.domimpl;

import com.gargoylesoftware.css.dom.DOMException;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLTableRowElement;
import org.loboevolution.html.dom.HTMLTableSectionElement;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.loboevolution.html.renderstate.DisplayRenderState;
import org.loboevolution.html.renderstate.RenderState;

import java.util.Arrays;

/**
 * <p>HTMLTableSectionElementImpl class.</p>
 */
public class HTMLTableSectionElementImpl extends HTMLElementImpl implements HTMLTableSectionElement {

	/**
	 * <p>Constructor for HTMLTableSectionElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLTableSectionElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public void deleteRow(int index) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public String getCh() {
		return getAttribute("ch");
	}

	/** {@inheritDoc} */
	@Override
	public String getChOff() {
		return getAttribute("choff");
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getRows() {
		return new HTMLCollectionImpl(this, Arrays.asList(this.getNodeList(new ElementFilter("TR")).toArray()));
	}

	/** {@inheritDoc} */
	@Override
	public String getvAlign() {
		return getAttribute("valign");
	}

	/** {@inheritDoc} */
	@Override
	public HTMLTableRowElement insertRow(int index) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public HTMLTableRowElement insertRow() throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
		
	}

	/** {@inheritDoc} */
	@Override
	public void setCh(String ch) {
		setAttribute("ch", ch);
		
	}

	/** {@inheritDoc} */
	@Override
	public void setChOff(String chOff) {
		setAttribute("choff", chOff);
		
	}

	/** {@inheritDoc} */
	@Override
	public void setvAlign(String vAlign) {
		setAttribute("valign", vAlign);
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		final String name = getNodeName();
		switch (name) {
			case "TFOOT":
				return new DisplayRenderState(prevRenderState, this, RenderState.DISPLAY_TABLE_FOOTER_GROUP);
			case "TBODY":
				return new DisplayRenderState(prevRenderState, this, RenderState.DISPLAY_TABLE_ROW_GROUP);
			case "THEAD":
				return new DisplayRenderState(prevRenderState, this, RenderState.DISPLAY_TABLE_HEADER_GROUP);
			default:
				return null;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLTableSectionElement]";
	}

}
