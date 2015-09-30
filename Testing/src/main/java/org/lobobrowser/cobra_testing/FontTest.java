/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.cobra_testing;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Locale;

/**
 * The Class FontTest.
 */
public class FontTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		String chinese = Locale.CHINESE.getDisplayLanguage(Locale.CHINESE);
		String japanese = Locale.JAPANESE.getDisplayLanguage(Locale.JAPANESE);
		String korean = Locale.KOREAN.getDisplayLanguage(Locale.KOREAN);
		for (int i = 0; i < fonts.length; i++) {
			Font font = fonts[i];
			boolean cn = font.canDisplayUpTo(chinese) == -1;
			boolean jp = font.canDisplayUpTo(japanese) == -1;
			boolean kr = font.canDisplayUpTo(korean) == -1;
			String prefix = cn && jp && kr ? "###" : "---";
			System.out.println(prefix + "Font[name=" + font.getName() + ",family=" + font.getFamily() + ",cn=" + cn
					+ ",jp=" + jp + ",kr=" + kr + "]");
		}
	}
}
