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

package org.loboevolution.html.gui;

import java.util.EventObject;

/**
 * Event associated with {@link org.loboevolution.html.gui.SelectionChangeListener}.
 *
 *
 *
 */
public class SelectionChangeEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final boolean hasSelection;

	/**
	 * <p>Constructor for SelectionChangeEvent.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param hasSelection a boolean.
	 */
	public SelectionChangeEvent(Object source, boolean hasSelection) {
		super(source);
		this.hasSelection = hasSelection;
	}

	/**
	 * <p>isSelectionAvailable.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isSelectionAvailable() {
		return this.hasSelection;
	}
}
