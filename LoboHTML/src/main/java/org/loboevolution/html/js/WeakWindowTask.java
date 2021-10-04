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

package org.loboevolution.html.js;

import java.awt.event.ActionListener;
import java.lang.ref.WeakReference;

abstract class WeakWindowTask implements ActionListener {
	private final WeakReference<WindowImpl> windowRef;

	/**
	 * <p>Constructor for WeakWindowTask.</p>
	 *
	 * @param window a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public WeakWindowTask(WindowImpl window) {
		this.windowRef = new WeakReference<>(window);
	}

	/**
	 * <p>getWindow.</p>
	 *
	 * @return a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	protected WindowImpl getWindow() {
		final WeakReference<WindowImpl> ref = this.windowRef;
		return ref == null ? null : ref.get();
	}
}
