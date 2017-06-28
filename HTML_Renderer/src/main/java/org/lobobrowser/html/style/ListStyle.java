/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
package org.lobobrowser.html.style;

import java.awt.Image;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Class ListStyle.
 */
public class ListStyle {

	/** The Constant TYPE_UNSET. */
	public static final int TYPE_UNSET = 256;

	/** The Constant TYPE_NONE. */
	public static final int TYPE_NONE = 0;

	/** The Constant TYPE_DISC. */
	public static final int TYPE_DISC = 1;

	/** The Constant TYPE_CIRCLE. */
	public static final int TYPE_CIRCLE = 2;

	/** The Constant TYPE_SQUARE. */
	public static final int TYPE_SQUARE = 3;

	/** The Constant TYPE_DECIMAL. */
	public static final int TYPE_DECIMAL = 4;

	/** The Constant TYPE_DECIMAL_LEADING_ZERO. */
	public static final int TYPE_DECIMAL_LEADING_ZERO = 5;

	/** The Constant TYPE_LOWER_ALPHA. */
	public static final int TYPE_LOWER_ALPHA = 6;

	/** The Constant TYPE_UPPER_ALPHA. */
	public static final int TYPE_UPPER_ALPHA = 7;

	/** The Constant TYPE_LOWER_LATIN. */
	public static final int TYPE_LOWER_LATIN = 8;

	/** The Constant TYPE_UPPER_LATIN. */
	public static final int TYPE_UPPER_LATIN = 9;

	/** The Constant TYPE_LOWER_ROMAN. */
	public static final int TYPE_LOWER_ROMAN = 10;

	/** The Constant TYPE_UPPER_ROMAN. */
	public static final int TYPE_UPPER_ROMAN = 11;

	/** The Constant POSITION_UNSET. */
	public static final int POSITION_UNSET = 0;

	/** The Constant POSITION_INSIDE. */
	public static final int POSITION_INSIDE = 0;

	/** The Constant POSITION_OUTSIDE. */
	public static final int POSITION_OUTSIDE = 0;

	/** The type. */
	public int type;

	/** The image. */
	public java.awt.Image image;

	/** The position. */
	public int position;

	/**
	 * Instantiates a new list style.
	 *
	 * @param type
	 *            the type
	 * @param image
	 *            the image
	 * @param position
	 *            the position
	 */
	public ListStyle(final int type, final Image image, final int position) {
		super();
		this.type = type;
		this.image = image;
		this.position = position;
	}

	/**
	 * Instantiates a new list style.
	 */
	public ListStyle() {
	}

	/**
	 * Gets the roman numerals.
	 *
	 * @param num
	 *            the num
	 * @return the roman numerals
	 */
	public static String getRomanNumerals(int num) {
		LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<String, Integer>();
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
		String res = "";
		for (Map.Entry<String, Integer> entry : roman_numerals.entrySet()) {
			int matches = num / entry.getValue();
			res += repeat(entry.getKey(), matches);
			num = num % entry.getValue();
		}
		return res;
	}

	/**
	 * Repeat.
	 *
	 * @param s
	 *            the s
	 * @param n
	 *            the n
	 * @return the string
	 */
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

}
