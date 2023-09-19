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
/*
 * Created on Feb 12, 2006
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLPreElement;
import org.loboevolution.html.renderstate.PreRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.HtmlValues;

/**
 * <p>HTMLPreElementImpl class.</p>
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
		return HtmlValues.getPixelSize(widthText, null, doc.getDefaultView(), 0);
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(double width) {
		setAttribute("width", String.valueOf(width));
	}

	@Override
	public int getClientHeight() {
		int clientHeight = super.getClientHeight();
		return clientHeight == 0 ? 15 : clientHeight;
	}


	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLPreElement]";
	}
}
