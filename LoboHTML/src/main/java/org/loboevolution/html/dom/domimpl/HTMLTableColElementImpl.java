/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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

import org.loboevolution.html.dom.HTMLTableColElement;
import org.loboevolution.html.renderstate.DisplayRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.HtmlValues;

/**
 * <p>HTMLTableColElementImpl class.</p>
 */
public class HTMLTableColElementImpl extends HTMLElementImpl implements HTMLTableColElement {

	/**
	 * <p>Constructor for HTMLTableColElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLTableColElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public String getCh() {
		return getAttribute("char");
	}

	/** {@inheritDoc} */
	@Override
	public String getChOff() {
		return getAttribute("charoff");
	}

	/** {@inheritDoc} */
	@Override
	public int getSpan() {
		final String span = getAttribute("span");
		final HTMLDocumentImpl doc =  (HTMLDocumentImpl)this.document;
		int iSpan = HtmlValues.getPixelSize(span, null, doc.getDefaultView(), 1);
		return Math.max(iSpan, 1);
	}

	/** {@inheritDoc} */
	@Override
	public String getvAlign() {
		return getAttribute("valign");
	}

	/** {@inheritDoc} */
	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(final String align) {
		setAttribute("align", align);

	}

	/** {@inheritDoc} */
	@Override
	public void setCh(final String ch) {
		setAttribute("char", ch);
	}

	/** {@inheritDoc} */
	@Override
	public void setChOff(final String chOff) {
		setAttribute("charoff", chOff);

	}

	/** {@inheritDoc} */
	@Override
	public void setSpan(final String span) {
		setAttribute("span", span);
	}

	/** {@inheritDoc} */
	@Override
	public void setvAlign(final String vAlign) {
		setAttribute("valign", vAlign);

	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(final String width) {
		setAttribute("width", width);
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(final RenderState prevRenderState) {
		return new DisplayRenderState(prevRenderState, this, RenderState.DISPLAY_TABLE_COLUMN);
	}

	@Override
	public Integer getOffsetWidth() {
		return null;
	}

	public Integer getClientWidth() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLTableColElement]";
	}


}
