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
import org.lobobrowser.html.info.BorderInfo;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.HtmlInsets;
import org.lobobrowser.html.style.HtmlValues;

/**
 * The Class ImageRenderState.
 */
public class ImageRenderState extends StyleSheetRenderState {

	/**
	 * Instantiates a new image render state.
	 *
	 * @param prevRenderState
	 *            the prev render state
	 * @param element
	 *            the element
	 */
	public ImageRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderstate.StyleSheetRenderState#getMarginInsets()
	 */
	@Override
	public HtmlInsets getMarginInsets() {
		HtmlInsets mi = this.marginInsets;
		if (!INVALID_INSETS.equals(mi)) {
			return mi;
		}
		AbstractCSS2Properties props = this.getCssProperties();
		if (props == null) {
			mi = null;
		} else {
			mi = HtmlValues.getMarginInsets(props, this);
		}
		if (mi == null) {
			boolean createNew = false;
			String hspaceText = this.element.getAttribute(HtmlAttributeProperties.HSPACE);
			int hspace = HtmlValues.getPixelSize(hspaceText, this, 0);
			String vspaceText = this.element.getAttribute(HtmlAttributeProperties.VSPACE);
			int vspace = HtmlValues.getPixelSize(vspaceText, this, 0);

			if (createNew) {
				mi = new HtmlInsets();
				mi.top = vspace;
				mi.topType = HtmlInsets.TYPE_PIXELS;
				mi.bottom = vspace;
				mi.bottomType = HtmlInsets.TYPE_PIXELS;
				mi.left = hspace;
				mi.leftType = HtmlInsets.TYPE_PIXELS;
				mi.right = hspace;
				mi.rightType = HtmlInsets.TYPE_PIXELS;
			}
		}
		this.marginInsets = mi;
		return mi;
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
					int valueType;
					int value = HtmlValues.getPixelSize(border, this, 0);

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
						binfo.setTopColor(Color.BLACK);
					}
					if (binfo.getLeftColor() == null) {
						binfo.setLeftColor(Color.BLACK);
					}
					if (binfo.getRightColor() == null) {
						binfo.setRightColor(Color.BLACK);
					}
					if (binfo.getBottomColor() == null) {
						binfo.setBottomColor(Color.BLACK);
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
