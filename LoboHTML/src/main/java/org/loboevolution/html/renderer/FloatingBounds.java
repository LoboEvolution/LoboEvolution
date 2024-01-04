/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
package org.loboevolution.html.renderer;

public interface FloatingBounds {
	/**
	 * The Y at which the float clears starting at the given Y.
	 *
	 * @param y a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	int getClearY(int y);

	/**
	 * The Y at which the first float clears. It does not recurse to check other
	 * floats.
	 *
	 * @param y a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	int getFirstClearY(int y);

	/**
	 * The offset from the left at the given Y, not counting insets.
	 *
	 * @param y a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	int getLeft(int y);

	/**
	 * <p>getLeftClearY.</p>
	 *
	 * @param y a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	int getLeftClearY(int y);

	/**
	 * <p>getMaxY.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getMaxY();

	/**
	 * The offset from the right at the given Y, not counting insets.
	 *
	 * @param y a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	int getRight(int y);

	/**
	 * <p>getRightClearY.</p>
	 *
	 * @param y a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	int getRightClearY(int y);
}
