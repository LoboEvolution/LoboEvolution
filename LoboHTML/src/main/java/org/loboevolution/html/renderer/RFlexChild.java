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

import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderstate.RenderState;

class RFlexChild {
	
	private final RenderState renderState;

	/**
	 * <p>Constructor for RFlexChild.</p>
	 *
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 */
	protected RFlexChild(RenderState renderState) {
		this.renderState = renderState;
	}

	/**
	 * <p>isInlineBlock.</p>
	 *
	 * @return a boolean.
	 */
	protected boolean isInlineBlock() {
		final RenderState previous = renderState.getPreviousRenderState();
		final String flexDirText = previous.getFlexDirection();
		final String flexWrapText = previous.getFlexWrap();
		final String justText = previous.getJustifyContent();
		final String alignItems = previous.getAlignItems();
		final String alignContent = previous.getAlignContent();
		boolean isInlineBlock = false;

		// flex-direction
		CSSValues flex = CSSValues.get(flexDirText);
		if (CSSValues.ROW.equals(flex) || CSSValues.ROW_REVERSE.equals(flex)) {
			isInlineBlock = true;
		}

		// flex-wrap
		flex = CSSValues.get(flexWrapText);
		if (CSSValues.WRAP.equals(flex) || CSSValues.WRAP_REVERSE.equals(flex)) {
			isInlineBlock = true;
		}

		// justify-content
		flex = CSSValues.get(justText);
		if (Strings.isNotBlank(justText)) {
			isInlineBlock = true;
		}

		// align-items
		flex = CSSValues.get(alignItems);
		if (CSSValues.FLEX_START.equals(flex) || CSSValues.STRETCH.equals(flex)) {
			isInlineBlock = true;
		}

		// align-content
		flex = CSSValues.get(alignContent);
		if (Strings.isNotBlank(alignContent)) {
			isInlineBlock = true;
		}

		return isInlineBlock; 
	}
	
	/**
	 * <p>flexAlign.</p>
	 *
	 * @param markupElement a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	protected void flexAlign(HTMLElementImpl markupElement) {
		final RenderState previous = renderState.getPreviousRenderState();
		final String justText = previous.getJustifyContent();
		CSSValues flex = CSSValues.get(justText);
		
		if (CSSValues.SPACE_AROUND.equals(flex)) {
			markupElement.getCurrentStyle().setTextAlign("center");
		}
		
		if (CSSValues.STRETCH.equals(flex)) {
			markupElement.getCurrentStyle().setHeight("100%");
		}
	}
}
