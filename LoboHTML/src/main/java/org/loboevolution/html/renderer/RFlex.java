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

package org.loboevolution.html.renderer;

import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>RFlex class.</p>
 *
 *
 *
 */
public class RFlex {

	private final RenderState renderState;

	/**
	 * <p>Constructor for RFlex.</p>
	 *
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 */
	public RFlex(RenderState renderState) {
		this.renderState = renderState;
	}

	/**
	 * <p>isFlexTable.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isFlexTable() {
		final String flexWrapText = renderState.getFlexWrap();
		final String justText = renderState.getJustifyContent();
		final String alignItems = renderState.getAlignItems();
		final String alignContent = renderState.getAlignContent();
		boolean isTable = false;

		// flex-wrap
		CSSValues flex = CSSValues.get(flexWrapText);
		if (CSSValues.NOWRAP.equals(flex)) {
			isTable = true;
		}

		// justify-content
		flex = CSSValues.get(justText);
		if (CSSValues.SPACE_AROUND.equals(flex)) {
			isTable = true;
		}

		// align-items
		flex = CSSValues.get(alignItems);
		if (CSSValues.FLEX_END.equals(flex) || CSSValues.CENTER.equals(flex)) {
			isTable = true;
		}

		// align-content
		flex = CSSValues.get(alignContent);
		if (CSSValues.FLEX_END.equals(flex) || CSSValues.CENTER.equals(flex)) {
			isTable = true;
		}
		
		return isTable;
	}

	
	/**
	 * <p>flexAlign.</p>
	 *
	 * @param markupElement a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	protected void flexAlign(HTMLElementImpl markupElement) {
		final String justText = renderState.getJustifyContent();
		CSSValues flex = CSSValues.get(justText);
		
		if (CSSValues.FLEX_END.equals(flex)) {
			markupElement.getCurrentStyle().setTextAlign("right");
		}
		
		if (CSSValues.CENTER.equals(flex)) {
			markupElement.getCurrentStyle().setTextAlign("center");
		}
		
		if (CSSValues.SPACE_BETWEEN.equals(flex)) {
			markupElement.getCurrentStyle().setTextAlign("justify");
		}
	}
}
