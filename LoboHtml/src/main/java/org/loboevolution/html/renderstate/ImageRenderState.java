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
package org.loboevolution.html.renderstate;

import java.awt.Color;

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.BorderInsets;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.MarginInsets;
import org.loboevolution.info.BorderInfo;

/**
 * <p>ImageRenderState class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ImageRenderState extends StyleSheetRenderState {
	/**
	 * <p>Constructor for ImageRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public ImageRenderState(RenderState prevRenderState, HTMLElementImpl element) {
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
						binfo.setTopStyle(BorderInsets.BORDER_STYLE_SOLID);
						binfo.setLeftStyle(BorderInsets.BORDER_STYLE_SOLID);
						binfo.setRightStyle(BorderInsets.BORDER_STYLE_SOLID);
						binfo.setBottomStyle(BorderInsets.BORDER_STYLE_SOLID);
					}
				}
			}
		}
		this.borderInfo = binfo;
		return binfo;
	}

	/** {@inheritDoc} */
	@Override
	public HtmlInsets getMarginInsets() {
		HtmlInsets mi = this.marginInsets;
		if (mi != INVALID_INSETS) {
			return mi;
		}
		final AbstractCSSProperties props = getCssProperties();
		if (props == null) {
			mi = null;
		} else {
			mi = MarginInsets.getMarginInsets(props, this);
		}
		if (mi == null) {
			int hspace = 0;
			int vspace = 0;
			boolean createNew = false;
			final String hspaceText = this.element.getAttribute("hspace");
			if (hspaceText != null && hspaceText.length() != 0) {
				createNew = true;
				try {
					hspace = Integer.parseInt(hspaceText);
				} catch (final NumberFormatException nfe) {
					// TODO: Percentages?
				}
			}
			final String vspaceText = this.element.getAttribute("vspace");
			if (vspaceText != null && vspaceText.length() != 0) {
				createNew = true;
				try {
					vspace = Integer.parseInt(vspaceText);
				} catch (final NumberFormatException nfe) {
					// TODO: Percentages?
				}
			}
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
}
