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
package org.lobobrowser.util;

/**
 * The Class Html.
 */
public class Html {
	/**
	 * Text to html.
	 *
	 * @param text
	 *            the text
	 * @return the string
	 */
	public static String textToHTML(String text) {
		if (text == null) {
			return null;
		}
		int length = text.length();
		boolean prevSlashR = false;
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < length; i++) {
			char ch = text.charAt(i);
			switch (ch) {
			case '\r':
				if (prevSlashR) {
					out.append("<br>");
				}
				prevSlashR = true;
				break;
			case '\n':
				prevSlashR = false;
				out.append("<br>");
				break;
			case '"':
				if (prevSlashR) {
					out.append("<br>");
					prevSlashR = false;
				}
				out.append("&quot;");
				break;
			case '<':
				if (prevSlashR) {
					out.append("<br>");
					prevSlashR = false;
				}
				out.append("&lt;");
				break;
			case '>':
				if (prevSlashR) {
					out.append("<br>");
					prevSlashR = false;
				}
				out.append("&gt;");
				break;
			case '&':
				if (prevSlashR) {
					out.append("<br>");
					prevSlashR = false;
				}
				out.append("&amp;");
				break;
			default:
				if (prevSlashR) {
					out.append("<br>");
					prevSlashR = false;
				}
				out.append(ch);
				break;
			}
		}
		return out.toString();
	}
}
