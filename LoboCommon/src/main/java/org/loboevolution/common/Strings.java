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
package org.loboevolution.common;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * The Class Strings.
 *
 * @author utente
 * @version $Id: $Id
 */
public class Strings {
	
	private static final int iterations = 20*1000;
        
    private static final int desiredKeyLen = 256;

	/** The Constant EMPTY_ARRAY. */
	public static final String[] EMPTY_ARRAY = new String[0];
	
	/**
	 * Instantiates a strings.
	 */
	private Strings() {
	}

	/**
	 * Compare versions.
	 *
	 * @param version1         the version1
	 * @param version2         the version2
	 * @param startsWithDigits the starts with digits
	 * @return the int
	 */
	public static int compareVersions(String version1, String version2, boolean startsWithDigits) {
		if (version1 == null) {
			return version2 == null ? 0 : -1;
		} else if (version2 == null) {
			return +1;
		}
		if (startsWithDigits) {
			final String v1prefix = leadingDigits(version1);
			final String v2prefix = leadingDigits(version2);
			if (v1prefix.length() == 0) {
				if (v2prefix.length() == 0) {
					return 0;
				}
				return -1;
			} else if (v2prefix.length() == 0) {
				return +1;
			}
			int diff;
			try {
				diff = Integer.parseInt(v1prefix) - Integer.parseInt(v2prefix);
			} catch (final NumberFormatException nfe) {
				diff = 0;
			}
			if (diff == 0) {
				return compareVersions(version1.substring(v1prefix.length()), version2.substring(v2prefix.length()),
						false);
			}
			return diff;
		} else {
			final String v1prefix = leadingNonDigits(version1);
			final String v2prefix = leadingNonDigits(version2);
			if (v1prefix.length() == 0) {
				if (v2prefix.length() == 0) {
					return 0;
				}
				return -1;
			} else if (v2prefix.length() == 0) {
				return +1;
			}
			final int diff = v1prefix.compareTo(v2prefix);
			if (diff == 0) {
				return compareVersions(version1.substring(v1prefix.length()), version2.substring(v2prefix.length()),
						true);
			}
			return diff;
		}
	}

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
			if (ch == text.charAt(i)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Count lines.
	 *
	 * @param text the text
	 * @return the int
	 */
	public static int countLines(String text) {
		int startIdx = 0;
		int lineCount = 1;
		while (true) {
			final int lbIdx = text.indexOf('\n', startIdx);
			if (lbIdx == -1) {
				break;
			}
			lineCount++;
			startIdx = lbIdx + 1;
		}
		return lineCount;
	}

	/**
	 * Gets the CRLF string.
	 *
	 * @param original the original
	 * @return the CRLF string
	 */
	public static String getCRLFString(String original) {
		if (original == null) {
			return null;
		}
		final StringBuilder buffer = new StringBuilder();
		boolean lastSlashR = false;
		final char[] list = original.toCharArray();
		for (final char ch : list) {
			switch (ch) {
			case '\r':
				lastSlashR = true;
				break;
			case '\n':
				lastSlashR = false;
				buffer.append("\r\n");
				break;
			default:
				if (lastSlashR) {
					lastSlashR = false;
					buffer.append("\r\n");
				}
				buffer.append(ch);
				break;
			}
		}
		return buffer.toString();
	}

	/**
	 * Gets the java identifier.
	 *
	 * @param candidateID the candidate id
	 * @return the java identifier
	 */
	public static String getJavaIdentifier(String candidateID) {
		final int len = candidateID.length();
		final StringBuilder buf = new StringBuilder();
		for (int i = 0; i < len; i++) {
			final char ch = candidateID.charAt(i);
			final boolean good = i == 0 ? Character.isJavaIdentifierStart(ch) : Character.isJavaIdentifierPart(ch);
			if (good) {
				buf.append(ch);
			} else {
				buf.append('_');
			}
		}
		return buf.toString();
	}

	/**
	 * Gets the java string literal.
	 *
	 * @param text the text
	 * @return the java string literal
	 */
	public static String getJavaStringLiteral(String text) {
		final StringBuilder buf = new StringBuilder();
		buf.append('"');
		final int len = text.length();
		for (int i = 0; i < len; i++) {
			final char ch = text.charAt(i);
			switch (ch) {
			case '\\':
				buf.append("\\\\");
				break;
			case '\n':
				buf.append("\\n");
				break;
			case '\r':
				buf.append("\\r");
				break;
			case '\t':
				buf.append("\\t");
				break;
			case '"':
				buf.append("\\\"");
				break;
			default:
				buf.append(ch);
				break;
			}
		}
		buf.append('"');
		return buf.toString();
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
	 * Checks if is java identifier.
	 *
	 * @param id the id
	 * @return true, if is java identifier
	 */
	public static boolean isJavaIdentifier(String id) {
		if (id == null) {
			return false;
		}
		final int len = id.length();
		if (len == 0) {
			return false;
		}
		if (!Character.isJavaIdentifierStart(id.charAt(0))) {
			return false;
		}
		for (int i = 1; i < len; i++) {
			if (!Character.isJavaIdentifierPart(id.charAt(i))) {
				return false;
			}
		}
		return true;
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

	/**
	 * <p>isNumeric.</p>
	 *
	 * @param keyCode a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isNumeric(String keyCode) {
		try {
			if (Strings.isBlank(keyCode)) {
				return true;
			}
			Float.parseFloat(keyCode);
			return true;
		} catch (final Exception ex) {
			return false;
		}
	}

	/**
	 * Leading digits.
	 *
	 * @param text the text
	 * @return the string
	 */
	private static String leadingDigits(String text) {
		final char[] list = text.toCharArray();
		StringBuilder buffer = null;
		for (final char ch : list) {
			if (!Character.isDigit(ch)) {
				break;
			}

			if (buffer == null) {
				buffer = new StringBuilder(3);
			}

			buffer.append(ch);
		}
		return buffer == null ? "" : buffer.toString();
	}

	/**
	 * Leading non digits.
	 *
	 * @param text the text
	 * @return the string
	 */
	private static String leadingNonDigits(String text) {
		StringBuilder buffer = null;
		final char[] list = text.toCharArray();
		for (final char ch : list) {
			if (Character.isDigit(ch)) {
				break;
			}
			if (buffer == null) {
				buffer = new StringBuilder(3);
			}
			buffer.append(ch);
		}
		return buffer == null ? "" : buffer.toString();
	}

	/**
	 * Split.
	 *
	 * @param phrase the phrase
	 * @return the string[]
	 */
	public static String[] split(String phrase) {
		final ArrayList<String> wordList = new ArrayList<String>();
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
		final ArrayList<String> arrLis = new ArrayList<String>(subject.length());

		while (strTkn.hasMoreTokens()) {
			arrLis.add(strTkn.nextToken());
		}

		return arrLis.toArray(new String[0]);
	}
	
	/**
	 * <p>getTokensWithCollection.</p>
	 *
	 * @param subject a {@link java.lang.String} object.
	 * @param delimiters a {@link java.lang.String} object.
	 * @return an list of {@link java.lang.String} objects.
	 */
	public static List<String> getTokensWithCollection(String str, String delimiters) {
	    return Collections.list(new StringTokenizer(str, delimiters, true)).stream()
	      .map(token -> (String) token)
	      .collect(Collectors.toList());
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
	 * Truncate.
	 *
	 * @param text      the text
	 * @param maxLength the max length
	 * @return the string
	 */
	public static String truncate(String text, int maxLength) {
		if (text == null) {
			return null;
		}
		if (text.length() <= maxLength) {
			return text;
		}
		return text.substring(0, Math.max(maxLength - 3, 0)) + "...";
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
	
	public static String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
            password.toCharArray(), salt, iterations, desiredKeyLen));
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
    
	public static String randomAlphaNumeric(int count) {
		final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
}
