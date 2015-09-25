/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project This
 * library is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library; if not, write
 * to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston,
 * MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net
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
 * The Class BrowserPanelNoSecurity.
 */
public class BrowserPanelNoSecurity extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws Exception {
		// Set Locale to Chinese.
		Locale.setDefault(Locale.CHINESE);

		// Initialize logging.
		PlatformInit.getInstance().initLogging(false);

		// Essential steps - we're not even initializing the look & feel.
		PlatformInit.getInstance().initProtocols();
		PlatformInit.getInstance().initExtensions();

		// Create frame with a specific size.
		JFrame frame = new BrowserPanelTest();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setVisible(true);
	}

	public BrowserPanelNoSecurity() throws Exception {
		this.setTitle("Lobo Demo - No Security!");
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
