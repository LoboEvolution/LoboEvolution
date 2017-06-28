/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.html.gui;

import java.util.EventObject;

/**
 * Event associated with {@link SelectionChangeListener}.
 */
public class SelectionChangeEvent extends EventObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The has selection. */
	private final boolean hasSelection;

	/**
	 * Instantiates a new selection change event.
	 *
	 * @param source
	 *            the source
	 * @param hasSelection
	 *            the has selection
	 */
	public SelectionChangeEvent(Object source, boolean hasSelection) {
		super(source);
		this.hasSelection = hasSelection;
	}

	/**
	 * Checks if is selection available.
	 *
	 * @return true, if is selection available
	 */
	public boolean isSelectionAvailable() {
		return this.hasSelection;
	}
}
