/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
package org.loboevolution.html.renderstate;

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.BorderInsets;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.info.BorderInfo;

import java.awt.*;

/**
 * <p>IFrameRenderState class.</p> 
 */
public class IFrameRenderState extends StyleSheetRenderState {

	/**
	 * <p>Constructor for IFrameRenderState.</p>
	 *
	 * @param prevRenderState a {@link RenderState} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public IFrameRenderState(final RenderState prevRenderState, final HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	/** {@inheritDoc} */
	@Override
	public BorderInfo getBorderInfo() {
		BorderInfo binfo = this.borderInfo;
		if (binfo != INVALID_BORDER_INFO) {
			return binfo;
		}
		binfo = super.getBorderInfo();
		if (binfo == null || binfo.borderInfoIsVoid()) {
			if (binfo == null) {
				binfo = new BorderInfo();
			}
			final HTMLElementImpl element = this.element;
			if (element != null) {
				String border = element.getAttribute("frameborder");
				if (border != null) {
					border = border.trim();
				}
				int value;
				if (border != null) {
					try {
						value = Integer.parseInt(border);
					} catch (final NumberFormatException nfe) {
						value = 0;
					}
				} else {
					value = 1;
				}

				binfo.setInsets(new HtmlInsets(value != 0 ? 1 : 0, HtmlInsets.TYPE_PIXELS));
				if (binfo.getTopColor() == null) {
					binfo.setTopColor(Color.DARK_GRAY);
				}
				if (binfo.getLeftColor() == null) {
					binfo.setLeftColor(Color.DARK_GRAY);
				}
				if (binfo.getRightColor() == null) {
					binfo.setRightColor(Color.LIGHT_GRAY);
				}
				if (binfo.getBottomColor() == null) {
					binfo.setBottomColor(Color.LIGHT_GRAY);
				}
				if (value != 0) {
					binfo.setTopStyle(BorderInsets.BORDER_STYLE_SOLID);
					binfo.setLeftStyle(BorderInsets.BORDER_STYLE_SOLID);
					binfo.setRightStyle(BorderInsets.BORDER_STYLE_SOLID);
					binfo.setBottomStyle(BorderInsets.BORDER_STYLE_SOLID);
				}
			}
		}
		this.borderInfo = binfo;
		return binfo;
	}

	/** {@inheritDoc} */
	@Override
	public int getOverflowX() {
		int overflow = this.overflowX;
		if (overflow != -1) {
			return overflow;
		}
		overflow = super.getOverflowX();
		if (overflow == OVERFLOW_NONE) {
			final HTMLElementImpl element = this.element;
			if (element != null) {
				String scrolling = element.getAttribute("scrolling");
				if (scrolling != null) {
					scrolling = scrolling.trim().toLowerCase();
					switch (scrolling) {
						case "no":
							overflow = OVERFLOW_HIDDEN;
							break;
						case "yes":
							overflow = OVERFLOW_SCROLL;
							break;
						case "auto":
							overflow = OVERFLOW_AUTO;
							break;
						default:
							break;
					}
				}
			}
		}
		this.overflowX = overflow;
		return overflow;
	}

	/** {@inheritDoc} */
	@Override
	public int getOverflowY() {
		int overflow = this.overflowY;
		if (overflow != -1) {
			return overflow;
		}
		overflow = super.getOverflowY();
		if (overflow == OVERFLOW_NONE) {
			final HTMLElementImpl element = this.element;
			if (element != null) {
				String scrolling = element.getAttribute("scrolling");
				if (scrolling != null) {
					scrolling = scrolling.trim().toLowerCase();
					switch (scrolling) {
						case "no":
							overflow = OVERFLOW_HIDDEN;
							break;
						case "yes":
							overflow = OVERFLOW_SCROLL;
							break;
						case "auto":
							overflow = OVERFLOW_AUTO;
							break;
						default:
							break;
					}
				}
			}
		}
		this.overflowY = overflow;
		return overflow;
	}
}
