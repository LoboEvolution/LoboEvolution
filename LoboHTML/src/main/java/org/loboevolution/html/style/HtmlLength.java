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
/*
 * Created on Nov 19, 2005
 */
package org.loboevolution.html.style;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;

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
	public HtmlLength(String spec, HTMLDocumentImpl doc) throws IndexOutOfBoundsException, NumberFormatException {
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
		this.value = HtmlValues.getPixelSize(parseable, null, doc.getDefaultView(), -1);
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
