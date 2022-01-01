/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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
package org.loboevolution.html.renderstate;

import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.FontValues;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;

import java.awt.*;

/**
 * <p>HeadingRenderState class.</p>
 */
public class HeadingRenderState extends AbstractMarginRenderState {

	private final HTMLElementImpl element;

	private final RenderState prevRenderState;

	/**
	 * <p>Constructor for HeadingRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public HeadingRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element);
		this.element = element;
		this.prevRenderState = prevRenderState;

	}

	/** {@inheritDoc} */
	@Override
	protected HtmlInsets getDefaultMarginInsets() {
		final HtmlInsets insets = new HtmlInsets();
		final String tagName = element.getTagName();
		final int lastCharValue = tagName.charAt(1) - '0';
		switch (lastCharValue) {
			case 1:
				final int topBottom1 = HtmlValues.getPixelSize("0.67rem", null, element.getDocumentNode().getDefaultView(), -1);
				insets.top = topBottom1;
				insets.bottom = topBottom1;
				insets.topType = HtmlInsets.TYPE_PIXELS;
				insets.bottomType = HtmlInsets.TYPE_PIXELS;
				break;
			case 2:
				final int topBottom2 = HtmlValues.getPixelSize("0.83rem", null, element.getDocumentNode().getDefaultView(), -1);
				insets.top = topBottom2;
				insets.bottom = topBottom2;
				insets.topType = HtmlInsets.TYPE_PIXELS;
				insets.bottomType = HtmlInsets.TYPE_PIXELS;
				break;
			case 3:
				final int topBottom3 = HtmlValues.getPixelSize("1rem", null, element.getDocumentNode().getDefaultView(), -1);
				insets.top = topBottom3;
				insets.bottom = topBottom3;
				insets.topType = HtmlInsets.TYPE_PIXELS;
				insets.bottomType = HtmlInsets.TYPE_PIXELS;
				break;
			case 4:
				final int topBottom4 = HtmlValues.getPixelSize("1.33rem", null, element.getDocumentNode().getDefaultView(), -1);
				insets.top = topBottom4;
				insets.bottom = topBottom4;
				insets.topType = HtmlInsets.TYPE_PIXELS;
				insets.bottomType = HtmlInsets.TYPE_PIXELS;
				break;
			case 5:
				final int topBottom5 = HtmlValues.getPixelSize("1.67rem", null, element.getDocumentNode().getDefaultView(), -1);
				insets.top = topBottom5;
				insets.bottom = topBottom5;
				insets.topType = HtmlInsets.TYPE_PIXELS;
				insets.bottomType = HtmlInsets.TYPE_PIXELS;
				break;
			case 6:
				final int topBottom6 = HtmlValues.getPixelSize("2.33rem", null, element.getDocumentNode().getDefaultView(), -1);
				insets.top = topBottom6;
				insets.bottom = topBottom6;
				insets.topType = HtmlInsets.TYPE_PIXELS;
				insets.bottomType = HtmlInsets.TYPE_PIXELS;
				break;
			default:
				break;
		}

		return insets;
	}

	@Override
	public Font getFont() {
		FontKey key = FontValues.getDefaultFontKey();
		key.setFontWeight(CSSValues.BOLD.getValue());
		key.setFontSize(FontValues.getFontSize(getHeadingFontSize(), element.getDocumentNode().getDefaultView(), prevRenderState));
		return FontFactory.getInstance().getFont(FontValues.getFontKey(key, element, this.getCssProperties(), prevRenderState));
	}

	private String getHeadingFontSize() {
		final String tagName = element.getTagName();
		try {
			final int lastCharValue = tagName.charAt(1) - '0';
			switch (lastCharValue) {
				case 1:
					return "2rem";
				case 2:
					return "1.5rem";
				case 3:
					return "1.2rem";
				case 4:
					return "1rem";
				case 5:
					return "0.83rem";
				case 6:
					return "0.67rem";
				default:
					return "";
			}
		} catch (final Exception thrown) {
			return "";
		}
	}
}
