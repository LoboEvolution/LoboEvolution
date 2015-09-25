/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 17, 2005
 */
package org.lobobrowser.util.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.StyleContext;

import org.lobobrowser.util.Objects;

/**
 * A factory for creating Font objects.
 *
 * @author J. H. S.
 */
public class FontFactory {
    /** The Constant logger. */
    private static final Logger logger = Logger
            .getLogger(FontFactory.class.getName());
    /** The Constant loggableFine. */
    private static final boolean loggableFine = logger.isLoggable(Level.FINE);
    /** The Constant instance. */
    private static final FontFactory instance = new FontFactory();
    /** The font families. */
    private final Set<String> fontFamilies = new HashSet<String>(40);
    /** The font map. */
    private final Map<FontKey, Font> fontMap = new HashMap<FontKey, Font>(50);
    /** The registered fonts. */
    private final Map<String, Font> registeredFonts = new HashMap<String, Font>(
            0);
    /** The default font name. */
    private String defaultFontName = Font.SANS_SERIF;
    
    /**
     * Instantiates a new font factory.
     */
    private FontFactory() {
        boolean liflag = loggableFine;
        String[] ffns = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();
        Set<String> fontFamilies = this.fontFamilies;
        synchronized (this) {
            for (int i = 0; i < ffns.length; i++) {
                String ffn = ffns[i];
                if (liflag) {
                    logger.fine("FontFactory(): family=" + ffn);
                }
                fontFamilies.add(ffn.toLowerCase());
            }
        }
    }
    
    /**
     * Gets the single instance of FontFactory.
     *
     * @return single instance of FontFactory
     */
    public static final FontFactory getInstance() {
        return instance;
    }
    
    /**
     * Registers a font family. It does not close the stream provided. Fonts
     * should be registered before the renderer has a chance to cache document
     * font specifications.
     *
     * @param fontName
     *            The name of a font as it would appear in a font-family
     *            specification.
     * @param fontFormat
     *            Should be {@link Font#TRUETYPE_FONT}.
     * @param fontStream
     *            the font stream
     * @throws FontFormatException
     *             the font format exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void registerFont(String fontName, int fontFormat,
            InputStream fontStream) throws FontFormatException, IOException {
        Font f = Font.createFont(fontFormat, fontStream);
        synchronized (this) {
            this.registeredFonts.put(fontName.toLowerCase(), f);
        }
    }
    
    /**
     * Unregisters a font previously registered with
     * {@link #registerFont(String, int, java.io.InputStream)}.
     *
     * @param fontName
     *            The font name to be removed.
     */
    public void unregisterFont(String fontName) {
        synchronized (this) {
            this.registeredFonts.remove(fontName.toLowerCase());
        }
    }
    
    /**
     * Gets the font.
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
     * @return the font
     */
    public Font getFont(String fontFamily, String fontStyle, String fontVariant,
            String fontWeight, float fontSize, Set locales,
            Integer superscript) {
        FontKey key = new FontKey(fontFamily, fontStyle, fontVariant,
                fontWeight, fontSize, locales, superscript);
        synchronized (this) {
            Font font = this.fontMap.get(key);
            if (font == null) {
                font = this.createFont(key);
                this.fontMap.put(key, font);
            }
            return font;
        }
    }
    
    /**
     * Gets the default font name.
     *
     * @return the default font name
     */
    public String getDefaultFontName() {
        return defaultFontName;
    }
    
    /**
     * Sets the default font name to be used when a name is unrecognized or when
     * a font is determined not to be capable of diplaying characters from a
     * given language. This should be the name of a font that can display
     * unicode text across all or most languages.
     *
     * @param defaultFontName
     *            The name of a font.
     */
    public void setDefaultFontName(String defaultFontName) {
        if (defaultFontName == null) {
            throw new IllegalArgumentException(
                    "defaultFontName cannot be null");
        }
        this.defaultFontName = defaultFontName;
    }
    
    /**
     * Creates a new Font object.
     *
     * @param key
     *            the key
     * @return the font
     */
    private final Font createFont(FontKey key) {
        Font font = createFont_Impl(key);
        return superscriptFont(font, key.superscript);
    }
    
    /**
     * Superscript font.
     *
     * @param baseFont
     *            the base font
     * @param newSuperscript
     *            the new superscript
     * @return the font
     */
    public static Font superscriptFont(Font baseFont, Integer newSuperscript) {
        if (newSuperscript == null) {
            return baseFont;
        }
        Integer fontSuperScript = (Integer) baseFont.getAttributes()
                .get(TextAttribute.SUPERSCRIPT);
        if (fontSuperScript == null) {
            fontSuperScript = new Integer(0);
        }
        if (fontSuperScript.equals(newSuperscript)) {
            return baseFont;
        } else {
            Map<TextAttribute, Integer> additionalAttributes = new HashMap<TextAttribute, Integer>();
            additionalAttributes.put(TextAttribute.SUPERSCRIPT, newSuperscript);
            return baseFont.deriveFont(additionalAttributes);
        }
    }
    
    /**
     * Creates a new Font object.
     *
     * @param key
     *            the key
     * @return the font
     */
    private final Font createFont_Impl(FontKey key) {
        String fontNames = key.fontFamily;
        String matchingFace = null;
        Set fontFamilies = this.fontFamilies;
        Map<String, Font> registeredFonts = this.registeredFonts;
        Font baseFont = null;
        if (fontNames != null) {
            StringTokenizer tok = new StringTokenizer(fontNames, ",");
            while (tok.hasMoreTokens()) {
                String face = tok.nextToken().trim();
                String faceTL = face.toLowerCase();
                if (registeredFonts.containsKey(faceTL)) {
                    baseFont = registeredFonts.get(faceTL);
                    break;
                } else if (fontFamilies.contains(faceTL)) {
                    matchingFace = faceTL;
                    break;
                }
            }
        }
        int fontStyle = Font.PLAIN;
        if ("italic".equalsIgnoreCase(key.fontStyle)) {
            fontStyle |= Font.ITALIC;
        }
        if ("bold".equalsIgnoreCase(key.fontWeight)
                || "bolder".equalsIgnoreCase(key.fontWeight)) {
            fontStyle |= Font.BOLD;
        }
        if (baseFont != null) {
            return baseFont.deriveFont(fontStyle, key.fontSize);
        } else if (matchingFace != null) {
            Font font = createFont(matchingFace, fontStyle,
                    Math.round(key.fontSize));
            Set locales = key.locales;
            if (locales == null) {
                Locale locale = Locale.getDefault();
                if (font.canDisplayUpTo(
                        locale.getDisplayLanguage(locale)) == -1) {
                    return font;
                }
            } else {
                Iterator i = locales.iterator();
                boolean allMatch = true;
                while (i.hasNext()) {
                    Locale locale = (Locale) i.next();
                    if (font.canDisplayUpTo(
                            locale.getDisplayLanguage(locale)) != -1) {
                        allMatch = false;
                        break;
                    }
                }
                if (allMatch) {
                    return font;
                }
            }
            // Otherwise, fall through.
        }
        // Last resort:
        return createFont(this.defaultFontName, fontStyle,
                Math.round(key.fontSize));
    }
    
    /**
     * Creates a new Font object.
     *
     * @param name
     *            the name
     * @param style
     *            the style
     * @param size
     *            the size
     * @return the font
     */
    private Font createFont(String name, int style, int size) {
        return StyleContext.getDefaultStyleContext().getFont(name, style, size);
        // Proprietary Sun API. Maybe shouldn't use it. Works well for Chinese.
        // return FontManager.getCompositeFontUIResource(new Font(name, style,
        // size));
    }
    
    /**
     * The Class FontKey.
     */
    private static class FontKey {
        /** The font family. */
        public final String fontFamily;
        /** The font style. */
        public final String fontStyle;
        /** The font variant. */
        public final String fontVariant;
        /** The font weight. */
        public final String fontWeight;
        /** The font size. */
        public final float fontSize;
        /** The locales. */
        public final Set locales;
        /** The superscript. */
        public final Integer superscript;
        
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
            this.fontVariant = fontVariant == null ? null
                    : fontVariant.intern();
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
            ch = ff.hashCode() ^ fw.hashCode() ^ fs.hashCode()
                    ^ (int) this.fontSize ^ (ss == null ? 0 : ss.intValue());
            this.cachedHash = ch;
            return ch;
        }
        
        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "FontKey[family=" + this.fontFamily + ",size="
                    + this.fontSize + ",style=" + this.fontStyle + ",weight="
                    + this.fontWeight + ",variant=" + this.fontVariant
                    + ",superscript=" + this.superscript + "]";
        }
    }
}
