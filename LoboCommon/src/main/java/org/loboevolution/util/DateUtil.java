/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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
