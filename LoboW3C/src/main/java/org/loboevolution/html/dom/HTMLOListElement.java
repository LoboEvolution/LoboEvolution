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

package org.loboevolution.html.dom;

/**
 * Provides special properties (beyond those defined on the regular HTMLElement
 * interface it also has available to it by inheritance) for manipulating
 * ordered list elements.
 *
 *
 *
 */
public interface HTMLOListElement extends HTMLElement {

	/**
	 * <p>isCompact.</p>
	 *
	 * @return a boolean.
	 */
	@Deprecated
	boolean isCompact();

	/**
	 * <p>setCompact.</p>
	 *
	 * @param compact a boolean.
	 */
	void setCompact(boolean compact);

	/**
	 * <p>isReversed.</p>
	 *
	 * @return a boolean.
	 */
	boolean isReversed();

	/**
	 * <p>setReversed.</p>
	 *
	 * @param reversed a boolean.
	 */
	void setReversed(boolean reversed);

	/**
	 * The starting number.
	 *
	 * @return a int.
	 */
	int getStart();

	/**
	 * <p>setStart.</p>
	 *
	 * @param start a int.
	 */
	void setStart(int start);

	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getType();

	/**
	 * <p>setType.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 */
	void setType(String type);

	/**
	 * <p>getCompact.</p>
	 *
	 * @return a boolean.
	 */
	boolean getCompact();

}
