/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.loboevolution.html.renderstate;

import java.awt.Color;

import org.loboevolution.info.BorderInfo;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.BorderInsets;
import org.loboevolution.html.style.HtmlInsets;

/**
 * <p>IFrameRenderState class.</p>
 *
 * @author utente
 * @version $Id: $Id
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
					if ("no".equals(scrolling)) {
						overflow = OVERFLOW_HIDDEN;
					} else if ("yes".equals(scrolling)) {
						overflow = OVERFLOW_SCROLL;
					} else if ("auto".equals(scrolling)) {
						overflow = OVERFLOW_AUTO;
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
					if ("no".equals(scrolling)) {
						overflow = OVERFLOW_HIDDEN;
					} else if ("yes".equals(scrolling)) {
						overflow = OVERFLOW_SCROLL;
					} else if ("auto".equals(scrolling)) {
						overflow = OVERFLOW_AUTO;
					}
				}
			}
		}
		this.overflowY = overflow;
		return overflow;
	}
}
