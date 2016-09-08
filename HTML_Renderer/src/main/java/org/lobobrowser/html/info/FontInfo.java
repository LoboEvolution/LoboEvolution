/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.info;

import java.io.Serializable;

/**
 * The Class FontInfo.
 */
public class FontInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7546739016083212792L;

	/** The font family. */
	private String fontFamily;

	/** The font style. */
	private String fontStyle;

	/** The font variant. */
	private String fontVariant;

	/** The font weight. */
	private String fontWeight;

	/** The font size. */
	private String fontSize;

	/**
	 * Gets the font family.
	 *
	 * @return the font family
	 */
	public String getFontFamily() {
		return fontFamily;
	}

	/**
	 * Sets the font family.
	 *
	 * @param fontFamily
	 *            the new font family
	 */
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
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
	 * Sets the font style.
	 *
	 * @param fontStyle
	 *            the new font style
	 */
	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
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
	 * Sets the font variant.
	 *
	 * @param fontVariant
	 *            the new font variant
	 */
	public void setFontVariant(String fontVariant) {
		this.fontVariant = fontVariant;
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
	 * Sets the font weight.
	 *
	 * @param fontWeight
	 *            the new font weight
	 */
	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}

	/**
	 * Gets the font size.
	 *
	 * @return the font size
	 */
	public String getFontSize() {
		return fontSize;
	}

	/**
	 * Sets the font size.
	 *
	 * @param fontSize
	 *            the new font size
	 */
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
}
