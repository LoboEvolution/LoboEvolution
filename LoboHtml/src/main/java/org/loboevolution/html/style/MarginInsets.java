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

import org.loboevolution.html.node.css.CSS3Properties;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>MarginInsets class.</p>
 *
 *
 *
 */
public class MarginInsets {
	
	/**
	 * <p>getMarginInsets.</p>
	 *
	 * @param cssProperties a {@link org.loboevolution.html.node.css.CSS3Properties} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link org.loboevolution.html.style.HtmlInsets} object.
	 */
	public static HtmlInsets getMarginInsets(CSS3Properties cssProperties, RenderState renderState) {
		HtmlInsets insets = null;
		final String topText = cssProperties.getMarginTop();
		insets = HtmlInsets.updateTopInset(insets, topText, renderState);
		final String leftText = cssProperties.getMarginLeft();
		insets = HtmlInsets.updateLeftInset(insets, leftText, renderState);
		final String bottomText = cssProperties.getMarginBottom();
		insets = HtmlInsets.updateBottomInset(insets, bottomText, renderState);
		final String rightText = cssProperties.getMarginRight();
		insets = HtmlInsets.updateRightInset(insets, rightText, renderState);
		return insets;
	}

	/**
	 * <p>getPaddingInsets.</p>
	 *
	 * @param cssProperties a {@link org.loboevolution.html.node.css.CSS3Properties} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link org.loboevolution.html.style.HtmlInsets} object.
	 */
	public static HtmlInsets getPaddingInsets(CSS3Properties cssProperties, RenderState renderState) {
		HtmlInsets insets = null;
		final String topText = cssProperties.getPaddingTop();
		insets = HtmlInsets.updateTopInset(insets, topText, renderState);
		final String leftText = cssProperties.getPaddingLeft();
		insets = HtmlInsets.updateLeftInset(insets, leftText, renderState);
		final String bottomText = cssProperties.getPaddingBottom();
		insets = HtmlInsets.updateBottomInset(insets, bottomText, renderState);
		final String rightText = cssProperties.getPaddingRight();
		insets = HtmlInsets.updateRightInset(insets, rightText, renderState);
		return insets;
	}

}
