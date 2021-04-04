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
package org.loboevolution.html.renderstate;

import java.awt.Color;

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.BorderInsets;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.info.BorderInfo;

/**
 * <p>IFrameRenderState class.</p>
 *
 *
 *
 */
public class IFrameRenderState extends StyleSheetRenderState {

	/**
	 * <p>Constructor for IFrameRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public IFrameRenderState(RenderState prevRenderState, HTMLElementImpl element) {
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
		if (binfo == null || binfo.getTopStyle() == BorderInsets.BORDER_STYLE_NONE
				&& binfo.getBottomStyle() == BorderInsets.BORDER_STYLE_NONE && binfo.getLeftStyle() == BorderInsets.BORDER_STYLE_NONE
				&& binfo.getRightStyle() == BorderInsets.BORDER_STYLE_NONE) {
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
				final HtmlInsets borderInsets = new HtmlInsets();
				borderInsets.top = borderInsets.left = borderInsets.right = borderInsets.bottom = value != 0 ? 1 : 0;
				borderInsets.topType = borderInsets.leftType = borderInsets.rightType = borderInsets.bottomType = HtmlInsets.TYPE_PIXELS;
				binfo.setInsets(borderInsets);
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
