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
import java.net.URL;

/**
 * <p>BackgroundInfo class.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BackgroundInfo {
	
	/** Constant BR_NO_REPEAT=1 */
	public static final int BR_NO_REPEAT = 1;
	
	/** Constant BR_REPEAT=0 */
	public static final int BR_REPEAT = 0;
	
	/** Constant BR_REPEAT_X=2 */
	public static final int BR_REPEAT_X = 2;
	
	/** Constant BR_REPEAT_Y=3 */
	public static final int BR_REPEAT_Y = 3;
	
	/** The backgroundColor*/
	private Color backgroundColor;

	/** The backgroundColor*/
	private URL backgroundImage;

	/** The backgroundColor*/
	@Builder.Default
	private int backgroundRepeat = BR_REPEAT;

	/** The backgroundColor*/
	private int backgroundXPosition;

	/** The backgroundColor*/
	private boolean backgroundXPositionAbsolute;

	/** The backgroundColor*/
	private int backgroundYPosition;

	/** The backgroundColor*/
	private boolean backgroundYPositionAbsolute;
}
