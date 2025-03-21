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

package org.loboevolution.js;

/**
 * A screen, usually the one on which the current window is being rendered, and
 * is obtained using window.screen.
 */
public interface Screen {

	/**
	 * <p>getAvailHeight.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getAvailHeight();

	/**
	 * <p>getAvailWidth.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getAvailWidth();

	/**
	 * <p>getColorDepth.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getColorDepth();

	/**
	 * <p>getHeight.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getHeight();

	/**
	 * <p>getPixelDepth.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getPixelDepth();

	/**
	 * <p>getWidth.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getWidth();

}
