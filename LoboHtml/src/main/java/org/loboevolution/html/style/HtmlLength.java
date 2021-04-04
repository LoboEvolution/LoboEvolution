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
/*
 * Created on Nov 19, 2005
 */
package org.loboevolution.html.style;

/**
 * <p>HtmlLength class.</p>
 *
 *
 *
 */
public final class HtmlLength {
	/** Constant EMPTY_ARRAY */
	public static final HtmlLength[] EMPTY_ARRAY = new HtmlLength[0];
	/** Constant LENGTH=2 */
	public static final int LENGTH = 2;
	/** Constant MULTI_LENGTH=0 */
	public static final int MULTI_LENGTH = 0;

	// Note: Preferred type has higher value
	/** Constant PIXELS=1 */
	public static final int PIXELS = 1;

	private final int lengthType;
	private volatile int value;

	/**
	 * <p>Constructor for HtmlLength.</p>
	 *
	 * @param pixels a int.
	 */
	public HtmlLength(int pixels) {
		this.lengthType = PIXELS;
		this.value = pixels;
	}

	/**
	 * <p>Constructor for HtmlLength.</p>
	 *
	 * @param spec a {@link java.lang.String} object.
	 * @throws java.lang.IndexOutOfBoundsException if any.
	 * @throws java.lang.NumberFormatException if any.
	 * @throws java.lang.IndexOutOfBoundsException if any.
	 * @throws java.lang.NumberFormatException if any.
	 */
	public HtmlLength(String spec) throws IndexOutOfBoundsException, NumberFormatException {
		spec = spec.trim();
		final int length = spec.length();
		final char lastChar = spec.charAt(length - 1);
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
		} else {
			this.lengthType = PIXELS;
			parseable = spec;
		}
		this.value = HtmlValues.getPixelSize(parseable, null, -1);
	}

	/**
	 * <p>divideBy.</p>
	 *
	 * @param denominator a int.
	 */
	public void divideBy(int denominator) {
		int val = this.value;
		val = val / denominator;
		this.value = val;
	}

	/**
	 * <p>getLength.</p>
	 *
	 * @param availLength a int.
	 * @return a int.
	 */
	public int getLength(int availLength) {
		final int lt = this.lengthType;
		if (lt == LENGTH) {
			return availLength * this.value / 100;
		} else {
			return this.value;
		}
	}

	/**
	 * <p>Getter for the field lengthType.</p>
	 *
	 * @return Returns the lengthType.
	 */
	public int getLengthType() {
		return this.lengthType;
	}

	/**
	 * <p>getRawValue.</p>
	 *
	 * @return Returns the spec.
	 */
	public int getRawValue() {
		return this.value;
	}

	/**
	 * <p>isPreferredOver.</p>
	 *
	 * @param otherLength a {@link org.loboevolution.html.style.HtmlLength} object.
	 * @return a boolean.
	 */
	public boolean isPreferredOver(HtmlLength otherLength) {
		if (otherLength == null) {
			return true;
		}
		if (this.lengthType > otherLength.lengthType) {
			return true;
		}
		return this.value > otherLength.value;
	}
}
