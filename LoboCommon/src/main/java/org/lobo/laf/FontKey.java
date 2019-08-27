package org.lobo.laf;

import java.awt.Font;
import java.util.Locale;
import java.util.Set;

import org.lobo.common.Objects;

/**
 * The Class FontKey.
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

	public FontKey() {
		this.fontFamily = Font.SANS_SERIF;
		this.fontStyle = null;
		this.fontVariant = null;
		this.fontWeight = null;
		this.fontSize = new LAFSettings().getIstance().getFontSize();
		this.locales = null;
		this.superscript = null;
		this.letterSpacing = 0;
		this.strikethrough = false;
		this.underline = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
	 * @return the cachedHash
	 */
	public int getCachedHash() {
		return this.cachedHash;
	}

	/**
	 * @return the fontFamily
	 */
	public String getFontFamily() {
		return this.fontFamily;
	}

	/**
	 * @return the fontSize
	 */
	public float getFontSize() {
		return this.fontSize;
	}

	/**
	 * @return the fontStyle
	 */
	public String getFontStyle() {
		return this.fontStyle;
	}

	/**
	 * @return the fontVariant
	 */
	public String getFontVariant() {
		return this.fontVariant;
	}

	/**
	 * @return the fontWeight
	 */
	public String getFontWeight() {
		return this.fontWeight;
	}

	/**
	 * @return the letterSpacing
	 */
	public Integer getLetterSpacing() {
		return this.letterSpacing;
	}

	/**
	 * @return the locales
	 */
	public Set<Locale> getLocales() {
		return this.locales;
	}

	/**
	 * @return the strikethrough
	 */
	public Boolean getStrikethrough() {
		return this.strikethrough;
	}

	/**
	 * @return the superscript
	 */
	public Integer getSuperscript() {
		return this.superscript;
	}

	/**
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
		ch = ff.hashCode() ^ fw.hashCode() ^ fs.hashCode() ^ (int) this.fontSize ^ (ss == null ? 0 : ss.intValue());
		this.cachedHash = ch;
		return ch;
	}

	/**
	 * @param cachedHash the cachedHash to set
	 */
	public void setCachedHash(int cachedHash) {
		this.cachedHash = cachedHash;
	}

	/**
	 * @param fontFamily the fontFamily to set
	 */
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	/**
	 * @param fontSize the fontSize to set
	 */
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @param fontStyle the fontStyle to set
	 */
	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

	/**
	 * @param fontVariant the fontVariant to set
	 */
	public void setFontVariant(String fontVariant) {
		this.fontVariant = fontVariant;
	}

	/**
	 * @param fontWeight the fontWeight to set
	 */
	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}

	/**
	 * @param letterSpacing the letterSpacing to set
	 */
	public void setLetterSpacing(Integer letterSpacing) {
		this.letterSpacing = letterSpacing;
	}

	/**
	 * @param locales the locales to set
	 */
	public void setLocales(Set locales) {
		this.locales = locales;
	}

	/**
	 * @param strikethrough the strikethrough to set
	 */
	public void setStrikethrough(Boolean strikethrough) {
		this.strikethrough = strikethrough;
	}

	/**
	 * @param superscript the superscript to set
	 */
	public void setSuperscript(Integer superscript) {
		this.superscript = superscript;
	}

	/**
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
	@Override
	public String toString() {
		return "FontKey[family=" + this.fontFamily + ",size=" + this.fontSize + ",style=" + this.fontStyle + ",weight="
				+ this.fontWeight + ",variant=" + this.fontVariant + ",superscript=" + this.superscript
				+ ",letterSpacing=" + this.letterSpacing + ",strikethrough=" + this.strikethrough + ",underline="
				+ this.underline + "]";
	}
}
