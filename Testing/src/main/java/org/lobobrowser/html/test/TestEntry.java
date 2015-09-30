/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Oct 22, 2005
 */
package org.lobobrowser.html.test;

import java.awt.Frame;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * The <code>TestEntry</code> class is a Java test program for the Cobra HTML
 * rendering engine. It displays a frame with a text field and three tabs. The
 * tabs show the renderered HTML, the HTML DOM represented as a JTree, and the
 * HTML source code.
 */
public class TestEntry {

	/**
	 * Instantiates a new test entry.
	 */
	private TestEntry() {
		super();
	}

	/**
	 * Test application entry point.
	 *
	 * @param args
	 *            Program arguments.
	 */
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {

				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}
		TestFrame frame = new TestFrame("Cobra Test Tool");
		frame.setSize(800, 400);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
}
