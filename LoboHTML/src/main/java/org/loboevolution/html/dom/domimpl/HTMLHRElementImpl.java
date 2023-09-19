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

import org.loboevolution.html.dom.HTMLHRElement;
import org.loboevolution.html.renderstate.HRRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>HTMLHRElementImpl class.</p>
 */
public class HTMLHRElementImpl extends HTMLElementImpl implements HTMLHRElement {

	/**
	 * <p>Constructor for HTMLHRElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLHRElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNoShade() {
		return "noshade".equalsIgnoreCase(getAttribute("noshade"));
	}

	/** {@inheritDoc} */
	@Override
	public String getSize() {
		return getAttribute("size");
	}

	/** {@inheritDoc} */
	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	@Override
	public String getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColor(String color) {
		// TODO Auto-generated method stub
	}

	/** {@inheritDoc} */
	@Override
	public void setNoShade(boolean noShade) {
		setAttribute("noshade", noShade ? "noshade" : null);
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(String size) {
		setAttribute("size", size);
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new HRRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLHRElement]";
	}
}
