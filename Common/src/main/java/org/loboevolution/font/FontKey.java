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
package org.loboevolution.font;

import java.awt.Font;
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
	
	
	public FontKey() {
		setFontFamily(Font.SANS_SERIF);
		setFontStyle(null);
		setFontVariant(null);
		setFontWeight(null);
		setFontSize(LAFSettings.getInstance().getFontSize());
		setLocales(null);
		setSuperscript(null);
		setLetterSpacing(0);
		setStrikethrough(false);
		setUnderline(0);
	}

	/**
	 * @return the fontFamily
	 */
	public String getFontFamily() {
		return fontFamily;
	}

	/**
	 * @param fontFamily the fontFamily to set
	 */
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	/**
	 * @return the fontStyle
	 */
	public String getFontStyle() {
		return fontStyle;
	}

	/**
	 * @param fontStyle the fontStyle to set
	 */
	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

	/**
	 * @return the fontVariant
	 */
	public String getFontVariant() {
		return fontVariant;
	}

	/**
	 * @param fontVariant the fontVariant to set
	 */
	public void setFontVariant(String fontVariant) {
		this.fontVariant = fontVariant;
	}

	/**
	 * @return the fontWeight
	 */
	public String getFontWeight() {
		return fontWeight;
	}

	/**
	 * @param fontWeight the fontWeight to set
	 */
	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}

	/**
	 * @return the fontSize
	 */
	public float getFontSize() {
		return fontSize;
	}

	/**
	 * @param fontSize the fontSize to set
	 */
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @return the locales
	 */
	public Set getLocales() {
		return locales;
	}

	/**
	 * @param locales the locales to set
	 */
	public void setLocales(Set locales) {
		this.locales = locales;
	}

	/**
	 * @return the superscript
	 */
	public Integer getSuperscript() {
		return superscript;
	}

	/**
	 * @param superscript the superscript to set
	 */
	public void setSuperscript(Integer superscript) {
		this.superscript = superscript;
	}

	/**
	 * @return the letterSpacing
	 */
	public Integer getLetterSpacing() {
		return letterSpacing;
	}

	/**
	 * @param letterSpacing the letterSpacing to set
	 */
	public void setLetterSpacing(Integer letterSpacing) {
		this.letterSpacing = letterSpacing;
	}

	/**
	 * @return the strikethrough
	 */
	public Boolean getStrikethrough() {
		return strikethrough;
	}

	/**
	 * @param strikethrough the strikethrough to set
	 */
	public void setStrikethrough(Boolean strikethrough) {
		this.strikethrough = strikethrough;
	}

	/**
	 * @return the underline
	 */
	public Integer getUnderline() {
		return underline;
	}

	/**
	 * @param underline the underline to set
	 */
	public void setUnderline(Integer underline) {
		this.underline = underline;
	}

	/**
	 * @return the cachedHash
	 */
	public int getCachedHash() {
		return cachedHash;
	}

	/**
	 * @param cachedHash the cachedHash to set
	 */
	public void setCachedHash(int cachedHash) {
		this.cachedHash = cachedHash;
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
