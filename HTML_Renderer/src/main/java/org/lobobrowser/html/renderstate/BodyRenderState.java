/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderstate;

import java.awt.Color;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.info.BackgroundInfo;
import org.lobobrowser.html.style.HtmlInsets;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.util.gui.ColorFactory;

/**
 * The Class BodyRenderState.
 */
public class BodyRenderState extends StyleSheetRenderState {

	/**
	 * Instantiates a new body render state.
	 *
	 * @param prevRenderState
	 *            the prev render state
	 * @param element
	 *            the element
	 */
	public BodyRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.StyleSheetRenderState#invalidate()
	 */
	@Override
	public void invalidate() {
		super.invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderstate.StyleSheetRenderState#getBackgroundInfo(
	 * )
	 */
	@Override
	public BackgroundInfo getBackgroundInfo() {
		BackgroundInfo binfo = this.iBackgroundInfo;
		if (binfo != INVALID_BACKGROUND_INFO) {
			return binfo;
		}
		binfo = super.getBackgroundInfo();
		if (binfo == null || binfo.getBackgroundColor() == null) {
			String bgcolor = this.element.getAttribute(HtmlAttributeProperties.BGCOLOR);
			if (bgcolor != null && bgcolor.length() > 0) {
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				binfo.setBackgroundColor(ColorFactory.getInstance().getColor(bgcolor));
			}
		}
		this.iBackgroundInfo = binfo;
		return binfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.StyleSheetRenderState#getColor()
	 */
	@Override
	public Color getColor() {
		Color c = super.getColor();
		if (c != null) {
			return c;
		}
		String tcolor = this.element.getAttribute(HtmlAttributeProperties.TEXT);

		if (tcolor == null) {
			tcolor = "black";
		}

		if (tcolor != null && tcolor.length() != 0) {
			c = ColorFactory.getInstance().getColor(tcolor);
		}

		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderstate.StyleSheetRenderState#getMarginInsets()
	 */
	@Override
	public HtmlInsets getMarginInsets() {
		HtmlInsets insets = this.marginInsets;
		if (insets != INVALID_INSETS) {
			return insets;
		}
		insets = super.getMarginInsets();
		if (insets == null) {
			HTMLElementImpl element = this.element;
			String leftMargin = element.getAttribute(HtmlAttributeProperties.LEFTMARGIN);
			String rightMargin = element.getAttribute(HtmlAttributeProperties.RIGHTMARGIN);
			String bottomMargin = element.getAttribute(HtmlAttributeProperties.TOPMARGIN);
			String topMargin = element.getAttribute(HtmlAttributeProperties.TOPMARGIN);
			String marginWidth = element.getAttribute(HtmlAttributeProperties.MARGINWIDTH);
			String marginHeight = element.getAttribute(HtmlAttributeProperties.MARGINHEIGHT);
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

}
