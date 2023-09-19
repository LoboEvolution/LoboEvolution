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
package org.loboevolution.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.Color;

/**
 * <p>BorderInfo class.</p>
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorderInfo {

	/** The bottomColor. */
	private Color bottomColor;

	/** The leftColor. */
	private Color leftColor;

	/** The rightColor. */
	private Color rightColor;

	/** The topColor. */
	private Color topColor;

	/** The insets. */
	private Object insets;

	/** The leftStyle. */
	private int leftStyle;

	/** The bottomStyle. */
	private int bottomStyle;

	/** The rightStyle. */
	private int rightStyle;

	/** The topStyle. */
	private int topStyle;

	/**
	 * <p>htmlInsetsIsVoid.</p>
	 * @return a {@link java.lang.Boolean} object.
	 */
	public boolean borderInfoIsVoid() {
		return topStyle == 0 && bottomStyle == 0 && leftStyle == 0 && rightStyle == 0;
	}
}
