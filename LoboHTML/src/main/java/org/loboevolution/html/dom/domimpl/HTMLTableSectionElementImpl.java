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

package org.loboevolution.html.dom.domimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLTableRowElement;
import org.loboevolution.html.dom.HTMLTableSectionElement;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.loboevolution.html.renderstate.DisplayRenderState;
import org.loboevolution.html.renderstate.RenderState;

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
	public void deleteRow(final int index) throws DOMException {
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
		return new HTMLCollectionImpl(this, new ElementFilter("TR"));
	}

	/** {@inheritDoc} */
	@Override
	public String getvAlign() {
		return getAttribute("valign");
	}

	/** {@inheritDoc} */
	@Override
	public HTMLTableRowElement insertRow(final int index) throws DOMException {
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
	public void setAlign(final String align) {
		setAttribute("align", align);
		
	}

	/** {@inheritDoc} */
	@Override
	public void setCh(final String ch) {
		setAttribute("ch", ch);
		
	}

	/** {@inheritDoc} */
	@Override
	public void setChOff(final String chOff) {
		setAttribute("choff", chOff);
		
	}

	/** {@inheritDoc} */
	@Override
	public void setvAlign(final String vAlign) {
		setAttribute("valign", vAlign);
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(final RenderState prevRenderState) {
		final String name = getNodeName();
		final int state;
		switch (name) {
			case "TFOOT":
				state = RenderState.DISPLAY_TABLE_FOOTER_GROUP;
				break;
			case "TBODY":
				state = RenderState.DISPLAY_TABLE_ROW_GROUP;
				break;
			case "THEAD":
				state = RenderState.DISPLAY_TABLE_HEADER_GROUP;
				break;
			default:
				return null;
		}

		return new DisplayRenderState(prevRenderState, this, state);
	}

	@Override
	public Integer getOffsetWidth() {
		return null;
	}

	@Override
	public Integer getClientWidth() {
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLTableSectionElement]";
	}

}
