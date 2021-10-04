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

package org.loboevolution.html;

import java.awt.Component;

/**
 * This interface should be implemented to provide OBJECT, EMBED or APPLET
 * functionality.
 *
 *
 *
 */
public interface HtmlObject {
	/**
	 * <p>destroy.</p>
	 */
	void destroy();

	/**
	 * <p>getComponent.</p>
	 *
	 * @return a {@link java.awt.Component} object.
	 */
	Component getComponent();

	/**
	 * Called as the object is layed out, either the first time it's layed out or
	 * whenever the DOM changes. This is where the object should reset its state
	 * based on element children or attributes and possibly change its preferred
	 * size if appropriate.
	 *
	 * @param availableWidth a int.
	 * @param availableHeight a int.
	 */
	void reset(int availableWidth, int availableHeight);

	/**
	 * <p>resume.</p>
	 */
	void resume();

	/**
	 * <p>suspend.</p>
	 */
	void suspend();
}
