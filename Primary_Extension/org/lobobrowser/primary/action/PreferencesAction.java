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

import java.awt.Frame;
import java.awt.event.ActionEvent;

import org.lobobrowser.primary.ext.ActionPool;
import org.lobobrowser.primary.ext.ComponentSource;
import org.lobobrowser.primary.gui.prefs.PreferencesDialog;
import org.lobobrowser.ua.NavigatorWindow;


// TODO: Auto-generated Javadoc
/**
 * The Class PreferencesAction.
 */
public class PreferencesAction extends ActionPool {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The window. */
	private NavigatorWindow window;

	/**
	 * Instantiates a new preferences action.
	 *
	 * @param componentSource
	 *            the component source
	 * @param window
	 *            the window
	 */
	public PreferencesAction(ComponentSource componentSource,
			NavigatorWindow window) {
		super(componentSource, window);
		this.window = window;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		showPreferences();
	}

	/**
	 * Show preferences.
	 */
	private void showPreferences() {
		java.awt.Window awtWindow = window.getAwtWindow();
		if (!(awtWindow instanceof Frame)) {
			throw new IllegalStateException(
					"Preferences dialog only supported when an AWT Frame is available.");
		}
		PreferencesDialog dialog = new PreferencesDialog((Frame) awtWindow);
		dialog.setTitle("Preferences");
		dialog.setLocationByPlatform(true);
		dialog.setResizable(false);
		dialog.setSize(new java.awt.Dimension(600, 400));
		dialog.setVisible(true);
	}

}
