/*
 * GNU GENERAL LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 -
 * 2015 Lobo Evolution This program is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either verion 2 of the License, or
 * (at your option) any later version. This program is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General License for more details. You should have received a copy of the GNU
 * General Public License along with this library; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301
 * USA Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.util.gui;

import java.util.Set;

import org.lobobrowser.util.Objects;

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
    public FontKey(final String fontFamily, final String fontStyle,
            final String fontVariant, final String fontWeight,
            final float fontSize, final Set locales,
            final Integer superscript) {
        this.fontFamily = fontFamily == null ? null : fontFamily.intern();
        this.fontStyle = fontStyle == null ? null : fontStyle.intern();
        this.fontVariant = fontVariant == null ? null : fontVariant.intern();
        this.fontWeight = fontWeight == null ? null : fontWeight.intern();
        this.fontSize = fontSize;
        this.locales = locales;
        this.superscript = superscript;
    }
    
    /*
     * (non-Javadoc)
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
        return (this.fontSize == ors.fontSize)
                && (this.fontFamily == ors.fontFamily)
                && (this.fontStyle == ors.fontStyle)
                && (this.fontWeight == ors.fontWeight)
                && (this.fontVariant == ors.fontVariant)
                && (this.superscript == ors.superscript)
                && Objects.equals(this.locales, ors.locales);
    }
    
    /** The cached hash. */
    private int cachedHash = -1;
    
    /*
     * (non-Javadoc)
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
        ch = ff.hashCode() ^ fw.hashCode() ^ fs.hashCode() ^ (int) this.fontSize
                ^ (ss == null ? 0 : ss.intValue());
        this.cachedHash = ch;
        return ch;
    }
    
    /**
     * @return the cachedHash
     */
    public int getCachedHash() {
        return cachedHash;
    }
    
    /**
     * @param cachedHash
     *            the cachedHash to set
     */
    public void setCachedHash(int cachedHash) {
        this.cachedHash = cachedHash;
    }
    
    /**
     * @return the fontFamily
     */
    public String getFontFamily() {
        return fontFamily;
    }
    
    /**
     * @return the fontStyle
     */
    public String getFontStyle() {
        return fontStyle;
    }
    
    /**
     * @return the fontVariant
     */
    public String getFontVariant() {
        return fontVariant;
    }
    
    /**
     * @return the fontWeight
     */
    public String getFontWeight() {
        return fontWeight;
    }
    
    /**
     * @return the fontSize
     */
    public float getFontSize() {
        return fontSize;
    }
    
    /**
     * @return the locales
     */
    public Set getLocales() {
        return locales;
    }
    
    /**
     * @return the superscript
     */
    public Integer getSuperscript() {
        return superscript;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "FontKey[family=" + this.fontFamily + ",size=" + this.fontSize
                + ",style=" + this.fontStyle + ",weight=" + this.fontWeight
                + ",variant=" + this.fontVariant + ",superscript="
                + this.superscript + "]";
    }
}
