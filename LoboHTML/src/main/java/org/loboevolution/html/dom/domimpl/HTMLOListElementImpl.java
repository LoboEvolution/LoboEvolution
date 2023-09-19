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

import org.loboevolution.html.dom.HTMLOListElement;
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
		return HtmlValues.getPixelSize(startText, null, doc.getDefaultView(), 1);
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
