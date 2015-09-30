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
package org.lobobrowser.lobo_testing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.lobobrowser.gui.BrowserPanel;

/**
 * The Class TestWindow.
 */
public class TestWindow extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The browser panel. */
	private final BrowserPanel browserPanel;

	public TestWindow() {
		super("Lobo Test...");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final BrowserPanel panel = new BrowserPanel();
		this.browserPanel = panel;
		this.getContentPane().add(panel);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				panel.windowClosing();
			}
		});
	}

	public static BrowserPanel newWindow() {
		TestWindow window = new TestWindow();
		window.setSize(700, 500);
		window.setVisible(true);
		return window.browserPanel;
	}
}
