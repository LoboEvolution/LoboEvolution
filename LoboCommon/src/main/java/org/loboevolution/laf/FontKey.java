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

package org.loboevolution.laf;

import java.awt.Font;
import java.util.Locale;
import java.util.Set;

import org.loboevolution.common.Objects;

/**
 * The Class FontKey.
 *
 * @author utente
 * @version $Id: $Id
 */
public class FontKey {

	/** The cached hash. */
	private int cachedHash = -1;

	/** The font family. */
	private String fontFamily;

	/** The font size. */
	private float fontSize;

	/** The font style. */
	private String fontStyle;

	/** The font variant. */
	private String fontVariant;

	/** The font weight. */
	private String fontWeight;

	/** The letterSpacing. */
	private Integer letterSpacing;

	/** The locales. */
	private Set<Locale> locales;

	/** The strikethrough. */
	private Boolean strikethrough;

	/** The superscript. */
	private Integer superscript;

	/** The underline. */
	private Integer underline;

	/**
	 * <p>Constructor for FontKey.</p>
	 */
	public FontKey() {
		this.fontFamily = Font.SANS_SERIF;
		this.fontStyle = null;
		this.fontVariant = null;
		this.fontWeight = null;
		this.fontSize = new LAFSettings().getInstance().getFontSize();
		this.locales = null;
		this.superscript = null;
		this.letterSpacing = 0;
		this.strikethrough = false;
		this.underline = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object other) {
		if (Objects.equals(other, this)) {
			return true;
		}
		FontKey ors;
		if (other instanceof FontKey) {
			ors = (FontKey) other;
		} else {
			return false;
		}
		// Note that we use String.intern() for all string fields,
		// so we can do instance comparisons.
		return this.fontSize == ors.fontSize && this.fontFamily == ors.fontFamily && this.fontStyle == ors.fontStyle
				&& this.fontWeight == ors.fontWeight && this.fontVariant == ors.fontVariant
				&& this.superscript == ors.superscript && Objects.equals(this.locales, ors.locales);
	}

	/**
	 * <p>Getter for the field cachedHash.</p>
	 *
	 * @return the cachedHash
	 */
	public int getCachedHash() {
		return this.cachedHash;
	}

	/**
	 * <p>Getter for the field fontFamily.</p>
	 *
	 * @return the fontFamily
	 */
	public String getFontFamily() {
		return this.fontFamily;
	}

	/**
	 * <p>Getter for the field fontSize.</p>
	 *
	 * @return the fontSize
	 */
	public float getFontSize() {
		return this.fontSize;
	}

	/**
	 * <p>Getter for the field fontStyle.</p>
	 *
	 * @return the fontStyle
	 */
	public String getFontStyle() {
		return this.fontStyle;
	}

	/**
	 * <p>Getter for the field fontVariant.</p>
	 *
	 * @return the fontVariant
	 */
	public String getFontVariant() {
		return this.fontVariant;
	}

	/**
	 * <p>Getter for the field fontWeight.</p>
	 *
	 * @return the fontWeight
	 */
	public String getFontWeight() {
		return this.fontWeight;
	}

	/**
	 * <p>Getter for the field letterSpacing.</p>
	 *
	 * @return the letterSpacing
	 */
	public Integer getLetterSpacing() {
		return this.letterSpacing;
	}

	/**
	 * <p>Getter for the field locales.</p>
	 *
	 * @return the locales
	 */
	public Set<Locale> getLocales() {
		return this.locales;
	}

	/**
	 * <p>Getter for the field strikethrough.</p>
	 *
	 * @return the strikethrough
	 */
	public Boolean getStrikethrough() {
		return this.strikethrough;
	}

	/**
	 * <p>Getter for the field superscript.</p>
	 *
	 * @return the superscript
	 */
	public Integer getSuperscript() {
		return this.superscript;
	}

	/**
	 * <p>Getter for the field underline.</p>
	 *
	 * @return the underline
	 */
	public Integer getUnderline() {
		return this.underline;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	/** {@inheritDoc} */
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
		final Integer ss = this.superscript;
		ch = ff.hashCode() ^ fw.hashCode() ^ fs.hashCode() ^ (int) this.fontSize ^ (ss == null ? 0 : ss);
		this.cachedHash = ch;
		return ch;
	}

	/**
	 * <p>Setter for the field cachedHash.</p>
	 *
	 * @param cachedHash the cachedHash to set
	 */
	public void setCachedHash(int cachedHash) {
		this.cachedHash = cachedHash;
	}

	/**
	 * <p>Setter for the field fontFamily.</p>
	 *
	 * @param fontFamily the fontFamily to set
	 */
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	/**
	 * <p>Setter for the field fontSize.</p>
	 *
	 * @param fontSize the fontSize to set
	 */
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * <p>Setter for the field fontStyle.</p>
	 *
	 * @param fontStyle the fontStyle to set
	 */
	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

	/**
	 * <p>Setter for the field fontVariant.</p>
	 *
	 * @param fontVariant the fontVariant to set
	 */
	public void setFontVariant(String fontVariant) {
		this.fontVariant = fontVariant;
	}

	/**
	 * <p>Setter for the field fontWeight.</p>
	 *
	 * @param fontWeight the fontWeight to set
	 */
	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}

	/**
	 * <p>Setter for the field letterSpacing.</p>
	 *
	 * @param letterSpacing the letterSpacing to set
	 */
	public void setLetterSpacing(Integer letterSpacing) {
		this.letterSpacing = letterSpacing;
	}

	/**
	 * <p>Setter for the field locales.</p>
	 *
	 * @param locales the locales to set
	 */
	public void setLocales(Set<Locale> locales) {
		this.locales = locales;
	}

	/**
	 * <p>Setter for the field strikethrough.</p>
	 *
	 * @param strikethrough the strikethrough to set
	 */
	public void setStrikethrough(Boolean strikethrough) {
		this.strikethrough = strikethrough;
	}

	/**
	 * <p>Setter for the field superscript.</p>
	 *
	 * @param superscript the superscript to set
	 */
	public void setSuperscript(Integer superscript) {
		this.superscript = superscript;
	}

	/**
	 * <p>Setter for the field underline.</p>
	 *
	 * @param underline the underline to set
	 */
	public void setUnderline(Integer underline) {
		this.underline = underline;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "FontKey[family=" + this.fontFamily + ",size=" + this.fontSize + ",style=" + this.fontStyle + ",weight="
				+ this.fontWeight + ",variant=" + this.fontVariant + ",superscript=" + this.superscript
				+ ",letterSpacing=" + this.letterSpacing + ",strikethrough=" + this.strikethrough + ",underline="
				+ this.underline + "]";
	}
}
