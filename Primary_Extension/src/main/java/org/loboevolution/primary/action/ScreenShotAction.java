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
package org.loboevolution.primary.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.gui.FramePanel;
import org.loboevolution.primary.screenshot.ScreenShootFrame;
import org.loboevolution.ua.NavigationEntry;
import org.loboevolution.ua.NavigatorWindow;

/**
 * The Class ScreenShotAction.
 */
public class ScreenShotAction extends AbstractAction implements EnableableAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The window. */
	private NavigatorWindow window;

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
	public ScreenShotAction(NavigatorWindow window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		NavigationEntry entry = window.getCurrentNavigationEntry();
		if (entry != null) {
			FramePanel f = (FramePanel) window.getTopFrame();
			new ScreenShootFrame(f);
		}
	}

	@Override
	public void updateEnabling() {
		// Method not implemented
	}
}
