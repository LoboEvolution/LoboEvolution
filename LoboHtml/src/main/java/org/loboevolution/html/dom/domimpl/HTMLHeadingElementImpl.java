/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLHeadingElement;
import org.loboevolution.html.renderstate.HeadingRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.AbstractCSSProperties;

/**
 * <p>HTMLHeadingElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLHeadingElementImpl extends HTMLAbstractUIElement implements HTMLHeadingElement {
	/**
	 * <p>Constructor for HTMLHeadingElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLHeadingElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	protected void appendInnerTextImpl(StringBuilder buffer) {
		final int length = buffer.length();
		int lineBreaks;
		if (length == 0) {
			lineBreaks = 2;
		} else {
			int start = length - 4;
			if (start < 0) {
				start = 0;
			}
			lineBreaks = 0;
			for (int i = start; i < length; i++) {
				final char ch = buffer.charAt(i);
				if (ch == '\n') {
					lineBreaks++;
				}
			}
		}
		for (int i = 0; i < 2 - lineBreaks; i++) {
			buffer.append("\r\n");
		}
		super.appendInnerTextImpl(buffer);
		buffer.append("\r\n\r\n");
	}

	/** {@inheritDoc} */
	@Override
	protected AbstractCSSProperties createDefaultStyleSheet() {
		final AbstractCSSProperties css = new AbstractCSSProperties(this);
		css.setPropertyValueLCAlt("font-size", getHeadingFontSizeText(), false);
		css.setPropertyValueLCAlt("font-weight", "bolder", false);
		return css;
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		getHeadingFontSize();
		// (can't put a RenderState in the middle - messes up "em" sizes).
		// prevRenderState = new FontSizeRenderState(prevRenderState, fontSize,
		// java.awt.Font.BOLD);
		return new HeadingRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	private float getHeadingFontSize() {
		final String tagName = getTagName();
		try {
			final int lastCharValue = tagName.charAt(1) - '0';
			switch (lastCharValue) {
			case 1:
				return 24.0f;
			case 2:
				return 18.0f;
			case 3:
				return 15.0f;
			case 4:
				return 12.0f;
			case 5:
				return 10.0f;
			case 6:
				return 8.0f;
			default:
				return 14.0f;
			}
		} catch (final Exception thrown) {
			this.warn("getHeadingFontSize(): Bad heading tag: " + getTagName(), thrown);
			return 14.0f;
		}
	}

	private String getHeadingFontSizeText() {
		final String tagName = getTagName();
		try {
			final int lastCharValue = tagName.charAt(1) - '0';
			switch (lastCharValue) {
			case 1:
				return "24pt";
			case 2:
				return "18pt";
			case 3:
				return "13.55pt";
			case 4:
				return "12pt";
			case 5:
				return "10pt";
			case 6:
				return "7.55pt";
			default:
				return "14px";
			}
		} catch (final Exception thrown) {
			this.warn("getHeadingFontSizeText(): Bad heading tag: " + getTagName(), thrown);
			return "14px";
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}
}
