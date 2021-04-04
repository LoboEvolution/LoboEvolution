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

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.info.BackgroundInfo;
import org.loboevolution.laf.ColorFactory;

/**
 * <p>BodyRenderState class.</p>
 *
 *
 *
 */
public class BodyRenderState extends StyleSheetRenderState {

	/**
	 * <p>Constructor for BodyRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public BodyRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	/** {@inheritDoc} */
	@Override
	public BackgroundInfo getBackgroundInfo() {
		BackgroundInfo binfo = this.iBackgroundInfo;
		if (binfo != INVALID_BACKGROUND_INFO) {
			return binfo;
		}
		binfo = super.getBackgroundInfo();
		if (binfo == null || binfo.getBackgroundColor() == null) {
			final String bgcolor = this.element.getAttribute("bgcolor");
			final String background = this.element.getAttribute("background");
			if (Strings.isNotBlank(bgcolor)) {
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				binfo.setBackgroundColor(ColorFactory.getInstance().getColor(bgcolor));
			}
			
			if (Strings.isNotBlank(background)) {
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				binfo.setBackgroundImage(this.document.getFullURL(background));
			}
		}
		this.iBackgroundInfo = binfo;
		return binfo;
	}
	
	/** {@inheritDoc} */
	@Override
	public Color getColor() {
		Color c = super.getColor();
		if (c != null) {
			return c;
		}
		String tcolor = this.element.getAttribute("text");
		
		if (Strings.isNotBlank(tcolor)) {
			c = ColorFactory.getInstance().getColor(tcolor);
		} else {
			tcolor = "black";
		}

		return c;
	}

	/** {@inheritDoc} */
	@Override
	public HtmlInsets getMarginInsets() {
		HtmlInsets insets = this.marginInsets;
		if (insets != INVALID_INSETS) {
			return insets;
		}
		insets = super.getMarginInsets();
		if (insets == null) {
			final HTMLElementImpl element = this.element;
			String leftMargin = element.getAttribute("leftmargin");
			String rightMargin = element.getAttribute("rightmargin");
            String bottomMargin = element.getAttribute("bottommargin");
			String topMargin = element.getAttribute("topmargin");
			final String marginWidth = element.getAttribute("marginwidth");
			final String marginHeight = element.getAttribute("marginheight");
			if (leftMargin == null) {
				leftMargin = marginWidth;
			}
			if (rightMargin == null) {
				rightMargin = marginWidth;
			}
			if (topMargin == null) {
				topMargin = marginHeight;
			}
			if (bottomMargin == null) {
				bottomMargin = marginHeight;
			}
			if (leftMargin != null) {
				if (insets == null) {
					insets = new HtmlInsets();
				}
				insets.left = HtmlValues.getPixelSize(leftMargin, null, 0);
				insets.leftType = HtmlInsets.TYPE_PIXELS;
			}
			if (rightMargin != null) {
				if (insets == null) {
					insets = new HtmlInsets();
				}
				insets.right = HtmlValues.getPixelSize(rightMargin, null, 0);
				insets.rightType = HtmlInsets.TYPE_PIXELS;
			}
			if (topMargin != null) {
				if (insets == null) {
					insets = new HtmlInsets();
				}
				insets.top = HtmlValues.getPixelSize(topMargin, null, 0);
				insets.topType = HtmlInsets.TYPE_PIXELS;
			}
			if (bottomMargin != null) {
				if (insets == null) {
					insets = new HtmlInsets();
				}
				insets.bottom = HtmlValues.getPixelSize(bottomMargin, null, 0);
				insets.bottomType = HtmlInsets.TYPE_PIXELS;
			}
		}
		this.marginInsets = insets;
		return insets;
	}

	/** {@inheritDoc} */
	@Override
	public void invalidate() {
		super.invalidate();
	}

}
