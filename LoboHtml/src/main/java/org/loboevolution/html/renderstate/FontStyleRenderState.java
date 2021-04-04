/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.renderstate;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import org.loboevolution.info.WordInfo;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;

/**
 * <p>FontStyleRenderState class.</p>
 *
 *
 *
 */
public class FontStyleRenderState extends RenderStateDelegator {
	/**
	 * <p>createSuperscriptFontStyleRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param superscript a {@link java.lang.Integer} object.
	 * @return a {@link org.loboevolution.html.renderstate.FontStyleRenderState} object.
	 */
	public static FontStyleRenderState createSuperscriptFontStyleRenderState(RenderState prevRenderState,
			Integer superscript) {
		return new FontStyleRenderState(prevRenderState, prevRenderState.getFont().getStyle(), superscript);
	}

	private Font iFont;

	private FontMetrics iFontMetrics;

	Map<String, WordInfo> iWordInfoMap = null;

	private final int style;

	private final Integer superscript;

	/**
	 * <p>Constructor for FontStyleRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param style a int.
	 */
	public FontStyleRenderState(RenderState prevRenderState, int style) {
		this(prevRenderState, style, null);
	}

	FontStyleRenderState(RenderState prevRenderState, int style, Integer superscript) {
		super(prevRenderState);
		this.style = style;
		this.superscript = superscript;
	}

	/** {@inheritDoc} */
	@Override
	public Font getFont() {
		Font f = this.iFont;
		if (f != null) {
			return f;
		}
		final Font parentFont = this.delegate.getFont();
		if (parentFont.getStyle() != this.style) {
			f = parentFont.deriveFont(this.style | parentFont.getStyle());
		} else {
			f = parentFont;
		}
		final FontKey key = new FontKey();
		key.setFontFamily("");
		key.setFontStyle("");
		key.setFontVariant("");
		key.setFontWeight("");
		key.setFontSize(0);
		key.setLocales(null);
		key.setSuperscript(this.superscript);
		key.setLetterSpacing(0);
		key.setStrikethrough(false);
		key.setUnderline(0);
		f = FontFactory.scriptFont(f, key);
		this.iFont = f;
		return f;
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
		wi = new WordInfo();
		final FontMetrics fm = getFontMetrics();
		wi.setFontMetrics(fm);
		wi.setAscentPlusLeading(fm.getAscent() + fm.getLeading());
		wi.setDescent(fm.getDescent());
		wi.setHeight(fm.getHeight());
		wi.setWidth(fm.stringWidth(word));
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
}
