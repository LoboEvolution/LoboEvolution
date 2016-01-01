/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html;

import java.awt.Component;

/**
 * This interface should be implemented to provide OBJECT, EMBED or APPLET
 * functionality.
 */
public interface HtmlObject {

	/**
	 * Gets the component.
	 *
	 * @return the component
	 */
	Component getComponent();

	/**
	 * Suspend.
	 */
	void suspend();

	/**
	 * Resume.
	 */
	void resume();

	/**
	 * Destroy.
	 */
	void destroy();

	/**
	 * Called as the object is layed out, either the first time it's layed out
	 * or whenever the DOM changes. This is where the object should reset its
	 * state based on element children or attributes and possibly change its
	 * preferred size if appropriate.
	 *
	 * @param availableWidth
	 *            the available width
	 * @param availableHeight
	 *            the available height
	 */
	void reset(int availableWidth, int availableHeight);
}
