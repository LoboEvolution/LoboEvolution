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
import org.lobobrowser.html.domimpl.HTMLTableElementImpl;
import org.lobobrowser.html.info.BackgroundInfo;
import org.lobobrowser.html.info.BorderInfo;
import org.lobobrowser.html.style.HtmlInsets;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.util.gui.ColorFactory;

/**
 * The Class TableRenderState.
 */
public class TableRenderState extends StyleSheetRenderState {
	
	/** The background info. */
	private BackgroundInfo backgroundInfo = INVALID_BACKGROUND_INFO;

	/**
	 * Instantiates a new table render state.
	 *
	 * @param prevRenderState
	 *            the prev render state
	 * @param element
	 *            the element
	 */
	public TableRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.StyleSheetRenderState#
	 * getTextBackgroundColor ()
	 */
	@Override
	public Color getTextBackgroundColor() {
		return super.getTextBackgroundColor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderstate.StyleSheetRenderState#getDefaultDisplay(
	 * )
	 */
	@Override
	protected int getDefaultDisplay() {
		return DISPLAY_TABLE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.StyleSheetRenderState#invalidate()
	 */
	@Override
	public void invalidate() {
		super.invalidate();
		this.backgroundInfo = INVALID_BACKGROUND_INFO;
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
		BackgroundInfo binfo = this.backgroundInfo;
		if (binfo != INVALID_BACKGROUND_INFO) {
			return binfo;
		}
		// Apply style based on deprecated attributes.
		binfo = super.getBackgroundInfo();
		HTMLTableElementImpl element = (HTMLTableElementImpl) this.element;
		if (binfo == null || binfo.getBackgroundColor() == null) {
			String bgColor = element.getBgColor();
			if (bgColor != null && !"".equals(bgColor)) {
				Color bgc = ColorFactory.getInstance().getColor(bgColor);
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				binfo.setBackgroundColor(bgc);
			}
		}
		this.backgroundInfo = binfo;
		return binfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderstate.StyleSheetRenderState#getBorderInfo()
	 */
	@Override
	public BorderInfo getBorderInfo() {
		BorderInfo binfo = this.borderInfo;
		if (!INVALID_BORDER_INFO.equals(binfo)) {
			return binfo;
		}
		binfo = super.getBorderInfo();
		if (binfo == null || binfo.getTopStyle() == HtmlValues.BORDER_STYLE_NONE
				&& binfo.getBottomStyle() == HtmlValues.BORDER_STYLE_NONE
				&& binfo.getLeftStyle() == HtmlValues.BORDER_STYLE_NONE
				&& binfo.getRightStyle() == HtmlValues.BORDER_STYLE_NONE) {
			if (binfo == null) {
				binfo = new BorderInfo();
			}
			HTMLElementImpl element = this.element;
			if (element != null) {
				String border = element.getAttribute(HtmlAttributeProperties.BORDER);
				if (border != null) {
					border = border.trim();
					int value = HtmlValues.getPixelSize(border, this, 0);
					int valueType;

					if (border.endsWith("%")) {
						valueType = HtmlInsets.TYPE_PERCENT;
					} else {
						valueType = HtmlInsets.TYPE_PIXELS;
					}

					HtmlInsets borderInsets = new HtmlInsets();
					borderInsets.top = borderInsets.left = borderInsets.right = borderInsets.bottom = value;
					borderInsets.topType = borderInsets.leftType = borderInsets.rightType = borderInsets.bottomType = valueType;
					binfo.setInsets(borderInsets);

					if (binfo.getTopColor() == null) {
						binfo.setTopColor(Color.LIGHT_GRAY);
					}

					if (binfo.getLeftColor() == null) {
						binfo.setLeftColor(Color.LIGHT_GRAY);
					}

					if (binfo.getRightColor() == null) {
						binfo.setRightColor(Color.GRAY);
					}

					if (binfo.getBottomColor() == null) {
						binfo.setBottomColor(Color.GRAY);
					}

					if (value != 0) {
						binfo.setTopStyle(HtmlValues.BORDER_STYLE_SOLID);
						binfo.setLeftStyle(HtmlValues.BORDER_STYLE_SOLID);
						binfo.setRightStyle(HtmlValues.BORDER_STYLE_SOLID);
						binfo.setBottomStyle(HtmlValues.BORDER_STYLE_SOLID);
					}
				}
			}
		}
		this.borderInfo = binfo;
		return binfo;
	}

}
