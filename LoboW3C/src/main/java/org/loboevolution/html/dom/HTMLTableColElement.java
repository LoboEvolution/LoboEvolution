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
 * Provides special properties (beyond the HTMLElement interface it also has
 * available to it inheritance) for manipulating single or grouped table column
 * elements.
 *
 *
 *
 */
public interface HTMLTableColElement extends HTMLElement {

	/**
	 * Sets or retrieves the alignment of the object relative to the display or
	 * table.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getAlign();

	/**
	 * <p>setAlign.</p>
	 *
	 * @param align a {@link java.lang.String} object.
	 */
	void setAlign(String align);

	/**
	 * <p>getCh.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getCh();

	/**
	 * <p>setCh.</p>
	 *
	 * @param ch a {@link java.lang.String} object.
	 */
	void setCh(String ch);

	/**
	 * <p>getChOff.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getChOff();

	/**
	 * <p>setChOff.</p>
	 *
	 * @param chOff a {@link java.lang.String} object.
	 */
	void setChOff(String chOff);

	/**
	 * Sets or retrieves the number of columns in the group.
	 *
	 * @return a int.
	 */
	int getSpan();

	/**
	 * <p>setSpan.</p>
	 *
	 * @param span a int.
	 */
	void setSpan(int span);

	/**
	 * <p>getvAlign.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getvAlign();

	/**
	 * <p>setvAlign.</p>
	 *
	 * @param vAlign a {@link java.lang.String} object.
	 */
	void setvAlign(String vAlign);

	/**
	 * Sets or retrieves the width of the object.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getWidth();

	/**
	 * <p>setWidth.</p>
	 *
	 * @param width a {@link java.lang.String} object.
	 */
	void setWidth(String width);

}
