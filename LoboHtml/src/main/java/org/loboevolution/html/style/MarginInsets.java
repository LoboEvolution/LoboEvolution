/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.html.style;

import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.css.CSS3Properties;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>MarginInsets class.</p>
 */
public class MarginInsets {
	
	/**
	 * <p>getMarginInsets.</p>
	 *
	 * @param cssProperties a {@link org.loboevolution.html.node.css.CSS3Properties} object.
	 * @param element a {@link  org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link org.loboevolution.html.style.HtmlInsets} object.
	 */
	public static HtmlInsets getMarginInsets(CSS3Properties cssProperties, HTMLElementImpl element, RenderState renderState) {
		final AbstractCSSProperties props = element.getParentStyle();
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
	 * @param cssProperties a {@link org.loboevolution.html.node.css.CSS3Properties} object.
	 * @param element a {@link  org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link org.loboevolution.html.style.HtmlInsets} object.
	 */
	public static HtmlInsets getPaddingInsets(CSS3Properties cssProperties, HTMLElementImpl element, RenderState renderState) {
		final AbstractCSSProperties props = element.getParentStyle();
		if (props == null) {
			return HtmlInsets.getInsets("0px", "0px", "0px", "0px", element, renderState);
		}
		final String topText = isInherit(cssProperties.getPaddingTop()) ? props.getPaddingTop() : cssProperties.getPaddingTop();
		final String leftText = isInherit(cssProperties.getPaddingLeft()) ? props.getPaddingLeft() : cssProperties.getPaddingLeft();
		final String bottomText = isInherit(cssProperties.getPaddingBottom()) ? props.getPaddingBottom() : cssProperties.getPaddingBottom();
		final String rightText = isInherit(cssProperties.getPaddingRight()) ? props.getPaddingRight() : cssProperties.getPaddingRight();
		return HtmlInsets.getInsets(topText, leftText, bottomText, rightText, element, renderState);
	}

	private static boolean isInherit(String value){
		return CSSValues.INHERIT.equals(CSSValues.get(value));
	}
}
