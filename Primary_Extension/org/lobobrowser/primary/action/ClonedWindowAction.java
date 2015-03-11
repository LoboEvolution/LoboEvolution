/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.action;

import java.awt.event.ActionEvent;

import org.lobobrowser.primary.ext.ComponentSource;
import org.lobobrowser.ua.NavigationEntry;
import org.lobobrowser.ua.NavigatorWindow;


// TODO: Auto-generated Javadoc
/**
 * The Class ClonedWindowAction.
 */
public class ClonedWindowAction extends EnableableAction {

	/** The window. */
	private NavigatorWindow window;

	/**
	 * Instantiates a new cloned window action.
	 *
	 * @param componentSource
	 *            the component source
	 * @param window
	 *            the window
	 */
	public ClonedWindowAction(ComponentSource componentSource,
			NavigatorWindow window) {
		super(componentSource, window);
		this.window = window;
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * @see
	 * org.lobobrowser.primary.ext.ActionPool.EnableableAction#updateEnabling()
	 */
	@Override
	public void updateEnabling() {
		NavigationEntry entry = window.getCurrentNavigationEntry();
		this.setEnabled(entry != null && entry.getMethod().equals("GET"));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		NavigationEntry entry = window.getCurrentNavigationEntry();
		if (entry != null && entry.getMethod().equals("GET")) {
			window.getTopFrame().open(entry.getUrl());
		}
	}
}