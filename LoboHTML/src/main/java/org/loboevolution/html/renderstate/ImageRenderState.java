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

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.style.BorderInsets;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.MarginInsets;
import org.loboevolution.info.BorderInfo;

import java.awt.*;

/**
 * <p>ImageRenderState class.</p>
 *
 *
 *
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
		if (binfo == null || binfo.borderInfoIsVoid()) {
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

					binfo.setInsets(new HtmlInsets(value, valueType));
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
		final CSSStyleDeclaration props = getCssProperties();
		if (props == null) {
			mi = null;
		} else {
			mi = MarginInsets.getMarginInsets(props, element,this);
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
				mi.setTop(vspace);
				mi.setBottom(vspace);
				mi.setLeft(hspace);
				mi.setRight(hspace);
				mi.setTopType(HtmlInsets.TYPE_PIXELS);
				mi.setBottomType(HtmlInsets.TYPE_PIXELS);
				mi.setLeftType(HtmlInsets.TYPE_PIXELS);
				mi.setRightType(HtmlInsets.TYPE_PIXELS);
			}
		}
		this.marginInsets = mi;
		return mi;
	}

	/** {@inheritDoc} */
	@Override
	public int getDefaultDisplay() {
		return DISPLAY_INLINE_BLOCK;
	}
}
