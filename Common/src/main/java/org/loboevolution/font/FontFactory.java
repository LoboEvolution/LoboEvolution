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
/*
 * Created on Apr 17, 2005
 */
package org.loboevolution.font;

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

import javax.swing.text.StyleContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A factory for creating Font objects.
 *
 * @author J. H. S.
 */
public class FontFactory {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(FontFactory.class);

	/** The Constant loggableFine. */
	private static final boolean loggableFine = logger.isInfoEnabled();

	/** The Constant instance. */
	private static final FontFactory instance = new FontFactory();

	/** The font families. */
	private final Set<String> fontFamilies = new HashSet<String>(40);

	/** The font map. */
	private final Map<FontKey, Font> fontMap = new HashMap<FontKey, Font>(50);

	/** The registered fonts. */
	private final Map<String, Font> registeredFonts = new HashMap<String, Font>(0);

	/** The default font name. */
	private String defaultFontName = LAFSettings.getInstance().getFont();

	/**
	 * Instantiates a new font factory.
	 */
	private FontFactory() {
		boolean liflag = loggableFine;
		String[] ffns = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		Set<String> fontFamilies = this.fontFamilies;
		synchronized (this) {
			for (String ffn : ffns) {
				if (liflag) {
					logger.debug("FontFactory(): family=" + ffn);
				}
				fontFamilies.add(ffn.toLowerCase());
			}
		}
	}

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
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
	public void registerFont(String fontName, int fontFormat, InputStream fontStream)
			throws FontFormatException, IOException {
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
	 * @param key the key
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
	 * Gets the default font name.
	 *
	 * @return the default font name
	 */
	public String getDefaultFontName() {
		return defaultFontName;
	}

	/**
	 * Sets the default font name.
	 *
	 * @param defaultFontName
	 *            the new default font name
	 */
	public void setDefaultFontName(String defaultFontName) {
		if (defaultFontName == null) {
			throw new IllegalArgumentException("defaultFontName cannot be null");
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
		Font font = createFontImpl(key);
		return scriptFont(font, key);
	}

	/**
	 * Script font.
	 *
	 * @param baseFont
	 *            the base font
	 * @param newSuperscript
	 *            the new superscript
	 * @return the font
	 */
	public static Font scriptFont(Font baseFont, FontKey key) {

		Map<TextAttribute, Object> additionalAttributes = new HashMap<TextAttribute, Object>();

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
	 * Creates a new Font object.
	 *
	 * @param key
	 *            the key
	 * @return the font
	 */
	private final Font createFontImpl(FontKey key) {
		String fontNames = key.getFontFamily();
		int letterSpacing = key.getLetterSpacing();
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
		if ("italic".equalsIgnoreCase(key.getFontStyle())) {
			fontStyle |= Font.ITALIC;
		}

		if ("bold".equalsIgnoreCase(key.getFontWeight()) || "bolder".equalsIgnoreCase(key.getFontWeight())) {
			fontStyle |= Font.BOLD;
		}

		Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
		attributes.put(TextAttribute.TRACKING, (double) letterSpacing / 10 / 2);

		if (baseFont != null) {
			Font f = baseFont.deriveFont(fontStyle, key.getFontSize());
			return f.deriveFont(attributes);
		} else if (matchingFace != null) {
			Font font = createFont(matchingFace, fontStyle, Math.round(key.getFontSize()));
			Set locales = key.getLocales();
			if (locales == null) {
				Locale locale = Locale.getDefault();
				if (font.canDisplayUpTo(locale.getDisplayLanguage(locale)) == -1) {
					return font.deriveFont(attributes);
				}
			} else {
				Iterator i = locales.iterator();
				boolean allMatch = true;
				while (i.hasNext()) {
					Locale locale = (Locale) i.next();
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
	}
}
