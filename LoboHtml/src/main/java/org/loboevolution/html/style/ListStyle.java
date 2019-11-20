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

public class ListStyle {
	
	private Image image;
	
	private int position;
	
	private int type;

	public ListStyle() {
	}

	public ListStyle(final int type, final Image image, final int position) {
		this.type = type;
		this.image = image;
		this.position = position;
	}

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
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}
