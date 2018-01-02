/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.lobobrowser.primary.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.lobobrowser.primary.ext.ActionPool;
import org.lobobrowser.primary.ext.ComponentSource;
import org.lobobrowser.ua.NavigationEntry;
import org.lobobrowser.ua.NavigatorWindow;

/**
 * The Class ClonedWindowAction.
 */
public class ClonedWindowAction extends AbstractAction implements EnableableAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The window. */
	private NavigatorWindow window;

	/** The action. */
	private ActionPool action;

	/**
	 * Instantiates a new cloned window action.
	 *
	 * @param componentSource
	 *            the component source
	 * @param window
	 *            the window
	 * @param action
	 *            the action
	 */
	public ClonedWindowAction(ComponentSource componentSource, NavigatorWindow window, ActionPool action) {
		this.action = action;
		this.window = window;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.primary.ext.ActionPool.EnableableAction#updateEnabling()
	 */
	@Override
	public void updateEnabling() {
		NavigationEntry entry = window.getCurrentNavigationEntry();
		action.setEnabled(entry != null && entry.getMethod().equals("GET"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		NavigationEntry entry = window.getCurrentNavigationEntry();
		if (entry != null && entry.getMethod().equals("GET")) {
			window.getTopFrame().open(entry.getUrl());
		}
	}
}
