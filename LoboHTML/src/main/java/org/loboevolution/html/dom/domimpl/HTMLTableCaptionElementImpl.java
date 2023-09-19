/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.loboevolution.html.dom.HTMLTableCaptionElement;
import org.loboevolution.html.renderstate.DisplayRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * The Class HTMLTableCaptionElementImpl.
 */
public class HTMLTableCaptionElementImpl extends HTMLElementImpl implements HTMLTableCaptionElement {

	/**
	 * Instantiates a new HTML table caption element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLTableCaptionElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return this.getAttribute("align");
	}


	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		this.setAttribute("align", align);
	}

	/**
	 * Gets the caption side.
	 *
	 * @return the caption side
	 */
	public String getCaptionSide() {
		return this.getAttribute("caption-side");
	}

	/**
	 * Sets the caption side.
	 *
	 * @param captionSide
	 *            the new caption side
	 */
	public void setCaptionSide(String captionSide) {
		this.setAttribute("caption-side", captionSide);
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new DisplayRenderState(prevRenderState, this, RenderState.DISPLAY_TABLE_CAPTION);
	}

	@Override
	public Integer getClientWidth() {
		return null;
	}

	@Override
	public Integer getOffsetWidth() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLTableCaptionElement]";
	}
}
