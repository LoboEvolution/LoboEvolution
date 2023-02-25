/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;

import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.*;

/**
 * A factory for creating Font objects.
 */
public final class FontFactory {

	/** The font families. */
	private final Set<String> fontFamilies = new HashSet<>();

	/** The font map. */
	private final Map<FontKey, Font> fontMap = new HashMap<>();

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
	 * Script font.
	 *
	 * @param baseFont       the base font
	 * @return the font
	 * @param key a {@link org.loboevolution.laf.FontKey} object.
	 */
	public Font scriptFont(Font baseFont, FontKey key) {

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
		Font baseFont = null;
		if (fontNames != null) {
			final StringTokenizer tok = new StringTokenizer(fontNames, ",");
			while (tok.hasMoreTokens()) {
				final String face = Strings.unquoteSingle(tok.nextToken().trim());
				final String faceTL = face.toLowerCase();
				if (fontFamilies.contains(faceTL)) {
					matchingFace = faceTL;
					break;
				} else if ("monospace".equals(faceTL)) {
					baseFont = Font.decode("monospaced");
				}
			}
		}

		Map<TextAttribute, Object> attributes;

		if (Strings.isNotBlank(key.getFontWeight())) {
			attributes = getBoldAttributes(key.getFontWeight());
		} else{
			attributes = new HashMap<>();
		}

		attributes.put(TextAttribute.TRACKING, (double) letterSpacing / 10 / 2);

		int fontStyle = Font.PLAIN;
		if ("italic".equalsIgnoreCase(key.getFontStyle())) {
			fontStyle |= Font.ITALIC;
		}

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
		}
		return createFont(key.getFont(), fontStyle, Math.round(key.getFontSize())).deriveFont(attributes);
	}

	private Map<TextAttribute, Object> getBoldAttributes(String fontWeight){

		Map<TextAttribute, Object> attributes = new HashMap<>();

		switch (CSSValues.get(fontWeight)) {
			case BOLD100:
				attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_EXTRA_LIGHT);
				break;
			case BOLD200:
			case LIGHTER:
				attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_LIGHT);
				break;
			case BOLD300:
				attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_DEMILIGHT);
				break;
			case NORMAL:
			case BOLD400:
			default:
				attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR);
				break;
			case BOLD500:
				attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_MEDIUM);
				break;
			case BOLD600:
				attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_DEMIBOLD);
				break;
			case BOLD:
			case BOLDER:
			case BOLD700:
				attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
				break;
			case BOLD800:
				attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_HEAVY);
				break;
			case BOLD900:
				attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_EXTRABOLD);
				break;
		}
		return attributes;
	}

}
