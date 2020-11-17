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
package org.loboevolution.html.style;

import java.awt.Image;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>ListStyle class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ListStyle {
	
	private Image image;
	
	private int position;
	
	private int type;

	/**
	 * <p>Constructor for ListStyle.</p>
	 */
	public ListStyle() {
	}

	/**
	 * <p>Constructor for ListStyle.</p>
	 *
	 * @param type a int.
	 * @param image a {@link java.awt.Image} object.
	 * @param position a int.
	 */
	public ListStyle(final int type, final Image image, final int position) {
		this.type = type;
		this.image = image;
		this.position = position;
	}

	/**
	 * <p>getRomanNumerals.</p>
	 *
	 * @param num a int.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getRomanNumerals(int num) {
		LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<>();
		roman_numerals.put("M", 1000);
		roman_numerals.put("CM", 900);
		roman_numerals.put("D", 500);
		roman_numerals.put("CD", 400);
		roman_numerals.put("C", 100);
		roman_numerals.put("XC", 90);
		roman_numerals.put("L", 50);
		roman_numerals.put("XL", 40);
		roman_numerals.put("X", 10);
		roman_numerals.put("IX", 9);
		roman_numerals.put("V", 5);
		roman_numerals.put("IV", 4);
		roman_numerals.put("I", 1);
		StringBuilder res = new StringBuilder();
		for (Map.Entry<String, Integer> entry : roman_numerals.entrySet()) {
			int matches = num / entry.getValue();
			res.append(repeat(entry.getKey(), matches));
			num = num % entry.getValue();
		}
		return res.toString();
	}

	private static String repeat(String s, int n) {
		if (s == null) {
			return null;
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * <p>Getter for the field image.</p>
	 *
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * <p>Getter for the field position.</p>
	 *
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * <p>Getter for the field type.</p>
	 *
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * <p>Setter for the field image.</p>
	 *
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * <p>Setter for the field position.</p>
	 *
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * <p>Setter for the field type.</p>
	 *
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}
