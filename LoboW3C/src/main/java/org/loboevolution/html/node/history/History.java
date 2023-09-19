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

package org.loboevolution.html.node.history;

import org.loboevolution.type.ScrollRestoration;

/**
 * Allows manipulation of the browser session history, that is the pages visited
 * in the tab or frame that the current page is loaded in.
 */
public interface History {

	/**
	 * <p>getLength.</p>
	 *
	 * @return a int.
	 */
	int getLength();

	/**
	 * <p>getScrollRestoration.</p>
	 *
	 * @return a {@link org.loboevolution.type.ScrollRestoration} object.
	 */
	ScrollRestoration getScrollRestoration();

	/**
	 * <p>setScrollRestoration.</p>
	 *
	 * @param scrollRestoration a {@link org.loboevolution.type.ScrollRestoration} object.
	 */
	void setScrollRestoration(ScrollRestoration scrollRestoration);

	/**
	 * <p>back.</p>
	 */
	void back();

	/**
	 * <p>forward.</p>
	 */
	void forward();

	/**
	 * <p>go.</p>
	 *
	 * @param delta a int.
	 */
	void go(int delta);

	/**
	 * <p>go.</p>
	 */
	void go();

	/**
	 * <p>pushState.</p>
	 *
	 * @param data a {@link java.lang.Object} object.
	 * @param title a {@link java.lang.String} object.
	 * @param url a {@link java.lang.String} object.
	 */
	void pushState(Object data, String title, String url);

	/**
	 * <p>pushState.</p>
	 *
	 * @param data a {@link java.lang.Object} object.
	 * @param title a {@link java.lang.String} object.
	 */
	void pushState(Object data, String title);

	/**
	 * <p>replaceState.</p>
	 *
	 * @param data a {@link java.lang.Object} object.
	 * @param title a {@link java.lang.String} object.
	 * @param url a {@link java.lang.String} object.
	 */
	void replaceState(Object data, String title, String url);

	/**
	 * <p>replaceState.</p>
	 *
	 * @param data a {@link java.lang.Object} object.
	 * @param title a {@link java.lang.String} object.
	 */
	void replaceState(Object data, String title);

}
