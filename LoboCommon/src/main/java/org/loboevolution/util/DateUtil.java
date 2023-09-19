/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * <p>DateUtil class.</p>
 *
 *
 *
 */
public final class DateUtil {

	/**
	 * Determine SimpleDateFormat pattern matching with the given date string.
	 * Returns null if format is unknown. You can simply extend DateUtil with more
	 * formats if needed.
	 *
	 * @param dateString The date string to determine the SimpleDateFormat pattern
	 *                   for.
	 * @return The matching SimpleDateFormat pattern, or null if format is unknown.
	 * @see SimpleDateFormat
	 * @param loc a {@link java.util.Locale} object.
	 */
	public Date determineDateFormat(String dateString, Locale loc) {

		final List<String> DATE_FORMAT = formatList();
		for (final String regexp : DATE_FORMAT) {
			try {
				return parse(dateString, regexp, loc);
			} catch (final ParseException e) {
			}
		}
		return null; // Unknown format.
	}

	private List<String> formatList() {
		final ArrayList<String> DATE_FORMAT = new ArrayList<>();
		DATE_FORMAT.add("yyyyMMdd");
		DATE_FORMAT.add("dd-MM-yyyy");
		DATE_FORMAT.add("yyyy-MM-dd");
		DATE_FORMAT.add("MM-dd-yyyy");
		DATE_FORMAT.add("MM/dd/yyyy");
		DATE_FORMAT.add("yyyy/MM/dd");
		DATE_FORMAT.add("dd MMM yyyy");
		DATE_FORMAT.add("dd MMMM yyyy");
		DATE_FORMAT.add("dd-MMM-yy");
		DATE_FORMAT.add("dd-MMM-yy");
		DATE_FORMAT.add("yy-MMM-dd");
		DATE_FORMAT.add("yy-MMM-dd");
		DATE_FORMAT.add("yyyyMMddHHmm");
		DATE_FORMAT.add("yyyyMMdd HHmm");
		DATE_FORMAT.add("dd-MM-yyyy HHmm");
		DATE_FORMAT.add("yyyy-MM-dd HHmm");
		DATE_FORMAT.add("MM/dd/yyyy HHmm");
		DATE_FORMAT.add("yyyy/MM/dd HHmm");
		DATE_FORMAT.add("dd MMM yyyy HHmm");
		DATE_FORMAT.add("dd MMMM yyyy HHmm");
		DATE_FORMAT.add("yyyyMMddHHmmss");
		DATE_FORMAT.add("yyyyMMdd HHmmss");
		DATE_FORMAT.add("dd-MM-yyyy HHmmss");
		DATE_FORMAT.add("yyyy-MM-dd HHmmss");
		DATE_FORMAT.add("MM/dd/yyyy HHmmss");
		DATE_FORMAT.add("yyyy/MM/dd HHmmss");
		DATE_FORMAT.add("dd MMM yyyy HHmmss");
		DATE_FORMAT.add("dd MMMM yyyy HHmmss");
		DATE_FORMAT.add("yyyy. MM. dd");
		DATE_FORMAT.add("yyyy-MM-dd'T'HHmmss'Z'");
		DATE_FORMAT.add("dd MMM yyyy HHmmss");
		DATE_FORMAT.add("dd-MMM-yyyy HHmmss Z");
		DATE_FORMAT.add("EEE MMM dd yyyy");
		DATE_FORMAT.add("EEE dd'th' MMM yyyy");
		DATE_FORMAT.add("EEE dd'st' MMM yyyy");
		DATE_FORMAT.add("EEE dd'nd' MMM yyyy");
		DATE_FORMAT.add("EEE dd'rd' MMM yyyy");
		DATE_FORMAT.add("EEE, dd-MMM-yyyy HH:mm:ss z");
		DATE_FORMAT.add("EEEMMM ddyyyy");
		DATE_FORMAT.add("EEEdd MMM yyyy HHmmss Z");
		DATE_FORMAT.add("EEE, dd MMM yyyy HH:mm:ss Z");
		DATE_FORMAT.add("EEE MMM dd HHmmss Z yyyy");
		DATE_FORMAT.add("yyyy-MM-dd HHmmss Z");
		DATE_FORMAT.add("yyyy-MM-dd HHmmss.S");

		return DATE_FORMAT;
	}

	/**
	 * Validate the actual date of the given date string based on the given date
	 * format pattern and return a date instance based on the given date string.
	 * 
	 * @param dateString The date string.
	 * @param dateFormat The date format pattern which should respect the
	 *                   SimpleDateFormat rules.
	 * @return The parsed date object.
	 * @throws ParseException If the given date string or its actual date is invalid
	 *                        based on the given date format pattern.
	 * @see SimpleDateFormat
	 */
	private Date parse(String dateString, String dateFormat, Locale loc) throws ParseException {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, loc);
		simpleDateFormat.setLenient(false);
		return simpleDateFormat.parse(dateString);
	}
}
