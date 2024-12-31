/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import lombok.Data;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;

/**
 * <p>HtmlLength class.</p>
 */
@Data
public final class HtmlLength {
	/** Constant EMPTY_ARRAY */
	public static final HtmlLength[] EMPTY_ARRAY = new HtmlLength[0];
	/** Constant LENGTH=2 */
	public static final int LENGTH = 2;
	/** Constant MULTI_LENGTH=0 */
	public static final int MULTI_LENGTH = 0;

	/** Constant PIXELS=1 */
	public static final int PIXELS = 1;

	private final int lengthType;
	private volatile int rawValue;

	/**
	 * <p>Constructor for HtmlLength.</p>
	 *
	 * @param pixels a {@link java.lang.Integer} object.
	 */
	public HtmlLength(final int pixels) {
		this.lengthType = PIXELS;
		this.rawValue = pixels;
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
	public HtmlLength(final String spec, final HTMLDocumentImpl doc) throws IndexOutOfBoundsException, NumberFormatException {
		final String specTrim = spec.trim();
		final int length = specTrim.length();
		final char lastChar = specTrim.charAt(length - 1);
		final String parseable;
		if (lastChar == '%') {
			this.lengthType = LENGTH;
			parseable = specTrim.substring(0, length - 1).trim();
		} else if (lastChar == '*') {
			this.lengthType = MULTI_LENGTH;
			if (length <= 1) {
				parseable = "1";
			} else {
				parseable = specTrim.substring(0, length - 1).trim();
			}
		} else {
			this.lengthType = PIXELS;
			parseable = specTrim;
		}
		this.rawValue = HtmlValues.getPixelSize(parseable, null, doc.getDefaultView(), -1);
	}

	/**
	 * <p>divideBy.</p>
	 *
	 * @param denominator a {@link java.lang.Integer} object.
	 */
	public void divideBy(final int denominator) {
		int val = this.rawValue;
		val = val / denominator;
		this.rawValue = val;
	}

	/**
	 * <p>getLength.</p>
	 *
	 * @param availLength a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getLength(final int availLength) {
        if (this.lengthType == LENGTH) {
			return availLength * this.rawValue / 100;
		} else {
			return this.rawValue;
		}
	}

	/**
	 * <p>isPreferredOver.</p>
	 *
	 * @param otherLength a {@link org.loboevolution.html.style.HtmlLength} object.
	 * @return a boolean.
	 */
	public boolean isPreferredOver(final HtmlLength otherLength) {
		if (otherLength == null) {
			return true;
		}
		if (this.lengthType > otherLength.lengthType) {
			return true;
		}
		return this.rawValue > otherLength.rawValue;
	}
}
