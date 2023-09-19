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

package org.loboevolution.html.style;

import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>MarginInsets class.</p>
 */
public class MarginInsets {
	
	/**
	 * <p>getMarginInsets.</p>
	 *
	 * @param cssProperties a {@link org.loboevolution.html.node.css.CSSStyleDeclaration} object.
	 * @param element a {@link  org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link org.loboevolution.html.style.HtmlInsets} object.
	 */
	public static HtmlInsets getMarginInsets(CSSStyleDeclaration cssProperties, HTMLElementImpl element, RenderState renderState) {
		final CSSStyleDeclaration props = element.getParentStyle();
		if (props == null) {
			return HtmlInsets.getInsets("0px", "0px", "0px", "0px", element, renderState);
		}
		final String topText = isInherit(cssProperties.getMarginTop()) ? props.getMarginTop() : cssProperties.getMarginTop();
		final String leftText = isInherit(cssProperties.getMarginLeft()) ? props.getMarginLeft() : cssProperties.getMarginLeft();
		final String bottomText = isInherit(cssProperties.getMarginBottom()) ? props.getMarginBottom() : cssProperties.getMarginBottom();
		final String rightText = isInherit(cssProperties.getMarginRight()) ? props.getMarginRight() : cssProperties.getMarginRight();
		return HtmlInsets.getInsets(topText, leftText, bottomText, rightText, element, renderState);
	}

	/**
	 * <p>getPaddingInsets.</p>
	 *
	 * @param cssProperties a {@link org.loboevolution.html.node.css.CSSStyleDeclaration} object.
	 * @param element a {@link  org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link org.loboevolution.html.style.HtmlInsets} object.
	 */
	public static HtmlInsets getPaddingInsets(CSSStyleDeclaration cssProperties, HTMLElementImpl element, RenderState renderState) {
		final CSSStyleDeclaration props = element.getParentStyle();
		if (props == null) {
			return HtmlInsets.getInsets("0px", "0px", "0px", "0px", element, renderState);
		}
		final String topText = isInherit(cssProperties.getPaddingTop()) ? props.getPaddingTop() : cssProperties.getPaddingTop();
		final String leftText = isInherit(cssProperties.getPaddingLeft()) ? props.getPaddingLeft() : cssProperties.getPaddingLeft();
		final String bottomText = isInherit(cssProperties.getPaddingBottom()) ? props.getPaddingBottom() : cssProperties.getPaddingBottom();
		final String rightText = isInherit(cssProperties.getPaddingRight()) ? props.getPaddingRight() : cssProperties.getPaddingRight();
		return HtmlInsets.getInsets(topText, leftText, bottomText, rightText, element, renderState);
	}

	private static boolean isInherit(String value) {
		return CSSValues.INHERIT.equals(CSSValues.get(value));
	}
}
