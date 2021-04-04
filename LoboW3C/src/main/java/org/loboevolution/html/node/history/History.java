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

package org.loboevolution.html.node.history;

import org.loboevolution.jsenum.ScrollRestoration;

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
	 * @return a {@link org.loboevolution.jsenum.ScrollRestoration} object.
	 */
	ScrollRestoration getScrollRestoration();

	/**
	 * <p>setScrollRestoration.</p>
	 *
	 * @param scrollRestoration a {@link org.loboevolution.jsenum.ScrollRestoration} object.
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
