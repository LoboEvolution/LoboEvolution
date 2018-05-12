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
import java.io.File;

import javax.swing.JFileChooser;

import org.loboevolution.primary.ext.ActionPool;
import org.loboevolution.primary.ext.ComponentSource;
import org.loboevolution.primary.settings.SearchEngine;
import org.loboevolution.store.SQLiteManager;
import org.loboevolution.ua.NavigatorWindow;
import org.loboevolution.ua.RequestType;

/**
 * The Class OpenFileAction.
 */
public class OpenFileAction extends ActionPool {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The component source. */
	private transient ComponentSource componentSource;

	/** The window. */
	private transient NavigatorWindow window;

	/**
	 * Instantiates a new open file action.
	 *
	 * @param componentSource
	 *            the component source
	 * @param window
	 *            the window
	 */
	public OpenFileAction(ComponentSource componentSource, NavigatorWindow window) {
		super(componentSource, window);
		this.componentSource = componentSource;
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
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnValue = fileChooser.showOpenDialog(window.getTopFrame().getComponent());
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			SQLiteManager sql = new SQLiteManager();
			componentSource.navigate(selectedFile.toURI().toString(), RequestType.PROGRAMMATIC);
			SearchEngine search = new SearchEngine();
			search.setName(selectedFile.getName());
			search.setDescription(selectedFile.getAbsolutePath());
			search.setType("OPEN");
			search.setSelected(true);
			sql.insertFileSelected(search);
		}
	}
}
