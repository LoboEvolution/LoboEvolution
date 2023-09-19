/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.renderstate;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLTableElement;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLTableElementImpl;
import org.loboevolution.html.style.BorderInsets;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.info.BackgroundInfo;
import org.loboevolution.info.BorderInfo;
import org.loboevolution.laf.ColorFactory;

import java.awt.*;
import java.util.Base64;

/**
 * <p>TableRenderState class.</p>
 *
 *
 *
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
		if (binfo == null || binfo.borderInfoIsVoid()) {
			if (binfo == null) {
				binfo = new BorderInfo();
			}
			final HTMLElementImpl element = this.element;
			if (element != null) {

				String border;
				if (element instanceof HTMLTableElement) {
					HTMLTableElement table = (HTMLTableElement) element;
					border = table.getBorder();
				} else {
					border = element.getAttribute("border");
				}

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
					binfo.setInsets(new HtmlInsets(value, valueType));
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
	public int getDefaultDisplay() {
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
