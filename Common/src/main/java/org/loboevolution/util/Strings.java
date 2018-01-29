/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * The Class Strings.
 */
public class Strings {
	
	/** The Constant MESSAGE_DIGEST. */
	private static final MessageDigest MESSAGE_DIGEST;
	
	/** The Constant EMPTY_ARRAY. */
	public static final String[] EMPTY_ARRAY = new String[0];
	
	/** The Constant HEX_CHARS. */
	private static final String HEX_CHARS = "0123456789ABCDEF";

	static {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException err) {
			throw new IllegalStateException();
		}
		MESSAGE_DIGEST = md;
	}

	/**
	 * Instantiates a strings.
	 */
	private Strings() {
	}

	/**
	 * Compare versions.
	 *
	 * @param version1
	 *            the version1
	 * @param version2
	 *            the version2
	 * @param startsWithDigits
	 *            the starts with digits
	 * @return the int
	 */
	public static int compareVersions(String version1, String version2, boolean startsWithDigits) {
		if (version1 == null) {
			return version2 == null ? 0 : -1;
		} else if (version2 == null) {
			return +1;
		}
		if (startsWithDigits) {
			String v1prefix = leadingDigits(version1);
			String v2prefix = leadingDigits(version2);
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
			} catch (NumberFormatException nfe) {
				diff = 0;
			}
			if (diff == 0) {
				return compareVersions(version1.substring(v1prefix.length()), version2.substring(v2prefix.length()),
						false);
			}
			return diff;
		} else {
			String v1prefix = leadingNonDigits(version1);
			String v2prefix = leadingNonDigits(version2);
			if (v1prefix.length() == 0) {
				if (v2prefix.length() == 0) {
					return 0;
				}
				return -1;
			} else if (v2prefix.length() == 0) {
				return +1;
			}
			int diff = v1prefix.compareTo(v2prefix);
			if (diff == 0) {
				return compareVersions(version1.substring(v1prefix.length()), version2.substring(v2prefix.length()),
						true);
			}
			return diff;
		}
	}

	/**
	 * Leading digits.
	 *
	 * @param text
	 *            the text
	 * @return the string
	 */
	public static String leadingDigits(String text) {
		int length = text.length();
		StringBuilder buffer = null;
		for (int i = 0; i < length; i++) {
			char ch = text.charAt(i);
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
	 * @param text
	 *            the text
	 * @return the string
	 */
	public static String leadingNonDigits(String text) {
		int length = text.length();
		StringBuilder buffer = null;
		for (int i = 0; i < length; i++) {
			char ch = text.charAt(i);
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
	 * Checks if is blank.
	 *
	 * @param text
	 *            the text
	 * @return true, if is blank
	 */
	public static boolean isBlank(String text) {
		return text == null || "".equals(text);
	}

	/**
	 * Count lines.
	 *
	 * @param text
	 *            the text
	 * @return the int
	 */
	public static int countLines(String text) {
		int startIdx = 0;
		int lineCount = 1;
		for (;;) {
			int lbIdx = text.indexOf('\n', startIdx);
			if (lbIdx == -1) {
				break;
			}
			lineCount++;
			startIdx = lbIdx + 1;
		}
		return lineCount;
	}

	/**
	 * Checks if is java identifier.
	 *
	 * @param id
	 *            the id
	 * @return true, if is java identifier
	 */
	public static boolean isJavaIdentifier(String id) {
		if (id == null) {
			return false;
		}
		int len = id.length();
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
	 * Gets the java string literal.
	 *
	 * @param text
	 *            the text
	 * @return the java string literal
	 */
	public static String getJavaStringLiteral(String text) {
		StringBuilder buf = new StringBuilder();
		buf.append('"');
		int len = text.length();
		for (int i = 0; i < len; i++) {
			char ch = text.charAt(i);
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
	 * Gets the java identifier.
	 *
	 * @param candidateID
	 *            the candidate id
	 * @return the java identifier
	 */
	public static String getJavaIdentifier(String candidateID) {
		int len = candidateID.length();
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < len; i++) {
			char ch = candidateID.charAt(i);
			boolean good = i == 0 ? Character.isJavaIdentifierStart(ch) : Character.isJavaIdentifierPart(ch);
			if (good) {
				buf.append(ch);
			} else {
				buf.append('_');
			}
		}
		return buf.toString();
	}

	/**
	 * Gets the m d5.
	 *
	 * @param source
	 *            the source
	 * @return the m d5
	 */
	public static String getMD5(String source) {
		byte[] bytes;
		try {
			bytes = source.getBytes("UTF-8");
		} catch (UnsupportedEncodingException ue) {
			throw new IllegalStateException(ue);
		}
		byte[] result;
		synchronized (MESSAGE_DIGEST) {
			MESSAGE_DIGEST.update(bytes);
			result = MESSAGE_DIGEST.digest();
		}
		char[] resChars = new char[32];
		int len = result.length;
		for (int i = 0; i < len; i++) {
			byte b = result[i];
			int lo4 = b & 0x0F;
			int hi4 = (b & 0xF0) >> 4;
			resChars[i * 2] = HEX_CHARS.charAt(hi4);
			resChars[i * 2 + 1] = HEX_CHARS.charAt(lo4);
		}
		return String.valueOf(resChars);
	}

	/**
	 * Gets the hash32.
	 *
	 * @param source
	 *            the source
	 * @return the hash32
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public static String getHash32(String source) throws UnsupportedEncodingException {
		String md5 = getMD5(source);
		return md5.substring(0, 8);
	}

	/**
	 * Gets the hash64.
	 *
	 * @param source
	 *            the source
	 * @return the hash64
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public static String getHash64(String source) throws UnsupportedEncodingException {
		String md5 = getMD5(source);
		return md5.substring(0, 16);
	}

	/**
	 * Count chars.
	 *
	 * @param text
	 *            the text
	 * @param ch
	 *            the ch
	 * @return the int
	 */
	public static int countChars(String text, char ch) {
		int len = text.length();
		int count = 0;
		for (int i = 0; i < len; i++) {
			if (ch == text.charAt(i)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Unquote.
	 *
	 * @param text
	 *            the text
	 * @return the string
	 */
	public static String unquote(String text) {
		if (text.startsWith("\"") && text.endsWith("\"")) {
			// substring works on indices
			return text.substring(1, text.length() - 1);
		}
		return text;
	}

	/**
	 * Split.
	 *
	 * @param phrase
	 *            the phrase
	 * @return the string[]
	 */
	public static String[] split(String phrase) {
		int length = phrase.length();
		ArrayList<String> wordList = new ArrayList<String>();
		StringBuilder word = null;
		for (int i = 0; i < length; i++) {
			char ch = phrase.charAt(i);
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
	
	public static String[] splitUsingTokenizer(String subject, String delimiters) {
		   StringTokenizer strTkn = new StringTokenizer(subject, delimiters);
		   ArrayList<String> arrLis = new ArrayList<String>(subject.length());

		   while(strTkn.hasMoreTokens())
		      arrLis.add(strTkn.nextToken());

		   return arrLis.toArray(new String[0]);
		}

	/**
	 * Truncate.
	 *
	 * @param text
	 *            the text
	 * @param maxLength
	 *            the max length
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
	 * Strict html encode.
	 *
	 * @param rawText
	 *            the raw text
	 * @param quotes
	 *            the quotes
	 * @return the string
	 */
	public static String strictHtmlEncode(String rawText, boolean quotes) {
		StringBuilder output = new StringBuilder();
		int length = rawText.length();
		for (int i = 0; i < length; i++) {
			char ch = rawText.charAt(i);
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
	 * @param rawText
	 *            the raw text
	 * @return the string
	 */
	public static String trimForAlphaNumDash(String rawText) {
		int length = rawText.length();
		for (int i = 0; i < length; i++) {
			char ch = rawText.charAt(i);
			if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9' || ch == '-') {
				continue;
			}
			return rawText.substring(0, i);
		}
		return rawText;
	}

	/**
	 * Gets the CRLF string.
	 *
	 * @param original
	 *            the original
	 * @return the CRLF string
	 */
	public static String getCRLFString(String original) {
		if (original == null) {
			return null;
		}
		int length = original.length();
		StringBuilder buffer = new StringBuilder();
		boolean lastSlashR = false;
		for (int i = 0; i < length; i++) {
			char ch = original.charAt(i);
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
}
