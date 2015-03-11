/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.util;


/**
 * The Class Timing.
 */
public class Timing {
	
	/**
	 * Round1.
	 *
	 * @param value the value
	 * @return the double
	 */
	public static double round1(double value) {
		return Math.round(value * 10.0) / 10.0;
	}

	/**
	 * Gets the elapsed text.
	 *
	 * @param elapsedMillis the elapsed millis
	 * @return the elapsed text
	 */
	public static String getElapsedText(long elapsedMillis) {
		if (elapsedMillis < 60000) {
			double unit = round1(elapsedMillis / 1000.0);
			return unit + (unit == 1 ? " second" : " seconds");
		} else if (elapsedMillis < 60000 * 60) {
			double unit = round1(elapsedMillis / 60000.0);
			return unit + (unit == 1 ? " minute" : " minutes");
		} else if (elapsedMillis < 60000 * 60 * 24) {
			double unit = round1(elapsedMillis / (60000.0 * 60));
			return unit + (unit == 1 ? " hour" : " hours");
		} else {
			double unit = round1(elapsedMillis / (60000.0 * 60 * 24));
			return unit + (unit == 1 ? " day" : " days");
		}
	}
}
