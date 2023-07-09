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
package org.loboevolution.common;

import org.loboevolution.html.CSSValues;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * The Class Strings.
 */
public final class Strings {

	/** The Constant EMPTY_ARRAY. */
	public static final String[] EMPTY_ARRAY = new String[0];

	/**
	 * Instantiates a strings.
	 */
	private Strings() { }


	/**
	 * Count chars.
	 *
	 * @param text the text
	 * @param ch   the ch
	 * @return the int
	 */
	public static int countChars(String text, char ch) {
		final int len = text.length();
		int count = 0;
		for (int i = 0; i < len; i++) {
			if (ch == text.trim().charAt(i)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Checks if is blank.
	 *
	 * @param text the text
	 * @return true, if is blank
	 */
	public static boolean isBlank(String text) {
		return text == null || "".equals(text);
	}

	public static boolean isCssBlank(String text) {
		return isBlank(text) || CSSValues.NONE.equals(CSSValues.get(text));
	}

	/**
	 * Linearize string.
	 *
	 * @param text the text
	 * @return string
	 */
	public static String linearize(String text) {
		return text == null ? "" : text.replaceAll("(\r\n|\n)", "").replaceAll("\\s+", "");
	}

	/**
	 * Checks if is not blank.
	 *
	 * @param text the text
	 * @return true, if is not blank
	 */
	public static boolean isNotBlank(String text) {
		return !isBlank(text);
	}

	public static boolean isCssNotBlank(String text) {
		return !isBlank(text) && !CSSValues.NONE.equals(CSSValues.get(text));
	}

	public static boolean isStringBuilderNotBlack(StringBuilder text) {
		return (text != null) && (text.length() > 0);
	}


	public static boolean containsIgnoreCase(String text, String text1) {
		return text != null && text1 != null && (text.contains(text1.toLowerCase()) || text.contains(text1.toUpperCase()));
	}

	public static boolean startsWithIgnoreCase(String text, String text1) {
		return text != null && text1 != null && (text.startsWith(text1.toLowerCase()) || text.startsWith(text1.toUpperCase()));
	}

	public static boolean endsWithIgnoreCase(String text, String text1) {
		return text != null && text1 != null && (text.endsWith(text1.toLowerCase()) || text.endsWith(text1.toUpperCase()));
	}

	public static String[] splitIgnoreCase(String text, String text1) {
		if (text != null && text1 != null) {
			return text.split(text1.toLowerCase()).length > 1 ? text.split(text1.toLowerCase()) :
					text.split(text1.toUpperCase()).length > 1 ? text.split(text1.toUpperCase()) : new String[]{};
		}
		return null;
	}


	/**
	 * <p>isNumeric.</p>
	 *
	 * @param keyCode a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isNumeric(String keyCode) {
		try {
			if (Strings.isBlank(keyCode)) {
				return false;
			}
			Float.parseFloat(keyCode);
			return true;
		} catch (final Exception ex) {
			return false;
		}
	}

	/**
	 * Split.
	 *
	 * @param phrase the phrase
	 * @return the string[]
	 */
	public static String[] split(String phrase) {
		final ArrayList<String> wordList = new ArrayList<>();
		StringBuilder word = null;
		final char[] list = phrase.toCharArray();
		for (final char ch : list) {
			switch (ch) {
				case ' ':
				case '\t':
				case '\r':
				case '\n':
					if (word != null) {
						wordList.add(word.toString());
						word = null;
					}
					break;
				default:
					if (word == null) {
						word = new StringBuilder();
					}
					word.append(ch);
					break;
			}
		}
		if (word != null) {
			wordList.add(word.toString());
		}
		return wordList.toArray(EMPTY_ARRAY);
	}

	/**
	 * <p>splitUsingTokenizer.</p>
	 *
	 * @param subject a {@link java.lang.String} object.
	 * @param delimiters a {@link java.lang.String} object.
	 * @return an array of {@link java.lang.String} objects.
	 */
	public static String[] splitUsingTokenizer(String subject, String delimiters) {
		final StringTokenizer strTkn = new StringTokenizer(subject, delimiters);
		final ArrayList<String> arrLis = new ArrayList<>(subject.length());

		while (strTkn.hasMoreTokens()) {
			arrLis.add(strTkn.nextToken());
		}

		return arrLis.toArray(new String[0]);
	}

	/**
	 * Strict html encode.
	 *
	 * @param rawText the raw text
	 * @param quotes  the quotes
	 * @return the string
	 */
	public static String strictHtmlEncode(String rawText, boolean quotes) {
		final StringBuilder output = new StringBuilder();
		final char[] list = rawText.toCharArray();
		for (final char ch : list) {
			switch (ch) {
				case '&':
					output.append("&amp;");
					break;
				case '"':
					if (quotes) {
						output.append("&quot;");
					} else {
						output.append(ch);
					}
					break;
				case '<':
					output.append("&lt;");
					break;
				case '>':
					output.append("&gt;");
					break;
				default:
					output.append(ch);
					break;
			}
		}
		return output.toString();
	}

	/**
	 * Trim for alpha num dash.
	 *
	 * @param rawText the raw text
	 * @return the string
	 */
	public static String trimForAlphaNumDash(String rawText) {
		final char[] list = rawText.toCharArray();
		int index = 0;
		for (final char ch : list) {
			index++;
			if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9' || ch == '-') {
				continue;
			}
			return rawText.substring(0, index);
		}
		return rawText;
	}

	/**
	 * Unquote.
	 *
	 * @param text the text
	 * @return the string
	 */
	public static String unquote(String text) {
		final int length = text.length();
		if (length >= 2) {
			if (text.charAt(0) == '"' && text.charAt(length - 1) == '"') {
				return text.substring(1, length - 1);
			}
		}
		return text;
	}

	/**
	 * <p>unquoteSingle.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String unquoteSingle(final String text) {
		final int length = text.length();
		if (length >= 2) {
			if ((text.charAt(0) == '\'') && (text.charAt(length - 1) == '\'')) {
				return text.substring(1, length - 1);
			}
		}
		return text;
	}

	/**
	 * <p>containsWords.</p>
	 *
	 * @param inputString a {@link java.lang.String} object.
	 * @param items an array of {@link java.lang.String} objects.
	 * @return a boolean.
	 */
	public static boolean containsWords(String inputString, String[] items) {
		boolean found = false;
		for (String item : items) {
			if (inputString.contains(item)) {
				found = true;
				break;
			}
		}
		return found;
	}

	/**
	 * <p>hash.</p>
	 *
	 * @param password a {@link java.lang.String} object.
	 * @param salt an array of {@link byte} objects.
	 * @return a {@link java.lang.String} object.
	 * @throws java.lang.Exception if any.
	 */
	public static String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty passwords are not supported.");

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 4096, 256 * 8);
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return Base64.getEncoder().encodeToString(f.generateSecret(spec).getEncoded());
    }

	/**
	 * <p>randomAlphaNumeric.</p>
	 *
	 * @param count a int.
	 * @return a {@link java.lang.String} object.
	 */
	public static String randomAlphaNumeric(int count) {
		final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	public static boolean isValidTag(String name, boolean isXml) {
		if (Strings.isBlank(name)) {
			return false;
		}
		final Pattern pattern = Pattern.compile(isXml ? "[A-Za-z0-9]*" : "(\\\"[^\\\"]*\\\"|'[^']*'|[^'\\\">])*", Pattern.CASE_INSENSITIVE);
		return pattern.matcher(name).matches();
	}


	public static boolean isXMLIdentifier(String s) {
		if (isBlank(s)) {
			return false;
		}

		s = s.trim();

		if (!isXMLIdentifierStart(s.charAt(0))) {
			return false;
		}

		for (int i = 1; i < s.length(); i++) {
			if (!isXMLIdentifierPart(s.charAt(i))) {
				return false;
			}
		}

		return true;
	}


	private static boolean isXMLIdentifierStart(char c) {
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c == '_');
	}

	private static boolean isXMLIdentifierPart(char c) {
		return isXMLIdentifierStart(c) || (c >= '0' && c <= '9') || (c == '-') || (c == '.');
	}
}