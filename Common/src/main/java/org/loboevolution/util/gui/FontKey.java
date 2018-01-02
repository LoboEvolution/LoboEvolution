/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.util.gui;

import java.util.Set;

import org.loboevolution.util.Objects;

/**
 * The Class FontKey.
 */
public class FontKey {

	/** The font family. */
	private String fontFamily;

	/** The font style. */
	private String fontStyle;

	/** The font variant. */
	private String fontVariant;

	/** The font weight. */
	private String fontWeight;

	/** The font size. */
	private float fontSize;

	/** The locales. */
	private Set locales;

	/** The superscript. */
	private Integer superscript;

	/** The letterSpacing. */
	private Integer letterSpacing;

	/** The strikethrough. */
	private Boolean strikethrough;

	/** The underline. */
	private Integer underline;
	
	/** The cached hash. */
	private int cachedHash = -1;

	/**
	 * Instantiates a new font key.
	 *
	 * @param fontFamily
	 *            the font family
	 * @param fontStyle
	 *            the font style
	 * @param fontVariant
	 *            the font variant
	 * @param fontWeight
	 *            the font weight
	 * @param fontSize
	 *            the font size
	 * @param locales
	 *            the locales
	 * @param superscript
	 *            the superscript
	 */
	public FontKey(final String fontFamily, final String fontStyle, final String fontVariant, final String fontWeight,
			final float fontSize, final Set locales, final Integer superscript, final Integer letterSpacing,
			final boolean strikethrough, final Integer underline) {
		this.fontFamily = fontFamily == null ? null : fontFamily.intern();
		this.fontStyle = fontStyle == null ? null : fontStyle.intern();
		this.fontVariant = fontVariant == null ? null : fontVariant.intern();
		this.fontWeight = fontWeight == null ? null : fontWeight.intern();
		this.fontSize = fontSize;
		this.locales = locales;
		this.superscript = superscript;
		this.letterSpacing = letterSpacing;
		this.strikethrough = strikethrough;
		this.underline = underline;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (other == this) {
			// Quick check.
			return true;
		}
		FontKey ors;
		try {
			ors = (FontKey) other;
		} catch (ClassCastException cce) {
			// Not expected
			return false;
		}
		// Note that we use String.intern() for all string fields,
		// so we can do instance comparisons.
		return this.fontSize == ors.fontSize && this.fontFamily == ors.fontFamily && this.fontStyle == ors.fontStyle
				&& this.fontWeight == ors.fontWeight && this.fontVariant == ors.fontVariant
				&& this.superscript == ors.superscript && Objects.equals(this.locales, ors.locales);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int ch = this.cachedHash;
		if (ch != -1) {
			// Object is immutable - caching is ok.
			return ch;
		}
		String ff = this.fontFamily;
		if (ff == null) {
			ff = "";
		}
		String fw = this.fontWeight;
		if (fw == null) {
			fw = "";
		}
		String fs = this.fontStyle;
		if (fs == null) {
			fs = "";
		}
		Integer ss = this.superscript;
		ch = ff.hashCode() ^ fw.hashCode() ^ fs.hashCode() ^ (int) this.fontSize ^ (ss == null ? 0 : ss.intValue());
		this.cachedHash = ch;
		return ch;
	}

	/**
	 * Gets the cached hash.
	 *
	 * @return the cached hash
	 */
	public int getCachedHash() {
		return cachedHash;
	}

	/**
	 * Sets the cached hash.
	 *
	 * @param cachedHash
	 *            the new cached hash
	 */
	public void setCachedHash(int cachedHash) {
		this.cachedHash = cachedHash;
	}

	/**
	 * Gets the font family.
	 *
	 * @return the font family
	 */
	public String getFontFamily() {
		return fontFamily;
	}

	/**
	 * Gets the font style.
	 *
	 * @return the font style
	 */
	public String getFontStyle() {
		return fontStyle;
	}

	/**
	 * Gets the font variant.
	 *
	 * @return the font variant
	 */
	public String getFontVariant() {
		return fontVariant;
	}

	/**
	 * Gets the font weight.
	 *
	 * @return the font weight
	 */
	public String getFontWeight() {
		return fontWeight;
	}

	/**
	 * Gets the font size.
	 *
	 * @return the font size
	 */
	public float getFontSize() {
		return fontSize;
	}

	/**
	 * Gets the locales.
	 *
	 * @return the locales
	 */
	public Set getLocales() {
		return locales;
	}

	/**
	 * Gets the superscript.
	 *
	 * @return the superscript
	 */
	public Integer getSuperscript() {
		return superscript;
	}

	/**
	 * Gets the letterSpacing.
	 *
	 * @return the letterSpacing
	 */
	public Integer getLetterSpacing() {
		return letterSpacing;
	}

	/**
	 * @return the strikethrough
	 */
	public Boolean getStrikethrough() {
		return strikethrough;
	}

	/**
	 * @return the underline
	 */
	public Integer getUnderline() {
		return underline;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FontKey[family=" + this.fontFamily + ",size=" + this.fontSize + ",style=" + this.fontStyle + ",weight="
				+ this.fontWeight + ",variant=" + this.fontVariant + ",superscript=" + this.superscript
				+ ",letterSpacing=" + this.letterSpacing + ",strikethrough=" + this.strikethrough + ",underline="
				+ this.underline + "]";
	}
}
