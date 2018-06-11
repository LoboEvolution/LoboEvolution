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
package org.loboevolution.primary.gui.cleardata;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;

import org.loboevolution.primary.ext.ActionPool;
import org.loboevolution.primary.ext.ComponentSource;
import org.loboevolution.ua.NavigatorWindow;

/**
 * The Class ClearHistoryAction.
 */
public class ClearHistoryAction extends ActionPool {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The window. */
	private transient NavigatorWindow window;

	/**
	 * Instantiates a new clear cache action.
	 *
	 * @param componentSource
	 *            the component source
	 * @param window
	 *            the window
	 */
	public ClearHistoryAction(ComponentSource componentSource, NavigatorWindow window) {
		super(componentSource, window);
		this.window = window;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		showNavigationDataDialog();
	}
	
	/**
	 * Show preferences.
	 */
	private void showNavigationDataDialog() {
		Window awtWindow = window.getAwtWindow();
		if (!(awtWindow instanceof Frame)) {
			throw new IllegalStateException("Preferences dialog only supported when an AWT Frame is available.");
		}
		ClearHistoryDialog dialog = new ClearHistoryDialog((Frame) awtWindow);
		dialog.setTitle("Clear History");
		dialog.setLocationByPlatform(true);
		dialog.setResizable(false);
		dialog.setSize(new Dimension(170, 200));
		dialog.setVisible(true);
	}
}
