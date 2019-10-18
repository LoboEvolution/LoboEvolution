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

import org.loboevolution.common.Strings;
import org.loboevolution.info.BackgroundInfo;
import org.loboevolution.info.BorderInfo;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLTableElementImpl;
import org.loboevolution.html.style.BorderInsets;
import org.loboevolution.html.style.HtmlInsets;

public class TableRenderState extends StyleSheetRenderState {
	private BackgroundInfo backgroundInfo = INVALID_BACKGROUND_INFO;

	public TableRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	@Override
	public BackgroundInfo getBackgroundInfo() {
		BackgroundInfo binfo = this.backgroundInfo;
		if (binfo != INVALID_BACKGROUND_INFO) {
			return binfo;
		}
		// Apply style based on deprecated attributes.
		binfo = super.getBackgroundInfo();
		final HTMLTableElementImpl element = (HTMLTableElementImpl) this.element;
		if (binfo == null || binfo.backgroundColor == null) {
			final String bgColor = element.getBgColor();
			if (Strings.isNotBlank(bgColor)) {
				final Color bgc = ColorFactory.getInstance().getColor(bgColor);
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				binfo.backgroundColor = bgc;
			}
		}
		if (binfo == null || binfo.backgroundImage == null) {
			final String background = element.getAttribute("background");
			if (Strings.isNotBlank(background)) {
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				binfo.backgroundImage = this.document.getFullURL(background);
			}
		}
		this.backgroundInfo = binfo;
		return binfo;
	}

	@Override
	public BorderInfo getBorderInfo() {
		BorderInfo binfo = this.borderInfo;
		if (binfo != INVALID_BORDER_INFO) {
			return binfo;
		}
		binfo = super.getBorderInfo();
		if (binfo == null || binfo.topStyle == BorderInsets.BORDER_STYLE_NONE
				&& binfo.bottomStyle == BorderInsets.BORDER_STYLE_NONE && binfo.leftStyle == BorderInsets.BORDER_STYLE_NONE
				&& binfo.rightStyle == BorderInsets.BORDER_STYLE_NONE) {
			if (binfo == null) {
				binfo = new BorderInfo();
			}
			final HTMLElementImpl element = this.element;
			if (element != null) {
				String border = element.getAttribute("border");
				if (border != null) {
					border = border.trim();
					int value;
					int valueType;
					if (border.endsWith("%")) {
						valueType = HtmlInsets.TYPE_PERCENT;
						try {
							value = Integer.parseInt(border.substring(0, border.length() - 1));
						} catch (final NumberFormatException nfe) {
							value = 0;
						}
					} else {
						valueType = HtmlInsets.TYPE_PIXELS;
						try {
							value = Integer.parseInt(border);
						} catch (final NumberFormatException nfe) {
							value = 0;
						}
					}
					final HtmlInsets borderInsets = new HtmlInsets();
					borderInsets.top = borderInsets.left = borderInsets.right = borderInsets.bottom = value;
					borderInsets.topType = borderInsets.leftType = borderInsets.rightType = borderInsets.bottomType = valueType;
					binfo.insets = borderInsets;
					if (binfo.topColor == null) {
						binfo.topColor = Color.LIGHT_GRAY;
					}
					if (binfo.leftColor == null) {
						binfo.leftColor = Color.LIGHT_GRAY;
					}
					if (binfo.rightColor == null) {
						binfo.rightColor = Color.GRAY;
					}
					if (binfo.bottomColor == null) {
						binfo.bottomColor = Color.GRAY;
					}
					if (value != 0) {
						binfo.topStyle = binfo.leftStyle = binfo.rightStyle = binfo.bottomStyle = BorderInsets.BORDER_STYLE_SOLID;
					}
				}
			}
		}
		this.borderInfo = binfo;
		return binfo;
	}

	@Override
	protected int getDefaultDisplay() {
		return DISPLAY_TABLE;
	}

	@Override
	public Color getTextBackgroundColor() {
		return super.getTextBackgroundColor();
	}

	@Override
	public void invalidate() {
		super.invalidate();
		this.backgroundInfo = INVALID_BACKGROUND_INFO;
	}

}
