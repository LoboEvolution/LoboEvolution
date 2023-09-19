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
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.style.FontValues;
import org.loboevolution.info.WordInfo;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;
import org.loboevolution.laf.LAFType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>FontStyleRenderState class.</p>
 */
public class FontStyleRenderState extends RenderStateDelegator {

	private Font iFont;

	private FontMetrics iFontMetrics;

	private final HTMLElementImpl element;

	private final RenderState prevRenderState;

	private final LAFType type;

	Map<String, WordInfo> iWordInfoMap = null;

	private int superscript;

	/**
	 * <p>Constructor for FontStyleRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param type a {@link org.loboevolution.laf.LAFType} object.
	 */
	public FontStyleRenderState(RenderState prevRenderState, HTMLElementImpl element, LAFType type) {
		super(prevRenderState);
		this.type = type;
		this.iFont = null;
		this.element = element;
		this.prevRenderState = prevRenderState;
	}

	/**
	 * <p>Constructor for FontStyleRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param superscript a {@link java.lang.Integer} object.
	 */
	public FontStyleRenderState(RenderState prevRenderState, HTMLElementImpl element, Integer superscript) {
		super(prevRenderState);
		this.iFont = prevRenderState.getFont();
		this.superscript = superscript;
		this.type = null;
		this.element = element;
		this.prevRenderState = prevRenderState;
	}

	/** {@inheritDoc} */
	@Override
	public Font getFont() {
		final HtmlRendererConfig config = element.getHtmlRendererConfig();
		FontKey fontkey = FontValues.getDefaultFontKey(config);

		if (iFont != null) {
			fontkey.setSuperscript(this.superscript);
			return  FontFactory.getInstance().scriptFont(this.iFont, fontkey);
		}

		if (LAFType.BOLD.equals(type)) {
			fontkey.setFontWeight(LAFType.BOLD.getValue());
		}

		if (LAFType.ITALIC.equals(type)) {
			fontkey.setFontStyle(LAFType.ITALIC.getValue());
		}

		if (LAFType.MONOSPACED.equals(type)) {
			fontkey.setFontFamily(LAFType.MONOSPACED.getValue());
		}

		final CSSStyleDeclaration props = element.getCurrentStyle();
		final String fontSize = props == null ? null : props.getFontSize();
		final String fSize = Strings.isNotBlank(fontSize) ? fontSize : "1.2rem";
		fontkey.setFontSize(FontValues.getFontSize(fSize, element.getDocumentNode().getDefaultView(), prevRenderState));
		return FontFactory.getInstance().getFont(FontValues.getFontKey(fontkey, element, props, prevRenderState));
	}

	/** {@inheritDoc} */
	@Override
	public FontMetrics getFontMetrics() {
		FontMetrics fm = this.iFontMetrics;
		if (fm == null) {
			// TODO getFontMetrics deprecated. How to get text width?
			fm = Toolkit.getDefaultToolkit().getFontMetrics(getFont());
			this.iFontMetrics = fm;
		}
		return fm;
	}

	/** {@inheritDoc} */
	@Override
	public final WordInfo getWordInfo(String word) {
		// Expected to be called only in the GUI (rendering) thread.
		// No synchronization necessary.
		Map<String, WordInfo> map = this.iWordInfoMap;
		if (map == null) {
			map = new HashMap<>();
			this.iWordInfoMap = map;
		}
		WordInfo wi = map.get(word);
		if (wi != null) {
			return wi;
		}
		final FontMetrics fm = getFontMetrics();
		wi = WordInfo.builder()
				.fontMetrics(fm)
				.ascentPlusLeading(fm.getAscent() + fm.getLeading())
				.descent(fm.getDescent())
				.height(fm.getHeight())
				.width(fm.stringWidth(word))
				.build();
		map.put(word, wi);
		return wi;
	}

	/** {@inheritDoc} */
	@Override
	public void invalidate() {
		this.delegate.invalidate();
		this.iFont = null;
		this.iFontMetrics = null;
		final Map<String, WordInfo> map = this.iWordInfoMap;
		if (map != null) {
			map.clear();
		}
	}

	@Override
	public int getDefaultDisplay() {
		return DISPLAY_INLINE;
	}

	@Override
	public int getDisplay() {
		return getDefaultDisplay();
	}
}
