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
/*
 * Created on Mar 12, 2005
 */
package org.lobobrowser.main;

import java.awt.Canvas;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * Entry point class of the browser application.
 */
public class EntryPoint extends Canvas {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launches a browser window. If a browser instance is found to already
	 * exist, the new browser window is opened in the running application.
	 * <p>
	 * Note: To run without an "ext" directory (e.g. from the project source
	 * code in Eclipse) you need to set up the "ext.dirs" and/or "ext.files"
	 * properties.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			ReuseManager.getInstance().launch(args);
		} catch (Throwable err) {
			StringWriter swriter = new StringWriter();
			PrintWriter writer = new PrintWriter(swriter);
			err.printStackTrace(writer);
			writer.flush();
			JOptionPane.showMessageDialog(new JFrame(),
					"An unexpected error occurred during application startup:\r\n"
							+ swriter.toString(), "ERROR",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}
