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
import java.util.Base64;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLTableElementImpl;
import org.loboevolution.html.style.BorderInsets;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.info.BackgroundInfo;
import org.loboevolution.info.BorderInfo;
import org.loboevolution.laf.ColorFactory;

/**
 * <p>TableRenderState class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class TableRenderState extends StyleSheetRenderState {
	private BackgroundInfo backgroundInfo = INVALID_BACKGROUND_INFO;

	/**
	 * <p>Constructor for TableRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public TableRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	/** {@inheritDoc} */
	@Override
	public BackgroundInfo getBackgroundInfo() {
		BackgroundInfo binfo = this.backgroundInfo;
		if (binfo != INVALID_BACKGROUND_INFO) {
			return binfo;
		}
		// Apply style based on deprecated attributes.
		binfo = super.getBackgroundInfo();
		final HTMLTableElementImpl element = (HTMLTableElementImpl) this.element;
		if (binfo == null || binfo.getBackgroundColor() == null) {
			final String bgColor = element.getBgColor();
			if (Strings.isNotBlank(bgColor)) {
				final Color bgc = ColorFactory.getInstance().getColor(bgColor);
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				binfo.setBackgroundColor(bgc);
			}
		}
		if (binfo == null || binfo.getBackgroundImage() == null) {
			String background = element.getAttribute("background");
			if (Strings.isNotBlank(background)) {
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				
				if (background.contains(";base64,")) {
                    final String base64 = background.split(";base64,")[1];
                    final byte[] decodedBytes = Base64.getDecoder().decode(Strings.linearize(base64));
                    background = String.valueOf(decodedBytes);
                }
				binfo.setBackgroundImage(this.document.getFullURL(background));
			}
		}
		this.backgroundInfo = binfo;
		return binfo;
	}

	/** {@inheritDoc} */
	@Override
	public BorderInfo getBorderInfo() {
		BorderInfo binfo = this.borderInfo;
		if (binfo != INVALID_BORDER_INFO) {
			return binfo;
		}
		binfo = super.getBorderInfo();
		binfo = super.getBorderInfo();
		if (binfo == null || binfo.getTopStyle() == BorderInsets.BORDER_STYLE_NONE
				&& binfo.getBottomStyle() == BorderInsets.BORDER_STYLE_NONE
				&& binfo.getLeftStyle() == BorderInsets.BORDER_STYLE_NONE
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
		}
		this.borderInfo = binfo;
		return binfo;
	}

	/** {@inheritDoc} */
	@Override
	protected int getDefaultDisplay() {
		return DISPLAY_TABLE;
	}

	/** {@inheritDoc} */
	@Override
	public Color getTextBackgroundColor() {
		return super.getTextBackgroundColor();
	}

	/** {@inheritDoc} */
	@Override
	public void invalidate() {
		super.invalidate();
		this.backgroundInfo = INVALID_BACKGROUND_INFO;
	}

}
