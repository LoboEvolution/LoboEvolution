/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
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

	/** The background info. */
	private BackgroundInfo backgroundInfo = INVALID_BACKGROUND_INFO;

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
		if ((binfo == null) || (binfo.getBackgroundColor() == null)) {
			String bgColor = element.getBgColor();
			if ((bgColor != null) && !"".equals(bgColor)) {
				Color bgc = ColorFactory.getInstance().getColor(bgColor);
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				binfo.setBackgroundColor(bgc);
			}
		}
		if ((binfo == null) || (binfo.getBackgroundImage() == null)) {
			String background = element.getAttribute(HtmlAttributeProperties.BACKGROUND);
			if ((background != null) && !"".equals(background)) {
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				binfo.setBackgroundImage(this.document.getFullURL(background));
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
		if (binfo != INVALID_BORDER_INFO) {
			return binfo;
		}
		binfo = super.getBorderInfo();
		if ((binfo == null) || ((binfo.getTopStyle() == HtmlValues.BORDER_STYLE_NONE)
				&& (binfo.getBottomStyle() == HtmlValues.BORDER_STYLE_NONE)
				&& (binfo.getLeftStyle() == HtmlValues.BORDER_STYLE_NONE)
				&& (binfo.getRightStyle() == HtmlValues.BORDER_STYLE_NONE))) {
			if (binfo == null) {
				binfo = new BorderInfo();
			}
			HTMLElementImpl element = this.element;
			if (element != null) {
				String border = element.getAttribute(HtmlAttributeProperties.BORDER);
				if (border != null) {
					border = border.trim();
					int value;
					int valueType;
					if (border.endsWith("%")) {
						valueType = HtmlInsets.TYPE_PERCENT;
						try {
							value = Integer.parseInt(border.substring(0, border.length() - 1));
						} catch (NumberFormatException nfe) {
							value = 0;
						}
					} else {
						valueType = HtmlInsets.TYPE_PIXELS;
						try {
							value = Integer.parseInt(border);
						} catch (NumberFormatException nfe) {
							value = 0;
						}
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
