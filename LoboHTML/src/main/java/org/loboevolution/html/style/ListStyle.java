/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.style;

import lombok.Data;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>ListStyle class.</p>
 */
@Data
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
	 * @param type a {@link java.lang.Integer} object.
	 * @param image a {@link java.awt.Image} object.
	 * @param position a {@link java.lang.Integer} object.
	 */
	public ListStyle(final int type, final Image image, final int position) {
		this.type = type;
		this.image = image;
		this.position = position;
	}

	/**
	 * <p>getRomanNumerals.</p>
	 *
	 * @param num a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getRomanNumerals(int num) {
		final Map<String, Integer> roman_numerals = new LinkedHashMap<>();
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
		final StringBuilder res = new StringBuilder();
		for (final Map.Entry<String, Integer> entry : roman_numerals.entrySet()) {
			final int matches = num / entry.getValue();
			res.append(repeat(entry.getKey(), matches));
			num = num % entry.getValue();
		}
		return res.toString();
	}

	private static String repeat(final String s, final int n) {
		if (s == null) {
			return null;
		}
		final StringBuilder sb = new StringBuilder();
        sb.append(s.repeat(Math.max(0, n)));
		return sb.toString();
	}
}
