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
/*
 * Created on Apr 17, 2005
 */
package org.loboevolution.laf;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.text.StyleContext;

import org.loboevolution.common.Strings;

/**
 * A factory for creating Font objects.
 *
 * Author J. H. S.
 *
 */
public class FontFactory {
	
	
	/** The default font name. */
	private String defaultFontName = new LAFSettings().getInstance().getFont();

	/** The font families. */
	private final Set<String> fontFamilies = new HashSet<>();

	/** The font map. */
	private final Map<FontKey, Font> fontMap = new HashMap<>();

	/** The registered fonts. */
	private final Map<String, Font> registeredFonts = new HashMap<>();

	/** The Constant instance. */
	private static final FontFactory instance = new FontFactory();

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static FontFactory getInstance() {
		return instance;
	}

	/**
	 * Script font.
	 *
	 * @param baseFont       the base font
	 * @return the font
	 * @param key a {@link org.loboevolution.laf.FontKey} object.
	 */
	public static Font scriptFont(Font baseFont, FontKey key) {

		final Map<TextAttribute, Object> additionalAttributes = new HashMap<>();

		Integer fontSuperScript = (Integer) baseFont.getAttributes().get(TextAttribute.SUPERSCRIPT);
		if (fontSuperScript == null) {
			fontSuperScript = key.getSuperscript();
		}
		additionalAttributes.put(TextAttribute.SUPERSCRIPT, fontSuperScript);

		Integer fontUnderline = (Integer) baseFont.getAttributes().get(TextAttribute.UNDERLINE);
		if (fontUnderline == null) {
			fontUnderline = key.getUnderline();
		}
		additionalAttributes.put(TextAttribute.UNDERLINE, fontUnderline);

		Boolean fontStrikethrough = (Boolean) baseFont.getAttributes().get(TextAttribute.STRIKETHROUGH_ON);
		if (fontStrikethrough == null) {
			fontStrikethrough = key.getStrikethrough();
		}
		additionalAttributes.put(TextAttribute.STRIKETHROUGH, fontStrikethrough);

		return baseFont.deriveFont(additionalAttributes);
	}

	/**
	 * Instantiates a new font factory.
	 */
	private FontFactory() {
		final String[] ffns = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		final Set<String> fontFamilies = this.fontFamilies;
		synchronized (this) {
			for (final String ffn : ffns) {
				fontFamilies.add(ffn.toLowerCase());
			}
		}
	}

	/**
	 * Creates a new Font object.
	 *
	 * @param key the key
	 * @return the font
	 */
	private Font createFont(FontKey key) {
		final Font font = createFontImpl(key);
		return scriptFont(font, key);
	}

	/**
	 * Creates a new Font object.
	 *
	 * @param name  the name
	 * @param style the style
	 * @param size  the size
	 * @return the font
	 */
	private Font createFont(String name, int style, int size) {
		return StyleContext.getDefaultStyleContext().getFont(name, style, size);
	}

	/**
	 * Creates a new Font object.
	 *
	 * @param key the key
	 * @return the font
	 */
	private Font createFontImpl(FontKey key) {
		final String fontNames = key.getFontFamily();
		final int letterSpacing = key.getLetterSpacing();
		String matchingFace = null;
		final Set<String> fontFamilies = this.fontFamilies;
		final Map<String, Font> registeredFonts = this.registeredFonts;
		Font baseFont = null;
		if (fontNames != null) {
			final StringTokenizer tok = new StringTokenizer(fontNames, ",");
			while (tok.hasMoreTokens()) {
				final String face = Strings.unquoteSingle(tok.nextToken().trim());
				final String faceTL = face.toLowerCase();
				if (registeredFonts.containsKey(faceTL)) {
					baseFont = registeredFonts.get(faceTL);
					break;
				} else if (fontFamilies.contains(faceTL)) {
					matchingFace = faceTL;
					break;
				} else if ("monospace".equals(faceTL)) {
					baseFont = Font.decode("monospaced");
				}
			}
		}

		int fontStyle = Font.PLAIN;
		if ("italic".equalsIgnoreCase(key.getFontStyle())) {
			fontStyle |= Font.ITALIC;
		}

		if ("bold".equalsIgnoreCase(key.getFontWeight()) || "bolder".equalsIgnoreCase(key.getFontWeight())) {
			fontStyle |= Font.BOLD;
		}

		final Map<TextAttribute, Object> attributes = new HashMap<>();
		attributes.put(TextAttribute.TRACKING, (double) letterSpacing / 10 / 2);

		if (baseFont != null) {
			final Font f = baseFont.deriveFont(fontStyle, key.getFontSize());
			return f.deriveFont(attributes);
		} else if (matchingFace != null) {
			final Font font = createFont(matchingFace, fontStyle, Math.round(key.getFontSize()));
			final Set<Locale> locales = key.getLocales();
			if (locales == null) {
				final Locale locale = Locale.getDefault();
				if (font.canDisplayUpTo(locale.getDisplayLanguage(locale)) == -1) {
					return font.deriveFont(attributes);
				}
			} else {
				boolean allMatch = true;
				for (Locale locale : locales) {
					if (font.canDisplayUpTo(locale.getDisplayLanguage(locale)) != -1) {
						allMatch = false;
						break;
					}
				}
				if (allMatch) {
					return font.deriveFont(attributes);
				}
			}
			// Otherwise, fall through.
		}
		// Last resort:
		return createFont(this.defaultFontName, fontStyle, Math.round(key.getFontSize())).deriveFont(attributes);
	}

	/**
	 * Gets the default font name.
	 *
	 * @return the default font name
	 */
	public String getDefaultFontName() {
		return this.defaultFontName;
	}

	/**
	 * Gets the font.
	 *
	 * @param key the key
	 * @return a {@link java.awt.Font} object.
	 */
	public Font getFont(FontKey key) {
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
	 * Registers a font family. It does not close the stream provided. Fonts should
	 * be registered before the renderer has a chance to cache document font
	 * specifications.
	 *
	 * @param fontName   The name of a font as it would appear in a font-family
	 *                   specification.
	 * @param fontFormat Should be {@link java.awt.Font#TRUETYPE_FONT}.
	 * @param fontStream the font stream
	 * @throws java.awt.FontFormatException if any.
	 * @throws java.io.IOException if any.
	 */
	public void registerFont(String fontName, int fontFormat, InputStream fontStream)
			throws FontFormatException, IOException {
		final Font f = Font.createFont(fontFormat, fontStream);
		synchronized (this) {
			this.registeredFonts.put(fontName.toLowerCase(), f);
		}
	}

	/**
	 * Sets the default font name.
	 *
	 * @param defaultFontName the new default font name
	 */
	public void setDefaultFontName(String defaultFontName) {
		if (defaultFontName == null) {
			throw new IllegalArgumentException("defaultFontName cannot be null");
		}
		this.defaultFontName = defaultFontName;
	}

	/**
	 * Unregisters a font previously registered with
	 * {@link #registerFont(String, int, java.io.InputStream)}.
	 *
	 * @param fontName The font name to be removed.
	 */
	public void unregisterFont(String fontName) {
		synchronized (this) {
			this.registeredFonts.remove(fontName.toLowerCase());
		}
	}
}
