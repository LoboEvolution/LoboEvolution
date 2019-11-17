/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.loboevolution.info;

public class FontInfo {
	
	private String fontFamily;
	
	private String fontSize;
	
	private String fontStyle;
	
	private String fontVariant;
	
	private String fontWeight;

	/**
	 * @return the fontFamily
	 */
	public String getFontFamily() {
		return fontFamily;
	}

	/**
	 * @return the fontSize
	 */
	public String getFontSize() {
		return fontSize;
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
	 * @param fontFamily the fontFamily to set
	 */
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	/**
	 * @param fontSize the fontSize to set
	 */
	public void setFontSize(String fontSize) {
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
}