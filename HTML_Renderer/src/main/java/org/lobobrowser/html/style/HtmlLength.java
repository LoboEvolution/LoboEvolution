/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
/*
 * Created on Nov 19, 2005
 */
package org.lobobrowser.html.style;

/**
 * The Class HtmlLength.
 */
public final class HtmlLength {
	// Note: Preferred type has higher value
	/** The Constant PIXELS. */
	public static final int PIXELS = 1;

	/** The Constant LENGTH. */
	public static final int LENGTH = 2;

	/** The Constant MULTI_LENGTH. */
	public static final int MULTI_LENGTH = 0;

	/** The Constant EMPTY_ARRAY. */
	public static final HtmlLength[] EMPTY_ARRAY = new HtmlLength[0];

	/** The length type. */
	private final int lengthType;

	/** The value. */
	private volatile int value;

	/**
	 * Instantiates a new html length.
	 *
	 * @param spec
	 *            the spec
	 * @throws IndexOutOfBoundsException
	 *             the index out of bounds exception
	 * @throws NumberFormatException
	 *             the number format exception
	 */
	public HtmlLength(String spec) throws IndexOutOfBoundsException, NumberFormatException {
		spec = spec.trim();
		int length = spec.length();
		char lastChar = spec.charAt(length - 1);
		String parseable;
		if (lastChar == '%') {
			this.lengthType = LENGTH;
			parseable = spec.substring(0, length - 1).trim();
		} else if (lastChar == '*') {
			this.lengthType = MULTI_LENGTH;
			if (length <= 1) {
				parseable = "1";
			} else {
				parseable = spec.substring(0, length - 1).trim();
			}
		} else if (spec.endsWith("px")) {
			this.lengthType = PIXELS;
			parseable = spec.substring(0, length - 2).trim();
		} else {
			this.lengthType = PIXELS;
			parseable = spec;
		}
		this.value = Integer.parseInt(parseable);
	}

	/**
	 * Instantiates a new html length.
	 *
	 * @param pixels
	 *            the pixels
	 */
	public HtmlLength(int pixels) {
		this.lengthType = PIXELS;
		this.value = pixels;
	}

	/**
	 * Gets the length type.
	 *
	 * @return the length type
	 */
	public final int getLengthType() {
		return lengthType;
	}

	/**
	 * Gets the raw value.
	 *
	 * @return the raw value
	 */
	public final int getRawValue() {
		return this.value;
	}

	/**
	 * Gets the length.
	 *
	 * @param availLength
	 *            the avail length
	 * @return the length
	 */
	public final int getLength(int availLength) {
		int lt = this.lengthType;
		if (lt == LENGTH) {
			return (availLength * this.value) / 100;
		} else {
			return this.value;
		}
	}

	/**
	 * Divide by.
	 *
	 * @param denominator
	 *            the denominator
	 */
	public final void divideBy(int denominator) {
		int val = this.value;
		val = val / denominator;
		this.value = val;
	}

	/**
	 * Checks if is preferred over.
	 *
	 * @param otherLength
	 *            the other length
	 * @return true, if is preferred over
	 */
	public final boolean isPreferredOver(HtmlLength otherLength) {
		if (otherLength == null) {
			return true;
		}
		if (this.lengthType > otherLength.lengthType) {
			return true;
		}
		return this.value > otherLength.value;
	}
}
