/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.js;

import java.awt.event.ActionListener;
import java.lang.ref.WeakReference;

import org.loboevolution.html.js.object.Window;


/**
 * The Class WeakWindowTask.
 */
public abstract class WeakWindowTask implements ActionListener {

	/** The window ref. */
	private final WeakReference<Window> windowRef;

	/**
	 * Instantiates a new weak window task.
	 *
	 * @param window
	 *            the window
	 */
	public WeakWindowTask(Window window) {
		this.windowRef = new WeakReference<Window>(window);
	}

	/**
	 * Gets the window.
	 *
	 * @return the window
	 */
	protected Window getWindow() {
		WeakReference<Window> ref = this.windowRef;
		return ref == null ? null : (Window) ref.get();
	}
}
