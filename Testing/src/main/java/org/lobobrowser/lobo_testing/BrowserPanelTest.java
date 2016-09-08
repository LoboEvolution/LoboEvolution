/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.lobo_testing;

import java.awt.event.WindowEvent;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import org.lobobrowser.gui.BrowserPanel;
import org.lobobrowser.main.PlatformInit;

/**
 * The Class BrowserPanelTest.
 */
public class BrowserPanelTest extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws Exception {
		// Set Locale to Chinese.
		Locale.setDefault(Locale.CHINESE);

		// Initialize logging.
		PlatformInit.getInstance().initLogging();

		// This step is necessary for extensions to work:
		PlatformInit.getInstance().init(false);

		// Create frame with a specific size.
		JFrame frame = new BrowserPanelTest();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setVisible(true);
	}

	public BrowserPanelTest() throws Exception {
		this.setTitle("Lobo Demo");
		final BrowserPanel bp = new BrowserPanel();
		bp.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(EtchedBorder.RAISED), "Embedded browser"));
		bp.navigate("http://www.google.com");
		this.getContentPane().add(bp);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// This needs to be called in order
				// to inform extensions about the
				// window closing.
				bp.windowClosing();
			}
		});
	}
}
